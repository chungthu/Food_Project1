package fpt.edu.com.food.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import dmax.dialog.SpotsDialog;
import fpt.edu.com.food.R;
import fpt.edu.com.food.model.Food;
import fpt.edu.com.food.viewholder.ADFoodViewHolder;

public class RequestAC extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerOptions<Food> options;
    private FirebaseRecyclerAdapter<Food, ADFoodViewHolder> adapter;
    private Query query;
    public String id_Cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_ac);
        init();

        load();
    }

    private void load() {
            options =
                    new FirebaseRecyclerOptions.Builder<Food>()
                            .setQuery(query,Food.class)
                            .build();
            adapter =
                    new FirebaseRecyclerAdapter<Food, ADFoodViewHolder>(options) {
                        @NonNull
                        @Override
                        public ADFoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                            View view = LayoutInflater.from(RequestAC.this).inflate(R.layout.item_adfood,viewGroup,false);
                            return new  ADFoodViewHolder(view);
                        }

                        @Override
                        protected void onBindViewHolder(@NonNull ADFoodViewHolder holder, int position, @NonNull Food model) {
                            holder.ad_namefood.setText(model.getName());
                            holder.ad_pricefood.setText(String.valueOf(model.getPrice()));
                            Picasso.with(RequestAC.this).load(model.getImage())
                                    .into(holder.ad_imagefood);
                        }

                    };
            adapter.startListening();
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);

    }

    public void init(){
        Intent intent = getIntent();
        Bundle ids = intent.getExtras();

        id_Cart = String.valueOf(ids.get("Cart_Id"));

        recyclerView = findViewById(R.id.RE_Request);
        linearLayoutManager = new LinearLayoutManager(RequestAC.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Request");
        query = databaseReference.child("foods").orderByKey().equalTo(id_Cart);

    }
}
