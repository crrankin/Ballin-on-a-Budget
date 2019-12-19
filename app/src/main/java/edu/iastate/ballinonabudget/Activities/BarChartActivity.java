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
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;

import edu.iastate.ballinonabudget.DatabaseConfig.AppDatabase;
import edu.iastate.ballinonabudget.Objects.Budget;
import edu.iastate.ballinonabudget.R;

public class BarChartActivity extends AppCompatActivity {

    private AppDatabase database;
    private Budget selectedBudget;
    private int id;
    private String[] monthsArray;

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
        BarChart barChart = findViewById(R.id.BarChart);

        ArrayList<BarEntry> entries = new ArrayList<>();
       // ArrayList<String> labels = new ArrayList<>();
        BarDataSet bardataset = new BarDataSet(entries, "Cells");

        for (int i = 0; i < 12; i++) { //for each month in this budget
            //add a new entry that states how much we spent each month
            entries.add(new BarEntry(i, (float) selectedBudget.getCurrentTotalForMonth(i)));
            //labels.add(i, monthsArray[i]);
        }

        System.out.println(entries.toString());

        BarData data = new BarData(bardataset);
        barChart.setData(data); // set the data into the chart
        //barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.animateY(5000);
    }
}
