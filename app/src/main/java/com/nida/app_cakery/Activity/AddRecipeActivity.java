package com.nida.app_cakery.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.nida.app_cakery.Models.Admin;
import com.nida.app_cakery.Models.Ingredient;
import com.nida.app_cakery.Models.IngredientInRecipe;
import com.nida.app_cakery.Models.Recipe;
import com.nida.app_cakery.Domain.CakeryDomain;
import com.nida.app_cakery.Models.User;
import com.nida.app_cakery.R;

import java.util.ArrayList;
import java.util.UUID;

public class AddRecipeActivity extends AppCompatActivity {

    private EditText etName, etDescription, etImageUrl, etCalorie, etAmount, etUnit;
    private Button btnSubmit, btnSend, btnAddIngredient;

    private Spinner portionSpinner, ingredientSpinner;
    private ArrayList<IngredientInRecipe> ingredientsList;

    private ArrayList<Ingredient> ingredients;

    private TextView ingredientListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etImageUrl = findViewById(R.id.etImageUrl);
        etCalorie = findViewById(R.id.etCalorie);
        etAmount = findViewById(R.id.etAmount);
        etUnit = findViewById(R.id.etUnit);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSend = findViewById(R.id.btnSend);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        portionSpinner = findViewById(R.id.portionSpinner);
        ingredientSpinner = findViewById(R.id.ingredientSpinner);
        ingredientListTextView = findViewById(R.id.ingredientList);

        ingredientsList = new ArrayList<>();
        ingredients = CakeryDomain.getInstance().getIngredientList();

        ArrayAdapter<String> portionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"1", "2", "4", "8"});
        portionSpinner.setAdapter(portionAdapter);

        ArrayAdapter<Ingredient> ingredientAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ingredients);
        ingredientSpinner.setAdapter(ingredientAdapter);

        portionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedPortion = parentView.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ingredient ingredient = (Ingredient) ingredientSpinner.getSelectedItem();
                String ingredientID = ingredient.getIngredientID();
                String ingredientName = ingredient.getName();
                double amount = Double.parseDouble(etAmount.getText().toString().trim());
                String unit = etUnit.getText().toString().trim();

                Ingredient selectedIngredient = new Ingredient(ingredientID, ingredientName);
                IngredientInRecipe ingredientInRecipe = new IngredientInRecipe(null, selectedIngredient, amount, unit);

                ingredientsList.add(ingredientInRecipe);

                addIngredientEntry();

                etAmount.setText("");
                etUnit.setText("");

                updateIngredientList();
            }
        });


        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String description = etDescription.getText().toString().trim();
                String imageUrl = etImageUrl.getText().toString().trim();
                double calorie = Integer.parseInt(etCalorie.getText().toString().trim());
                long portion = Long.parseLong(portionSpinner.getSelectedItem().toString());

                String recipeID = UUID.randomUUID().toString();
                Recipe recipeRequest = new Recipe(recipeID, name, description, ingredientsList, calorie, portion, "unshared", imageUrl);

                User user = (User) CakeryDomain.getInstance().getPerson();
                user.addRecipeToMyRecipeList(recipeRequest);
                finish();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String description = etDescription.getText().toString().trim();
                String imageUrl = etImageUrl.getText().toString().trim();
                double calorie = Integer.parseInt(etCalorie.getText().toString().trim());
                long portion = Long.parseLong(portionSpinner.getSelectedItem().toString());

                String recipeID = UUID.randomUUID().toString();
                Recipe recipeRequest = new Recipe(recipeID,name, description, ingredientsList, calorie, portion, "waiting", imageUrl);
                User user = (User) CakeryDomain.getInstance().getPerson();
                user.addRecipeToMyRecipeList(recipeRequest);
                user.addRecipeToAdminRequestList(recipeRequest);
                finish();
            }
        });
    }

    private String[] getIngredientNames() {
        String[] names = new String[ingredients.size()];
        for (int i = 0; i < ingredients.size(); i++) {
            names[i] = ingredients.get(i).getName();
        }
        return names;
    }

    private void addIngredientEntry() {
        ConstraintLayout newLayout = new ConstraintLayout(AddRecipeActivity.this);
        newLayout.setId(View.generateViewId());

        EditText newAmountEditText = new EditText(AddRecipeActivity.this);
        newAmountEditText.setId(View.generateViewId());
        newAmountEditText.setHint("Amount");

        EditText newUnitEditText = new EditText(AddRecipeActivity.this);
        newUnitEditText.setId(View.generateViewId());
        newUnitEditText.setHint("Unit");

        Spinner newIngredientSpinner = new Spinner(AddRecipeActivity.this);
        newIngredientSpinner.setId(View.generateViewId());
        ArrayAdapter<String> newIngredientAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getIngredientNames());
        newIngredientSpinner.setAdapter(newIngredientAdapter);

        newLayout.addView(newAmountEditText);
        newLayout.addView(newUnitEditText);
        newLayout.addView(newIngredientSpinner);

        ConstraintSet set = new ConstraintSet();
        set.clone(newLayout);

        set.connect(newAmountEditText.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 8);
        set.connect(newUnitEditText.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 8);
        set.connect(newIngredientSpinner.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 8);
        set.connect(newAmountEditText.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16);
        set.connect(newAmountEditText.getId(), ConstraintSet.END, newUnitEditText.getId(), ConstraintSet.START, 8);
        set.connect(newUnitEditText.getId(), ConstraintSet.END, newIngredientSpinner.getId(), ConstraintSet.START, 8);
        set.connect(newIngredientSpinner.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 16);

        set.applyTo(newLayout);

    }


    private void updateIngredientList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (IngredientInRecipe ingredient : ingredientsList) {
            stringBuilder.append(ingredient.getIngredient().getName())
                    .append(": ")
                    .append(ingredient.getAmount())
                    .append(" ")
                    .append(ingredient.getUnit())
                    .append("\n");
        }
        ingredientListTextView.setText(stringBuilder.toString());
    }



}