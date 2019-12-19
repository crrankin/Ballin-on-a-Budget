package edu.iastate.ballinonabudget.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import edu.iastate.ballinonabudget.DatabaseConfig.AppDatabase;
import edu.iastate.ballinonabudget.Objects.Budget;
import edu.iastate.ballinonabudget.R;

/**
 * EditBudgetActivity is where it is possible to adjust the value of the budgets
 */
public class EditBudgetActivity extends AppCompatActivity {

    private AppDatabase database;
    private EditText nameText;
    private EditText amountText;
    private Budget selectedBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_budget);
        database = AppDatabase.getAppDatabase(this);

        nameText = findViewById(R.id.nameInputBudget);
        amountText = findViewById(R.id.maxAmountInput);

        Intent intent = getIntent();
        int id = intent.getIntExtra("uid", 0);
        selectedBudget = database.budgetDao().findByID(id);
    }

    /**
     * This method runs when a user decides to stop
     * updating a budget. This will take the user back
     * to the home screen.
     * @param view
     */
    public void cancelBudget(View view){
        Intent goHome = new Intent(this, MainActivity.class);
        startActivity(goHome);
    }

    /**
     * This method runs when a user updates a Budget
     * @param view
     */
    public void updateBudget(View view){
        //create a new Budget object using the variables we just made
        String name = nameText.getText().toString();
        double amount = Double.parseDouble(amountText.getText().toString());
        selectedBudget.setName(name);
        selectedBudget.setTotalAmount(amount);

        //insert new budget into our database
        database.budgetDao().update(selectedBudget);

        //now go back to home screen
        Intent goHome = new Intent(this, MainActivity.class);
        startActivity(goHome);
    }
}
