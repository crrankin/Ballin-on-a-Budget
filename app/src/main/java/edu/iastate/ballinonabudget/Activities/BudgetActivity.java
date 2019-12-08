package edu.iastate.ballinonabudget.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import edu.iastate.ballinonabudget.DatabaseConfig.AppDatabase;
import edu.iastate.ballinonabudget.Objects.Budget;
import edu.iastate.ballinonabudget.Objects.Items;
import edu.iastate.ballinonabudget.R;

public class BudgetActivity extends AppCompatActivity {

    private AppDatabase database;
    private Budget selectedBudget;
    private ListView listView;
    private List<Items> itemsList;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        database = AppDatabase.getAppDatabase(this);

        listView = findViewById(R.id.item_list);

        Intent intent = getIntent();
        id = intent.getIntExtra("uid", 0);

        selectedBudget = database.budgetDao().findByID(id);

        TextView temp = findViewById(R.id.budget_name);
        temp.setText(selectedBudget.getTitle());

        temp = findViewById(R.id.current_total);
        temp.setText(String.format(Locale.ENGLISH, "%1$,.2f", selectedBudget.getCurrentTotal()));

        temp = findViewById(R.id.max_total);
        temp.setText(String.format(Locale.ENGLISH, "%1$,.2f", selectedBudget.getTotalAmount()));

        itemsList = selectedBudget.getItems();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Need to figure out how to save items properly
                Items selectedItem = itemsList.get(i);
                Intent intent = new Intent(BudgetActivity.this, ItemsInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(getString(R.string.ItemsKey), selectedItem);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectedBudget = database.budgetDao().findByID(id);
        itemsList = selectedBudget.getItems();
        ArrayAdapter<Items> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, itemsList);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_budget, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_item:
                Intent addItemIntent = new Intent(this, AddItemsActivity.class);
                addItemIntent.putExtra("uid", id);
                startActivity(addItemIntent);
                return true;
            case R.id.menu_open_pie_chart:
                Intent pieChartIntent = new Intent(this, PiechartActivity.class);
                pieChartIntent.putExtra("uid", id);
                startActivity(pieChartIntent);
                return true;
            case R.id.menu_go_back_to_home:
                Intent goHomeIntent = new Intent(this, MainActivity.class);
                startActivity(goHomeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
