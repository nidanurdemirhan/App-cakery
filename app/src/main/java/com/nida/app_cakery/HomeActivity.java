package com.nida.app_cakery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        homeActivity();
    }
    public void homeActivity(){
        ImageButton btnCart = findViewById(R.id.btnCart1);
        ImageButton btnFav = findViewById(R.id.btnFav1);
        ImageButton btnHome = findViewById(R.id.btnHome1); //home'a tıklayınca bişi olmuyo onu değiştirebiliriz
        ImageButton btnAdd = findViewById(R.id.btnAdd1);
        ImageButton btnList = findViewById(R.id.btnList1);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CartActivity.class));
            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, FavoriteActivity.class));
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AddActivity.class));
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ListActivity.class));
            }
        });
    }
}
