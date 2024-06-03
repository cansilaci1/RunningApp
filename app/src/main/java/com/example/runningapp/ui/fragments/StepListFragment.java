package com.example.runningapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.runningapp.R;
import com.example.runningapp.db.entity.AppDatabase;
import com.example.runningapp.db.entity.StepData;
import com.example.runningapp.ui.adapter.StepListAdapter;
import java.util.List;

//tarihler ve atılan adımların gözüktüğü sayfa
public class StepListFragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AppDatabase db = AppDatabase.getDatabase(requireContext());
        new Thread(() -> {
            List<StepData> stepDataList = db.stepDataDao().getAllStepData();
            getActivity().runOnUiThread(() -> {
                StepListAdapter adapter = new StepListAdapter(stepDataList);
                recyclerView.setAdapter(adapter);
            });
        }).start();

        return view;
    }
}
