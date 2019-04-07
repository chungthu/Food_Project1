package fpt.edu.com.food.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fpt.edu.com.food.R;
import fpt.edu.com.food.model.User;

public class SignUpAC extends AppCompatActivity {

    private EditText edtUser;
    private EditText edtPassword;
    private EditText edtRepassword;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    public void SignUp(View view) {
        init();
        String phone = edtUser.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String repassword = edtRepassword.getText().toString().trim();

        if (phone.equals("")){
            edtUser.setError(""+R.string.entry);
            return;
        }if (password.equals("")){
            edtPassword.setError(""+R.string.entry);
            return;
        }if (repassword.equals("")){
            edtRepassword.setError(""+R.string.entry);
        }if (repassword.equals(password)){
            User user = new User(phone,password);

            databaseReference.push()
                    .setValue(user);
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(SignUpAC.this, LoginAC.class);
            intent.putExtra("phone",phone);
            intent.putExtra("password",password);
            startActivity(intent);
        }else {
            Toast.makeText(this, "The Re_enter Password does not match the Password ", Toast.LENGTH_SHORT).show();
        }

    }

    public void init(){
        edtUser = (EditText) findViewById(R.id.edt_user);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        edtRepassword = (EditText) findViewById(R.id.edt_repassword);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");
    }
}
