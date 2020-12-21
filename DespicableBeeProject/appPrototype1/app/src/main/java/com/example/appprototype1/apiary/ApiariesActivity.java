package com.example.appprototype1.apiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.example.appprototype1.R;
import com.example.appprototype1.hive.HivesActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ApiariesActivity extends AppCompatActivity implements ApiaryAdapter.OnNoteListener {
    ImageButton newApiaryBTN;
    RecyclerView apiariesListRV;
    private DatabaseReference database;
    private FirebaseAuth firebaseAuth;
    final ArrayList<Apiary> apiaries = new ArrayList<>();
    String uid,location,name,notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiaries);

        newApiaryBTN = findViewById(R.id.newApiaryBTN2);
        newApiaryBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),NewApiaryActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getApiaries();
    }

    public void setupAdapter(){
        ApiaryAdapter apiaryAdapter = new ApiaryAdapter(apiaries,this);
        apiariesListRV = (RecyclerView) findViewById(R.id.apiariesListRV);
        apiariesListRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        apiariesListRV.setAdapter(apiaryAdapter);
    }

    private void getApiaries(){
        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Apiaries");;
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                apiaries.clear();
                for(DataSnapshot snapshot1: datasnapshot.getChildren()){
                    name = snapshot1.child("apiaryName").getValue().toString();
                    location = snapshot1.child("locationName").getValue().toString();
                    notes = snapshot1.child("notesName").getValue().toString();
                    Apiary p = new Apiary(name,location,notes);
                    apiaries.add(p);
                }
                setupAdapter();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, HivesActivity.class);
        intent.putExtra("name",apiaries.get(position).getApiaryName());
        intent.putExtra("location",apiaries.get(position).getLocationName());
        intent.putExtra("notes",apiaries.get(position).getNotesName());
        startActivity(intent);
    }
}