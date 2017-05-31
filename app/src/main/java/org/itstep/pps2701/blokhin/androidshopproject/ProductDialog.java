package org.itstep.pps2701.blokhin.androidshopproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProductDialog extends AppCompatActivity implements View.OnClickListener {

    private Product prod;
    private long prodId = -1;

    EditText edProdName;
    EditText edProdDesc;
    EditText edProdPrice;

    Button btnOk, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_dialog);

        edProdName = (EditText)findViewById(R.id.editProdName);
        edProdDesc = (EditText)findViewById(R.id.editProdDesc);
        edProdPrice = (EditText)findViewById(R.id.editProdPrice);

        Intent intent = getIntent();
        if(intent.hasExtra("product")) {
            prod = intent.getParcelableExtra("product");
            prodId = prod.getId();
            edProdName.setText(prod.getName());
            edProdDesc.setText(prod.getDescription());
            edProdPrice.setText(String.valueOf(prod.getPrice()));
        }

        btnOk = (Button)findViewById(R.id.btnOk);
        btnCancel = (Button)findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    } // onCreate

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch(v.getId()){
            case R.id.btnOk:
                try {
                    if(edProdName.getText().length() == 0) throw new IllegalArgumentException("Заполните все поля!");
                    if(edProdDesc.getText().length() == 0) throw new IllegalArgumentException("Заполните все поля!");
                    if(edProdPrice.getText().length() == 0) throw new IllegalArgumentException("Заполните все поля!");
                    prod = new Product(prodId,
                            edProdName.getText().toString(),
                            edProdDesc.getText().toString(),
                            Double.parseDouble(edProdPrice.getText().toString()));
                    intent.putExtra("product", prod);
                    setResult(RESULT_OK, intent);
                    break;
                } catch (Exception ex){
                    Toast.makeText(this, ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            case R.id.btnCancel:
                setResult(RESULT_CANCELED);
                break;
        }
        finish();
    } // onClick
} // class ProductDialog
