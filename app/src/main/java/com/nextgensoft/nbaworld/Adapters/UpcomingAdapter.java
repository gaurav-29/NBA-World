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
import com.nextgensoft.nbaworld.Models.UpcomingModel;
import com.nextgensoft.nbaworld.R;
import com.nextgensoft.nbaworld.databinding.RowTodayAppointmentsBinding;
import com.nextgensoft.nbaworld.databinding.RowUpcomingBinding;

import java.util.ArrayList;

public class UpcomingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    RowUpcomingBinding binding;
    ArrayList<UpcomingModel.Data> upcomingList;
    Context context;

    public UpcomingAdapter(ArrayList<UpcomingModel.Data> upcomingList, Context context) {
        this.upcomingList = upcomingList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_upcoming, parent, false);
        return new UpcomingAdapter.ItemViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final UpcomingAdapter.ItemViewHolder mViewHolder = (UpcomingAdapter.ItemViewHolder) holder;
        mViewHolder.binding.tvBookcode.setText(upcomingList.get(position).bookcode);
        mViewHolder.binding.tvName.setText(upcomingList.get(position).first_name);
        mViewHolder.binding.tvMobile.setText(upcomingList.get(position).mobile_no);
        mViewHolder.binding.tvAddress.setText(upcomingList.get(position).address);
    }

    @Override
    public int getItemCount() {
        return upcomingList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private RowUpcomingBinding binding;

        public ItemViewHolder(@NonNull View itemView, RowUpcomingBinding binding) {
            super(itemView);
            this.binding = binding;
        }
    }
}
