package fpt.edu.com.food.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fpt.edu.com.food.R;
import fpt.edu.com.food.model.Food;
import fpt.edu.com.food.ui.ProductAC;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Food> list;

    public ProductAdapter(Context context, ArrayList<Food> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView name, price;
        ImageView imageView;
        CardView cardView;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row == null){
            final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_product,null);

            holder.name = row.findViewById(R.id.txt_nameProduct);
            holder.price = row.findViewById(R.id.txt_priceProduct);
            holder.imageView = row.findViewById(R.id.image_item2);
            holder.cardView = row.findViewById(R.id.ll_item2);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, ""+list.get(position).getName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ProductAC.class);
                    intent.putExtra("namefood",list.get(position).getName());
                    context.startActivity(intent);
                }
            });

            double price_d = list.get(position).getPrice();
            String price_s = String.valueOf(price_d);

            holder.name.setText(list.get(position).getName());
            holder.price.setText(price_s);
            Picasso.with(context).load(list.get(position).getImage())
                    .into(holder.imageView);

        }
        else {
            holder = (ViewHolder) row.getTag();
        }


        return row;
    }
}
