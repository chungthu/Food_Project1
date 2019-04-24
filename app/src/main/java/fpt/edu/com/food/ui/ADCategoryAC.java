package fpt.edu.com.food.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import fpt.edu.com.food.R;
import fpt.edu.com.food.dialog.AddCategoryDialog;
import fpt.edu.com.food.model.Category;
import fpt.edu.com.food.viewholder.CategoryViewHolder;

public class ADCategoryAC extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerOptions<Category> options;
    private FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter;
    private FloatingActionButton fab;
    private AddCategoryDialog dialog;
    private EditText updatename, updateiamge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adcategory_ac);
        init();
        addCategory();


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


    public void displayCategory(){
        options =
                new FirebaseRecyclerOptions.Builder<Category>()
                        .setQuery(databaseReference,Category.class)
                        .build();
        adapter =
                new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CategoryViewHolder holder,
                                                    final int position, @NonNull final Category model) {
                        holder.txt_Category.setText(model.getName());
                        Picasso.with(ADCategoryAC.this).load(model.getImage())
                                .into(holder.img_Category);
                        holder.card_Category.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ADCategoryAC.this, ADFoodAC.class);
                                intent.putExtra("CategoryId",adapter.getRef(position).getKey());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View itemview = LayoutInflater.from(ADCategoryAC.this).inflate(R.layout.item_category,viewGroup,false);
                        return new CategoryViewHolder(itemview);
                    }
                };
        adapter.startListening();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void addCategory(){
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

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case 1:
//                adapter.UpdateAccount(item.getGroupId());
                showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
                adapter.notifyDataSetChanged();
                return true;
            case 2:
//                adapter.removeItem(item.getGroupId());
                    removedata(item.getGroupId());
//                Toast.makeText(this, ""+adapter.getRef(item.getOrder()).getKey(), Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void showUpdateDialog(final String key, final Category item) {
        final AlertDialog.Builder alertdialog = new AlertDialog.Builder(ADCategoryAC.this);
        alertdialog.setTitle(R.string.updateCateogry);

        LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.update_category1,null);

        updatename = add_menu_layout.findViewById(R.id.edt_update_name_category);
        updateiamge = add_menu_layout.findViewById(R.id.edt_update_image_category);

        updatename.setText(item.getName());
        updateiamge.setText(item.getImage());


        alertdialog.setView(add_menu_layout);
        alertdialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertdialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                String name = updatename.getText().toString().trim();
//                String image = updateiamge.getText().toString().trim();
                item.setName(updatename.getText().toString().trim());
                item.setImage(updateiamge.getText().toString().trim());
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


    public void  init(){
        recyclerView = findViewById(R.id.RV_ADCategory);
        linearLayoutManager = new LinearLayoutManager(ADCategoryAC.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Category");
        fab = findViewById(R.id.fab_ADcategory);
        dialog = new AddCategoryDialog();
    }

    public void removedata(final int position){
        Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, ""+ adapter.getItemCount(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "" +adapter.getRef(position).getKey(), Toast.LENGTH_SHORT).show();
        databaseReference.child(adapter.getRef(position).getKey()).removeValue();
        Toast.makeText(this, "Delete Success!", Toast.LENGTH_SHORT).show();

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
}
