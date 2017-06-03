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
import org.itstep.pps2701.blokhin.androidshopproject.dataclasses.*;

/**
 * Created by Vit on 01.06.2017.
 */
public class PurchaseBoxAdapter extends BaseAdapter {
    Context cont;
    LayoutInflater lInflater;
    List<CartProduct> products;
    CartProduct product;

    //TextView txtName;
    //TextView txtPrice;
    //EditText editQuantity;

    PurchaseBoxAdapter(Context context, List<CartProduct> products) {
        cont = context;
        this.products = products;
        lInflater = (LayoutInflater) cont
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

        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = lInflater.inflate(R.layout.item_purchaselist, parent, false);

            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtPrice = (TextView) convertView.findViewById(R.id.txtPrice);
            holder.editQuantity = (EditText) convertView.findViewById(R.id.editQuantity);
            holder.chkBuy = (CheckBox) convertView.findViewById(R.id.chkBox);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.ref = position;
        product = getProduct(position);

        holder.txtName.setText(product.getName());
        holder.txtPrice.setText(String.valueOf(product.getPrice()) + " р.");
        holder.editQuantity.setText(String.valueOf(product.getQuantity()));

        holder.chkBuy.setOnCheckedChangeListener(holder.checkChangeList);
        holder.chkBuy.setTag(position);
        holder.chkBuy.setChecked(product.isInCart());


        holder.editQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    //holder.product.setQuantity(Integer.parseInt(s.toString()));
                    getProduct(holder.ref).setQuantity(Integer.parseInt(s.toString()));
                } catch (Exception ex){
                }
            }
        });

        return convertView;
    } // getView

    // товар по позиции
    CartProduct getProduct(int position) {
        return ((CartProduct) getItem(position));
    } // getProduct

    // содержимое корзины
    ArrayList<CartProduct> getCart() {
        ArrayList<CartProduct> cart = new ArrayList<>();
        for (CartProduct product : products) {
            // если в корзине
            if (product.isInCart())
              cart.add(product);
        }
        return cart;
    } // getCart

    private class ViewHolder{
        int ref;

        TextView txtName;
        TextView txtPrice;
        EditText editQuantity;
        CheckBox chkBuy;

        OnCheckedChangeListener checkChangeList = new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // меняем данные товара (в корзине или нет)
                getProduct(ref).setInCart(isChecked);
            }
        };
    } // class ViewHolder
} // class PurchaseBoxAdapter
