package edu.gatech.seclass.jobcompare6300.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import edu.gatech.seclass.jobcompare6300.Entities.ComparisonSetting;
import edu.gatech.seclass.jobcompare6300.Entities.Job;
import edu.gatech.seclass.jobcompare6300.R;

public class JobComparisonActivity extends AppCompatActivity {
    LinearLayout layout;
    // move 3 variables to static utility class; currentJob comes up as null even after it's saved
    public static Job currentJob;
    public static ArrayList<Job> jobList;
    public static ComparisonSetting comparisonSetting;
    public static ArrayList<Job> sortedJobs;
    private List<Boolean> selectedButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranked_job_list);

        selectedButtons = new ArrayList<>();
        layout = findViewById(R.id.container);

        sortedJobs = compareJobOffers(JobComparisonActivity.jobList, comparisonSetting);
        for (int i = 0; i < sortedJobs.size(); i++) {
            Job job = sortedJobs.get(i);
            job.setIsSelected(false);
            selectedButtons.add(false);
            addJobCard(job, i);
        }
    }

    public void returnToMainMenu(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void addJobCard(Job job, int i) {
        final View view = getLayoutInflater().inflate(R.layout.job_card, null);
        TextView nameView = view.findViewById(R.id.name);
        Button select = view.findViewById(R.id.select);
        Boolean isSelected = false;
        if(job.getIsCurrJob()) {
            view.setBackgroundColor(getResources().getColor(R.color.currentJob));
        }

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selectedButtons.get(i)) {
                    if (!moreThanTwoSelected(selectedButtons)) {
                        select.setText("SELECTED");
                        select.setBackgroundColor(getResources().getColor(R.color.colorStateSelected));
                        selectedButtons.set(i, !selectedButtons.get(i));
                        job.setIsSelected(true);
                    } else {
                        Context context = getApplicationContext();
                        CharSequence text = "Two jobs selected. Unselect one!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                } else {
                    select.setText("SELECT");
                    select.setBackgroundColor(getResources().getColor(R.color.purple_500));
                    selectedButtons.set(i, !selectedButtons.get(i));
                    job.setIsSelected(false);
                }
            }
        });

        nameView.setText(job.toString());
        layout.addView(view);
    }

    static boolean moreThanTwoSelected(List<Boolean> selectedButtons) {
        return selectedButtons.stream().filter(p -> p).count() >= 2;
    }

    static boolean twoSelected(List<Boolean> selectedButtons) {
        return selectedButtons.stream().filter(p -> p).count() >= 2;
    }

    public void rankedJobs(View view) {
        if(twoSelected(selectedButtons)) {
            Intent intent = new Intent(getBaseContext(), RankedJobListActivity.class);
            intent.putParcelableArrayListExtra("sortedJobs",
                    (ArrayList<? extends Parcelable>)sortedJobs.stream()
                            .filter(job -> job.getIsSelected()).collect(Collectors.toList()));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Select two jobs to compare!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public static double computeJobScore(Job job, ComparisonSetting weights){
        double score;

        // changed salary, bonus to cost of living adjusted salary, cost of living adjusted bonus
        // https://edstem.org/us/courses/30997/discussion/2527721?comment=5838428
        score = (job.getYearlySalary() * 100 / job.getCostOfLiving() * weights.getSalaryWght() // ((salary * 100 / COL) * salary weight
                + job.getYearlyBonus() / job.getCostOfLiving() * weights.getBonusWght() // (bonus * 100 / COL) * bonus weight
                + (job.getRestrictedStockUnitAward() / 4.0) * weights.getRsuWght() // RSU * RSU weight
                + job.getRelocationStipend() * weights.getReloWght() // RELO * RELO weight
                + (job.getPersonalChoiceHolidays() * job.getYearlySalary() * 100 / job.getCostOfLiving() / 260.0) * weights.getPtoWght()) // + (PTO * (salary * 100 / COL) / 260) * weight)
                / weights.getWeightSum(); // / (sum of all weights)

        return score;
    }

    public static ArrayList<Job> compareJobOffers(List<Job> jobs, ComparisonSetting weights) {
        List<JobTuple> jobTuplesList = new ArrayList<>();

        ArrayList<Job> sortedJobs = new ArrayList<>();
        for (Job job : jobs) {
            double score = computeJobScore(job, weights);
            job.setJobScore(score);

            // add Job and score pair to tuple
            jobTuplesList.add(new JobTuple(job, score));
        }

        // sort first by score then by title... can make the string compare title+company for uniqueness
        List<JobTuple> sortedJobTuples = jobTuplesList.stream().sorted((a, b) -> {
            double scoreCompare = Double.compare(b.jobScore, a.jobScore);
            if(scoreCompare == 0) {
                return a.job.toString().compareToIgnoreCase(b.job.toString());
            }
            return (int)scoreCompare;
        }).collect(Collectors.toList());

        sortedJobs = (ArrayList<Job>)sortedJobTuples.stream().map(JobTuple::getJob).collect(Collectors.toList());

        return sortedJobs;
    }

    // inner class to store tuple
    static class JobTuple {
        Job job;
        double jobScore;

        JobTuple(Job job, double jobScore) {
            this.job = job;
            this.jobScore = jobScore;
        }

        public Job getJob() {
            return job;
        }
    }
}
