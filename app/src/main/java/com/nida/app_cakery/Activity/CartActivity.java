package com.nida.app_cakery.Activity;

import android.os.Bundle;
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

        CakeryDomain.getInstance().readIngredients(new FirebaseListener() {
            @Override
            public void onTaskCompleted() {
                ArrayList<Ingredient> ingredients = CakeryDomain.getInstance().getIngredientList();
                IngredientAdapter adapter = new IngredientAdapter(CartActivity.this, ingredients);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}
