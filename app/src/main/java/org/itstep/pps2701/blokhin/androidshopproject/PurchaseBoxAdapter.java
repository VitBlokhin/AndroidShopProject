package org.itstep.pps2701.blokhin.androidshopproject;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;
import org.itstep.pps2701.blokhin.androidshopproject.R;
import org.itstep.pps2701.blokhin.androidshopproject.dataclasses.*;

/**
 * Created by Vit on 01.06.2017.
 */
public class PurchaseBoxAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    List<BoxProduct> products;
    BoxProduct pr;

    TextView txtName;
    TextView txtPrice;
    EditText editQuantity;

    // TODO !!!!
    PurchaseBoxAdapter(Context context, List<BoxProduct> products) {
        ctx = context;
        this.products = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return products.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_purchaselist, parent, false);
        }

        pr = getProduct(position);

        txtName = (TextView) view.findViewById(R.id.txtName);
        txtPrice = (TextView) view.findViewById(R.id.txtPrice);
        editQuantity = (EditText) view.findViewById(R.id.editQuantity);
        editQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                pr.setQuantity(Integer.parseInt(editQuantity.getText().toString()));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pr.setQuantity(Integer.parseInt(editQuantity.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                pr.setQuantity(Integer.parseInt(editQuantity.getText().toString()));
            }
        });

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и количество
        txtName.setText(pr.getProduct().getName());
        txtPrice.setText(String.valueOf(pr.getProduct().getPrice()) + " р.");
        editQuantity.setText(String.valueOf(pr.getQuantity()));

        //btnSetQuantity.setTag(position);

        CheckBox chkBuy = (CheckBox) view.findViewById(R.id.chkBox);
        // присваиваем чекбоксу обработчик
        chkBuy.setOnCheckedChangeListener(checkChangeList);
        // пишем позицию
        chkBuy.setTag(position);
        // заполняем данными из товаров: в корзине или нет
        chkBuy.setChecked(pr.isBoxed());
        return view;
    }

    // товар по позиции
    BoxProduct getProduct(int position) {
        return ((BoxProduct) getItem(position));
    }

    // содержимое корзины
    ArrayList<BoxProduct> getBox() {
        ArrayList<BoxProduct> box = new ArrayList<>();
        for (BoxProduct p : products) {
            // если в корзине
            if (p.isBoxed())
              box.add(p);
        }
        return box;
    }

    // обработчик для чекбоксов
    OnCheckedChangeListener checkChangeList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // меняем данные товара (в корзине или нет)
            getProduct((Integer) buttonView.getTag()).setBoxed(isChecked);
        }
    };
} // class PurchaseBoxAdapter
