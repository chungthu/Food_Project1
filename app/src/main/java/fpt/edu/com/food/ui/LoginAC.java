package fpt.edu.com.food.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import fpt.edu.com.food.R;
import fpt.edu.com.food.model.User;

public class LoginAC extends AppCompatActivity {

    private EditText edtPhone;
    private EditText edtPassword;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        getdata();

    }

    public void SignUp(View view) {
        startActivity(new Intent(LoginAC.this, SignUpAC.class));
    }

    public void Login(View view) {
        init();
        database = FirebaseDatabase.getInstance();
        DatabaseReference table_user = database.getReference("User");
        Query query = table_user.orderByChild("phone").equalTo(edtPhone.getText().toString());
        String phone = edtPhone.getText().toString().trim();

        final Intent intent = new Intent(LoginAC.this, HomeAC.class);
        intent.putExtra("Account",phone);


        table_user.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Check if user not exist in Database
                if (dataSnapshot.child(edtPhone.getText().toString().trim()).exists()) {

                    //Get user information
                    User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                    if (user.getPassword().equals(edtPassword.getText().toString().trim())) {
                        Toast.makeText(LoginAC.this, "Sign In successfully !", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginAC.this, "Sign In failed", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginAC.this, "User not exist in Databse", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(LoginAC.this, HomeAC.class));
                }
//                startActivity(new Intent(LoginAC.this, HomeAC.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        startActivity(intent);

    }


    public void init() {
        edtPhone = (EditText) findViewById(R.id.edt_user);
        edtPassword = (EditText) findViewById(R.id.edt_password);
    }

    public void getdata() {
        Intent intent = getIntent();
        Bundle phone = intent.getExtras();
        Bundle password = intent.getExtras();

        if (phone != null) {
            String a = (String) phone.get("phone");
            edtPhone    .setText(a);
        }

        if (password != null) {
            String b = (String) phone.get("password");
            edtPassword.setText(b);
        }
    }
}
