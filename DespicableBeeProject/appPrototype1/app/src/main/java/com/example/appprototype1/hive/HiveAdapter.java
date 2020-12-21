package com.example.appprototype1.hive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appprototype1.R;
import com.example.appprototype1.apiary.ApiaryAdapter;
import com.example.appprototype1.apiary.ApiaryViewHolder;

import java.util.ArrayList;

public class HiveAdapter extends RecyclerView.Adapter<HiveViewHolder> {

    private OnNoteListener mOnNoteListener;
    ArrayList<Hive> hiveList;

    public HiveAdapter(ArrayList<Hive> hiveList, OnNoteListener onNoteListener){
        mOnNoteListener = onNoteListener;
        this.hiveList = hiveList;
    }

    @NonNull
    @Override
    public HiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_hive_viewholder, parent, false);
        return new HiveViewHolder(itemView, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HiveViewHolder holder, int position) {
        holder.hiveName.setText(hiveList.get(position).getHiveNumber());
    }

    @Override
    public int getItemCount() {
       return hiveList.size();
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
