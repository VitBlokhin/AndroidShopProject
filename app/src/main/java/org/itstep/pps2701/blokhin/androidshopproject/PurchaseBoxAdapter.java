package org.itstep.pps2701.blokhin.androidshopproject;

import java.util.ArrayList;
import android.content.Context;
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
    ArrayList<Product> products;
    ArrayList<Purchase> purchases;

    // TODO !!!!
    PurchaseBoxAdapter(Context context, ArrayList<Product> products, ArrayList<Purchase> purchases) {
        ctx = context;
        this.products = products;
        this.purchases = purchases;
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

    public Product getProduct(int position) {
        return products.get(position);
    }
    public Purchase getPurchase(int position) {
        return purchases.get(position);
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

        Product pr = getProduct(position);
        Purchase pu = getPurchase(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и количество
        ((TextView) view.findViewById(R.id.txtName)).setText(pr.getName());
        ((TextView) view.findViewById(R.id.txtPrice)).setText(String.valueOf(pr.getPrice()) + " р.");
        ((EditText) view.findViewById(R.id.editText)).setText(String.valueOf(pu.getQuantity()));

        CheckBox chkBuy = (CheckBox) view.findViewById(R.id.chkBox);
        // присваиваем чекбоксу обработчик
        //chkBuy.setOnCheckedChangeListener(myCheckChangeList);
        // пишем позицию
        chkBuy.setTag(position);
        // заполняем данными из товаров: в корзине или нет
        //cbBuy.setChecked(p.box);
        return view;
    }

    // товар по позиции
    //ProductInOrder getProd(int position) {
    //    return ((ProductInOrder) getItem(position));
    //}

    // содержимое корзины
    ArrayList<Product> getBox() {
        ArrayList<Product> box = new ArrayList<Product>();
        for (Product p : products) {
            // если в корзине
            //if (p.box)
            //   box.add(p);
        }
        return box;
    }

    // обработчик для чекбоксов
    /*OnCheckedChangeListener myCheckChangeList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // меняем данные товара (в корзине или нет)
            getProduct((Integer) buttonView.getTag()).box = isChecked;
        }
    };*/
} // class org.itstep.pps2701.blokhin.androidshopproject.PurchaseBoxAdapter
