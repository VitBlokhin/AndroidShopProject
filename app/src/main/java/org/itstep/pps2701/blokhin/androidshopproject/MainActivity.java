package org.itstep.pps2701.blokhin.androidshopproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.*;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    SQLiteDatabase db;
    DBManager dbManager;

    Intent intent;

    Button btnOpenProducts;

    private static final String LOG_TAG = "SQLiteShop";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DBManager(this);

        btnOpenProducts = (Button) findViewById(R.id.btnOpenProducts);

        btnOpenProducts.setOnClickListener(this);
    } // onCreate


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOpenProducts:
                intent = new Intent(this, ProductActivity.class);
                startActivity(intent);
                break;
        } // switch
    } // onClick





} // class MainActivity
