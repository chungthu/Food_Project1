package fpt.edu.com.food.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dmax.dialog.SpotsDialog;
import fpt.edu.com.food.R;
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
    private Query query;
    private DatabaseReference data_category;
    private TextInputEditText edtNamefood;
    private TextInputEditText edtPrice;
    private TextInputEditText edtDescription;
    private Button Select;
    private String ids = "";
    private int PICK_IMAGE_CODE =10000;
    android.app.AlertDialog alertDialog;
    StorageReference storageReference;
    public String image;
    public String id_Category;


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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_CODE){
            alertDialog.show();

            UploadTask uploadTask = storageReference.putFile(data.getData());

            Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (!task.isSuccessful()){
                        Toast.makeText(ADFoodAC.this, "Failly", Toast.LENGTH_SHORT).show();
                    }

                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        image = task.getResult().toString();

                    }
                }
            });
        }
    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case 1:
                showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
                return true;
            case 2:
                remove(adapter.getRef(item.getOrder()).getKey());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void remove(String key) {
        databaseReference.child(key).removeValue();
    }

    private void showUpdateDialog(final String key, final Food item) {

        final AlertDialog.Builder alertdialog = new AlertDialog.Builder(ADFoodAC.this);
        alertdialog.setTitle(R.string.updateFood);

        LayoutInflater inflater = this.getLayoutInflater();
        View update_food = inflater.inflate(R.layout.dialog_add_food,null);

        edtNamefood =  update_food.findViewById(R.id.edt_namefood);
//        edtImage = update_food.findViewById(R.id.edt_image);
        edtPrice =  update_food.findViewById(R.id.edt_price);
        edtDescription =  update_food.findViewById(R.id.edt_description);
        Select = update_food.findViewById(R.id.btn_select);

        edtNamefood.setText(item.getName());
        edtPrice.setText(String.valueOf(item.getPrice()));
        edtDescription.setText(item.getDescription());

        Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storageReference = FirebaseStorage.getInstance().getReference("image"+ String.valueOf(new Random(10000000)));
                upload();
            }
        });


        alertdialog.setView(update_food);
        alertdialog.setIcon(R.drawable.ic_access_time_black_24dp);

        alertdialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (image == null){
                    item.setName(edtNamefood.getText().toString().trim());
                    item.setId(id_Category);
                    item.setImage(item.getImage());
                    item.setPrice(Double.parseDouble(edtPrice.getText().toString().trim()));
                    item.setDescription(edtDescription.getText().toString().trim());
                    databaseReference.child(key).setValue(item);
                }
                else {
                    item.setName(edtNamefood.getText().toString().trim());
                    item.setId(id_Category);
                    item.setImage(image);
                    item.setPrice(Double.parseDouble(edtPrice.getText().toString().trim()));
                    item.setDescription(edtDescription.getText().toString().trim());
                    databaseReference.child(key).setValue(item);
                }

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
            }
        });
    }

    public void showDialog(){
        final AlertDialog.Builder alertdialog = new AlertDialog.Builder(ADFoodAC.this);
        alertdialog.setTitle("Add Food");

        LayoutInflater inflater = this.getLayoutInflater();
        View update_food = inflater.inflate(R.layout.dialog_add_food,null);

        edtNamefood =  update_food.findViewById(R.id.edt_namefood);
//        edtImage = update_food.findViewById(R.id.edt_image);
        edtPrice =  update_food.findViewById(R.id.edt_price);
        edtDescription =  update_food.findViewById(R.id.edt_description);
        Select = update_food.findViewById(R.id.btn_select);
        ;

        Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storageReference = FirebaseStorage.getInstance().getReference("image"+ String.valueOf(new Random(10000000)));
                upload();
            }
        });


        alertdialog.setView(update_food);
        alertdialog.setIcon(R.drawable.ic_access_time_black_24dp);

        alertdialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String name = edtNamefood.getText().toString().trim();
                String price = edtPrice.getText().toString().trim();
                String description = edtDescription.getText().toString().trim();

                Food food = new Food(name,id_Category,image,Double.parseDouble(price),description);

                databaseReference = firebaseDatabase.getReference("Food");
                databaseReference.push()
                                .setValue(food);

            }
        });

        alertdialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertdialog.show();

    }

    public void  init(){
        Intent intent = getIntent();
        Bundle ids = intent.getExtras();

        id_Category = String.valueOf(ids.get("CategoryId"));

        recyclerView = findViewById(R.id.RE_ADFood);
        linearLayoutManager = new LinearLayoutManager(ADFoodAC.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Food");
        fab = findViewById(R.id.fab_ADFood);
        query = databaseReference.orderByChild("id").equalTo(id_Category);
        alertDialog = new SpotsDialog.Builder().setContext(this).build();
    }




    public void upload(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select picture"),PICK_IMAGE_CODE);
    }


}
