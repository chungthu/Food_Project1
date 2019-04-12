package fpt.edu.com.food.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import fpt.edu.com.food.R;
import fpt.edu.com.food.dialog.AddCategoryDialog;
import fpt.edu.com.food.dialog.AddFoodDiaLog;
import fpt.edu.com.food.model.Category;
import fpt.edu.com.food.model.Food;
import fpt.edu.com.food.viewholder.ADFoodViewHolder;
import fpt.edu.com.food.viewholder.CategoryViewHolder;

public class ADFoodAC extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerOptions<Food> options;
    private FirebaseRecyclerAdapter<Food, ADFoodViewHolder> adapter;
    private FloatingActionButton fab;
    private AddFoodDiaLog dialog;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adfood_ac);
        init();
        addFood();



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                displayCategory();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        displayCategory();
    }


    @Override
    public void onStop() {
        if (adapter != null){
            adapter.stopListening();
        }
        super.onStop();
    }

    public void displayCategory(){
        options =
                new FirebaseRecyclerOptions.Builder<Food>()
                        .setQuery(query,Food.class)
                        .build();
        adapter =
                new FirebaseRecyclerAdapter<Food, ADFoodViewHolder>(options) {
                    @NonNull
                    @Override
                    public ADFoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(ADFoodAC.this).inflate(R.layout.item_adfood,viewGroup,false);
                        return new  ADFoodViewHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull ADFoodViewHolder holder, int position, @NonNull Food model) {
                        holder.ad_namefood.setText(model.getName());
                        holder.ad_pricefood.setText(String.valueOf(model.getPrice()));
                        Picasso.with(ADFoodAC.this).load(model.getImage())
                                .into(holder.ad_imagefood);
                    }

                };
        adapter.startListening();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void addFood(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void showDialog(){

        FragmentManager fm = getSupportFragmentManager();
        dialog.show(fm,"OK");
        adapter.notifyDataSetChanged();
        recyclerView.clearFocus();

    }

    public void  init(){
        Intent intent = getIntent();
        Bundle ids = intent.getExtras();

        String id_Category = String.valueOf(ids.get("CategoryId"));

        recyclerView = findViewById(R.id.RE_ADFood);
        linearLayoutManager = new LinearLayoutManager(ADFoodAC.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Food");
        fab = findViewById(R.id.fab_ADFood);
        dialog = new AddFoodDiaLog();
        query = databaseReference.orderByChild("id").equalTo(id_Category);
    }

}
