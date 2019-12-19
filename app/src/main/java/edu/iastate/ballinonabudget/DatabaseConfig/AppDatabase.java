package edu.iastate.ballinonabudget.DatabaseConfig;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import edu.iastate.ballinonabudget.Objects.Budget;

/**
 * Class of the app database
 */
@Database(entities = {Budget.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract BudgetDao budgetDao();

    /**
     * Returns the database for the app
     * @param context context for the app
     * @return instance
     */
    public static AppDatabase getAppDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "budget-database").allowMainThreadQueries().build();
        }        return INSTANCE;
    }

    /**
     * Destroys an instance :)
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }
}