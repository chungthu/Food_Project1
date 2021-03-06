package fpt.edu.com.food.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fpt.edu.com.food.R;
import fpt.edu.com.food.model.TimeOder;
import fpt.edu.com.food.viewholder.TimeViewHolder;

public class ADCartAC extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerOptions<TimeOder> options;
    private FirebaseRecyclerAdapter<TimeOder, TimeViewHolder> adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adcart_ac);
        init();
        loadTimeOrder();
    }

    @Override
    protected void onStop() {
        if (adapter != null){
            adapter.stopListening();
        }
        super.onStop();
    }

    private void loadTimeOrder() {
        options =
                new FirebaseRecyclerOptions.Builder<TimeOder>()
                        .setQuery(databaseReference,TimeOder.class)
                        .build();

        adapter =
                new FirebaseRecyclerAdapter<TimeOder, TimeViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull TimeViewHolder holder, int position, @NonNull TimeOder model) {
                        holder.txt_phone.setText(model.getPhone());
                        holder.txt_location.setText(model.getLocation());
//                        holder.txt_status.setText(model.getSatus());
                        holder.txt_total.setText(model.getTotal());
                        holder.txt_date.setText(model.getDate());
                    }

                    @NonNull
                    @Override
                    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(ADCartAC.this).inflate(R.layout.item_time_order, viewGroup, false);
                        return new TimeViewHolder(view);
                    }
                };

        adapter.startListening();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void init(){
        recyclerView = findViewById(R.id.RV_ADCart);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Request");
        linearLayoutManager = new LinearLayoutManager(this);
    }
}
