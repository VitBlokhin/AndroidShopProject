package org.itstep.pps2701.blokhin.androidshopproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.*;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener, ISelectedProduct {
    private final int REQUEST_PRODUCT = 1;
    private final int REQUEST_PRODUCT_EDIT = 2;

    SQLiteDatabase db;
    DBHelper helper;
    Cursor userCursor;
    long prodId = 0;

    Button btnAddProduct;
    Button btnEditProduct;
    ListView productListView;

    private static final String LOG_TAG = "SQLiteShop";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddProduct = (Button) findViewById(R.id.btnAddProduct);
        btnEditProduct = (Button) findViewById(R.id.btnEditProduct);
        productListView = (ListView) findViewById(R.id.listProduct);

        btnAddProduct.setOnClickListener(this);
        btnEditProduct.setOnClickListener(this);

        helper = new DBHelper(this);
        fillProductList();
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.btnAddProduct:
                intent = new Intent(this, ProductDialog.class);
                startActivityForResult(intent, REQUEST_PRODUCT);
                break;
            case R.id.btnEditProduct:
                Toast.makeText(this, "Функционал еще не добавлен", Toast.LENGTH_SHORT).show();
                break;
        } // switch
    } // onClick

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PRODUCT:
                    Product product = data.getParcelableExtra("product");
                    addProduct(product);
                    break;
                case REQUEST_PRODUCT_EDIT:
                    break;
            }
            // если вернулось не ОК
        } else {
            Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
        }
    }

    private void fillProductList(){
        db = helper.getReadableDatabase();
        userCursor = db.rawQuery("select * from Goods", null);

        if (userCursor.moveToFirst()) {
            List<Product> prodList = new ArrayList<>();
            int idIndex = userCursor.getColumnIndex("_id");
            int nameIndex = userCursor.getColumnIndex("name");
            int descIndex = userCursor.getColumnIndex("description");
            int priceIndex = userCursor.getColumnIndex("price");
            do {
                prodList.add(new Product(userCursor.getInt(idIndex),
                        userCursor.getString(nameIndex),
                        userCursor.getString(descIndex),
                        userCursor.getDouble(priceIndex)));
            } while (userCursor.moveToNext());

            ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(this,
                    android.R.layout.simple_list_item_1, prodList);
            productListView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Строк в таблице: 0",Toast.LENGTH_LONG).show();
        } // if
    }

    private void addProduct(Product prod){
        ContentValues cv = new ContentValues();
        cv.put("name", prod.getName());
        cv.put("description", prod.getDescription());
        cv.put("price", prod.getPrice());
        db.insert("Goods", null, cv);
        fillProductList();
        Toast.makeText(this, "Новый товар добавлен", Toast.LENGTH_SHORT).show();
    } // addProduct

    @Override
    public void onResume() {
        super.onResume();
        fillProductList();
    }

    @Override
    public void onSelectedData(Product product) {

    }
}
