package org.itstep.pps2701.blokhin.androidshopproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.*;
import org.itstep.pps2701.blokhin.androidshopproject.dataclasses.BoxProduct;
import org.itstep.pps2701.blokhin.androidshopproject.dataclasses.Order;
import org.itstep.pps2701.blokhin.androidshopproject.dataclasses.Product;
import org.itstep.pps2701.blokhin.androidshopproject.dataclasses.Purchase;
import org.itstep.pps2701.blokhin.androidshopproject.dbutils.DBManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderDialog extends AppCompatActivity implements View.OnClickListener{

    private Calendar dateAndTime = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

    private Order order;
    private long orderId = -1;

    private DBManager dbManager;

    Intent intent;

    TextView txtProdCnt, txtProdSum;
    EditText editOrderNum, editOrderDate;
    Button btnOk, btnCancel;
    ListView purchaseListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_dialog);

        try {
            dbManager = new DBManager(this);
            dbManager.open();

            editOrderNum = (EditText)findViewById(R.id.editOrderNum);
            editOrderDate = (EditText)findViewById(R.id.editOrderDate);

            txtProdCnt = (TextView)findViewById(R.id.txtProdCnt);
            txtProdSum = (TextView)findViewById(R.id.txtProdSum);

            btnOk = (Button) findViewById(R.id.btnOk);
            btnCancel = (Button) findViewById(R.id.btnCancel);
            purchaseListView = (ListView) findViewById(R.id.listPurchase);

            editOrderDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(OrderDialog.this, dateListener,
                            dateAndTime.get(Calendar.YEAR),
                            dateAndTime.get(Calendar.MONTH),
                            dateAndTime.get(Calendar.DAY_OF_MONTH))
                            .show();
                }
            });

            intent = getIntent();
            if(intent.hasExtra("orderId")) {
                //prod = intent.getParcelableExtra("product");
                orderId = intent.getLongExtra("orderId", orderId);
                order = dbManager.getOrderById(orderId);
                editOrderNum.setText(String.valueOf(order.getNumber()));
                editOrderDate.setText(df.format(order.getDate()));

                txtProdCnt.setText("Товаров: " + dbManager.getPurchaseCountByOrderId(orderId));
                txtProdSum.setText("На сумму " + dbManager.getPurchaseTotalSumByOrderId(orderId) + " р.");
            }

            btnOk.setOnClickListener(this);
            btnCancel.setOnClickListener(this);

            fillPurchaseList();

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btnOk:
                try {
                    //if(intent.hasExtra("request"))
                    order = new Order(orderId,
                            Integer.parseInt(editOrderNum.getText().toString()),
                            df.parse(editOrderDate.getText().toString()));
                    for(BoxProduct boxProduct : ((PurchaseBoxAdapter) purchaseListView.getAdapter()).getBox()) {
                        order.addToPurchaseList(new Purchase(orderId, boxProduct.getProduct().getId(), boxProduct.getQuantity()));
                    }
                    intent.putExtra("order", order);

                    setResult(RESULT_OK, intent);
                    break;
                } catch (Exception ex){
                    Toast.makeText(this, ex.getMessage(),Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnCancel:
                setResult(RESULT_CANCELED);
                break;
        } // switch
        finish();
    } // onClick

    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear+=1;
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            editOrderDate.setText(dayOfMonth + "-" + monthOfYear + "-" + year);
        }
    };


    private void fillPurchaseList() {
        List<BoxProduct> boxProductList = new ArrayList<>();
        List<Product> productList = new ArrayList<>(dbManager.getAllProducts());
        if(orderId >= 0) {
            List<Purchase> purchaseList = new ArrayList<>(dbManager.getPurchasesByOrderId(orderId));
            if(purchaseList.isEmpty()) {
                for(Product product : productList) {
                    boxProductList.add(new BoxProduct(product));
                }
            } else
            for(Purchase purchase : purchaseList) {
                for(Product product : productList) {
                    if(purchase.getProductId() == product.getId())
                        boxProductList.add(new BoxProduct(product, true, purchase.getQuantity()));
                    else boxProductList.add(new BoxProduct(product));
                }
            }
        } else {
            for(Product product : productList) {
                boxProductList.add(new BoxProduct(product));
            }
        }

        PurchaseBoxAdapter adapter = new PurchaseBoxAdapter(this, boxProductList);
        purchaseListView.setAdapter(adapter);

        dbManager.close();
    } // fillPurchaseList
}
