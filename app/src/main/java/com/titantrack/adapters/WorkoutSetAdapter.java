package com.titantrack.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.titantrack.R;
import com.titantrack.models.WorkoutSet;
import java.util.List;

public class WorkoutSetAdapter extends RecyclerView.Adapter<WorkoutSetAdapter.SetViewHolder> {

    private List<WorkoutSet> setList;

    public WorkoutSetAdapter(List<WorkoutSet> setList) {
        this.setList = setList;
    }

    @NonNull
    @Override
    public SetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout_set, parent, false);
        return new SetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetViewHolder holder, int position) {
        WorkoutSet workoutSet = setList.get(position);
        holder.tvSetNumber.setText(String.valueOf(position + 1));
        holder.tvSetDetails.setText(workoutSet.toString());
    }

    @Override
    public int getItemCount() {
        return setList.size();
    }

    public static class SetViewHolder extends RecyclerView.ViewHolder {
        TextView tvSetNumber, tvSetDetails;
        public SetViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSetNumber = itemView.findViewById(R.id.tvSetNumber);
            tvSetDetails = itemView.findViewById(R.id.tvSetDetails);
        }
    }
}