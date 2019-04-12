package fpt.edu.com.food.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import fpt.edu.com.food.R;

public class ADAccoutAC extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaccout_ac);
    }

    public void init(){
        recyclerView.findViewById(R.id.RE_ADAccount);
    }
}
