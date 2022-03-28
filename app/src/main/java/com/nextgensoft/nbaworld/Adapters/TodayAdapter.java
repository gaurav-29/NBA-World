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
import com.nextgensoft.nbaworld.Activities.DetailActivity2;
import com.nextgensoft.nbaworld.Models.TodayAppointmentsModel;
import com.nextgensoft.nbaworld.R;
import com.nextgensoft.nbaworld.databinding.RowTodayAppointmentsBinding;

import java.util.ArrayList;

public class TodayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RowTodayAppointmentsBinding binding;
    ArrayList<TodayAppointmentsModel.Data> todayList;
    Context context;

    public TodayAdapter(ArrayList<TodayAppointmentsModel.Data> todayList, Context context) {
        this.todayList = todayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_today_appointments, parent, false);
        return new ItemViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder mViewHolder = (ItemViewHolder) holder;
        mViewHolder.binding.tvBookcode.setText(todayList.get(position).bookcode);
        mViewHolder.binding.tvName.setText(todayList.get(position).first_name);
        mViewHolder.binding.tvMobile.setText(todayList.get(position).mobile_no);
        mViewHolder.binding.tvAddress.setText(todayList.get(position).address);

        mViewHolder.binding.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(todayList.get(position).status.equals("Running")) {
                    Intent detail = new Intent(context, DetailActivity2.class);
                    detail.putExtra("ID", todayList.get(position).appointment_id);
                    context.startActivity(detail);
                }else{
                    Intent detail = new Intent(context, DetailActivity.class);
                    detail.putExtra("ID3", todayList.get(position).appointment_id);
                    context.startActivity(detail);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return todayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private RowTodayAppointmentsBinding binding;

        public ItemViewHolder(@NonNull View itemView, RowTodayAppointmentsBinding binding) {
            super(itemView);
            this.binding = binding;
        }
    }
}
