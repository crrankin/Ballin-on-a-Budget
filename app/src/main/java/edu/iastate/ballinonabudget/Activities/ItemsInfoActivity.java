package edu.iastate.ballinonabudget.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import edu.iastate.ballinonabudget.DatabaseConfig.AppDatabase;
import edu.iastate.ballinonabudget.Objects.Budget;
import edu.iastate.ballinonabudget.Objects.Items;
import edu.iastate.ballinonabudget.R;

public class ItemsInfoActivity extends AppCompatActivity {
    private AppDatabase database;
    private Items items;
    private Budget selectedBudget;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_info);

        database = AppDatabase.getAppDatabase(this);

        //display budget name at the top of the screen
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String[] monthsArray = getResources().getStringArray(R.array.months);

        try {
            id = bundle.getInt("uid", 0);
            items = (Items) bundle.getSerializable("Item");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to Fetch Item", Toast.LENGTH_LONG).show();
            items = new Items("Default", 0, "Default", "Default", false, 0, 2019);
        }

        //display item information
        selectedBudget = database.budgetDao().findByID(id);
        TextView temp = findViewById(R.id.budget_name);
        temp.setText(selectedBudget.getTitle());
        TextView name = findViewById(R.id.item_name);
        name.setText(items.getPurchaseTitle());
        TextView month = findViewById(R.id.item_month);
        month.setText(monthsArray[items.getPurchaseMonth()]);
        TextView year = findViewById(R.id.item_year);
        year.setText(String.format(Locale.ENGLISH, "%d", items.getPurchaseYear()));
        TextView category = findViewById(R.id.item_category);
        category.setText(items.getCategory());
        TextView hyperlink = findViewById(R.id.item_hyperlink);
        hyperlink.setText(items.getHyperlink());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_budget, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_go_back_to_home:
                Intent goHomeIntent = new Intent(this, MainActivity.class);
                startActivity(goHomeIntent);
                return true;
            case R.id.menu_delete_item:
                Intent deleteItemIntent = new Intent(this, BudgetActivity.class);
                startActivity(deleteItemIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
