package org.itstep.pps2701.blokhin.androidshopproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import org.itstep.pps2701.blokhin.androidshopproject.dataclasses.Product;
import org.itstep.pps2701.blokhin.androidshopproject.dbutils.DBManager;

import java.util.List;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {
    private final int REQUEST_PRODUCT = 1;
    private final int REQUEST_PRODUCT_EDIT = 2;

    private DBManager dbManager;

    Intent intent;

    Button btnAddProduct, btnBack;
    ListView productListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        try {
            dbManager = new DBManager(this);
            dbManager.open();

            btnAddProduct = (Button) findViewById(R.id.btnAddProduct);
            btnBack = (Button) findViewById(R.id.btnBack);
            productListView = (ListView) findViewById(R.id.listProduct);

            btnAddProduct.setOnClickListener(this);
            btnBack.setOnClickListener(this);

            productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Product prod = (Product)productListView.getItemAtPosition(position);
                    intent = new Intent(getApplicationContext(), ProductDialog.class);
                    intent.putExtra("productId", prod.getId());
                    startActivityForResult(intent, REQUEST_PRODUCT_EDIT);
                }
            });

            fillProductList();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddProduct:
                intent = new Intent(this, ProductDialog.class);
                startActivityForResult(intent, REQUEST_PRODUCT);
                break;
            case R.id.btnBack:
                finish();
                break;
        } // switch
    } // onClick

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Product product = data.getParcelableExtra("product");
            switch (requestCode) {
                case REQUEST_PRODUCT:
                    dbManager.addProduct(product);
                    Toast.makeText(this,"Товар добавлен" ,Toast.LENGTH_LONG).show();
                    break;
                case REQUEST_PRODUCT_EDIT:
                    dbManager.updateProduct(product);
                    Toast.makeText(this,"Товар добавлен" ,Toast.LENGTH_LONG).show();
                    break;
            } // switch
        } // if
    } // onActivityResult

    private void fillProductList(){
        List<Product> prodList = dbManager.getAllProducts();

        ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(this,
                android.R.layout.simple_list_item_1, prodList);
        productListView.setAdapter(adapter);

        dbManager.close();
    } // fillProductList

    @Override
    public void onResume() {
        super.onResume();
        fillProductList();
    } // onResume
} // class ProductActivity
