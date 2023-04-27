package edu.gatech.seclass.jobcompare6300;

import android.view.View;
import android.widget.EditText;

import java.util.Optional;

import edu.gatech.seclass.jobcompare6300.Entities.Job;

public interface JobInterface {
     abstract void saveJobEntry(View view);

     default Optional<Job> createJob(EditText textTitle, EditText textCompany, EditText textPTO,
                                     EditText textReloc, EditText textCity, EditText textState,
                                     EditText textCostOfLiving, EditText textSalary,
                                     EditText textBonus, EditText textRSU) {
        boolean isValid = true;

        String title = textTitle.getText().toString().trim();
        if(title.isEmpty()) {
            CharSequence errorMessage = "Title cannot be empty";
            textTitle.setError(errorMessage);
            isValid = false;
        }

        String company = textCompany.getText().toString().trim();
        if(company.isEmpty()) {
            CharSequence errorMessage = "Company cannot be empty";
            textCompany.setError(errorMessage);
            isValid = false;
        }

        String pto = textPTO.getText().toString().trim();
        if(!isPTOValid(pto)) {
            CharSequence errorMessage = "Personal Choice Holidays must be between 0 and 20 days";
            textPTO.setError(errorMessage);
            isValid = false;
        }

        String reloc = textReloc.getText().toString().trim();
        if(!isRelocValid(reloc)) {
            CharSequence errorMessage = "Relocation stipend must be between $0 and $25,000";
            textReloc.setError(errorMessage);
            isValid = false;
        }

         String costOfLiving = textCostOfLiving.getText().toString().trim();
        if(!isCostOfLivingValid(costOfLiving)) {
            CharSequence errorMessage = "Cost of living must be an integer greater than 0";
            textCostOfLiving.setError(errorMessage);
            isValid = false;
        }

        if(isValid) {
            String city = textCity.getText().toString().trim();
            String state = textState.getText().toString().trim();
            String salary = textSalary.getText().toString().trim();
            String bonus = textBonus.getText().toString().trim();
            String rsu = textRSU.getText().toString().trim();

            int costOfLivingInt = parseStringToInt(costOfLiving);
            int salaryInt = parseStringToInt(salary);
            int bonusInt = parseStringToInt(bonus);
            int rsuInt = parseStringToInt(rsu);
            int relocInt = parseStringToInt(reloc);
            int ptoInt = parseStringToInt(pto);

            // need to add to jobList
            return Optional.of(new Job(title, company, city, state,
                                        costOfLivingInt, salaryInt, bonusInt, rsuInt,
                                        relocInt, ptoInt));
        }

        return Optional.empty();
    }

    private boolean isPTOValid(String s) {
        if(s.isEmpty()) {
            return false;
        }

        try {
            int x = Integer.parseInt(s);
            return x >= 0 && x <= 20;
        } catch(NumberFormatException ex) {
            return false;
        }
    }

    private boolean isRelocValid(String s) {
        if(s.isEmpty()) {
            return false;
        }

        try {
            int x = Integer.parseInt(s);
            return x >= 0 && x <= 25000;
        } catch(NumberFormatException ex) {
            return false;
        }
    }

    private boolean isCostOfLivingValid(String s) {
        if(s.isEmpty()) {
            return false;
        }

        try {
            int x = Integer.parseInt(s);
            return x > 0;
        } catch(NumberFormatException ex) {
            return false;
        }
    }

    private int parseStringToInt(String s) {
        return !s.isEmpty() ? Integer.parseInt(s) : 0;
    }
}
