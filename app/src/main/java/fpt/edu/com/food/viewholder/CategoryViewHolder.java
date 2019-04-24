package fpt.edu.com.food.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fpt.edu.com.food.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

    public TextView txt_Category;
    public ImageView img_Category;
    public CardView card_Category;
    public CardView cardView;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_Category = itemView.findViewById(R.id.txt_category);
        img_Category = itemView.findViewById(R.id.img_category);
        card_Category = itemView.findViewById(R.id.cardview_Category);
        cardView = itemView.findViewById(R.id.cardview_Category);
        cardView.setOnCreateContextMenuListener(this);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select an Option");
        menu.add(1, 1,0,"Update");
        menu.add(1, 2,1,"Delete");
    }
}
