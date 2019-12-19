package edu.iastate.ballinonabudget.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.Calendar;

import edu.iastate.ballinonabudget.DatabaseConfig.AppDatabase;
import edu.iastate.ballinonabudget.Objects.Budget;
import edu.iastate.ballinonabudget.Objects.Items;
import edu.iastate.ballinonabudget.R;

/**
 * This class shows us the adding items screen
 */
public class AddItemsActivity extends AppCompatActivity {

    private AppDatabase database;
    private Budget selectedBudget;
    private EditText nameText; //name of item
    private EditText amountText; //item cost
    private EditText hrefText; //hyperlink text
    private CalendarView calendar; //calendar variable to get current month
    private CheckBox checkBox; //checkbox variable
    private int monthInt; // month of purchase
    private int yearInt; // year of purchase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        //fills in all the item info
        nameText = findViewById(R.id.nameInputItems);
        amountText = findViewById(R.id.amountInput);
        hrefText = findViewById(R.id.hyperlinkInput);
        calendar = findViewById(R.id.calendarView);
        checkBox = findViewById(R.id.recurringCheckBox);

        //autofills the item's purchase data with today's date
        Calendar cal = Calendar.getInstance();
        monthInt = cal.get(Calendar.MONTH);
        yearInt = cal.get(Calendar.YEAR);
        database = AppDatabase.getAppDatabase(this);

        //start intent
        Intent intent = getIntent();
        int id = intent.getIntExtra("uid", 0);
        selectedBudget = database.budgetDao().findByID(id);

        calendar.setDate(Calendar.getInstance().getTimeInMillis(),false,true);

        //listens if the user changes the date
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                monthInt = month;
                yearInt = year;
            }
        });

    }

    /**
     * This allows the user to cancel making a new item
     * @param view view
     */
    public void cancelItem(View view) {
        Intent goBack = new Intent(this, BudgetActivity.class);
        goBack.putExtra("uid", selectedBudget.getUid());
        startActivity(goBack);
    }

    /**
     * Adds a new item to our budget
     * @param view view
     */
    public void addItem(View view) {
        String name = nameText.getText().toString();
        double amount = Double.parseDouble(amountText.getText().toString());
        String href = hrefText.getText().toString();
        boolean isRecurring = checkBox.isChecked();

        //creates a new item with information
        Items item = new Items(name, amount, "", href, isRecurring, monthInt, yearInt);
        selectedBudget.addItem(item);

        //updates our budget with the new item
        database.budgetDao().update(selectedBudget);

        //takes the user back to the budget screen after adding the item
        Intent goBack = new Intent(this, BudgetActivity.class);
        goBack.putExtra("uid", selectedBudget.getUid());
        startActivity(goBack);
    }
}
