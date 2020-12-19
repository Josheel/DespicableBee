package com.example.appprototype1.apiary;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appprototype1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ApiaryAdapter extends RecyclerView.Adapter<ApiaryViewHolder>{
    FirebaseAuth firebaseAuth;
    DatabaseReference database;
    ArrayList<Apiary> apiaryList;
    String uid;
    String name,location,notes;

    public ApiaryAdapter(ArrayList<Apiary> apiaryList){
        this.apiaryList = apiaryList;
    }

    @NonNull
    @Override
    public ApiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_apiary_view_holder, parent, false);
        return new ApiaryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ApiaryViewHolder holder, int position) {
        holder.apiaryNameTV.setText(apiaryList.get(position).getApiaryName());
        holder.locationNameTV.setText(apiaryList.get(position).getLocationName());
        holder.notesTV.setText(apiaryList.get(position).getNotesName());

    }

    @Override
    public int getItemCount() {
        return apiaryList.size();
    }

    private void setApiaryData(String apiary, @NonNull ApiaryViewHolder holder){
        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Apiaries").child(apiary);
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()) {
                    name = datasnapshot.child("apiaryName").getValue().toString();
                    location = datasnapshot.child("locationName").getValue().toString();
                    notes = datasnapshot.child("notesName").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.apiaryNameTV.setText(apiary);
        holder.locationNameTV.setText(location);
        holder.notesTV.setText(notes);
    }
}
