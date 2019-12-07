package edu.iastate.ballinonabudget.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import edu.iastate.ballinonabudget.DatabaseConfig.AppDatabase;
import edu.iastate.ballinonabudget.Objects.Budget;
import edu.iastate.ballinonabudget.R;

public class PiechartActivity extends AppCompatActivity {

    private AppDatabase database;
    private Budget selectedBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);

        database = AppDatabase.getAppDatabase(this);

        Intent intent = getIntent();
        int id = intent.getIntExtra("uid", 0);
        selectedBudget = database.budgetDao().findByID(id);

        //Found a handy tutorial for making Pie Charts:)
        //https://www.codingdemos.com/android-pie-chart-tutorial/
    }
}
