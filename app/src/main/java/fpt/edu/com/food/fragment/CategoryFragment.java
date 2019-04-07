package fpt.edu.com.food.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import fpt.edu.com.food.model.Category;
import fpt.edu.com.food.ui.FoodAC;
import fpt.edu.com.food.viewholder.CategoryViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerOptions<Category> options;
    private FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter;


    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_Category);
        linearLayoutManager = new LinearLayoutManager(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Category");

        String id = databaseReference.getRef().getKey();
        Log.e("Tag","id1 "+id);

//        postCategory();

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


        return view;
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
                                Toast.makeText(getActivity(), model.getName(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), FoodAC.class);
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

//    private void postCategory() {
//
//        Category post = new Category("Pizza","https://d2gk7xgygi98cy.cloudfront.net/20-4-facebook.jpg");
//        Category post1 = new Category("Salad","https://i.dietdoctor.com/wp-content/uploads/2018/03/DD-599-ketocobbsalad-2.jpg?auto=compress%2Cformat&w=1600&h=1067&fit=crop");
//        Category post2 = new Category("Spaghetti","https://www.archanaskitchen.com/images/archanaskitchen/10-Brands/DelMonte-KidsRecipes/Spaghetti_Pasta_Recipe_In_Creamy_Tomato_Sauce_-_Kids_Recipes_Made_With_Del_Monte-3.jpg");
//        Category post3 = new Category("Appetizer","https://images-gmi-pmc.edge-generalmills.com/2f9ed659-fcc0-41f0-9d1e-ecbcd12499e1.jpg");
//        Category post4 = new Category("Drinks", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shutterstock-626261780mod-1515166546.jpg");
//
//        databaseReference.push()
//                .setValue(post);
//        databaseReference.push()
//                .setValue(post1);
//        databaseReference.push()
//                .setValue(post2);
//        databaseReference.push()
//                .setValue(post3);
//        databaseReference.push()
//                .setValue(post4);
//
//
////        adapter.notifyDataSetChanged();
//    }

}
