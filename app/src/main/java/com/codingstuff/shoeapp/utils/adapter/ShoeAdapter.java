package com.codingstuff.shoeapp.utils.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.codingstuff.shoeapp.R;
import com.codingstuff.shoeapp.utils.model.Shoe;

import java.util.List;

public class ShoeAdapter extends RecyclerView.Adapter<ShoeAdapter.ShoeViewHolder> {

    private List<Shoe> shoeList;
    private ShoeClickEventListener onAddBtnClickListener;

    public ShoeAdapter(ShoeClickEventListener onAddBtnClickListener,List<Shoe> shoeList) {
        this.onAddBtnClickListener = onAddBtnClickListener;
        this.shoeList = shoeList;
    }

    @NonNull
    @Override
    public ShoeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_shoe, parent, false);
        return new ShoeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoeViewHolder holder, int position) {
        Shoe shoe = shoeList.get(position);
        holder.shoeImageView.setImageResource(shoe.getShoeImage());
        holder.shoeNameTv.setText(shoe.getShoeName());
        holder.shoePriceTv.setText("$ " + shoe.getShoePrice());
        holder.shoeBrandNameTv.setText(shoe.getShoeBrandName());
        holder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddBtnClickListener.onAddToCartBtnClicked(shoe);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddBtnClickListener.onShoeCardClicked(shoe);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (shoeList == null) {
            return 0;
        } else {
            return shoeList.size();
        }
    }

    public class ShoeViewHolder extends RecyclerView.ViewHolder {
        private ImageView shoeImageView , addToCartBtn;
        private TextView shoeNameTv, shoeBrandNameTv, shoePriceTv;
        private CardView cardView;

        public ShoeViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.eachShoeCardView);
            addToCartBtn = itemView.findViewById(R.id.eachShoeAddToCartBtn);
            shoeNameTv = itemView.findViewById(R.id.eachShoeName);
            shoeImageView = itemView.findViewById(R.id.eachShoeIv);
            shoeBrandNameTv = itemView.findViewById(R.id.eachShoeBrandNameTv);
            shoePriceTv = itemView.findViewById(R.id.eachShoePriceTv);
        }
    }

    public interface ShoeClickEventListener{
        void onAddToCartBtnClicked(Shoe shoe);
        void onShoeCardClicked(Shoe shoe);
    }
}
