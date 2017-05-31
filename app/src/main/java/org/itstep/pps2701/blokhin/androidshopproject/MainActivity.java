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
    private final int REQUEST_PRODUCT = 1;
    private final int REQUEST_PRODUCT_EDIT = 2;

    SQLiteDatabase db;
    DBHelper helper;
    Cursor userCursor;

    Intent intent;

    Button btnAddProduct;
    ListView productListView;

    private static final String LOG_TAG = "SQLiteShop";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddProduct = (Button) findViewById(R.id.btnAddProduct);
        productListView = (ListView) findViewById(R.id.listProduct);

        btnAddProduct.setOnClickListener(this);

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                intent = new Intent(getApplicationContext(), ProductDialog.class);

                intent.putExtra("product", getProductById((int)id+1));
                startActivityForResult(intent, REQUEST_PRODUCT_EDIT);
            }
        });

        helper = new DBHelper(this);
        fillProductList();
    } // onCreate


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddProduct:
                intent = new Intent(this, ProductDialog.class);
                intent.putExtra("request", REQUEST_PRODUCT);
                startActivityForResult(intent, REQUEST_PRODUCT);
                break;
        } // switch
    } // onClick

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Product product;
            switch (requestCode) {
                case REQUEST_PRODUCT:
                    product = data.getParcelableExtra("product");
                    addProduct(product);
                    break;
                case REQUEST_PRODUCT_EDIT:
                    product = data.getParcelableExtra("product");
                    editProduct(product);
                    break;
            } // switch
        } // if
    } // onActivityResult

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
        helper.close();
    } // fillProductList

    private Product getProductById(int id){

        db = helper.getReadableDatabase();
        userCursor = db.rawQuery("select * from Goods where " +
                "_id =?", new String[]{String.valueOf(id)});
        userCursor.moveToFirst();
        int idIndex = userCursor.getColumnIndex("_id");
        int nameIndex = userCursor.getColumnIndex("name");
        int descIndex = userCursor.getColumnIndex("description");
        int priceIndex = userCursor.getColumnIndex("price");
        //helper.close();
        return new Product(userCursor.getInt(idIndex),
                userCursor.getString(nameIndex),
                userCursor.getString(descIndex),
                userCursor.getDouble(priceIndex));
    } // getProductById

    private void addProduct(Product prod){
        db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", prod.getName());
        cv.put("description", prod.getDescription());
        cv.put("price", prod.getPrice());
        db.insert("Goods", null, cv);
        helper.close();

        fillProductList();
        Toast.makeText(this, "Новый товар добавлен", Toast.LENGTH_SHORT).show();
    } // addProduct

    private void editProduct(Product prod){
        db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", prod.getName());
        cv.put("description", prod.getDescription());
        cv.put("price", prod.getPrice());
        db.update("Goods", cv, "_id" + "=" + String.valueOf(prod.getId()), null);

        helper.close();
        fillProductList();
        Toast.makeText(this, "Товар изменён", Toast.LENGTH_SHORT).show();
    } // addProduct

    @Override
    public void onResume() {
        super.onResume();
        fillProductList();
    } // onResume

} // class MainActivity
