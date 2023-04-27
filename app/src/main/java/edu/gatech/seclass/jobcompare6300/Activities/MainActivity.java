package edu.gatech.seclass.jobcompare6300.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.jobcompare6300.Entities.ComparisonSetting;
import edu.gatech.seclass.jobcompare6300.Entities.Job;
import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.ViewModels.AppViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppViewModel appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        appViewModel.getAllSettings().observe(this, new Observer<ComparisonSetting>() {
            @Override
            public void onChanged(ComparisonSetting comparisonSetting) {
                if (comparisonSetting == null) {
                    // if table is null (only true on 1st open), populate with default vals (1)
                    ComparisonSetting defaultVals = new ComparisonSetting(1,1, 1, 1, 1, 1);
                    appViewModel.insertSettings(defaultVals);
                } else {
                    JobComparisonActivity.comparisonSetting = comparisonSetting;
                }
            }
        });

        // if there are less than two jobs entered, disable compare job menu
        appViewModel.getAllJobs().observe(this, new Observer<List<Job>>() {
            Button compareJobButton = findViewById(R.id.compareJobButton);
            @Override
            public void onChanged(@Nullable List<Job> jobs) {
                if(jobs != null && jobs.size() < 2) {
                    compareJobButton.setEnabled(false);
                } else {
                    compareJobButton.setEnabled(true);
                }
            }
        });

        // Set current job if stored in DB
        appViewModel.getCurrJob().observe(this, new Observer<Job>() {
            @Override
            public void onChanged(Job job) {
                if (job != null) {
                    JobComparisonActivity.currentJob = job;
                }
            }
        });

        appViewModel.getAllJobs().observe(this, new Observer<List<Job>>() {
            @Override
            public void onChanged(@Nullable List<Job> jobs) {
                if (jobs.size() > 0) {
                    JobComparisonActivity.jobList = new ArrayList<>();

                    for (int i = 0; i <= jobs.size() - 1; i++) {
                        Job job = new Job(jobs.get(i).getTitle(), jobs.get(i).getCompany(),
                                jobs.get(i).getCity(), jobs.get(i).getState(),
                                jobs.get(i).getCostOfLiving(), jobs.get(i).getYearlySalary(),
                                jobs.get(i).getYearlyBonus(), jobs.get(i).getRestrictedStockUnitAward(),
                                jobs.get(i).getRelocationStipend(), jobs.get(i).getPersonalChoiceHolidays());

                        if (jobs.get(i).getIsCurrJob()) {
                            job.setIsCurrJob(true);
                        }

                        JobComparisonActivity.jobList.add(job);
                    }
                }
            }
        });
    }

    public void updateCurrentJob(View view) {
        Intent i = new Intent(getApplicationContext(), CurrentJobActivity.class);
        startActivity(i);
    }

    public void addJob(View view) {
        Intent i = new Intent(getApplicationContext(), JobOfferActivity.class);
        startActivity(i);
    }

    public void adjustComparisonSetting(View view) {
        Intent i = new Intent(getApplicationContext(), ComparisonSettingActivity.class);
        startActivity(i);
    }

    public void rankJobOffers(View view) {
        Intent i = new Intent(getApplicationContext(), JobComparisonActivity.class);
        startActivity(i);
    }
}
