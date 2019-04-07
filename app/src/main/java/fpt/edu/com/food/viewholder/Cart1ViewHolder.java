package fpt.edu.com.food.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fpt.edu.com.food.R;

public class Cart1ViewHolder extends RecyclerView.ViewHolder {

    public TextView txt_item_cart_name;
    public TextView txt_item_cart_price;
    public ImageView img_item_cart;

    public Cart1ViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_item_cart_name = itemView.findViewById(R.id.cart_item_name);
        txt_item_cart_price = itemView.findViewById(R.id.cart_item_price);
        img_item_cart = itemView.findViewById(R.id.cart_item_image);

    }
}
