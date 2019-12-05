package edu.iastate.ballinonabudget.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import edu.iastate.ballinonabudget.DatabaseConfig.AppDatabase;
import edu.iastate.ballinonabudget.Objects.Budget;
import edu.iastate.ballinonabudget.Objects.Items;
import edu.iastate.ballinonabudget.R;

public class AddBudgetActivity extends AppCompatActivity {

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = AppDatabase.getAppDatabase(this);
    }

    /**
     * This method runs when a user decides to stop
     * creating a new budget. This will take the user back
     * to the home screen.
     * @param view
     */
    public void cancelBudget(View view){
        Intent goHome = new Intent(this, MainActivity.class);
        startActivity(goHome);
    }

    /**
     * This method runs when a user creates a new Budget
     * @param view
     */
    public void addBudget(View view){
        //Collects the data from views
        EditText nameText = findViewById(R.id.nameInput);
        EditText amountText = findViewById(R.id.amountInput);

        //create a new Budget object using the variables we just made
        String name = nameText.getText().toString();
        double amount = Double.parseDouble(amountText.getText().toString());
        List<Items> items = new ArrayList<>(); //start with a blank items/purchases list
        Budget b = new Budget(name, amount, items);

        //insert new budget into our database
        database.budgetDao().insertBudget(b);

        //now go back to home screen
        Intent goHome = new Intent(this, MainActivity.class);
        startActivity(goHome);
    }

}
