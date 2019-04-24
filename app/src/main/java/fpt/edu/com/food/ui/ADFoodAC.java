package fpt.edu.com.food.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.com.food.R;
import fpt.edu.com.food.dialog.AddFoodDiaLog;
import fpt.edu.com.food.model.Food;
import fpt.edu.com.food.viewholder.ADFoodViewHolder;

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
    private DatabaseReference data_category;
    private TextInputEditText edtNamefood;
    private Spinner spIdcategory;
    private TextInputEditText edtImage;
    private TextInputEditText edtPrice;
    private TextInputEditText edtDescription;
    private String ids = "";


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


//        @Override
//        public void onStop() {
//            if (adapter != null){
//                adapter.stopListening();
//            }
//            super.onStop();
//        }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case 1:
//                adapter.UpdateAccount(item.getGroupId());
                showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
//                adapter.notifyDataSetChanged();
                return true;
            case 2:
//                adapter.removeItem(item.getGroupId());
//                removedata(item.getGroupId());
//               Toast.makeText(this, ""+adapter.getRef(item.getOrder()).getKey(), Toast.LENGTH_SHORT).show();
//                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void showUpdateDialog(final String key, final Food item) {

        final AlertDialog.Builder alertdialog = new AlertDialog.Builder(ADFoodAC.this);
        alertdialog.setTitle(R.string.updateFood);

        LayoutInflater inflater = this.getLayoutInflater();
        View update_food = inflater.inflate(R.layout.dialog_add_food,null);

        edtNamefood =  update_food.findViewById(R.id.edt_namefood);
        spIdcategory =  update_food.findViewById(R.id.sp_idcategory);
        edtImage = update_food.findViewById(R.id.edt_image);
        edtPrice =  update_food.findViewById(R.id.edt_price);
        edtDescription =  update_food.findViewById(R.id.edt_description);

        edtNamefood.setText(item.getName());
        edtImage.setText(item.getImage());
        edtPrice.setText(String.valueOf(item.getPrice()));
        edtDescription.setText(item.getDescription());


        alertdialog.setView(update_food);
        alertdialog.setIcon(R.drawable.ic_access_time_black_24dp);

        alertdialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadIdCategory();
//                String name = updatename.getText().toString().trim();
//                String image = updateiamge.getText().toString().trim();
                item.setName(edtNamefood.getText().toString().trim());
                item.setImage(edtImage.getText().toString().trim());
                item.setPrice(Double.parseDouble(edtPrice.getText().toString().trim()));
                item.setDescription(edtDescription.getText().toString().trim());
                databaseReference.child(key).setValue(item);

            }
        });

        alertdialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertdialog.show();

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


    public void loadIdCategory(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        data_category = firebaseDatabase.getReference("Category");

        data_category.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> category = new ArrayList<String>();
                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {

                    String Categorys = areaSnapshot.child("name").getValue(String.class);
                    category.add(Categorys);
                    ids = areaSnapshot.getKey();

                }
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(ADFoodAC.this, android.R.layout.simple_spinner_item, category);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spIdcategory.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
