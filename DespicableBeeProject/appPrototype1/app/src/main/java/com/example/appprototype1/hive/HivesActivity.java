package com.example.appprototype1.hive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appprototype1.R;
import com.example.appprototype1.apiary.Apiary;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HivesActivity extends AppCompatActivity implements HiveAdapter.OnNoteListener{

    Bundle bundle;
    TextView apiaryName, apiaryLocation, apiaryNotes;
    String name,location,notes, uid, hiveName,size,type;
    RecyclerView hivesRV;
    FirebaseAuth firebaseAuth;
    DatabaseReference database, database2;
    final ArrayList<Hive> hives = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hives);
        initialize();
        setTextViews();
        getHives();
    }

    public void initialize(){
        apiaryName = findViewById(R.id.hiveApiaryName);
        apiaryLocation = findViewById(R.id.hiveApiaryLocation);
        apiaryNotes = findViewById(R.id.hiveApiaryNotes);
        hivesRV = findViewById(R.id.hivesRV);

        bundle = getIntent().getExtras();

        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
    }

    public void setTextViews(){
        name = bundle.getString("name");
        location = bundle.getString("location");
        notes = bundle.getString("notes");

        apiaryName.setText(name);
        apiaryLocation.setText(location);
        apiaryNotes.setText(notes);
    }

    public void setupAdapter(){
        HiveAdapter hiveAdapter = new HiveAdapter(hives,this);
        hivesRV = (RecyclerView) findViewById(R.id.hivesRV);
        hivesRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        hivesRV.setAdapter(hiveAdapter);
    }

    private void getHives(){
        hives.clear();
        database = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Apiaries").child(name);;
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot1: datasnapshot.getChildren()) {
                    if(snapshot1.hasChildren()) {
                        hives.add(snapshot1.getValue(Hive.class));
                    }
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
            Intent intent = new Intent(this, HiveDetailsActivity.class);
            intent.putExtra("apiaryname",hives.get(position).apiaryName);
            intent.putExtra("hivename",hives.get(position).hiveNumber);
            intent.putExtra("hivesize",hives.get(position).size);
            intent.putExtra("hivetype",hives.get(position).type);
            startActivity(intent);
    }

}