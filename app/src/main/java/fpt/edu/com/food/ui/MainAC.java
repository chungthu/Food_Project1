package fpt.edu.com.food.ui;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;

        import fpt.edu.com.food.R;

public class MainAC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start(View view) {
        startActivity(new Intent(MainAC.this, LoginAC.class));
        finish();
    }
}
