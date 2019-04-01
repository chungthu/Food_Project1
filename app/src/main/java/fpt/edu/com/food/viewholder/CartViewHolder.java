package fpt.edu.com.food.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import fpt.edu.com.food.R;


public class CartViewHolder extends RecyclerView.ViewHolder {

    public ImageView imgItemCart;
    public TextView nameItemCart;
    public ElegantNumberButton nbtnItemCart;
    public TextView txtItemCart;
    public ImageView imgItemCartclear;



    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        imgItemCart = itemView.findViewById(R.id.img_itemCart);
        nameItemCart =  itemView.findViewById(R.id.name_itemCart);
        nbtnItemCart =  itemView.findViewById(R.id.nbtn_itemCart);
        txtItemCart =  itemView.findViewById(R.id.txt_itemCart);
        imgItemCartclear =  itemView.findViewById(R.id.img_itemCartclear);

    }
}
