package edu.gatech.seclass.jobcompare6300.DBRepos;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.gatech.seclass.jobcompare6300.Entities.ComparisonSetting;
import edu.gatech.seclass.jobcompare6300.DAOs.AppDAO;
import edu.gatech.seclass.jobcompare6300.DBClasses.AppDB;
import edu.gatech.seclass.jobcompare6300.Entities.Job;

// Citations:
//      https://developer.android.com/codelabs/android-room-with-a-view#8
public class AppRepo {
    AppDAO appDAO;

    public AppRepo(Application application){
        AppDB db = AppDB.getAppDB(application);
        appDAO = db.appDAO();
    }

    public LiveData<ComparisonSetting> getSettings(){
        return appDAO.getAllSettings();
    }

    public void insertSettings(ComparisonSetting comparisonSetting) {
        AppDB.databaseWriteExecutor.execute(() -> appDAO.insertSettings(comparisonSetting));
    }

    public LiveData<List<Job>> getJobs(){
        return appDAO.getAllJobs();
    }

    public LiveData<Job> getCurrJob(){
        return appDAO.getCurrJob();
    }

    public void updateCurrJob(String title, String company, String city, String state, Integer col, Integer salary, Integer bonus, Integer rsu, Integer relo, Integer pto) {
        AppDB.databaseWriteExecutor.execute(() -> appDAO.updateCurrentJob(title, company, city, state, col, salary, bonus, rsu, relo, pto));
    }

    public void insertJobs(Job job) {
        AppDB.databaseWriteExecutor.execute(() -> appDAO.insertJobs(job));
    }
}
