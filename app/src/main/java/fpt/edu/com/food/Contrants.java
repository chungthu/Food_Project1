package fpt.edu.com.food;

public interface Contrants {
    String TABLE_ORDER_DETAIL = "ORDER_DETAIL";

    String COLUMN_PRODUCT_ID = "ProductID";
    String COLUMN_PRODUCT_NAME = "ProductName";
    String COLUMN_QUANTITY = "Quantity";
    String COLUMN_PRICE= "Price";
    String COLUMN_DISCOUNT = "Discount";

    String CREATE_TABLE_ORDER_DETAIL = "CREATE TABLE " + TABLE_ORDER_DETAIL + " (" +

            COLUMN_PRODUCT_ID + " TEXT," +
            COLUMN_PRODUCT_NAME + " TEXT," +
            COLUMN_QUANTITY + " TEXT,"+
            COLUMN_PRICE + " TEXT,"+
            COLUMN_DISCOUNT + " TEXT"
            + ")";
}
