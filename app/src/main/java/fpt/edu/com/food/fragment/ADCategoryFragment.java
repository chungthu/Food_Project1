package fpt.edu.com.food.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Random;

import dmax.dialog.SpotsDialog;
import fpt.edu.com.food.R;
import fpt.edu.com.food.model.Category;
import fpt.edu.com.food.ui.ADCategoryAC;
import fpt.edu.com.food.ui.ADFoodAC;
import fpt.edu.com.food.ui.FoodAC;
import fpt.edu.com.food.viewholder.CategoryViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ADCategoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerOptions<Category> options;
    private FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter;
    private FloatingActionButton fab;
    public String image;
    private EditText edt_name;
    private Button btn_iamge;
    private android.app.AlertDialog alertDialog;
    private StorageReference storageReference;
    private int PICK_IMAGE_CODE = 10000;


    public ADCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_CODE){
            alertDialog.show();

            UploadTask uploadTask = storageReference.putFile(data.getData());

            Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (!task.isSuccessful()){
                        Toast.makeText(getActivity(), "Failly", Toast.LENGTH_SHORT).show();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adcategory, container, false);

        //init
        recyclerView = view.findViewById(R.id.RE_adCategory);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Category");
        linearLayoutManager = new LinearLayoutManager(getActivity());
        alertDialog = new SpotsDialog.Builder().setContext(getContext()).build();
        fab = view.findViewById(R.id.fab);




        //---------------------------------------------------------

        loadcategory();

        //---------------------------------------------------------

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCategory();
            }
        });

        return view;
    }

    private void addCategory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Category");
        builder.setIcon(R.drawable.ic_local_dining_black_24dp);

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.add_category1,null);


        edt_name = view.findViewById(R.id.name_category1);
        btn_iamge = view.findViewById(R.id.btn_img_category);


        btn_iamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storageReference = FirebaseStorage.getInstance().getReference("image"+ String.valueOf(new Random(10000000)));
                updateImage();
            }
        });


        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = edt_name.getText().toString();

                Category category = new Category(name,image);

                databaseReference.push()
                        .setValue(category);

            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        builder.setView(view);
        builder.show();

    }

    public void loadcategory(){
        options =
                new FirebaseRecyclerOptions.Builder<Category>()
                        .setQuery(databaseReference,Category.class)
                        .build();
        adapter =
                new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position, @NonNull final Category model) {
                        holder.txt_Category.setText(model.getName());
                        Picasso.with(getContext()).load(model.getImage())
                                .into(holder.img_Category);
                        holder.card_Category.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), ADFoodAC.class);
                                intent.putExtra("CategoryId",adapter.getRef(position).getKey());
                                startActivity(intent);

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View itemview = LayoutInflater.from(getContext()).inflate(R.layout.item_category,viewGroup,false);
                        return new CategoryViewHolder(itemview);
                    }
                };
        adapter.startListening();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getTitle().equals("Update")){
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        }else {
            remove(adapter.getRef(item.getOrder()).getKey());
        }

        return super.onContextItemSelected(item);
    }

    private void remove(String key) {
        databaseReference.child(key).removeValue();
    }

    private void showUpdateDialog(final String key, final Category item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update Category");
        builder.setIcon(R.drawable.ic_local_dining_black_24dp);

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.add_category1,null);

        
        edt_name = view.findViewById(R.id.name_category1);
        btn_iamge = view.findViewById(R.id.btn_img_category);

        edt_name.setText(item.getName());
        
        btn_iamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storageReference = FirebaseStorage.getInstance().getReference("image"+ String.valueOf(new Random(10000000)));
                updateImage();
            }
        });



        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (image==null){
                    item.setName(edt_name.getText().toString().trim());
                    item.setImage(item.getImage());
                    databaseReference.child(key).setValue(item);
                }else {
                    item.setName(edt_name.getText().toString().trim());
                    item.setImage(image);
                    databaseReference.child(key).setValue(item);
                }

            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        builder.setView(view);
        builder.show();
        
    }

    private void updateImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select picture"),PICK_IMAGE_CODE);
    }

}
