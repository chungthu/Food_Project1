package fpt.edu.com.food.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fpt.edu.com.food.R;

public class TimeViewHolder extends RecyclerView.ViewHolder {
    public TextView txt_phone;
    public TextView txt_location;
    public TextView txt_status;
    public TextView txt_total;
    public TextView txt_date;
    public TimeViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_phone = itemView.findViewById(R.id.time_item_phone);
        txt_location = itemView.findViewById(R.id.time_item_location);
        txt_status = itemView.findViewById(R.id.time_item_status);
        txt_total = itemView.findViewById(R.id.time_item_total);
        txt_date = itemView.findViewById(R.id.time_item_date);
    }
}
