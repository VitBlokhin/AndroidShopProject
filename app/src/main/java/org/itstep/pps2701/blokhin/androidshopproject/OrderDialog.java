package org.itstep.pps2701.blokhin.androidshopproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import org.itstep.pps2701.blokhin.androidshopproject.dataclasses.Purchase;
import org.itstep.pps2701.blokhin.androidshopproject.dbutils.DBManager;

public class OrderDialog extends AppCompatActivity implements View.OnClickListener{

    private final int REQUEST_PURCHASE = 1;
    private final int REQUEST_PURCHASE_EDIT = 2;

    private DBManager dbManager;

    Intent intent;

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

            btnAddPurchase.setOnClickListener(this);
            btnOk.setOnClickListener(this);
            btnCancel.setOnClickListener(this);

            purchaseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Purchase selectedPurchase = (Purchase)purchaseListView.getItemAtPosition(position);
                    ProductDialogFragment prodDialog = new ProductDialogFragment();
                    Bundle args = new Bundle();
                    args.putParcelable("purchaseId", selectedPurchase);
                    prodDialog.setArguments(args);
                    prodDialog.show(getSupportFragmentManager(), "custom");
                }
            });

            fillPurchaseList();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View v) {

    }

    private void fillPurchaseList() {

    }
}
