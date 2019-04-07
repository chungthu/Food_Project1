package fpt.edu.com.food.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fpt.edu.com.food.R;
import fpt.edu.com.food.adapter.CartAdapter;
import fpt.edu.com.food.database.DatabaseHelper;
import fpt.edu.com.food.model.Order;
import fpt.edu.com.food.sqlDao.OrderDetail;

public class CartAC extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference request;
    private TextView txt_Total;
    private Button btn_order;
    private List<Order> cart;
    private CartAdapter adapter;
    private DatabaseHelper databaseHelper;
    private OrderDetail order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_ac);
        init();
        loadFoodList();
        place();
    }

    private void place() {
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void loadFoodList() {
        cart = order.getAllCarts();
        adapter = new CartAdapter(this,cart);
        recyclerView.setAdapter(adapter);

        int total = 0;
        for (Order order:cart)
            total += (Integer.parseInt(order.getPrice()))* (Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txt_Total.setText(fmt.format(total));

    }

    public void init(){
        recyclerView = findViewById(R.id.lv_listCat);
        txt_Total = findViewById(R.id.txt_total);
        btn_order = findViewById(R.id.btn_Order);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        firebaseDatabase = FirebaseDatabase.getInstance();
        request = firebaseDatabase.getReference("Request");
        cart = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);
        order = new OrderDetail(databaseHelper);
    }
}
