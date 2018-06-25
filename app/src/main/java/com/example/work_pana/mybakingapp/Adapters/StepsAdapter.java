package com.example.work_pana.mybakingapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.work_pana.mybakingapp.Models.StepObject;
import com.example.work_pana.mybakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private List<StepObject> steps;
    private OnStepClickListener listener;

    public interface OnStepClickListener {
        void onStepSelected(int position);
    }

    public StepsAdapter(List<StepObject> steps, OnStepClickListener listener) {
        this.listener = listener;
        this.steps = steps;
    }

    @NonNull
    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_step, parent, false);

        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.ViewHolder holder, int position) {

        holder.itemNr.setText(String.valueOf(position) + ".");
        holder.shortDescription.setText(steps.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return steps != null ? steps.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        private OnStepClickListener listener;

        @BindView(R.id.step_short_description)
        TextView shortDescription;
        @BindView(R.id.step_number)
        TextView itemNr;

        ViewHolder(View itemView, OnStepClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onStepSelected(getAdapterPosition());
        }
    }
}
