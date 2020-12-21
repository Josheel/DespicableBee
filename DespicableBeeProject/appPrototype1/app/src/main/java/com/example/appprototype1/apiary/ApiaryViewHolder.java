package com.example.appprototype1.apiary;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.appprototype1.R;

public class ApiaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView apiaryNameTV;
    ApiaryAdapter.OnNoteListener onNoteListener;

    public ApiaryViewHolder(@NonNull View view, ApiaryAdapter.OnNoteListener onNoteListener){
        super(view);
        apiaryNameTV = (TextView) view.findViewById(R.id.apairyNameTV);
        this.onNoteListener = onNoteListener;
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onNoteListener.onNoteClick(getAdapterPosition());
    }
}