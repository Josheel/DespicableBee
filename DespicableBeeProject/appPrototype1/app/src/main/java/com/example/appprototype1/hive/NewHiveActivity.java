package com.example.appprototype1.hive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.appprototype1.MainMenu;
import com.example.appprototype1.R;
import com.example.appprototype1.apiary.Apiary;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewHiveActivity extends AppCompatActivity {


    Spinner size;
    Spinner apiaryName;
    Spinner type;
    Spinner dropdown4;
    ElegantNumberButton elegantNumberButton;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    ArrayAdapter<String> adapter3;
    ArrayAdapter<String> adapter4;
    Button savebutton;

    DatabaseReference database;
    FirebaseAuth firebaseAuth;

    EditText newHiveET;
    Bundle bundle;

    String uid;
    String[] apiariesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_hive);

        initialize();;
        initializeAdapters();
        setOnClickListeners();
        getApiaries();
    }

    private void addHive(){
        Hive hive = new Hive(apiaryName.getSelectedItem().toString(),newHiveET.getText().toString().trim(),
                size.getSelectedItem().toString(),type.getSelectedItem().toString());
        database.child(hive.apiaryName).child(hive.hiveNumber).setValue(hive).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Apiary Added", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Failed to add apiary", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void getApiaries() {
        ArrayList<String> list = bundle.getStringArrayList("apiary");
        int i = 0;
        if(list.isEmpty())
        {
            apiariesList = new String[]{"No Apiares"};
            return;
        }
        apiariesList = new String[list.size()];
        for(String str: list) {
            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            apiariesList[i] = str;
            i++;
        }

    }


    private void initialize(){
        bundle = getIntent().getExtras();
        getApiaries();

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Apiaries");

        size = findViewById(R.id.newHiveSpinner);
        apiaryName = findViewById(R.id.newHiveSpinner2);
        type = findViewById(R.id.newHiveSpinner3);
        dropdown4 = findViewById(R.id.newHiveSpinner4);
        elegantNumberButton = (ElegantNumberButton)findViewById(R.id.elegantbutton);
        savebutton = findViewById(R.id.savebutton);

        newHiveET = findViewById(R.id.newHiveET);
    }


    private void initializeAdapters(){
        String[] items = new String[]{"4 frames", "5 frames", "8 frames", "10 frames"};
        String[] items2 = new String[]{"Extract", "From", "Database"};
        String[] items3 = new String[]{"Builder","Honey Production","Mating Nuc", "Mother Hive", "Nucleus", "Starter"};
        String[] item4 = new String[]{"White","Yellow","Red","Green","Blue"};

        adapter = new ArrayAdapter<>(this, R.layout.list_dropdown, items);
        adapter2 = new ArrayAdapter<>(this, R.layout.list_dropdown, apiariesList);
        adapter3 = new ArrayAdapter<>(this, R.layout.list_dropdown, items3);
        adapter4 = new ArrayAdapter<>(this, R.layout.list_dropdown,item4);

        size.setAdapter(adapter);
        apiaryName.setAdapter(adapter2);
        type.setAdapter(adapter3);
        dropdown4.setAdapter(adapter4);

    }

    private void setOnClickListeners(){

        elegantNumberButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = elegantNumberButton.getNumber();
                Log.e("NUM",num);
            }
        });

        savebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addHive();
                Intent intent = new Intent(v.getContext(), MainMenu.class);
                startActivity(intent);
            }
        });

        //        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 1) {
//                    items3 = new String[]{"Nucleus", "Mating Nuc"};
//                    //adapter3 = new ArrayAdapter<>(parent, R.layout.list_dropdown, items3);
//                    adapter3.notifyDataSetChanged();
//                    Toast.makeText(getApplicationContext(),"This works",Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }
}