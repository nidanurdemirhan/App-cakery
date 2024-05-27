package com.nida.app_cakery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.nida.app_cakery.Models.Admin;
import com.nida.app_cakery.Models.IngredientInRecipe;
import com.nida.app_cakery.Models.Recipe;
import com.nida.app_cakery.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RequestDetailActivity extends AppCompatActivity {
    private EditText etName, etCalorie, etDescription;
    private Spinner portionSpinner;
    private LinearLayout ingredientsContainer;
    private Button btnConfirm, btnReject, btnBack;
    private String recipeID;
    private Recipe loadedRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_request_detail);

        etName = findViewById(R.id.etName);
        etCalorie = findViewById(R.id.etCalorie);
        etDescription = findViewById(R.id.etDescription);
        portionSpinner = findViewById(R.id.portionSpinner);
        ingredientsContainer = findViewById(R.id.ingredientsContainer);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnReject = findViewById(R.id.btnReject);
        btnBack = findViewById(R.id.btnBack);

        ArrayAdapter<String> portionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"1", "2", "4", "8"});
        portionSpinner.setAdapter(portionAdapter);

        btnConfirm.setOnClickListener(v -> {
            ((Admin)(CakeryDomain.getInstance()).getPerson()).confirmRequest(loadedRequest);
            finish();
        });

        btnReject.setOnClickListener(v -> {
            ((Admin)(CakeryDomain.getInstance()).getPerson()).rejectRequest(loadedRequest);
            finish();
        });

        btnBack.setOnClickListener(v -> finish());

        recipeID = getIntent().getStringExtra("recipeID");

       loadRecipeData();
    }

    private void loadRecipeData() {
        ArrayList<Recipe> requestList = ((Admin) (CakeryDomain.getInstance()).getPerson()).getRequestList();
        loadedRequest = null;

        for (Recipe request : requestList) {
            if (request.getRecipeID().equals(recipeID)) {
                loadedRequest = request;
                break;
            }
        }

        if (loadedRequest != null) {
            etName.setText(loadedRequest.getName());
            etCalorie.setText(String.valueOf(loadedRequest.getCalorie()));
            etDescription.setText(loadedRequest.getDescription());

            addIngredientEditTexts(loadedRequest.getIngredientInRecipe());
        } else {
            Log.e("RequestDetailActivity", "Recipe with ID " + recipeID + " not found");
        }
    }

    private void addIngredientEditTexts(List<IngredientInRecipe> ingredients) {
        for (IngredientInRecipe ingredient : ingredients) {
            LinearLayout ingredientLayout = new LinearLayout(this);
            ingredientLayout.setOrientation(LinearLayout.HORIZONTAL);

            EditText etIngredientName = new EditText(this);
            etIngredientName.setHint("Ingredient Name");
            etIngredientName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            etIngredientName.setText(ingredient.getIngredient().getName());
            ingredientLayout.addView(etIngredientName);

            EditText etIngredientAmount = new EditText(this);
            etIngredientAmount.setHint("Amount");
            etIngredientAmount.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            etIngredientAmount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etIngredientAmount.setText(String.valueOf(ingredient.getAmount()));
            ingredientLayout.addView(etIngredientAmount);

            EditText etIngredientUnit = new EditText(this);
            etIngredientUnit.setHint("Unit");
            etIngredientUnit.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            etIngredientUnit.setText(ingredient.getUnit());
            ingredientLayout.addView(etIngredientUnit);

            ingredientsContainer.addView(ingredientLayout);
        }
    }


}