package edu.gatech.seclass.jobcompare6300.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import edu.gatech.seclass.jobcompare6300.Entities.ComparisonSetting;
import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.ViewModels.AppViewModel;


public class ComparisonSettingActivity extends AppCompatActivity {
    private EditText salaryWghtInput;
    private EditText bonusWghtInput;
    private EditText rsuWghtInput;
    private EditText reloStipendWghtInput;
    private EditText ptoDaysWghtInput;
    private Button compSettSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison_setting);

        salaryWghtInput = (EditText) findViewById(R.id.salaryWghtInputID);
        bonusWghtInput = (EditText) findViewById(R.id.bonusWghtInputID);
        rsuWghtInput = (EditText) findViewById(R.id.rsuWghtInputID);
        reloStipendWghtInput = (EditText) findViewById(R.id.reloStipendWghtInputID);
        ptoDaysWghtInput = (EditText) findViewById(R.id.ptoDaysWghtInputID);
        compSettSaveButton = (Button) findViewById(R.id.compSettSaveButtonID);

        if (JobComparisonActivity.comparisonSetting != null) { // if table is populated, show in textviews
            displayComparisonSettings(JobComparisonActivity.comparisonSetting);
        }
        else { // if table is null (only true on 1st open), populate with default vals (1)
            ComparisonSetting defaultVals = new ComparisonSetting(1, 1, 1, 1, 1, 1);
            displayComparisonSettings(defaultVals);
        }

        AppViewModel appViewmodel = new ViewModelProvider(this).get(AppViewModel.class);
        compSettSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current weight data from the UI
                int salaryWeight = Integer.parseInt(salaryWghtInput.getText().toString());
                int bonusWeight = Integer.parseInt(bonusWghtInput.getText().toString());
                int rsuWeight = Integer.parseInt(rsuWghtInput.getText().toString());
                int reloStipendWeight = Integer.parseInt(reloStipendWghtInput.getText().toString());
                int ptoDaysWeight = Integer.parseInt(ptoDaysWghtInput.getText().toString());


                if (salaryWeight < 0 || bonusWeight < 0 || rsuWeight < 0 || reloStipendWeight < 0 || ptoDaysWeight < 0){
                    Toast.makeText(getApplicationContext(), "Weight must be a positive number", Toast.LENGTH_SHORT).show();
                } else{
                    // Create comparison setting object and insert that into comparison_setting table
                    ComparisonSetting comparisonSetting = new ComparisonSetting(1, salaryWeight, bonusWeight, rsuWeight, reloStipendWeight, ptoDaysWeight);
                    JobComparisonActivity.comparisonSetting = comparisonSetting;
                    appViewmodel.insertSettings(comparisonSetting);

                    // return to main menu after save
                    finish();
                }
            }
        });
    }

    public void returnToMain(View view) {
        finish();
    }

    public void displayComparisonSettings(ComparisonSetting comparisonSetting) {
        salaryWghtInput.setText(String.valueOf(comparisonSetting.getSalaryWght()));
        bonusWghtInput.setText(String.valueOf(comparisonSetting.getBonusWght()));
        rsuWghtInput.setText(String.valueOf(comparisonSetting.getRsuWght()));
        reloStipendWghtInput.setText(String.valueOf(comparisonSetting.getReloWght()));
        ptoDaysWghtInput.setText(String.valueOf(comparisonSetting.getPtoWght()));
    }
}
