package edu.iastate.ballinonabudget.Activities;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;

import edu.iastate.ballinonabudget.DatabaseConfig.AppDatabase;
import edu.iastate.ballinonabudget.Objects.Budget;
import edu.iastate.ballinonabudget.Objects.Items;
import edu.iastate.ballinonabudget.R;

public class AddItemsActivity extends AppCompatActivity {

    private AppDatabase database;
    private Budget selectedBudget;
    private EditText nameText;
    private EditText amountText;
    private EditText hrefText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        nameText = findViewById(R.id.nameInputItems);
        amountText = findViewById(R.id.amountInput);
        hrefText = findViewById(R.id.hyperlinkInput);

        database = AppDatabase.getAppDatabase(this);
        Intent intent = getIntent();
        int id = intent.getIntExtra("uid", 0);
        selectedBudget = database.budgetDao().findByID(id);
    }

    public void cancelItem(View view) {
        Intent goBack = new Intent(this, BudgetActivity.class);
        goBack.putExtra("uid", selectedBudget.getUid());
        startActivity(goBack);
    }

    public void addItem(View view) {
        String name = nameText.getText().toString();
        double amount = Double.parseDouble(amountText.getText().toString());
        String href = hrefText.getText().toString();

        //Needs to be updated to include month, recurring, year and category.
        Items item = new Items(name, amount, "", href, false, "", "");

        selectedBudget.addItem(item);

        database.budgetDao().update(selectedBudget);

        Intent goBack = new Intent(this, BudgetActivity.class);
        goBack.putExtra("uid", selectedBudget.getUid());
        startActivity(goBack);
    }
}
