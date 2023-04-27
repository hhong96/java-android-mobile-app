package edu.gatech.seclass.jobcompare6300.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Optional;

import androidx.lifecycle.ViewModelProvider;

import edu.gatech.seclass.jobcompare6300.Entities.Job;
import edu.gatech.seclass.jobcompare6300.JobInterface;
import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.ViewModels.AppViewModel;


public class CurrentJobActivity extends AppCompatActivity implements JobInterface {
    private EditText textTitle;
    private EditText textCompany;
    private EditText textCity;
    private EditText textState;
    private EditText textCostOfLiving;
    private EditText textSalary;
    private EditText textBonus;
    private EditText textRSU;
    private EditText textReloc;
    private EditText textPTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_current_job_info);

        textTitle = (EditText)findViewById(R.id.currTitleText);
        textCompany = (EditText)findViewById(R.id.currCompanyText);
        textCity = (EditText)findViewById(R.id.currCityText);
        textState = (EditText)findViewById(R.id.currStateText);
        textCostOfLiving = (EditText)findViewById(R.id.currCostOfLivingText);
        textSalary = (EditText)findViewById(R.id.currSalaryText);
        textBonus = (EditText)findViewById(R.id.currBonusText);
        textRSU = (EditText)findViewById(R.id.currRsuText);
        textReloc = (EditText)findViewById(R.id.currRelocText);
        textPTO = (EditText)findViewById(R.id.currPtoText);

        if(JobComparisonActivity.currentJob != null) {
            setCurrentJobFields(JobComparisonActivity.currentJob);
        }
    }

    public void cancelJobEntry(View view) {
        finish();
    }

    @Override
    public void saveJobEntry(View view) {
        Optional<Job> jobOfferOpt = createJob(textTitle, textCompany, textPTO,
                textReloc, textCity, textState,
                textCostOfLiving, textSalary, textBonus,
                textRSU);

        if (jobOfferOpt.isPresent()) {
            AppViewModel appViewmodel = new ViewModelProvider(this).get(AppViewModel.class);

            Job job = jobOfferOpt.get();
            job.setIsCurrJob(true);

            if(JobComparisonActivity.currentJob != null) {
                appViewmodel.updateCurrJob(job.getTitle(), job.getCompany(), job.getCity(),
                        job.getState(), job.getCostOfLiving(), job.getYearlySalary(),
                        job.getYearlyBonus(), job.getRestrictedStockUnitAward(),
                        job.getRelocationStipend(), job.getPersonalChoiceHolidays());
                JobComparisonActivity.currentJob.updateJob(job);
            } else {
                appViewmodel.insertJobs(job);
                JobComparisonActivity.currentJob = job;

                if(JobComparisonActivity.jobList == null) {
                    JobComparisonActivity.jobList = new ArrayList<>();
                }
                JobComparisonActivity.jobList.add(JobComparisonActivity.currentJob);
            }

            finish();
        }
    }

    private void setCurrentJobFields(Job job) {
        textTitle.setText(job.getTitle());
        textCompany.setText(job.getCompany());
        textCity.setText(job.getCity());
        textState.setText(job.getState());
        textCostOfLiving.setText(job.getCostOfLiving() + "");
        textSalary.setText(job.getYearlySalary() + "");
        textBonus.setText(job.getYearlyBonus() + "");
        textRSU.setText(job.getRestrictedStockUnitAward() + "");
        textReloc.setText(job.getRelocationStipend() + "");
        textPTO.setText((job.getPersonalChoiceHolidays() + ""));
    }
}
