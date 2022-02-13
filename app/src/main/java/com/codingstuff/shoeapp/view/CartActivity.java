package com.codingstuff.shoeapp.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codingstuff.shoeapp.R;
import com.codingstuff.shoeapp.utils.adapter.CartAdapter;
import com.codingstuff.shoeapp.utils.model.Shoe;
import com.codingstuff.shoeapp.viewmodel.CartViewModel;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.CartItemClickListeners , PaymentResultListener {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private CartViewModel cartViewModel;
    private TextView totalCartPriceTv,textView;
    private AppCompatButton checkoutBtn;
    private double checkoutPrice;
    private CardView cardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initializeVariables();

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });
    }

    private void initializeVariables() {

        Checkout.preload(getApplicationContext());
        textView = findViewById(R.id.textView2);
        cardView = findViewById(R.id.cartActivityCardView);
        totalCartPriceTv = findViewById(R.id.cartActivityTotalPriceTv);
        checkoutBtn = findViewById(R.id.cartActivityCheckoutBtn);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CartAdapter(this);
        recyclerView.setAdapter(adapter);

        cartViewModel.getShoeListMutableLiveData().observe(this, new Observer<List<Shoe>>() {
            @Override
            public void onChanged(List<Shoe> shoes) {
                double price = 0;
                adapter.setCartItemList(shoes);
                adapter.notifyDataSetChanged();

                for (int i=0;i<shoes.size();i++){
                    price = price + shoes.get(i).getTotalItemPrice();
                }
                checkoutPrice = price;
                totalCartPriceTv.setText("$ " +price);
            }
        });


    }

    @Override
    public void onDeleteBtnClicked(Shoe shoe) {
        cartViewModel.deleteCartItem(shoe);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAddQuantityBtnClicked(Shoe shoe) {
        int quantity = shoe.getQuantity() + 1;
        cartViewModel.updateQuantity(shoe.getId(), quantity);
        cartViewModel.updatePrice(shoe.getId(), shoe.getShoePrice() * quantity);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onMinusQuantityBtnClicked(Shoe shoe) {
        int quantity = shoe.getQuantity() - 1;
        cartViewModel.updateQuantity(shoe.getId(), quantity);
        cartViewModel.updatePrice(shoe.getId(), shoe.getShoePrice() * quantity);
        adapter.notifyDataSetChanged();
    }


    private void startPayment(){

        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_eNwgA5oEnElHde");
        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "CodingSTUFF");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "USD");
            options.put("amount", checkoutPrice*100);//pass amount in currency subunits
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact","9657215547");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        cartViewModel.deleteAllItems();
        cardView.setVisibility(View.VISIBLE);
        checkoutBtn.setVisibility(View.INVISIBLE);
        totalCartPriceTv.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "Payment Successful !!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed " + s, Toast.LENGTH_SHORT).show();
    }
}