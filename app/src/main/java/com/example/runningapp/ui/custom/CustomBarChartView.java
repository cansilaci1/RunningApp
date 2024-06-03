package com.example.runningapp.ui.custom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.example.runningapp.db.entity.StepData;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CustomBarChartView extends BarChart {

    private List<StepData> stepDataList = new ArrayList<>();

    public CustomBarChartView(Context context) {
        super(context);
        init();
    }

    public CustomBarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomBarChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void init() {
        super.init();

        // Disable description and legend
        if (getDescription() != null) {
            getDescription().setEnabled(false);
        }

        if (getLegend() != null) {
            getLegend().setEnabled(false);
        }

        // Customize X axis
        XAxis xAxis = getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);

        // Customize Y axis
        YAxis leftAxis = getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextColor(Color.BLACK);
        getAxisRight().setEnabled(false);
    }

    public void setStepData(List<StepData> stepDataList) {
        this.stepDataList = stepDataList;
        setData();
    }

    private void setData() {
        // Sort the step data list by date if necessary
        Collections.sort(stepDataList, new Comparator<StepData>() {
            @Override
            public int compare(StepData o1, StepData o2) {
                return o1.date.compareTo(o2.date);
            }
        });

        ArrayList<BarEntry> entries = new ArrayList<>();
        int mostActiveDayIndex = 0;
        int maxSteps = 0;

        for (int i = 0; i < stepDataList.size(); i++) {
            int steps = stepDataList.get(i).steps;
            entries.add(new BarEntry(i, steps));
            if (steps > maxSteps) {
                maxSteps = steps;
                mostActiveDayIndex = i;
            }
        }

        BarDataSet dataSet = new BarDataSet(entries, "Steps");

        // Create a list of colors
        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 0; i < stepDataList.size(); i++) {
            if (i == mostActiveDayIndex) {
                colors.add(Color.parseColor("#B2FF59")); // Light green color for most active day
            } else {
                colors.add(Color.parseColor("#D1C4E9")); // Light purple color for other days
            }
        }
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.BLACK);

        BarData data = new BarData(dataSet);
        data.setValueTextSize(10f);
        setData(data);

        // Customize X axis labels to display days of the week
        XAxis xAxis = getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getDayLabels(stepDataList.size())));
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        invalidate();
    }

    private String[] getDayLabels(int size) {
        String[] labels = new String[size];
        // Assuming you want to display day names from Monday to Sunday or another convention
        String[] days = {"Pzt", "Sal", "Çrş", "Prş", "Cum", "Cts", "Pzr"};
        for (int i = 0; i < size; i++) {
            labels[i] = days[i % days.length]; // Cycle through days array
        }
        return labels;
    }
}
