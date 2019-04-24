package fpt.edu.com.food.ui;

        import android.content.Context;
        import android.content.Intent;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.Toast;

        import fpt.edu.com.food.R;

public class MainAC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void start(View view) {

        if (isNetworkAvailable(this)) {

            startActivity(new Intent(MainAC.this, LoginAC.class));
            finish();
        }else {
            Toast.makeText(this, "Please check your connection!!", Toast.LENGTH_SHORT).show();
            final AlertDialog.Builder alertdialog = new AlertDialog.Builder(MainAC.this);
            LayoutInflater inflater = this.getLayoutInflater();
            View view1 = inflater.inflate(R.layout.dialog_internet,null);
            alertdialog.setView(view1);
            alertdialog.show();
        }
    }

//    public static  boolean isConnectedToInternet(Context context){
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivityManager !=null){
//            NetworkInfo[] infos = connectivityManager.getAllNetworkInfo();
//            if (infos!= null){
//                for (int i = 0; i< infos.length; i++){
//                    if (infos[i].getState() == NetworkInfo.State.CONNECTED);
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    public boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
