package fpt.edu.com.food.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
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
import fpt.edu.com.food.database.Database;
import fpt.edu.com.food.database.DatabaseHelper;
import fpt.edu.com.food.model.Cart;
import fpt.edu.com.food.model.Food;
import fpt.edu.com.food.model.Order;
import fpt.edu.com.food.sqlDao.OrderDetail;

public class ProductAC extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsing;
    private ImageView imgProduct;
    private Toolbar toolbarProduct;
    private FloatingActionButton btnCart;
    private NestedScrollView nestedScrollView;
    private TextView foodName;
    private LinearLayout layoutPrice;
    private TextView foodPrice;
    private ElegantNumberButton numberButtom;
    private TextView foodDescription;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference data_Prodcut;
    private DatabaseReference data_Cart;
    private List<Food> list;
    private Food currentFood;
    private String foodid;
    private Database database;
    private DatabaseHelper databaseHelper;
    private OrderDetail orderDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        init();
        loaiProduct();
//        number();
//        remove();
        order();

        Toast.makeText(this, ""+ foodid, Toast.LENGTH_SHORT).show();

    }

    public void  init(){
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        collapsing = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        imgProduct = (ImageView) findViewById(R.id.img_product);
        toolbarProduct = findViewById(R.id.toolbar_product);
        btnCart = (FloatingActionButton) findViewById(R.id.btn_Cart);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        foodName = (TextView) findViewById(R.id.food_name);
        layoutPrice = (LinearLayout) findViewById(R.id.layout_price);
        foodPrice = (TextView) findViewById(R.id.food_price);
        numberButtom = (ElegantNumberButton) findViewById(R.id.number_buttom);
        foodDescription = (TextView) findViewById(R.id.food_description);
        firebaseDatabase = FirebaseDatabase.getInstance();
        data_Prodcut = firebaseDatabase.getReference("Food");
        list = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);
        orderDetail = new OrderDetail(databaseHelper);

    }

    public void loaiProduct(){

        Intent intent = getIntent();
        final Bundle ids = intent.getExtras();

        final String name_food = String.valueOf(ids.get("namefood"));
        Log.e("Tag","Namesss " + name_food);

        final Query products = data_Prodcut.orderByChild("name").equalTo(name_food);
        Log.e("Tag", "sfdsf"+ products.getRef().getKey());
        products.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    currentFood = postSnapshot.getValue(Food.class);
                    list.add(currentFood);

                    foodid = postSnapshot.getKey();

                    foodName.setText(list.get(0).getName());
                    final double price_d = list.get(0).getPrice();
                    String price_s = String.valueOf(price_d);
                    foodPrice.setText(price_s);
                    foodDescription.setText(list.get(0).getDescription());

                    Picasso.with(ProductAC.this).load(list.get(0).getImage())
                            .into(imgProduct);


                    numberButtom.setOnClickListener(new ElegantNumberButton.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String num = numberButtom.getNumber();
                            Double a = Double.parseDouble(num);

                            Double b = a * price_d;

                            String c = String.valueOf(b);

                            foodPrice.setText(c);
                            Log.e("Tag","nums"+num);

                        }
                    });

                    String name = list.get(0).getName();

                    String image = list.get(0).getImage();
                    final Cart cart = new Cart(name,price_d,1,price_d,image);

//                    String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
//                    Log.e("Tag", "Time "+ mydate);

//                    btnCart.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            DatabaseReference reference = firebaseDatabase.getReference("Cart");
//                            reference.push().setValue(cart);
//                        }
//                    });

                }
                Log.e("Tag","ID" + foodid);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void remove(){
        foodName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data_Cart = firebaseDatabase.getReference("Cart");
                data_Prodcut.removeValue();
            }
        });

    }

    public void order(){
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderDetail.insertCart(new Order(
                    foodid, currentFood.getName(), String.valueOf(numberButtom.getNumber()), String.valueOf(currentFood.getPrice()),currentFood.getImage(), currentFood.getDescription()
                ));
                Toast.makeText(ProductAC.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
