package com.codingstuff.shoeapp.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.codingstuff.shoeapp.dao.CartDAO;
import com.codingstuff.shoeapp.database.CartDatabase;
import com.codingstuff.shoeapp.utils.model.Shoe;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CartRepo {

    private CartDAO cartDAO;
    private LiveData<List<Shoe>> allCartItemsLiveData;
    private Executor executor = Executors.newSingleThreadExecutor();

    public LiveData<List<Shoe>> getAllCartItemsLiveData() {
        return allCartItemsLiveData;
    }

    public CartRepo(Application application){
        cartDAO = CartDatabase.getInstance(application).cartDAO();
        allCartItemsLiveData = cartDAO.getAllCartItems();
    }

    public void insertCartItem(Shoe shoe){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDAO.insertCartItem(shoe);
            }
        });
    }

    public void deleteCartItem(Shoe shoe){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDAO.deleteCartItem(shoe);
            }
        });
    }

    public void updateQuantity(int id , int quantity){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDAO.updateQuantity(id , quantity);
            }
        });
    }

    public void deleteAllItems(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDAO.deleteAll();
            }
        });
    }


    public void updatePrice(int id , double price){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDAO.updatePrice(id , price);
            }
        });
    }
}
