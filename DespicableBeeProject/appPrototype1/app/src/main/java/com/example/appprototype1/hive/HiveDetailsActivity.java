package com.example.appprototype1.hive;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appprototype1.R;

public class HiveDetailsActivity extends AppCompatActivity {

    TextView hiveName,hiveSize,hiveType;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hive_details);

        initialize();
        setData();
    }

    public void initialize(){
        hiveName = findViewById(R.id.hiveDetailsHiveName);
        hiveSize = findViewById(R.id.hiveDetailsSize);
        hiveType = findViewById(R.id.hiveDetailsType);

        bundle = getIntent().getExtras();
    }

    public void setData(){
        hiveName.setText(bundle.getString("hivename"));
        hiveSize.setText(bundle.getString("hivesize"));
        hiveType.setText(bundle.getString("hivetype"));
    }
}