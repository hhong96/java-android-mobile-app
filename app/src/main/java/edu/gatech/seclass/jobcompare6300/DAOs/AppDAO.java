package edu.gatech.seclass.jobcompare6300.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Insert;

import java.util.List;

import edu.gatech.seclass.jobcompare6300.Entities.ComparisonSetting;
import edu.gatech.seclass.jobcompare6300.Entities.Job;

// Citations:
//      https://developer.android.com/training/data-storage/room/accessing-data
@Dao
public interface AppDAO {
    @Query("SELECT * FROM comparison_setting LIMIT 1")
    LiveData<ComparisonSetting> getAllSettings();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSettings(ComparisonSetting... settings);

//        changed salary, bonus to cost of living adjusted salary, cost of living adjusted bonus
//        https://edstem.org/us/courses/30997/discussion/2527721?comment=5838428
    @Query(
            "SELECT * FROM jobs j cross join comparison_setting c " +
                    "order by ((j.salary * 100 / j.col) * c.salary_wght " + // ((salary * 100 / COL) * salary weight +
                    "+ (j.bonus * 100 / j.col) * c.bonus_wght " + // (bonus * 100 / COL) * bonus weight +
                    "+ j.rsu/4 * c.rsu_wght " + // RSU * RSU weight +
                    "+ j.reloc * c.relo_wght " + // RELO * RELO weight +
                    "+ j.pto * (j.salary * 100 / j.col) / 260 * c.pto_wght) " + // (PTO * (salary * 100 / COL) / 260) * weight)
                    "/ (c.salary_wght + c.bonus_wght + c.rsu_wght + c.relo_wght + c.pto_wght) desc" // / (sum of all weights)
    )
    LiveData<List<Job>> getAllJobs();

    @Query("SELECT * FROM jobs WHERE is_curr_job = 1")
    LiveData<Job> getCurrJob();

    @Query("UPDATE jobs SET title=:title, company=:company, city=:city, state=:state, col=:col, salary=:salary, bonus=:bonus, rsu=:rsu, reloc=:relo, pto=:pto WHERE is_curr_job = 1")
    void updateCurrentJob(String title, String company, String city, String state, Integer col, Integer salary, Integer bonus, Integer rsu, Integer relo, Integer pto);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertJobs(Job... jobs);
}
