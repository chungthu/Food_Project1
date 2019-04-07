package fpt.edu.com.food.sqlDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.com.food.Contrants;
import fpt.edu.com.food.database.DatabaseHelper;
import fpt.edu.com.food.model.Order;
import fpt.edu.com.food.model.User;

public class OrderDetail implements Contrants {
    private DatabaseHelper databaseHelper;

    public OrderDetail(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void insertCart(Order order){

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_PRODUCT_ID,order.getProductId());
        values.put(COLUMN_PRODUCT_NAME,order.getProductName());
        values.put(COLUMN_QUANTITY,order.getQuantity());
        values.put(COLUMN_PRICE,order.getPrice());
        values.put(COLUMN_DISCOUNT,order.getDiscount());

        sqLiteDatabase.insert(TABLE_ORDER_DETAIL,null,values);
        sqLiteDatabase.close();
    }

    public List<Order> getAllCarts(){
        List<Order> orderList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        // viet cau lenh truy can toan bo danh sach user;
        String SELECT_ALL_USERS = "SELECT * FROM " + TABLE_ORDER_DETAIL;

        // cursor la doi tuong de chua ket qua truy van
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL_USERS, null);

        if (cursor.moveToFirst()) {
            do {

                String id = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME));
                String quantity = cursor.getString(cursor.getColumnIndex(COLUMN_QUANTITY));
                String price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE));
                String discount = cursor.getString(cursor.getColumnIndex(COLUMN_DISCOUNT));

                Order order = new Order();
                order.productId = id;
                order.productName = name;
                order.quantity = quantity;
                order.price = price;
                order.discount = discount;

                orderList.add(order);


            } while (cursor.moveToNext());

        }

        return orderList;
    }

    public void delete(){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }
}
