package edu.iastate.ballinonabudget.DatabaseConfig;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.iastate.ballinonabudget.Objects.Budget;

@Dao
public interface BudgetDao {
    @Query("SELECT * FROM budget")
    List<Budget> getAll();

    @Query("SELECT * FROM budget WHERE uid IS :id")
    Budget findByID(int id);

    @Insert
    void insertBudget(Budget budget);

    @Delete
    void delete(Budget budget);

    @Update
    void update(Budget budget);
}
