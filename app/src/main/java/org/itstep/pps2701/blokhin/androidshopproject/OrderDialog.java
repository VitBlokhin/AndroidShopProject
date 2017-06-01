package org.itstep.pps2701.blokhin.androidshopproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import org.itstep.pps2701.blokhin.androidshopproject.dataclasses.Order;
import org.itstep.pps2701.blokhin.androidshopproject.dataclasses.Product;
import org.itstep.pps2701.blokhin.androidshopproject.dataclasses.ProductInOrder;
import org.itstep.pps2701.blokhin.androidshopproject.dataclasses.Purchase;
import org.itstep.pps2701.blokhin.androidshopproject.dbutils.DBManager;

import java.util.ArrayList;
import java.util.List;

public class OrderDialog extends AppCompatActivity implements View.OnClickListener{

    private final int REQUEST_PURCHASE = 1;
    private final int REQUEST_PURCHASE_EDIT = 2;

    private Order order;
    private long orderId = -1;

    private DBManager dbManager;

    Intent intent;

    TextView txtHeader, txtProdCnt, txtProdSum;
    Button btnAddPurchase, btnOk, btnCancel;
    ListView purchaseListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_dialog);

        try {
            dbManager = new DBManager(this);
            dbManager.open();

            btnAddPurchase = (Button) findViewById(R.id.btnAddPurchase);
            btnOk = (Button) findViewById(R.id.btnOk);
            btnCancel = (Button) findViewById(R.id.btnCancel);
            purchaseListView = (ListView) findViewById(R.id.listPurchase);

            intent = getIntent();
            if(intent.hasExtra("orderId")) {
                //prod = intent.getParcelableExtra("product");
                orderId = intent.getLongExtra("orderId", orderId);
                order = dbManager.getOrderById(orderId);
                txtHeader.setText("Заказ №" + order.getNumber() + " от " + order.getDate());

                txtProdCnt.setText("Товаров: " + dbManager.getPurchaseCountByOrderId(orderId));
                txtProdSum.setText("На сумму " + dbManager.getPurchaseTotalSumByOrderId(orderId) + " р.");
            }

            btnAddPurchase.setOnClickListener(this);
            btnOk.setOnClickListener(this);
            btnCancel.setOnClickListener(this);

            /*purchaseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Purchase selectedPurchase = ((ProductInOrder)purchaseListView.getItemAtPosition(position)).getPurchase();
                    ProductDialogFragment prodDialog = new ProductDialogFragment();
                    Bundle args = new Bundle();
                    args.putParcelable("purchaseId", selectedPurchase);
                    prodDialog.setArguments(args);
                    prodDialog.show(getSupportFragmentManager(), "custom");
                }
            });*/

            if(orderId >= 0) fillPurchaseList();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View v) {

    }

    private void fillPurchaseList() {
        List<Product> productList = new ArrayList<>();
        List<Purchase> purchaseList = new ArrayList<>();
        for(Purchase purchase : dbManager.getPurchasesByOrderId(orderId)) {
            productInOrderList.add(new ProductInOrder(
                    purchase, dbManager.getProductById(purchase.getProductId())
            ));
        }

        PurchaseBoxAdapter adapter;
        adapter = new PurchaseBoxAdapter(this, productList, purchaseList);
        purchaseListView.setAdapter(adapter);

        dbManager.close();
    } // fillPurchaseList
}
