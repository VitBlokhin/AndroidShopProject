package org.itstep.pps2701.blokhin.androidshopproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.*;
import android.widget.*;
import org.itstep.pps2701.blokhin.androidshopproject.dbutils.*;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    SQLiteDatabase db;
    DBManager dbManager;

    Intent intent;

    Button btnOpenProducts, btnExit;

    private static final String LOG_TAG = "SQLiteShop";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DBManager(this);

        btnOpenProducts = (Button) findViewById(R.id.btnOpenProducts);
        btnExit = (Button) findViewById(R.id.btnExit);

        btnOpenProducts.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    } // onCreate


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOpenProducts:
                intent = new Intent(this, ProductActivity.class);
                startActivity(intent);
                break;
            case R.id.btnExit:
                android.os.Process.killProcess(android.os.Process.myPid());
        } // switch
    } // onClick





} // class MainActivity
