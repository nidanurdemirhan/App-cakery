package com.nida.app_cakery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CakeryDomain cd = new CakeryDomain();
        //cd.addIngredient();
        //cd.deleteIngredient();
        //cd.updateIngredient("5", "pineapple");
        cd.readIngredients();
    }
}