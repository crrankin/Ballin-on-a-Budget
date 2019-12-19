package edu.iastate.ballinonabudget.Activities;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import edu.iastate.ballinonabudget.DatabaseConfig.AppDatabase;
import edu.iastate.ballinonabudget.Objects.Budget;
import edu.iastate.ballinonabudget.R;

/**
 * This class represents our yearly budget spendings as a bar chart
 */
public class BarChartActivity extends AppCompatActivity {

    private AppDatabase database; //database of the app
    private Budget selectedBudget; //the current budget we are working with
    private int id; //id for intents
    private String[] monthsArray; //string array of months

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart);

        database = AppDatabase.getAppDatabase(this);
        monthsArray = getResources().getStringArray(R.array.months);

        Intent intent = getIntent();
        id = intent.getIntExtra("uid", 0);
        selectedBudget = database.budgetDao().findByID(id);
        onResume();
    }

    @Override
    protected void onResume(){
        super.onResume();
        database = AppDatabase.getAppDatabase(this);
        selectedBudget = database.budgetDao().findByID(id);
        BarChart barChart = findViewById(R.id.BarChart); //creates a new bar chart

        ArrayList<BarEntry> entries = new ArrayList<>(); //list of amount spend each month
        ArrayList<String> labels = new ArrayList<>(); //list of each month in a year

        for (int i = 0; i < 12; i++) { //for each month in this budget
            //add a new entry that states how much we spent each month
            entries.add(new BarEntry(i, (float) selectedBudget.getCurrentTotalForMonth(i)));
            labels.add(i, monthsArray[i]);
        }

        BarDataSet bardataset = new BarDataSet(entries, "Cells"); //this graphs our bar chart
        BarData data = new BarData(bardataset); //fills the cart with data
        data.setBarWidth(0.9f); // sets the bar chart width
        barChart.setData(data); // set the data into the chart
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate();
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels)); // shows the labels
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.animateY(5000); // cute little animation
    }
}
