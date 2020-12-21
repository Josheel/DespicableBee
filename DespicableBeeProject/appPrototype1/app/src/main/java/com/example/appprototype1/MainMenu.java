package com.example.appprototype1;

import com.example.appprototype1.apiary.ApiariesActivity;
import com.example.appprototype1.apiary.NoApiariesActivity;
import com.example.appprototype1.hive.NewHiveActivity;
import com.example.appprototype1.hive.NoHiveActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


public class MainMenu extends AppCompatActivity {

    Button apiariesButton;
    Button hivesButton;
    Button inspectionButton;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference database, database2;
    final ArrayList<String> apiariesList = new ArrayList<>();

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        firebaseAuth = FirebaseAuth.getInstance();
        apiariesButton = findViewById(R.id.button2);
        hivesButton = findViewById(R.id.button3);
        inspectionButton = findViewById(R.id.button4);

        apiariesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApiaries();
                Intent intent;
                if(!apiariesList.isEmpty()){
                    intent = new Intent(getApplicationContext(), ApiariesActivity.class);
                    intent.putExtra("apiariesList",apiariesList);
                }
                else {
                    intent = new Intent(getApplicationContext(), NoApiariesActivity.class);
                }
                startActivity(intent);
            }
        });

        hivesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NewHiveActivity.class);
//                getApiariesToHive(intent);
                intent.putExtra("apiary",apiariesList);
                startActivity(intent);

            }
        });

        inspectionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getApiaries();

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        getApiaries();
    }

    private void getApiaries(){
        uid = firebaseAuth.getCurrentUser().getUid();
        Toast.makeText(getApplicationContext(), "get apiaries", Toast.LENGTH_SHORT);//getApiaryData(str);
        database = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Apiaries");;
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                apiariesList.clear();
                for(DataSnapshot snapshot1: datasnapshot.getChildren()){
                    apiariesList.add(snapshot1.getKey());
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }



//    private void getApiaryData(String apiary){
//        firebaseAuth = FirebaseAuth.getInstance();
//        uid = firebaseAuth.getCurrentUser().getUid();
//        database2 = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Apiaries").child(apiary);
//        database2.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
//                if (datasnapshot.exists()) {
//                    name = datasnapshot.child("apiaryName").getValue().toString();
//                    location = datasnapshot.child("locationName").getValue().toString();
//                    notes = datasnapshot.child("notesName").getValue().toString();
//                }
//                Toast.makeText(getApplicationContext(),name + " " + location + " " + notes,Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//}
}