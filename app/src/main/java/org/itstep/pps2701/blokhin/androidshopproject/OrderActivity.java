package org.itstep.pps2701.blokhin.androidshopproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import org.itstep.pps2701.blokhin.androidshopproject.dataclasses.Order;
import org.itstep.pps2701.blokhin.androidshopproject.dbutils.DBManager;

import java.util.List;

public class OrderActivity extends AppCompatActivity  implements View.OnClickListener {
    private final int REQUEST_ORDER = 1;
    private final int REQUEST_ORDER_EDIT = 2;

    private DBManager dbManager;

    Intent intent;

    Button btnAddOrder, btnBack;
    ListView orderListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        try {
            dbManager = new DBManager(this);
            dbManager.open();

            btnAddOrder = (Button) findViewById(R.id.btnAddOrder);
            btnBack = (Button) findViewById(R.id.btnBack);
            orderListView = (ListView) findViewById(R.id.listOrder);

            btnAddOrder.setOnClickListener(this);
            btnBack.setOnClickListener(this);

            orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Order order = (Order)orderListView.getItemAtPosition(position);
                    intent = new Intent(getApplicationContext(), OrderDialog.class);
                    intent.putExtra("orderId", order.getId());
                    startActivityForResult(intent, REQUEST_ORDER_EDIT);
                }
            });

            fillOrderList();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    } // onCreate

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddOrder:
                intent = new Intent(this, OrderDialog.class);
                startActivityForResult(intent, REQUEST_ORDER);
                break;
            case R.id.btnBack:
                finish();
                break;
        } // switch
    } // onClick

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Order order = data.getParcelableExtra("order");
            switch (requestCode) {
                case REQUEST_ORDER:
                    dbManager.addOrder(order);
                    Toast.makeText(this,"Заказ добавлен" ,Toast.LENGTH_LONG).show();
                    break;
                case REQUEST_ORDER_EDIT:
                    dbManager.updateOrder(order);
                    Toast.makeText(this,"Заказ изменён" ,Toast.LENGTH_LONG).show();
                    break;
            } // switch
        } // if
        //fillOrderList();
    } // onActivityResult

    private void fillOrderList() {
        List<Order> orderList = dbManager.getAllOrders();
        //List<Order> orderList = dbManager.ordersByProduct(dbManager.getProductById(1)); // проверка: поиск заказов с определённым товаром


        ArrayAdapter<Order> adapter = new ArrayAdapter<Order>(this,
                android.R.layout.simple_list_item_1, orderList);
        orderListView.setAdapter(adapter);

        dbManager.close();
    } // fillOrderList

    @Override
    public void onResume() {
        super.onResume();
        fillOrderList();
    } // onResume
}
