package org.itstep.pps2701.blokhin.androidshopproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vit on 31.05.2017.
 */
public class DBManager {
    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    private Cursor cursor;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }


    //> Операции с таблицей товаров
    public Product getProductById(long id){
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from Goods where " +
                "_id =?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        int idIndex = cursor.getColumnIndex("_id");
        int nameIndex = cursor.getColumnIndex("name");
        int descIndex = cursor.getColumnIndex("description");
        int priceIndex = cursor.getColumnIndex("price");

        return new Product(cursor.getLong(idIndex),
                cursor.getString(nameIndex),
                cursor.getString(descIndex),
                cursor.getDouble(priceIndex));
    } // getProductById

    public Product getProductByName(String name){
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from Goods where " +
                "name =?", new String[]{name});
        cursor.moveToFirst();
        int idIndex = cursor.getColumnIndex("_id");
        int nameIndex = cursor.getColumnIndex("name");
        int descIndex = cursor.getColumnIndex("description");
        int priceIndex = cursor.getColumnIndex("price");

        return new Product(cursor.getLong(idIndex),
                cursor.getString(nameIndex),
                cursor.getString(descIndex),
                cursor.getDouble(priceIndex));
    } // getProductByName

    public List<Product> getAllProducts(){
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from Goods", null);
        List<Product> prodList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("_id");
            int nameIndex = cursor.getColumnIndex("name");
            int descIndex = cursor.getColumnIndex("description");
            int priceIndex = cursor.getColumnIndex("price");
            do {
                prodList.add(new Product(cursor.getLong(idIndex),
                        cursor.getString(nameIndex),
                        cursor.getString(descIndex),
                        cursor.getDouble(priceIndex)));
            } while (cursor.moveToNext());
        } // if
        close();
        return prodList;
    } // getAllProducts

    public void addProduct(Product prod){
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", prod.getName());
        cv.put("description", prod.getDescription());
        cv.put("price", prod.getPrice());
        db.insert("Goods", null, cv);
        close();
    } // addProduct

    public void updateProduct(Product prod){
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", prod.getName());
        cv.put("description", prod.getDescription());
        cv.put("price", prod.getPrice());
        db.update("Goods", cv, "_id" + "=" + String.valueOf(prod.getId()), null);

        close();
    } // updateProduct
    // Операции с таблицей товаров <//

    //> Операции с таблицей заказов
    public Order getOrderById(long id){

        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from Orders where " +
                "_id =?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        int idIndex = cursor.getColumnIndex("_id");
        int numberIndex = cursor.getColumnIndex("number");
        int dateIndex = cursor.getColumnIndex("date");

        Order order = new Order(cursor.getLong(idIndex),
                cursor.getInt(numberIndex),
                cursor.getLong(dateIndex));
        close();

        // добавляем информацию о товарах в заказе
        for(Purchase purchase : getPurchasesByOrderId(order.getId())) {
            order.addToPurchaseList(purchase);
        }
        return order;
    } // getProductById

    public Order getOrderByNumber(int num){
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from Orders where " +
                "number =?", new String[]{String.valueOf(num)});
        cursor.moveToFirst();
        int idIndex = cursor.getColumnIndex("_id");
        int numberIndex = cursor.getColumnIndex("number");
        int dateIndex = cursor.getColumnIndex("date");

        Order order = new Order(cursor.getLong(idIndex),
                cursor.getInt(numberIndex),
                cursor.getLong(dateIndex));
        close();
        return order;
    } // getOrderByNumber

    public List<Order> getAllOrders(){
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from Orders", null);
        List<Order> orderList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("_id");
            int numberIndex = cursor.getColumnIndex("number");
            int dateIndex = cursor.getColumnIndex("date");
            do {
                Order order = new Order(cursor.getLong(idIndex),
                        cursor.getInt(numberIndex),
                        cursor.getLong(dateIndex));
                for(Purchase purchase : getPurchasesByOrderId(order.getId())) {
                    order.addToPurchaseList(purchase);
                }
                orderList.add(order);
            } while (cursor.moveToNext());
        } // if
        close();
        return orderList;
    } // getAllOrders

    public void addOrder(Order order){
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("number", order.getNumber());
        cv.put("date", order.getDate().toString());
        db.insert("Orders", null, cv);
        cv = new ContentValues();

        // получаем последний созданный заказ для записи его id в таблицу товаров в заказе
        cursor = db.rawQuery("select _id from Orders order by _id desc limit 1", null);
        long id = cursor.getLong(cursor.getColumnIndex("_id"));
        //order.setId(id);
        close();
        for(Purchase purchase : order.getPurchaseList()) {
            purchase.setOrderId(id);
            addPurchase(purchase);
        }
        close();
    } // addOrder

    public void updateOrder(Order order){
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("number", order.getNumber());
        cv.put("date", order.getDate().toString());
        db.update("Orders", cv, "_id" + "=" + String.valueOf(order.getId()), null);
        close();

        removePurchasesByOrderId(order.getId());                // удаляем все товары из заказа в бд,
        for(Purchase purchase : order.getPurchaseList()) {      // добавляем новые товары из изменённого заказа
            addPurchase(purchase);
        }
        close();
    } // updateOrder

    public void removeOrder(Order order){
        db = dbHelper.getWritableDatabase();
        db.delete("Orders", "_id=" + order.getId(), null);
        close();
        removePurchasesByOrderId(order.getId());
    } // removeOrder
    // Операции с таблицей заказов <//


    //> Операции с таблицей товаров в заказе
    // TODO
    public Purchase getPurchaseById(long id){
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from Purchases where " +
                "_id =?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        int idIndex = cursor.getColumnIndex("_id");
        int orderIndex = cursor.getColumnIndex("order_id");
        int productIndex = cursor.getColumnIndex("goods_id");
        int quantityIndex = cursor.getColumnIndex("quantity");

        return new Purchase(cursor.getLong(idIndex),
                cursor.getLong(orderIndex),
                cursor.getLong(productIndex),
                cursor.getInt(quantityIndex));
    } // getPurchaseById

    public List<Purchase> getPurchasesByOrderId(long id){
        db = dbHelper.getReadableDatabase();
        List<Purchase> purchList= new ArrayList<>();
        cursor = db.rawQuery("select * from Purchases where " +
                "order_id =?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("_id");
            int orderIndex = cursor.getColumnIndex("order_id");
            int productIndex = cursor.getColumnIndex("goods_id");
            int quantityIndex = cursor.getColumnIndex("quantity");
            do {
                purchList.add(new Purchase(cursor.getLong(idIndex),
                        cursor.getLong(orderIndex),
                        cursor.getLong(productIndex),
                        cursor.getInt(quantityIndex)));
            } while (cursor.moveToNext());
        } // if
        close();
        return purchList;
    } // getPurchasesByOrderId

    public void addPurchase(Purchase purchase){
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("order_id", purchase.getId());
        cv.put("goods_id", purchase.getProductId());
        cv.put("quantity", purchase.getQuantity());
        db.insert("Purchases", null, cv);
        close();
    } // addPurchase

    public void removePurchase(Purchase purchase){
        db = dbHelper.getWritableDatabase();
        db.delete("Purchases", "_id=" + purchase.getId(), null);
        close();
    } // removePurchase

    public void removePurchasesByOrderId(long orderId){
        db = dbHelper.getWritableDatabase();
        db.delete("Purchases", "order_id=" + orderId, null);
        close();
    } // removePurchasesByOrderId

    public void removePurchasesByProductId(long productId){
        db = dbHelper.getWritableDatabase();
        db.delete("Purchases", "goods_id=" + productId, null);
        close();
    } // removePurchasesByProductId
    // Операции с таблицей товаров в заказе <//

} // class DBManager
