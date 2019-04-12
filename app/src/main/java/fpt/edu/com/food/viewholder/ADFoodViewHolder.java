package fpt.edu.com.food.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import fpt.edu.com.food.R;

public class ADFoodViewHolder extends RecyclerView.ViewHolder {

    public ImageView ad_imagefood;
    public TextView ad_namefood;
    public TextView ad_pricefood;

    public ADFoodViewHolder(@NonNull View itemView) {
        super(itemView);

        ad_imagefood = itemView.findViewById(R.id.img_adfood);
        ad_namefood = itemView.findViewById(R.id.name_adfood);
        ad_pricefood = itemView.findViewById(R.id.price_adfood);
    }
}
