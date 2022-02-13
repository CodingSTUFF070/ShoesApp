package com.codingstuff.shoeapp.utils.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codingstuff.shoeapp.R;
import com.codingstuff.shoeapp.utils.model.Shoe;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Shoe> cartItemList;
    private CartItemClickListeners cartItemClickListeners;

    public CartAdapter(CartItemClickListeners cartItemClickListeners) {
        this.cartItemClickListeners = cartItemClickListeners;
    }

    public void setCartItemList(List<Shoe> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Shoe shoe = cartItemList.get(position);
        holder.shoeNameTv.setText(shoe.getShoeName());
        holder.shoeBrandNameTv.setText(shoe.getShoeBrandName());
        holder.shoePriceTv.setText("$ " + shoe.getTotalItemPrice());
        holder.shoeImageView.setImageResource(shoe.getShoeImage());
        holder.shoeQuantity.setText(String.valueOf(shoe.getQuantity()));

        holder.deleteShoeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartItemClickListeners.onDeleteBtnClicked(shoe);
            }
        });

        holder.addQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartItemClickListeners.onAddQuantityBtnClicked(shoe);
            }
        });
        holder.minusQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartItemClickListeners.onMinusQuantityBtnClicked(shoe);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cartItemList == null) {
            return 0;
        } else {
            return cartItemList.size();
        }
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView shoeNameTv, shoeBrandNameTv, shoePriceTv , shoeQuantity;
        private ImageView deleteShoeBtn;
        private ImageView shoeImageView;
        private ImageButton addQuantityBtn , minusQuantityBtn;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            shoeNameTv = itemView.findViewById(R.id.eachCartItemName);
            shoeBrandNameTv = itemView.findViewById(R.id.eachCartItemBrandNameTv);
            shoePriceTv = itemView.findViewById(R.id.eachCartItemPriceTv);
            deleteShoeBtn = itemView.findViewById(R.id.eachCartItemDeleteBtn);
            shoeImageView = itemView.findViewById(R.id.eachCartItemIV);
            shoeQuantity = itemView.findViewById(R.id.eachCartItemQuantityTV);
            addQuantityBtn = itemView.findViewById(R.id.eachCartItemAddQuantityBtn);
            minusQuantityBtn = itemView.findViewById(R.id.eachCartItemMinusQuantityBtn);
        }
    }

    public interface CartItemClickListeners {
        void onDeleteBtnClicked(Shoe shoe);
        void onAddQuantityBtnClicked(Shoe shoe);
        void onMinusQuantityBtnClicked(Shoe shoe);
    }
}
