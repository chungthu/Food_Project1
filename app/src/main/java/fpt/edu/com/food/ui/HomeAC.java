package fpt.edu.com.food.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import fpt.edu.com.food.R;

import fpt.edu.com.food.fragment.CategoryFragment;
import fpt.edu.com.food.fragment.TimeOderFragment;
import fpt.edu.com.food.model.Category;
import fpt.edu.com.food.service.AddService;
import fpt.edu.com.food.viewholder.CategoryViewHolder;


public class HomeAC extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView txt_account;
    private static FragmentManager fragmentManager;
    public static String j ;

    //Category
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerOptions<Category> options;
    private FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();

        setFragment(new CategoryFragment());

//        startService(new Intent(HomeAC.this, AddService.class));



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (Double.parseDouble(j) != 1234 ){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_home_drawer);
            setFragment(new CategoryFragment());
        }else {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.home);
            setFragment(new TimeOderFragment());
        }


        txt_account = findViewById(R.id.account);
        fragmentManager = getSupportFragmentManager();

        //LoadCategory

        recyclerView = findViewById(R.id.RE_Home);
        linearLayoutManager = new LinearLayoutManager(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Category");

        String id = databaseReference.getRef().getKey();

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


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_home) {

            if (Double.parseDouble(j) != 1234 ){
                setFragment(new CategoryFragment());
            }else {
                startActivity(new Intent(HomeAC.this, ADCategoryAC.class));
            }

        } else if (id == R.id.nav_cart) {


            if (Double.parseDouble(j) != 1234 ){
                Intent intent1 = new Intent(HomeAC.this, CartAC.class);
                intent1.putExtra("Account", j);
                startActivity(intent1);
            }else {
                Intent intent1 = new Intent(HomeAC.this, ADCartAC.class);
                startActivity(intent1);
            }

        } else if (id == R.id.nav_time) {
            Intent intent1 = new Intent(HomeAC.this, TimeOrderAC.class);
            intent1.putExtra("Account", j);
            startActivity(intent1);
        } else if (id == R.id.nav_account) {

            Intent intent1 = new Intent(HomeAC.this, AccountAC.class);
            intent1.putExtra("Account", j);
            startActivity(intent1);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_home,fragment);
        ft.commit();
    }

    public void init(){
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b!=null){
            j = (String) b.get("Account");
        }

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
                        Picasso.with(HomeAC.this).load(model.getImage())
                                .into(holder.img_Category);
                        holder.card_Category.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(HomeAC.this, FoodAC.class);
                                intent.putExtra("CategoryId",adapter.getRef(position).getKey());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View itemview = LayoutInflater.from(HomeAC.this).inflate(R.layout.item_category,viewGroup,false);
                        return new CategoryViewHolder(itemview);
                    }
                };
        adapter.startListening();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
