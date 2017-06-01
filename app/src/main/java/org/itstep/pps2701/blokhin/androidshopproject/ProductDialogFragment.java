package org.itstep.pps2701.blokhin.androidshopproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import org.itstep.pps2701.blokhin.androidshopproject.dataclasses.Order;
import org.itstep.pps2701.blokhin.androidshopproject.dataclasses.Product;

import java.util.List;

import android.widget.AdapterView.*;
import org.itstep.pps2701.blokhin.androidshopproject.dataclasses.Purchase;

/**
 * Created by Vit on 31.05.2017.
 */
public class ProductDialogFragment extends DialogFragment implements View.OnClickListener{

    private Product prod;

    Spinner spinnerProduct;
    EditText editQuantity;
    Button btnAdd, btnCancel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_product_dialog, null);
        builder.setView(view);
        // Остальной код
        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        spinnerProduct = (Spinner)view.findViewById(R.id.spinnerProduct);
        fillProductSpinner();

        OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prod = (Product)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinnerProduct.setOnItemSelectedListener(itemSelectedListener);

        editQuantity = (EditText)view.findViewById(R.id.editQuantity);

        return builder.create();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnAdd:
                // TODO: переделать, не передавать сюда Order, возвращать Purchase
                //Purchase = getArguments().getParcelable("purchase");
                //order.addToPurchaseList(
                        //new Purchase(0
                        //prod.getId(),
                        //Integer.parseInt(editQuantity.getText().toString())));
                break;
            case R.id.btnCancel:
                break;
        }
    }

    private void fillProductSpinner(){
        List<Product> prodList = getArguments().getParcelable("products");

        ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(getContext(), android.R.layout.simple_spinner_item, prodList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProduct.setAdapter(adapter);
    } // fillProductSpinner
} // class ProductDialogFragment
