package fpt.edu.com.food.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import fpt.edu.com.food.R;

public class ADFoodViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

    public ImageView ad_imagefood;
    public TextView ad_namefood;
    public TextView ad_pricefood;
    public CardView cardView;

    public ADFoodViewHolder(@NonNull View itemView) {
        super(itemView);

        ad_imagefood = itemView.findViewById(R.id.img_adfood);
        ad_namefood = itemView.findViewById(R.id.name_adfood);
        ad_pricefood = itemView.findViewById(R.id.price_adfood);
        cardView = itemView.findViewById(R.id.Cart_ADFood);
        cardView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select an Option");
        menu.add(1, 1,0,"Update");
        menu.add(1, 2,1,"Delete");
    }
}
