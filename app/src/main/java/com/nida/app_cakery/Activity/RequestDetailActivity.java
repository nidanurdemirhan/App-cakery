package com.nida.app_cakery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nida.app_cakery.Adapters.IngredientAdapter;
import com.nida.app_cakery.Adapters.IngredientInRecipeAdapter;
import com.nida.app_cakery.Domain.CakeryDomain;
import com.nida.app_cakery.Models.IngredientInRecipe;
import com.nida.app_cakery.Models.Recipe;
import com.nida.app_cakery.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RequestDetailActivity extends AppCompatActivity {
    private TextView etName, etCalorie, etDescription, etImageUrl;
    private Spinner portionSpinner;
    private TextView ingredientList;
    private Button btnConfirm, btnReject, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_request_detail);

        // Initialize views
        etName = findViewById(R.id.etName);
        etCalorie = findViewById(R.id.etCalorie);
        etDescription = findViewById(R.id.etDescription);
        etImageUrl = findViewById(R.id.etImageUrl);
        portionSpinner = findViewById(R.id.portionSpinner);
        ingredientList = findViewById(R.id.ingredientList);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnReject = findViewById(R.id.btnReject);
        btnBack = findViewById(R.id.btnBack);

        // Set listeners
        btnConfirm.setOnClickListener(v -> {
            confirmRecipe();
            finish();  // Navigate back to the previous activity
        });
        btnReject.setOnClickListener(v -> {
            rejectRecipe();
            finish();  // Navigate back to the previous activity
        });
        btnBack.setOnClickListener(v -> finish());

        // Populate fields with data from the user (this would typically come from an Intent or database)
        loadRecipeData();
    }

    private void loadRecipeData() {
        // Implement logic to load recipe data (e.g., from Intent extras or a database)
    }

    private void confirmRecipe() {
        // Implement logic to confirm the recipe
    }

    private void rejectRecipe() {
        // Implement logic to reject the recipe
    }



}