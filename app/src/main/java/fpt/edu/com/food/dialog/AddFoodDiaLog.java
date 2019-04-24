package fpt.edu.com.food.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.com.food.R;
import fpt.edu.com.food.model.Food;

public class AddFoodDiaLog extends DialogFragment {
    private TextInputEditText edtNamefood;
    private Spinner spIdcategory;
    private TextInputEditText edtImage;
    private TextInputEditText edtPrice;
    private TextInputEditText edtDescription;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference data_category;
    private DatabaseReference data_food;
    private String ids = "";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_food,null);

        edtNamefood = (TextInputEditText) view.findViewById(R.id.edt_namefood);
        spIdcategory = (Spinner) view.findViewById(R.id.sp_idcategory);
        edtImage = (TextInputEditText) view.findViewById(R.id.edt_image);
        edtPrice = (TextInputEditText) view.findViewById(R.id.edt_price);
        edtDescription = (TextInputEditText) view.findViewById(R.id.edt_description);
        firebaseDatabase = FirebaseDatabase.getInstance();

        loadIdCategory();
//        spinner();
//        IdCategory();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("Add Food")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String name = edtNamefood.getText().toString();
                        String image = edtImage.getText().toString().trim();
                        String price = edtPrice.getText().toString().trim();
                        String descriptions = edtDescription.getText().toString().trim();

                        double price1 = Double.parseDouble(price);

                        Food food = new Food(name,ids,image,price1,descriptions);

                        data_food = firebaseDatabase.getReference("Food");
                        data_food.push()
                                .setValue(food);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        return builder.create();
    }

    public void loadIdCategory(){
        data_category = firebaseDatabase.getReference("Category");

        data_category.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> category = new ArrayList<String>();
                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {

                    String Categorys = areaSnapshot.child("name").getValue(String.class);
                    category.add(Categorys);
                    ids = areaSnapshot.getKey();

                }
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, category);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spIdcategory.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

//    public void IdCategory(){
//        spIdcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), ids, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }

//    public void spinner(){
//        data_category = firebaseDatabase.getReference("Category");
//        data_category.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
//                    String areaName = areaSnapshot.child("Name").getValue(String.class);
//                    Log.e("Tag","Name" + areaName);
//
//                    final String[] areas = {areaName};
//                    ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, areas);
//                    areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spIdcategory.setAdapter(areasAdapter);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
}
