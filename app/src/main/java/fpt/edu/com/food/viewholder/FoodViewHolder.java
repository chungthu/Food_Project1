package fpt.edu.com.food.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fpt.edu.com.food.R;

public class FoodViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageItemfood;
    public TextView nameItemfood;
    public TextView txtReviewPruct;
    public TextView priceItemfood;


    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);
        imageItemfood = (ImageView) itemView.findViewById(R.id.image_itemfood);
        nameItemfood = (TextView) itemView.findViewById(R.id.name_itemfood);
        txtReviewPruct = (TextView) itemView.findViewById(R.id.txt_reviewPruct);
        priceItemfood = (TextView) itemView.findViewById(R.id.price_itemfood);
    }
}
