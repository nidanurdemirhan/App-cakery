package com.nida.app_cakery.Activity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nida.app_cakery.Adapters.RecipeAdapter;
import com.nida.app_cakery.Domain.CakeryDomain;
import com.nida.app_cakery.Listeners.FirebaseListener;
import com.nida.app_cakery.Models.Recipe;
import com.nida.app_cakery.R;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private ArrayList<Recipe> recipeList;
    private SeekBar calorieSeekBar;
    private TextView seekBarValue;
    private int selectedCalorieValue = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recipeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recipeList = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(this, recipeList);
        recyclerView.setAdapter(recipeAdapter);

        // SeekBar ve TextView'i referans alın
        calorieSeekBar = findViewById(R.id.calorieSeekBar);
        seekBarValue = findViewById(R.id.seekBarValue);

        // SeekBar değişikliklerini dinleyin
        calorieSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedCalorieValue = progress;
                seekBarValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Kullanıcı SeekBar'a dokunduğunda
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Kullanıcı SeekBar'ı bırakınca
                fetchRecipes();
            }
        });

        // Tarif verilerini al
        fetchRecipes();
    }

    private void fetchRecipes() {
        // Tarif verilerini CakeryDomain sınıfından al
        CakeryDomain.getInstance().readRecipes(new FirebaseListener() {
            @Override
            public void onTaskCompleted() {
                // Veri alındığında, RecyclerView'e ekle ve güncelle
                recipeList.clear();
                for (Recipe recipe : CakeryDomain.getInstance().getRecipeList()) {
                    if (recipe.getCalorie() <= selectedCalorieValue) {
                        recipeList.add(recipe);
                    }
                }
                recipeAdapter.notifyDataSetChanged();
            }
        });
    }
}
