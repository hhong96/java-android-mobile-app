package edu.gatech.seclass.jobcompare6300.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Optional;

import edu.gatech.seclass.jobcompare6300.Entities.Job;
import edu.gatech.seclass.jobcompare6300.JobInterface;
import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.ViewModels.AppViewModel;

public class JobOfferActivity extends AppCompatActivity implements JobInterface {
    private Job jobOffer;
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
        setContentView(R.layout.activity_enter_job_info);

        textTitle = (EditText)findViewById(R.id.titleText);
        textCompany = (EditText)findViewById(R.id.companyText);
        textCity = (EditText)findViewById(R.id.cityText);
        textState = (EditText)findViewById(R.id.stateText);
        textCostOfLiving = (EditText)findViewById(R.id.costOfLivingText);
        textSalary = (EditText)findViewById(R.id.salaryText);
        textBonus = (EditText)findViewById(R.id.bonusText);
        textRSU = (EditText)findViewById(R.id.rsuText);
        textReloc = (EditText)findViewById(R.id.relocText);
        textPTO = (EditText)findViewById(R.id.ptoText);
    }

    public void returnToMainMenu(View view) {
        finish();
    }

    public void cancelJobEntry(View view) {
        clearFields();
    }

    @Override
    public void saveJobEntry(View view) {
        Optional<Job> jobOfferOpt = createJob(textTitle, textCompany, textPTO,
                                              textReloc, textCity, textState,
                                              textCostOfLiving, textSalary, textBonus,
                                              textRSU);
        if(jobOfferOpt.isPresent()) {
            jobOffer = jobOfferOpt.get();

            AppViewModel appViewmodel = new ViewModelProvider(this).get(AppViewModel.class);
            appViewmodel.insertJobs(jobOffer);

            if(JobComparisonActivity.jobList == null) {
                JobComparisonActivity.jobList = new ArrayList<>();
            }
            JobComparisonActivity.jobList.add(jobOffer);

            showSavedDialog();
        }
    }

    public void addAnotherOffer(View view) {
        saveJobEntry(view);
        clearFields();
    }

    public void compareToCurrentJob(View view) {
        if(jobOffer == null || JobComparisonActivity.currentJob == null) {
            showJobNotSetDialog();
        } else {
            boolean compareWithCurrentJob = true;
            Intent i = new Intent(getBaseContext(), RankedJobListActivity.class);
            i.putExtra("compareWithCurrentJob", compareWithCurrentJob);
            i.putExtra("jobOffer", jobOffer);
            startActivity(i);
        }
    }

    private void clearFields() {
        textTitle.setText("");
        textCompany.setText("");
        textCity.setText("");
        textState.setText("");
        textCostOfLiving.setText("");
        textSalary.setText("");
        textBonus.setText("");
        textRSU.setText("");
        textReloc.setText("");
        textPTO.setText((""));
    }

    private void showSavedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(JobOfferActivity.this);
        builder.setTitle("Job Offer");
        builder.setMessage("Job offer saved!");

        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showJobNotSetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(JobOfferActivity.this);
        builder.setTitle("Check Job Info");

        if(JobComparisonActivity.currentJob == null) {
            builder.setMessage("Current job may not be set; please set current job then retry.");
        } else if(jobOffer == null) {
            builder.setMessage("Please save job offer and retry.");
        }
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
