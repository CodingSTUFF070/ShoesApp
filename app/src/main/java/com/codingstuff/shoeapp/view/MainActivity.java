package com.codingstuff.shoeapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codingstuff.shoeapp.R;
import com.codingstuff.shoeapp.utils.adapter.ShoeAdapter;
import com.codingstuff.shoeapp.utils.model.Shoe;
import com.codingstuff.shoeapp.viewmodel.CartViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements ShoeAdapter.ShoeClickEventListener {

    private RecyclerView shoeRecyclerView;
    private List<Shoe> shoeItemList;
    private CartViewModel cartViewModel;
    private ImageView cartImageView;
    private List<Shoe> cartItemList;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables();

        cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });

        String uuid = UUID.randomUUID().toString();
        Toast.makeText(this, uuid, Toast.LENGTH_SHORT).show();
        Log.d("RANDOM UID", "onCreate: "+ uuid);


    }

    private void initializeVariables() {

        cartItemList = new ArrayList<>();
        cartImageView = findViewById(R.id.mainActivityCartIV);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        shoeItemList = new ArrayList<>();
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        shoeItemList.add(new Shoe(R.drawable.adidas_ultraboost, "Adidas Ultraboost", "ADIDAS", 15));
        shoeItemList.add(new Shoe(R.drawable.nike_revolution_road, "Nike Revolution 5", "NIKE", 25));
        shoeItemList.add(new Shoe(R.drawable.flex_run_road_running, "Nike Flex Run 2021", "NIKE", 20));
        shoeItemList.add(new Shoe(R.drawable.nikecourt_zoom_vapor_cage, "Court Zoom Vapor", "NIKE", 18));
        shoeItemList.add(new Shoe(R.drawable.adidas_eq_run, "EQ21 Run COLD.RDY", "ADIDAS", 16.5));
        shoeItemList.add(new Shoe(R.drawable.adidas_ozelia_shoes_grey, "Adidas Ozelia", "ADIDAS", 20));
        shoeItemList.add(new Shoe(R.drawable.adidas_questar_shoes, "Adidas Questar", "ADIDAS", 22));
        shoeItemList.add(new Shoe(R.drawable.adidas_questar_shoes, "Adidas Questar", "ADIDAS", 12));

        shoeRecyclerView = findViewById(R.id.mainActivityShoeRecyclerView);
        shoeRecyclerView.setHasFixedSize(true);
        shoeRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        ShoeAdapter shoeAdapter = new ShoeAdapter(this, shoeItemList);
        shoeRecyclerView.setAdapter(shoeAdapter);

        cartViewModel.getShoeListMutableLiveData().observe(this, new Observer<List<Shoe>>() {
            @Override
            public void onChanged(List<Shoe> shoes) {
                cartItemList.addAll(shoes);
            }

        });
    }

    @Override
    public void onAddToCartBtnClicked(Shoe shoe) {

        final int[] quantity = {1};
        final int[] id = new int[1];

        Log.d("TAG", "onAddToCartBtnClicked: " + cartItemList);

        if (!cartItemList.isEmpty()) {
            for (int i = 0; i < cartItemList.size(); i++) {
                if (shoe.getShoeName().equals(cartItemList.get(i).getShoeName())) {
                    quantity[0] = cartItemList.get(i).getQuantity();
                    quantity[0]++;
                    id[0] = cartItemList.get(i).getId();
                }
            }
        }
        if (quantity[0] == 1) {
            shoe.setQuantity(quantity[0]);
            shoe.setTotalItemPrice(shoe.getShoePrice());
            cartViewModel.insertCartItem(shoe);
        } else {
            cartViewModel.updateQuantity(id[0], quantity[0]);
            cartViewModel.updatePrice(id[0], shoe.getShoePrice() * quantity[0]);
        }
        makeSnackBar("Item Added To Cart");
    }

    @Override
    public void onShoeCardClicked(Shoe shoe) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("shoe", shoe);
        startActivity(intent);
    }

    private void makeSnackBar(String msg) {
        Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_SHORT)
        .setAction("Go To Cart", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , CartActivity.class));
            }
        }).show();
    }

}