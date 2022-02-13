package com.codingstuff.shoeapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.codingstuff.shoeapp.R;
import com.codingstuff.shoeapp.utils.model.Shoe;
import com.codingstuff.shoeapp.viewmodel.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private ImageView shoeImageView;
    private TextView shoeNameTV, shoeBrandNameTV, shoePriceTV;
    private AppCompatButton addToCartBtn;
    private Shoe shoe;
    private CartViewModel cartViewModel;
    private List<Shoe> cartItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initializeVariables();

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertItemToRoom();
            }
        });


    }

    private void insertItemToRoom() {
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
            cartViewModel.insertCartItem(shoe);
        } else {
            cartViewModel.updateQuantity(id[0], quantity[0]);
            cartViewModel.updatePrice(id[0], shoe.getShoePrice() * quantity[0]);
        }
        startActivity(new Intent(DetailActivity.this , CartActivity.class));
    }

    private void initializeVariables() {
        cartItemList = new ArrayList<>();
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        shoeImageView = findViewById(R.id.detailActivityShoeIV);
        shoeNameTV = findViewById(R.id.detailActivityShoeNameTv);
        shoeBrandNameTV = findViewById(R.id.detailActivityShoeBrandNameTv);
        shoePriceTV = findViewById(R.id.detailActivityShoePriceTv);
        addToCartBtn = findViewById(R.id.detailActivityAddToCartBtn);

        shoe = getIntent().getParcelableExtra("shoe");
        if (shoe != null) {
            shoeImageView.setImageResource(shoe.getShoeImage());
            shoeNameTV.setText(shoe.getShoeName());
            shoeBrandNameTV.setText(shoe.getShoeBrandName());
            shoePriceTV.setText("$ " + shoe.getShoePrice());
        }

        cartViewModel.getShoeListMutableLiveData().observe(this, new Observer<List<Shoe>>() {
            @Override
            public void onChanged(List<Shoe> shoes) {
                cartItemList.addAll(shoes);
            }

        });
    }
}