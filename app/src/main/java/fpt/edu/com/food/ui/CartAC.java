package fpt.edu.com.food.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import fpt.edu.com.food.R;
import fpt.edu.com.food.adapter.CartAdapter;
import fpt.edu.com.food.database.DatabaseHelper;
import fpt.edu.com.food.model.Order;
import fpt.edu.com.food.model.Request;
import fpt.edu.com.food.sqlDao.OrderDetail;

public class CartAC extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference requests;
    private TextView txt_Total;
    private Button btn_order;
    private List<Order> cart;
    private CartAdapter adapter;
    private DatabaseHelper databaseHelper;
    private OrderDetail order;
    private String account;

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
                showAlertDiaLog();
            }
        });
    }

    private void showAlertDiaLog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(CartAC.this);
        dialog.setTitle("One more step: ");
        dialog.setMessage("Enter your address: ");

        final EditText edtAddress = new EditText(CartAC.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        edtAddress.setLayoutParams(lp);
        dialog.setView(edtAddress);

        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strDate = sdf.format(c.getTime());
                Request request = new Request(
                        account,
                        edtAddress.getText().toString(),
                        txt_Total.getText().toString(),
                        cart,
                        strDate,
                        "0"
                );

                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);

                order.delete();
                Toast.makeText(CartAC.this,R.string.CartAC_Thank, Toast.LENGTH_SHORT).show();
                showNotification();
                finish();

            }
        });
        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void showNotification() {

        Intent intent = new Intent(getBaseContext(), TimeOrderAC.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,intent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setTicker("Food")
                .setContentInfo("New Order")
                .setContentText("You have new order: ")
                .setSmallIcon(R.drawable.ic_shopping_cart_black_24dp)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


//        NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // if you want to many notification show, you need give unique Id for each Notification
//        int random = new Random().nextInt(9999-1)+1;
//        manager.notify(random,builder.build());

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setDefaults(Notification.DEFAULT_ALL)
////                .setTicker("Food")
////                .setContentInfo("New Order")
////                .setContentText("You have new order: ")
////                .setSmallIcon(R.drawable.ic_shopping_cart_black_24dp);
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    private void loadFoodList() {
        cart = order.getAllCarts();
        adapter = new CartAdapter(this,cart);
        recyclerView.setAdapter(adapter);

        double total = 0;
        for (Order order:cart)
            total += (Double.parseDouble(order.getPrice()))* (Double.parseDouble(order.getQuantity()));
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
        requests = firebaseDatabase.getReference("Request");
        cart = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);
        order = new OrderDetail(databaseHelper);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null){
            account = (String) bundle.get("Account");
        }
    }
}
