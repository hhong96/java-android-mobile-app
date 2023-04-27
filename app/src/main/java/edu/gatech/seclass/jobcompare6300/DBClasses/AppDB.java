package edu.gatech.seclass.jobcompare6300.DBClasses;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.gatech.seclass.jobcompare6300.Entities.ComparisonSetting;
import edu.gatech.seclass.jobcompare6300.DAOs.AppDAO;
import edu.gatech.seclass.jobcompare6300.Entities.Job;

// Citations:
//  https://www.geeksforgeeks.org/how-to-use-singleton-pattern-for-room-database-in-android/
@Database(entities = {ComparisonSetting.class, Job.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase{
    public abstract AppDAO appDAO();
    private static volatile AppDB INSTANCE;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(6);

    public static AppDB getAppDB(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                                    AppDB.class, "app_db").build();
                }
            }
        }

        return INSTANCE;
    }
}
