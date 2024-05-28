package com.nida.app_cakery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nida.app_cakery.Adapters.IngredientAdapter;
import com.nida.app_cakery.Domain.CakeryDomain;
import com.nida.app_cakery.Listeners.FirebaseListener;
import com.nida.app_cakery.Models.Ingredient;
import com.nida.app_cakery.R;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_page);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        ArrayList<Ingredient> ingredients = CakeryDomain.getInstance().getIngredientList();
        IngredientAdapter adapter = new IngredientAdapter(CartActivity.this, ingredients);
        recyclerView.setAdapter(adapter);

        CartActivity();
    }

    public void CartActivity() {
        ImageButton btnCart = findViewById(R.id.btnCart);
        ImageButton btnFav = findViewById(R.id.btnFavorites);
        ImageButton btnHome = findViewById(R.id.btnHome);
        ImageButton btnAdd = findViewById(R.id.btnMyRecipes);
        ImageButton btnList = findViewById(R.id.btnAllRecipes);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, FavoriteActivity.class));
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, HomeActivity.class));
                // Home butonu zaten HomeActivity'de olduğu için işlem yapmayabiliriz
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, AddActivity.class));
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, ListActivity.class));
            }
        });
    }
}
