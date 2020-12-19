package com.example.appprototype1.apiary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appprototype1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ApiariesActivity extends AppCompatActivity {
    ImageButton newApiaryBTN;
    RecyclerView apiariesListRV;
    private DatabaseReference database;
    private FirebaseAuth firebaseAuth;
    final private ArrayList<Apiary> apiaries = new ArrayList<>();
    private Context c;
    String uid,location,name,notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiaries);
        getApiaries();

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

        //setupAdapter();
    }



    public void setupAdapter(){
        getApiaries();
        ApiaryAdapter apiaryAdapter = new ApiaryAdapter(apiaries);
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
                for(DataSnapshot snapshot1: datasnapshot.getChildren()){
                    name = snapshot1.child("apiaryName").getValue().toString();
                    location = snapshot1.child("locationName").getValue().toString();
                    notes = snapshot1.child("notesName").getValue().toString();
                    Apiary p = new Apiary(name,location,notes);
                    apiaries.add(p);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        int i = 0;
        for(Apiary pee: apiaries) {
            Toast.makeText(getApplicationContext(), pee.getLocationName(), Toast.LENGTH_SHORT).show();
        }

    }


}