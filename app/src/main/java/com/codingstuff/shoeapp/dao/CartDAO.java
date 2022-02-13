package com.codingstuff.shoeapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.codingstuff.shoeapp.utils.model.Shoe;

import java.util.List;

@Dao
public interface CartDAO {

    @Insert
    void insertCartItem(Shoe shoe);

    @Query("SELECT * FROM shoe_table")
    LiveData<List<Shoe>> getAllCartItems();

    @Delete
    void deleteCartItem(Shoe shoe);

    @Query("UPDATE shoe_table SET quantity= :quantity WHERE id= :id ")
    void updateQuantity(int id , int quantity);


    @Query("UPDATE shoe_table SET totalItemPrice= :price WHERE id= :id ")
    void updatePrice(int id , double price);


    @Query("DELETE FROM shoe_table")
    public void deleteAll();
}
