package fpt.edu.com.food.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fpt.edu.com.food.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    public TextView txt_Category;
    public ImageView img_Category;
    public CardView card_Category;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_Category = itemView.findViewById(R.id.txt_category);
        img_Category = itemView.findViewById(R.id.img_category);
        card_Category = itemView.findViewById(R.id.cardview_Category);
    }
}
