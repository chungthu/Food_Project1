package fpt.edu.com.food.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import fpt.edu.com.food.R;

import fpt.edu.com.food.fragment.CategoryFragment;
import fpt.edu.com.food.fragment.TimeOderFragment;


public class HomeAC extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView txt_account;
    private static FragmentManager fragmentManager;

    public static String j ;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();


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


//        txt_account.setText(j);

        fragmentManager = getSupportFragmentManager();

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
}
