package fpt.edu.com.food.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.com.food.R;
import fpt.edu.com.food.adapter.ProductAdapter;
import fpt.edu.com.food.model.Food;

public class FoodAC extends AppCompatActivity {
    private GridView gridView;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
//    private AddFoodDiaLog addFoodDiaLog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<Food> list;
    private ProductAdapter adapter;
//    private FirebaseRecyclerOptions<Food> options;
//    private FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;
//    private Query id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        init();
//        addFood();
        loadProduct();
    }

    public void init(){
        gridView = findViewById(R.id.gv_food);
//        recyclerView = findViewById(R.id.RE_Food);
//        addFoodDiaLog = new AddFoodDiaLog();
        firebaseDatabase = FirebaseDatabase.getInstance();
//        adapter = new ProductAdapter(this, (ArrayList<Food>) list);
}

    public void addFood(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
//        addFoodDiaLog.show(fm,"OK");
        adapter.notifyDataSetChanged();
        gridView.clearFocus();
    }

    public void loadProduct(){

        Intent intent = getIntent();
        Bundle ids = intent.getExtras();

        String id_Category = String.valueOf(ids.get("CategoryId"));
        Log.e("Tag","idcate "+id_Category);


        list = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference("Food");
        Query id = databaseReference.orderByChild("id").equalTo(id_Category);
        id.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Food food = postSnapshot.getValue(Food.class);
                    list.add(food);
                    String key = postSnapshot.getKey();
                    Toast.makeText(FoodAC.this, ""+ key, Toast.LENGTH_SHORT).show();
                }
                adapter = new ProductAdapter(getBaseContext(), (ArrayList<Food>) list);
                gridView.setAdapter(adapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

//    public String getId(String id_cateogry){
//        Intent intent = getIntent();
//        Bundle id = intent.getExtras();
//
//        if(id!=null)
//        {
//            id_cateogry =(String) id.get("CategoryId");
//
//        }
//        Log.e("Tag","sss"+id_cateogry);
//        return id_cateogry;
//    }

}
