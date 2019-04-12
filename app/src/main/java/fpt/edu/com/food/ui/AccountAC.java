package fpt.edu.com.food.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import fpt.edu.com.food.R;
import fpt.edu.com.food.model.Request;

public class AccountAC extends AppCompatActivity {

    private TextView edt_phone;
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_ac);
        init();

    }

    public void Password(View view) {
        showAlertDiaLog();
    }

    private void showAlertDiaLog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(AccountAC.this);
        dialog.setTitle(R.string.RePassword);

        final EditText edtAddress = new EditText(AccountAC.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        edtAddress.setLayoutParams(lp);
        dialog.setView(edtAddress);

        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                HashMap<String, Object> result = new HashMap<>();
                result.put("password", edtAddress.getText().toString());

            FirebaseDatabase.getInstance().getReference().child("User").child(account).updateChildren(result);

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

    public void init(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        edt_phone = findViewById(R.id.txt_account_phone);

        if (bundle != null){
            account = (String) bundle.get("Account");
            edt_phone.setText(account);
        }
    }
}
