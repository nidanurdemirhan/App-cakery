package com.nida.app_cakery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nida.app_cakery.R;

public class RecipeDetailActivity extends AppCompatActivity {
    private TextView recipeName, recipeDescription, recipeCalories, recipePortion;
    private ImageView recipeImage;
    private FirebaseFirestore db;
    private String recipeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Geri düğmesine tıklandığında bir önceki aktiviteye dön
                finish();
            }
        });


        // Görünümleri bağla
        recipeName = findViewById(R.id.recipe_name);
        recipeDescription = findViewById(R.id.recipe_description);
        recipeCalories = findViewById(R.id.recipe_calories);
        recipePortion = findViewById(R.id.recipe_portion);
        recipeImage = findViewById(R.id.recipe_image);

        // Firestore instance
        db = FirebaseFirestore.getInstance();

        // Intent'ten Recipe ID'yi al
        recipeID = getIntent().getStringExtra("recipeID");

        // Firestore'dan tarifi çek
        loadRecipeDetails();
    }

    private void loadRecipeDetails() {
        db.collection("Recipe").document(recipeID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String name = document.getString("name");
                        String description = document.getString("description");
                        double calorie = document.getDouble("calorie");
                        long portion = document.getLong("portion");
                        String imageUrl = document.getString("url");

                        recipeName.setText(name);
                        recipeDescription.setText(description);
                        recipeCalories.setText("Kalori: " + calorie);
                        recipePortion.setText("Porsiyon: " + portion);

                        Glide.with(RecipeDetailActivity.this)
                                .load(imageUrl)
                                .into(recipeImage);
                    } else {
                        Log.d("RecipeDetailActivity", "No such document");
                    }
                } else {
                    Log.d("RecipeDetailActivity", "get failed with ", task.getException());
                }
            }
        });
    }
}
