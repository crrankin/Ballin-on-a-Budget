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
import android.widget.PopupMenu;

import java.util.List;

import edu.iastate.ballinonabudget.DatabaseConfig.AppDatabase;
import edu.iastate.ballinonabudget.Objects.Budget;
import edu.iastate.ballinonabudget.R;

/**
 * List of Budgets by Category
 */
public class MainActivity extends AppCompatActivity {

    private AppDatabase database;
    private ListView listView;
    private List<Budget> budgetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = AppDatabase.getAppDatabase(this);
        budgetList = database.budgetDao().getAll();
        listView = findViewById(R.id.budget_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Budget selectedBudget = budgetList.get(i);
                Intent intent = new Intent(MainActivity.this, BudgetActivity.class);
                intent.putExtra("uid", selectedBudget.getUid());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, listView);
                popupMenu.getMenuInflater().inflate(R.menu.delete_edit_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_edit_budget:
                                Budget selectedBudget = budgetList.get(position);
                                Intent intent = new Intent(MainActivity.this, EditBudgetActivity.class);
                                intent.putExtra("uid", selectedBudget.getUid());
                                startActivity(intent);
                                return true;
                            case R.id.menu_delete_budget:
                                database.budgetDao().delete(budgetList.get(position));
                                budgetList.remove(position);
                                onResume();
                                return true;
                            default:
                                return true;
                        }
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }

    /**
     * OnResume re-intializes our values in case of refresh
     */
    @Override
    protected void onResume() {
        super.onResume();
        budgetList = database.budgetDao().getAll();
        ArrayAdapter<Budget> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, budgetList);
        listView.setAdapter(adapter);
    }

    /**
     * Adds Menu on Creation clicked.
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_budget:
                Intent intent = new Intent(this, AddBudgetActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
