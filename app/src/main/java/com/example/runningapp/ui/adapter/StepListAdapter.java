package com.example.runningapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.runningapp.R;
import com.example.runningapp.db.entity.StepData;
import java.util.List;


//recyclerview'ın çalışması ve dönen bilgilerin kontrolü için adapter classı
public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepViewHolder> {

    private final List<StepData> stepDataList;

    public StepListAdapter(List<StepData> stepDataList) {
        this.stepDataList = stepDataList;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_step, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        StepData stepData = stepDataList.get(position);
        holder.tvDate.setText(stepData.date);
        holder.tvSteps.setText(String.valueOf(stepData.steps));
    }

    @Override
    public int getItemCount() {
        return stepDataList.size();
    }

    static class StepViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvSteps;

        StepViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvSteps = itemView.findViewById(R.id.tv_steps);
        }
    }
}
