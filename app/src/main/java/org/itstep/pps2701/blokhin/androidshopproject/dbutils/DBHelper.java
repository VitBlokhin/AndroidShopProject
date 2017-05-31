package org.itstep.pps2701.blokhin.androidshopproject.dbutils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vit on 30.05.2017.
 */
public class DBHelper  extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "shopDB.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    } // DBHelper

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создать таблицу товаров
        db.execSQL("create table if not exists Goods (" +
                "_id integer primary key autoincrement, " +
                "name text, " +
                "description text, " +
                "price double);"
        );

        // Начальное заполнение таблицы товаров
        ContentValues cv = new ContentValues();
        String[] names = {"Апельсин", "Груша", "Яблоко"};
        String[] descs = {"Цитрусовый фрукт", "Плод грушового дерева", "Плод яблочного дерева"};
        double[] prices = {60.5, 32., 25.5};
        for (int i = 0; i < names.length; i++) {
            // добавить данные в формате поле - значение
            cv.put("name", names[i]);
            cv.put("description", descs[i]);
            cv.put("price", prices[i]);
            db.insert("Goods", null, cv);
        } // for i

        // создать таблицу товаров в заказе
        db.execSQL("create table Purchases (" +
                "_id integer primary key autoincrement, " +
                "order_id int, " +
                "goods_id int, " +
                "quantity int);"
        );

        // создать таблицу заказов
        db.execSQL("create table Orders (" +
                "_id integer primary key autoincrement, " +
                "number int, " +
                "date timestamp(6));"
        );
    } // onCreate

    // Для изменения структуры БД - изменение структуры таблиц, добавление
    // таблиц, удаление таблиц
    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    } // onUpgrade

} // class DBHelper
