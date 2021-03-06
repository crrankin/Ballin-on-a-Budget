package edu.iastate.ballinonabudget.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.iastate.ballinonabudget.DatabaseConfig.AppDatabase;
import edu.iastate.ballinonabudget.Objects.Budget;
import edu.iastate.ballinonabudget.Objects.Items;
import edu.iastate.ballinonabudget.R;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Creates the Monthly Piecharts for the budgets
 */
public class PiechartActivity extends AppCompatActivity {

    List<SliceValue> pieData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);

        AppDatabase database = AppDatabase.getAppDatabase(this);

        Intent intent = getIntent();
        int id = intent.getIntExtra("uid", 0);
        int currentMonth = intent.getIntExtra("month", 0);
        Budget selectedBudget = database.budgetDao().findByID(id);

        PieChartView pieChartView = findViewById(R.id.chart);

        List<Items> items = selectedBudget.getItemsForMonth(currentMonth);
        for(Items item : items){
            pieData.add(new SliceValue((float) item.getPurchaseAmount(), getRandomColor()).setLabel(item.getPurchaseTitle()));
        }
        pieData.add(new SliceValue((float) (selectedBudget.getTotalAmount() - selectedBudget.getCurrentTotalForMonth(currentMonth)), getRandomColor()).setLabel(getString(R.string.remaining_balance)));
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        pieChartView.setPieChartData(pieChartData);
        //Found a handy tutorial for making Pie Charts:) thx b
        //https://www.codingdemos.com/android-pie-chart-tutorial/
    }

    /**
     * Makes random colors for the pie chart
     * @return Rando Colors
     */
    private int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}
