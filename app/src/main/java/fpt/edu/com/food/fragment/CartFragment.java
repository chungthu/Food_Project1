package fpt.edu.com.food.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.com.food.R;
import fpt.edu.com.food.model.Cart;
import fpt.edu.com.food.viewholder.CartViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    private RecyclerView reCart;
    private TextView Total;
    private Button book;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference data_Cart;
    private DatabaseReference id_Cart;
    private FirebaseRecyclerOptions<Cart> options;
    private FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter;
    double sum = 0;


    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        reCart =  view.findViewById(R.id.re_Cart);
        Total =  view.findViewById(R.id.Total);
        book =  view.findViewById(R.id.book);
        firebaseDatabase = FirebaseDatabase.getInstance();
        data_Cart = firebaseDatabase.getReference("Cart");

        String id = data_Cart.getRef().getKey();

        data_Cart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loadCart();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        loadCart();

        return view;
    }

    @Override
    public void onStop() {
        if (adapter != null){
            adapter.stopListening();
        }
        super.onStop();
    }

    public void loadCart(){

        options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(data_Cart,Cart.class)
                .build();
        adapter =
                new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model) {

                        final String id = adapter.getRef(position).getKey();
                        holder.nameItemCart.setText(model.getNamefood());
                        holder.txtItemCart.setText("Total: "+model.getTotal()+ " $");
                        Picasso.with(getContext()).load(model.getImage())
                                .into(holder.imgItemCart);


//                        holder.nbtnItemCart.setNumber(amount);

                        holder.imgItemCartclear.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                id_Cart = firebaseDatabase.getReference("Cart").getRef().child(id);
                                Toast.makeText(getActivity(), ""+id_Cart, Toast.LENGTH_SHORT).show();
                                id_Cart.removeValue();
                                notifyDataSetChanged();

                            }
                        });


                        Log.e("Tag","sum "+ model.getTotal());
                        Log.e("Tag","sum "+ sum);

                        List list = new ArrayList();
                        list.add(model.getTotal());

//                        for (int i = 0; i< list.size(); i++){
//                            sum = sum + list.get(i);
//                        }
//
////
                        Double a = (Double) list.get(0);
                        sum = sum +  a;


                        Total.setText("Total: "+ String.valueOf(sum)+" $");
//                        for (double number : list) {
//                            sum += number;
//                        }

                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View itemview = LayoutInflater.from(getContext()).inflate(R.layout.item_cart,viewGroup,false);
                        return new CartViewHolder(itemview);
                    }
                };
        adapter.startListening();
        reCart.setLayoutManager(new LinearLayoutManager(getContext()));
        reCart.setAdapter(adapter);
    }

}
