package edu.iastate.ballinonabudget.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import java.util.Calendar;

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
    private CalendarView calendar;
    private int monthInt;
    private int yearInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        nameText = findViewById(R.id.nameInputItems);
        amountText = findViewById(R.id.amountInput);
        hrefText = findViewById(R.id.hyperlinkInput);
        calendar = findViewById(R.id.calendarView);
        Calendar cal = Calendar.getInstance();
        monthInt = cal.get(Calendar.MONTH);
        yearInt = cal.get(Calendar.YEAR);
        database = AppDatabase.getAppDatabase(this);
        Intent intent = getIntent();
        int id = intent.getIntExtra("uid", 0);
        selectedBudget = database.budgetDao().findByID(id);

        calendar.setDate(Calendar.getInstance().getTimeInMillis(),false,true);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                monthInt = month;
                yearInt = year;
            }
        });

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
        Items item = new Items(name, amount, "", href, false, monthInt, yearInt);

        selectedBudget.addItem(item);

        database.budgetDao().update(selectedBudget);

        Intent goBack = new Intent(this, BudgetActivity.class);
        goBack.putExtra("uid", selectedBudget.getUid());
        startActivity(goBack);
    }
}
