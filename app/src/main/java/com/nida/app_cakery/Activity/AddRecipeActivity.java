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

        // Malzeme listesini alarak Spinner'ı doldurun
        ArrayAdapter<String> ingredientAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getIngredientNames());
        ingredientSpinner.setAdapter(ingredientAdapter);

        // Spinner'da bir öğe seçildiğinde ne yapılacağını belirleyin
        portionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedPortion = parentView.getItemAtPosition(position).toString();
                // Seçilen portion değerini almak için kullanılabilir
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Bir şey seçilmediğinde yapılacak işlemler
            }
        });

        // Malzeme ekleme düğmesine tıklandığında
        btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredientName = ingredientSpinner.getSelectedItem().toString();
                double amount = Double.parseDouble(etAmount.getText().toString().trim());
                String unit = etUnit.getText().toString().trim();

                // Yeni bir malzeme nesnesi oluştur
                Ingredient ingredient = new Ingredient(ingredientName);
                IngredientInRecipe ingredientInRecipe = new IngredientInRecipe(null, ingredient, amount, unit);

                // Malzemeyi listeye ekle
                ingredientsList.add(ingredientInRecipe);

                // Yeni bir malzeme giriş alanı ekleyin
                addIngredientEntry();

                // EditText alanlarını temizle
                etAmount.setText("");
                etUnit.setText("");

                // Malzeme listesini güncelle
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


                // Create Recipe_Request object
                Recipe recipeRequest = new Recipe("5678",name, description, ingredientsList, calorie, portion, "unshared", imageUrl);
                User user = (User) CakeryDomain.getInstance().getPerson();
                user.getMyRecipes().add(recipeRequest); // FIRESTOREA EKLENECEK
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


                // Create Recipe_Request object
                Recipe recipeRequest = new Recipe("56789",name, description, ingredientsList, calorie, portion, "unshared", imageUrl);
                Admin admin = (Admin) CakeryDomain.getInstance().getPerson();
                admin.getRequestList().add(recipeRequest); // FIRESTOREA EKLENECEK
                User user = (User) CakeryDomain.getInstance().getPerson();
                user.getMyRecipes().add(recipeRequest); // FIRESTOREA EKLENECEK
            }
        });
    }

    // Malzeme isimlerini içeren bir dizi döndür
    private String[] getIngredientNames() {
        String[] names = new String[ingredients.size()];
        for (int i = 0; i < ingredients.size(); i++) {
            names[i] = ingredients.get(i).getName();
        }
        return names;
    }

    private void addIngredientEntry() {
        // Yeni bir EditText oluşturun
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

        // Yeni oluşturulan EditText ve Spinner'ları ConstraintLayout'a ekleyin
        ConstraintLayout layout = findViewById(R.id.constraintLayout);
        ConstraintSet set = new ConstraintSet();
        set.clone(layout);

        // Yeni EditText'leri ve Spinner'ları ConstraintSet'e ekleyin
        layout.addView(newAmountEditText);
        layout.addView(newUnitEditText);
        layout.addView(newIngredientSpinner);

        // Bağlantıları kurun
        set.connect(newAmountEditText.getId(), ConstraintSet.TOP, R.id.btnAddIngredient, ConstraintSet.BOTTOM, 8);
        set.connect(newUnitEditText.getId(), ConstraintSet.TOP, R.id.btnAddIngredient, ConstraintSet.BOTTOM, 8);
        set.connect(newIngredientSpinner.getId(), ConstraintSet.TOP, R.id.btnAddIngredient, ConstraintSet.BOTTOM, 8);
        set.connect(newAmountEditText.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16);
        set.connect(newAmountEditText.getId(), ConstraintSet.END, newUnitEditText.getId(), ConstraintSet.START, 8);
        set.connect(newUnitEditText.getId(), ConstraintSet.END, newIngredientSpinner.getId(), ConstraintSet.START, 8);
        set.connect(newIngredientSpinner.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 16);
        set.applyTo(layout);
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