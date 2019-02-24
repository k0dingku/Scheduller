package com.npe.scheduller.ui;

import android.graphics.Color;
import android.hardware.camera2.params.ColorSpaceTransform;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.npe.scheduller.R;
import com.npe.scheduller.model.JadwalModel;
import com.npe.scheduller.model.dbsqlite.DatabaseHelper;
import com.npe.scheduller.model.dbsqlite.JadwalOperations;

import java.util.ArrayList;
import java.util.List;

public class AdapterJadwal extends RecyclerView.Adapter<AdapterJadwal.ViewHolder> implements Filterable {

    private ArrayList<JadwalModel> dataSet;
    private OnItemClickListener mListener;
    private JadwalOperations jadwalOperations;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    jadwalOperations.openDb();
                    dataSet = jadwalOperations.getAllJadwal();
                    jadwalOperations.closeDb();
                } else {
                    ArrayList<JadwalModel> filteredList = new ArrayList<>();
                    jadwalOperations.openDb();
                    for (JadwalModel row : jadwalOperations.getAllJadwal()) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getJudul().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    dataSet = filteredList;
                    jadwalOperations.closeDb();
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataSet;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataSet = (ArrayList<JadwalModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


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
        viewHolder.btnWarnaSamping.setBackgroundColor(dataSet.get(i).getWarna());
        viewHolder.relativeLayout.setBackgroundColor(dataSet.get(i).getWarna());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,date,time;
        Button btnWarnaSamping;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvTitle);
            date = (TextView) itemView.findViewById(R.id.tvDate);
            time = (TextView) itemView.findViewById(R.id.tvTime);
            btnWarnaSamping = itemView.findViewById(R.id.btnWarnaSamping);
            relativeLayout = itemView.findViewById(R.id.layoutRelatifCard);
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
