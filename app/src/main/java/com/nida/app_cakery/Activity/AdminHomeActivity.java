package com.nida.app_cakery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nida.app_cakery.Adapters.RequestAdapter;
import com.nida.app_cakery.Models.Admin;
import com.nida.app_cakery.Models.Recipe;
import com.nida.app_cakery.R;
import com.nida.app_cakery.Domain.CakeryDomain;
import com.nida.app_cakery.Listeners.FirebaseListener;
import com.nida.app_cakery.Adapters.RecipeAdapter;

import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RequestAdapter requestAdapter;
    private ArrayList<Recipe> requestList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home_page);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchRecipes();
    }

    @Override
    protected void onResume(){
        super.onResume();
        fetchRecipes();
    }

    private void fetchRecipes() {
        CakeryDomain.getInstance().readRecipes(new FirebaseListener() {
            @Override
            public void onTaskCompleted() {

                requestList = ((Admin)(CakeryDomain.getInstance().getPerson())).getRequestList();
                requestAdapter = new RequestAdapter(AdminHomeActivity.this, requestList);
                recyclerView.setAdapter(requestAdapter);
            }
        });
    }
}
