package com.codingstuff.shoeapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.codingstuff.shoeapp.repository.CartRepo;
import com.codingstuff.shoeapp.utils.model.Shoe;

import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private CartRepo cartRepo;

    public CartViewModel(@NonNull Application application) {
        super(application);
        cartRepo = new CartRepo(application);
    }

    public void insertCartItem(Shoe shoe){
        cartRepo.insertCartItem(shoe);
    }

    public void deleteCartItem(Shoe shoe){
        cartRepo.deleteCartItem(shoe);
    }

    public void updateQuantity(int id , int quantity){
        cartRepo.updateQuantity(id , quantity);
    }

    public void updatePrice(int id , double price){
        cartRepo.updatePrice(id , price);
    }

    public void deleteAllItems(){
        cartRepo.deleteAllItems();
    }
    public LiveData<List<Shoe>> getShoeListMutableLiveData() {
        return cartRepo.getAllCartItemsLiveData();
    }
}
