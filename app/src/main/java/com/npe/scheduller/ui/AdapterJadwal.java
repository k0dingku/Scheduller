package com.npe.scheduller.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.npe.scheduller.R;
import com.npe.scheduller.model.JadwalModel;

import java.util.ArrayList;

public class AdapterJadwal extends RecyclerView.Adapter<AdapterJadwal.ViewHolder> {

    private ArrayList<JadwalModel> dataSet;
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void OnItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public AdapterJadwal(ArrayList<JadwalModel> data) {
        this.dataSet = data;
    }

    @NonNull
    @Override
    public AdapterJadwal.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

        View view = layoutInflater.inflate(R.layout.list_jadwal,viewGroup,false);

        return new ViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterJadwal.ViewHolder viewHolder, int i) {

        viewHolder.title.setText(dataSet.get(i).getJudul());
        viewHolder.date.setText(dataSet.get(i).getDate());
        viewHolder.time.setText(dataSet.get(i).getTime());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,date,time;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvTitle);
            date = (TextView) itemView.findViewById(R.id.tvDate);
            time = (TextView) itemView.findViewById(R.id.tvTime);
            itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.OnItemClick(position);
                        }
                    }
                    return false;
                }
            });

        }

    }


}
