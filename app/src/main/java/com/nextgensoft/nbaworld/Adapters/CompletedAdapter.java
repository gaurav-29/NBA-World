package com.nextgensoft.nbaworld.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nextgensoft.nbaworld.Activities.DetailActivity;
import com.nextgensoft.nbaworld.Models.CompletedModel;
import com.nextgensoft.nbaworld.R;
import com.nextgensoft.nbaworld.databinding.RowCompletedBinding;
import com.nextgensoft.nbaworld.databinding.RowUpcomingBinding;

import java.util.ArrayList;

public class CompletedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    RowCompletedBinding binding;
    ArrayList<CompletedModel.Data> completedList;
    Context context;

    public CompletedAdapter(ArrayList<CompletedModel.Data> completedList, Context context) {
        this.completedList = completedList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_completed, parent, false);
        return new CompletedAdapter.ItemViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final CompletedAdapter.ItemViewHolder mViewHolder = (CompletedAdapter.ItemViewHolder) holder;
        mViewHolder.binding.tvBookcode.setText(completedList.get(position).bookcode);
        mViewHolder.binding.tvName.setText(completedList.get(position).first_name);
        mViewHolder.binding.tvMobile.setText(completedList.get(position).mobile_no);
        mViewHolder.binding.tvAddress.setText(completedList.get(position).address);
    }

    @Override
    public int getItemCount() {
        return completedList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private RowCompletedBinding binding;

        public ItemViewHolder(@NonNull View itemView, RowCompletedBinding binding) {
            super(itemView);
            this.binding = binding;
        }
    }
}

