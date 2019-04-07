package fpt.edu.com.food.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import fpt.edu.com.food.R;
import fpt.edu.com.food.model.Order;
import fpt.edu.com.food.viewholder.Cart1ViewHolder;

public class CartAdapter extends RecyclerView.Adapter<Cart1ViewHolder> {

    private Context contet;
    private List<Order> list;

    public CartAdapter(Context contet, List<Order> list) {
        this.contet = contet;
        this.list = list;
    }

    @NonNull
    @Override
    public Cart1ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(contet).inflate(R.layout.item_cart, viewGroup, false);
        return new Cart1ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Cart1ViewHolder holder, int i) {
        holder.txt_item_cart_name.setText(list.get(i).productName);
//        holder.txt_item_cart_price.setText("$ "+ list.get(i).price);

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(""+ list.get(i).getQuantity(), Color.RED);
        holder.img_item_cart.setImageDrawable(drawable);

        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(list.get(i).getPrice())) * (Integer.parseInt(list.get(i).getQuantity()));
        holder.txt_item_cart_price.setText(fmt.format(price));

    }

    @Override
    public int getItemCount() {
        if (list == null){
            return 0;
        }
        return list.size();
    }
}
