package edu.iastate.ballinonabudget.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private int currentMonth;
    private String[] monthsArray;
    private TextView monthView;
    private TextView currentTotal;
    private TextView balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        database = AppDatabase.getAppDatabase(this);

        listView = findViewById(R.id.item_list);
        monthsArray = getResources().getStringArray(R.array.months);
        Calendar calendar = Calendar.getInstance();
        currentMonth = calendar.get(Calendar.MONTH);

        String monthName = monthsArray[currentMonth];

        Intent intent = getIntent();
        id = intent.getIntExtra("uid", 0);

        selectedBudget = database.budgetDao().findByID(id);

        TextView temp = findViewById(R.id.budget_name);
        temp.setText(selectedBudget.getTitle());

        temp = findViewById(R.id.max_total);
        temp.setText(String.format(Locale.ENGLISH, "%1$,.2f", selectedBudget.getTotalAmount()));

        monthView = findViewById(R.id.currentMonth);
        monthView.setText(monthName);

        currentTotal = findViewById(R.id.current_total);
        balance = findViewById(R.id.balance);

        itemsList = selectedBudget.getItems();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Need to figure out how to save items properly
                Items selectedItem = itemsList.get(i);
                Intent intent = new Intent(BudgetActivity.this, ItemsInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(getString(R.string.ItemsKey), selectedItem);
                bundle.putInt("uid", id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
                PopupMenu popupMenu = new PopupMenu(BudgetActivity.this, listView);
                popupMenu.getMenuInflater().inflate(R.menu.delete_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.menu_delete_item) {
                            selectedBudget.removeItem(position);
                            database.budgetDao().update(selectedBudget);
                            onResume();
                            return true;
                        } else {
                            return true;
                        }
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectedBudget = database.budgetDao().findByID(id);
        itemsList = selectedBudget.getItems();

        List<Items> listToShow = selectedBudget.getItemsForMonth(currentMonth);

        currentTotal.setText(String.format(Locale.ENGLISH, "%1$,.2f", getCurrentTotal(listToShow)));
        //get balance and change it's color based on positive/negative
        double remainingBalance = getBalance();
        balance.setText(String.format(Locale.ENGLISH,"%1$,.2f", remainingBalance));
        if(remainingBalance<0){
            balance.setBackgroundColor(Color.RED);
        }else{
            balance.setBackgroundColor(Color.GREEN);
        }

        ArrayAdapter<Items> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listToShow);
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
                pieChartIntent.putExtra("month", currentMonth);
                startActivity(pieChartIntent);
                return true;
            case R.id.menu_go_back_to_home:
                Intent goHomeIntent = new Intent(this, MainActivity.class);
                startActivity(goHomeIntent);
                return true;
            case R.id.menu_view_yearly_budget:
                Intent yearlyBudgetIntent = new Intent(this, BarChartActivity.class);
                yearlyBudgetIntent.putExtra("uid", id);
                startActivity(yearlyBudgetIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateList() {
        monthView.setText(monthsArray[currentMonth]);
        onResume();
    }

    public void onLeftClick(View view) {
        if(currentMonth == 0) {
            currentMonth = 11;
        } else {
            currentMonth--;
        }
        updateList();
    }

    public void onRightClick(View view) {
        if(currentMonth == 11) {
            currentMonth = 0;
        } else {
            currentMonth++;
        }
        updateList();
    }

    public double getCurrentTotal(List<Items> list) {
        return selectedBudget.getCurrentTotalForMonth(currentMonth);
    }

    public double getBalance(){
        return selectedBudget.getBalanceForMonth(currentMonth);
    }
}
