package fpt.edu.com.food.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fpt.edu.com.food.R;
import fpt.edu.com.food.model.TimeOder;
import fpt.edu.com.food.ui.ADCartAC;
import fpt.edu.com.food.ui.ADFoodAC;
import fpt.edu.com.food.ui.RequestAC;
import fpt.edu.com.food.viewholder.TimeViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerOptions<TimeOder> options;
    private FirebaseRecyclerAdapter<TimeOder, TimeViewHolder> adapter;
    private LinearLayoutManager linearLayoutManager;


    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.RE_Cart);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Request");
        linearLayoutManager = new LinearLayoutManager(getContext());


        loadTimeOrder();
        return view;
    }

    private void loadTimeOrder() {
        options =
                new FirebaseRecyclerOptions.Builder<TimeOder>()
                        .setQuery(databaseReference,TimeOder.class)
                        .build();

        adapter =
                new FirebaseRecyclerAdapter<TimeOder, TimeViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull TimeViewHolder holder, final int position, @NonNull TimeOder model) {
                        holder.txt_phone.setText(model.getPhone());
                        holder.txt_location.setText(model.getLocation());
//                        holder.txt_status.setText(model.getSatus());
                        holder.txt_total.setText(model.getTotal());
                        holder.txt_date.setText(model.getDate());
                        holder.cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), RequestAC.class);
                                intent.putExtra("Cart_Id",adapter.getRef(position).getKey());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_time_order, viewGroup, false);
                        return new TimeViewHolder(view);
                    }
                };

        adapter.startListening();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

}
