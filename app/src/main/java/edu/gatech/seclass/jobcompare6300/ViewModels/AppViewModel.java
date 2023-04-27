package edu.gatech.seclass.jobcompare6300.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.gatech.seclass.jobcompare6300.DBRepos.AppRepo;
import edu.gatech.seclass.jobcompare6300.Entities.ComparisonSetting;
import edu.gatech.seclass.jobcompare6300.Entities.Job;

// Citations:
//      https://developer.android.com/codelabs/android-room-with-a-view#9
public class AppViewModel extends AndroidViewModel {
    AppRepo repository;
    LiveData<ComparisonSetting> comparisonSettingList;
    LiveData<List<Job>> jobList;
    LiveData<Job> currJob;

    public AppViewModel(Application application) {
        super(application);
        repository = new AppRepo(application);
        comparisonSettingList = repository.getSettings();
        jobList = repository.getJobs();
        currJob = repository.getCurrJob();
    }

    public LiveData<ComparisonSetting> getAllSettings(){
        return comparisonSettingList;
    }
    public LiveData<List<Job>> getAllJobs(){
        return jobList;
    }
    public LiveData<Job> getCurrJob(){
        return currJob;
    }

    public void insertSettings(ComparisonSetting comparisonSetting){
        repository.insertSettings(comparisonSetting);
    }

    public void updateCurrJob(String title, String company, String city, String state, Integer col, Integer salary, Integer bonus, Integer rsu, Integer relo, Integer pto) {
        repository.updateCurrJob(title, company, city, state, col, salary, bonus, rsu, relo, pto);
    }

    public void insertJobs(Job job){
        repository.insertJobs(job);
    }
}
