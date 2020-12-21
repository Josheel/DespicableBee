package com.example.appprototype1.hive;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appprototype1.R;

public class HiveViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView hiveName;
    HiveAdapter.OnNoteListener onNoteListener;

    public HiveViewHolder(@NonNull View view, HiveAdapter.OnNoteListener onNoteListener){
        super(view);
        hiveName = view.findViewById(R.id.hiveName);

        this.onNoteListener = onNoteListener;
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onNoteListener.onNoteClick(getAdapterPosition());
    }
}
