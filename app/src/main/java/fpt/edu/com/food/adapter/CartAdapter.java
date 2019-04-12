package fpt.edu.com.food.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import fpt.edu.com.food.R;
import fpt.edu.com.food.database.DatabaseHelper;
import fpt.edu.com.food.model.Order;
import fpt.edu.com.food.sqlDao.OrderDetail;
import fpt.edu.com.food.ui.CartAC;
import fpt.edu.com.food.viewholder.Cart1ViewHolder;

public class CartAdapter extends RecyclerView.Adapter<Cart1ViewHolder> {

    private Context context;
    private List<Order> list;
    private DatabaseHelper databaseHelper;
    private OrderDetail orderDetail;


    public CartAdapter(Context context, List<Order> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public Cart1ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_request, viewGroup, false);
        return new Cart1ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Cart1ViewHolder holder, final int i) {
        databaseHelper = new DatabaseHelper(context);
        orderDetail = new OrderDetail(databaseHelper);
        holder.txt_item_cart_name.setText(list.get(i).getProductName());
//        holder.txt_item_cart_price.setText("$ "+ list.get(i).price);

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(""+ list.get(i).getQuantity(), Color.RED);
        holder.img_item_cart.setImageDrawable(drawable);

        final Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        double price = (Double.parseDouble(list.get(i).getPrice())) * (Double.parseDouble(list.get(i).getQuantity()));
        holder.txt_item_cart_price.setText(fmt.format(price));
        Picasso.with(context).load(list.get(i).getImage())
                .into(holder.img_item_cart1);

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderDetail.deleteID(list.get(i).productName);
                notifyDataSetChanged();
                list.remove(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (list == null){
            return 0;
        }
        return list.size();
    }
}
