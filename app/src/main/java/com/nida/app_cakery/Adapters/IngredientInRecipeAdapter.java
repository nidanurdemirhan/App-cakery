package com.nida.app_cakery.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nida.app_cakery.Models.IngredientInRecipe;
import com.nida.app_cakery.R;

import java.util.ArrayList;

public class IngredientInRecipeAdapter extends RecyclerView.Adapter<IngredientInRecipeAdapter.IngredientViewHolder> {

    private ArrayList<IngredientInRecipe> ingredients;

    public IngredientInRecipeAdapter(ArrayList<IngredientInRecipe> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_recipe_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        IngredientInRecipe ingredient = ingredients.get(position);
        holder.ingredientName.setText(ingredient.getIngredient().getName());
        holder.ingredientAmount.setText(String.valueOf(ingredient.getAmount()));
        holder.ingredientUnit.setText(ingredient.getUnit());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void updateIngredients(ArrayList<IngredientInRecipe> newIngredients) {
        ingredients = newIngredients;
        notifyDataSetChanged();
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName, ingredientAmount, ingredientUnit;

        IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingredient_name);
            ingredientAmount = itemView.findViewById(R.id.ingredient_amount);
            ingredientUnit = itemView.findViewById(R.id.ingredient_unit);
        }
    }
}
