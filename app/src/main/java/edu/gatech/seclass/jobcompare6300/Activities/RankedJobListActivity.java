package edu.gatech.seclass.jobcompare6300.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.gatech.seclass.jobcompare6300.Entities.Job;
import edu.gatech.seclass.jobcompare6300.R;

public class RankedJobListActivity extends AppCompatActivity {

    DecimalFormat cur = new DecimalFormat("$ #,###");
    DecimalFormat rsu = new DecimalFormat("#,###");

    private TextView textCompany1;
    private TextView textCompany2;
    private TextView textTitle1;
    private TextView textTitle2;

    private TextView textLocation1;
    private TextView textLocation2;

    private TextView textSalary1;
    private TextView textSalary2;

    private TextView textBonus1;
    private TextView textBonus2;

    private TextView textRSU1;
    private TextView textRSU2;

    private TextView textRelocationStipend1;
    private TextView textRelocationStipend2;

    private TextView textPCH1;
    private TextView textPCH2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_comparison);

        Intent intent = getIntent();

        textCompany1 = (TextView)findViewById(R.id.offerCompany1);
        textCompany2 = (TextView)findViewById(R.id.offerCompany2);
        textTitle1 = (TextView)findViewById(R.id.offerTitle1);
        textTitle2 = (TextView)findViewById(R.id.offerTitle2);
        textLocation1 = (TextView)findViewById(R.id.offerLocation1);
        textLocation2 = (TextView)findViewById(R.id.offerLocation2);
        textSalary1 = (TextView)findViewById(R.id.offerSalary1);
        textSalary2 = (TextView)findViewById(R.id.offerSalary2);
        textBonus1 = (TextView)findViewById(R.id.offerBonus1);
        textBonus2 = (TextView)findViewById(R.id.offerBonus2);
        textRSU1 = (TextView)findViewById(R.id.offerRSU1);
        textRSU2 = (TextView)findViewById(R.id.offerRSU2);
        textRelocationStipend1 = (TextView)findViewById(R.id.offerRelocationStipend1);
        textRelocationStipend2 = (TextView)findViewById(R.id.offerRelocationStipend2);
        textPCH1 = (TextView)findViewById(R.id.offerPCH1);
        textPCH2 = (TextView)findViewById(R.id.offerPCH2);

        boolean compareWithCurrent = intent.getBooleanExtra("compareWithCurrentJob",false);
        ArrayList<Job> sortedJobs = intent.getParcelableArrayListExtra("sortedJobs");

        if(compareWithCurrent) {
            Job newlyEnteredJobOffer = intent.getParcelableExtra("jobOffer");
            compareWithCurrentJob(newlyEnteredJobOffer);
        } else {
            compareSelectedJobs(sortedJobs);
        }
    }

    public void returnToMain(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void returnToJobComparison(View view) {
        Intent intent = new Intent(getApplicationContext(), JobComparisonActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void compareSelectedJobs(ArrayList<Job> jobs) {
        int counter = 0;
        for(Job job: jobs) {
            if(job.getIsSelected()) {
                setJobCompareTable(job,counter);
                counter += 1;
            }
        }
    }

    private void setJobCompareTable(Job job, int counter) {
        if(counter == 0) {
            textCompany1.setText(job.getCompany() + (job.getIsCurrJob() ? " (Current)" : ""));
            textTitle1.setText(job.getTitle());
            textLocation1.setText(job.getCity().concat(", ").concat(job.getState()));
            textSalary1.setText(cur.format(Integer.parseInt(String.valueOf(job.getYearlySalary()))));
            textBonus1.setText(cur.format(Integer.parseInt(String.valueOf(job.getYearlyBonus()))));
            textRSU1.setText(rsu.format(Integer.parseInt(String.valueOf(job.getRestrictedStockUnitAward()))));
            textRelocationStipend1.setText(cur.format(Integer.parseInt(String.valueOf(job.getRelocationStipend()))));
            textPCH1.setText(String.valueOf(job.getPersonalChoiceHolidays()).concat(" days"));

        } else if(counter == 1) {
            textCompany2.setText(job.getCompany() + (job.getIsCurrJob() ? " (Current)" : ""));
            textTitle2.setText(job.getTitle());
            textLocation2.setText(job.getCity().concat(", ").concat(job.getState()));
            textSalary2.setText(cur.format(Integer.parseInt(String.valueOf(job.getYearlySalary()))));
            textBonus2.setText(cur.format(Integer.parseInt(String.valueOf(job.getYearlyBonus()))));
            textRSU2.setText(rsu.format(Integer.parseInt(String.valueOf(job.getRestrictedStockUnitAward()))));
            textRelocationStipend2.setText(cur.format(Integer.parseInt(String.valueOf(job.getRelocationStipend()))));
            textPCH2.setText(String.valueOf(job.getPersonalChoiceHolidays()).concat(" days"));
        }
    }

    private void compareWithCurrentJob(Job job) {
        textCompany1.setText(JobComparisonActivity.currentJob.getCompany() + " (Current)");
        textTitle1.setText(JobComparisonActivity.currentJob.getTitle());
        textLocation1.setText(JobComparisonActivity.currentJob.getCity().concat(", ").concat(JobComparisonActivity.currentJob.getState()));
        textSalary1.setText(cur.format(Integer.parseInt(String.valueOf(JobComparisonActivity.currentJob.getYearlySalary()))));
        textBonus1.setText(cur.format(Integer.parseInt(String.valueOf(JobComparisonActivity.currentJob.getYearlyBonus()))));
        textRSU1.setText(rsu.format(Integer.parseInt(String.valueOf(JobComparisonActivity.currentJob.getRestrictedStockUnitAward()))));
        textRelocationStipend1.setText(cur.format(Integer.parseInt(String.valueOf(JobComparisonActivity.currentJob.getRelocationStipend()))));
        textPCH1.setText(String.valueOf(JobComparisonActivity.currentJob.getPersonalChoiceHolidays()).concat(" days"));

        textCompany2.setText(job.getCompany());
        textTitle2.setText(job.getTitle());
        textLocation2.setText(job.getCity().concat(", ").concat(job.getState()));
        textSalary2.setText(cur.format(Integer.parseInt(String.valueOf(job.getYearlySalary()))));
        textBonus2.setText(cur.format(Integer.parseInt(String.valueOf(job.getYearlyBonus()))));
        textRSU2.setText(rsu.format(Integer.parseInt(String.valueOf(job.getRestrictedStockUnitAward()))));
        textRelocationStipend2.setText(cur.format(Integer.parseInt(String.valueOf(job.getRelocationStipend()))));
        textPCH2.setText(String.valueOf(job.getPersonalChoiceHolidays()).concat(" days"));
    }
}
