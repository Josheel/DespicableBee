package com.example.appprototype1.apiary;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appprototype1.R;

import java.util.ArrayList;

public class ApiaryAdapter extends RecyclerView.Adapter<ApiaryViewHolder>{

    private OnNoteListener mOnNoteListener;
    ArrayList<Apiary> apiaryList;

    public ApiaryAdapter(ArrayList<Apiary> apiaryList, OnNoteListener onNoteListener){
        this.apiaryList = apiaryList;
        mOnNoteListener = onNoteListener;

    }

    @NonNull
    @Override
    public ApiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_apiary_view_holder, parent, false);
        return new ApiaryViewHolder(itemView, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ApiaryViewHolder holder, int position) {
        holder.apiaryNameTV.setText(apiaryList.get(position).getApiaryName());
    }

    @Override
    public int getItemCount() {
        return apiaryList.size();
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
