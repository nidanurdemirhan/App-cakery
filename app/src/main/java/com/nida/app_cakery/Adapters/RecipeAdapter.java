package com.nida.app_cakery.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nida.app_cakery.Activity.RecipeDetailActivity;
import com.nida.app_cakery.Domain.CakeryDomain;
import com.nida.app_cakery.Models.Recipe;
import com.nida.app_cakery.Models.User;
import com.nida.app_cakery.R;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context context;
    private ArrayList<Recipe> recipeList;
    private ArrayList<Boolean> favStatusOfRecipes = new ArrayList<>();


    public RecipeAdapter(Context context, ArrayList<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
        determineFavStatusOfRecipe(recipeList);
    }

    private void determineFavStatusOfRecipe(ArrayList<Recipe> recipeList) {
        favStatusOfRecipes.clear();
        ArrayList<Recipe> favoriteList = ((User) (CakeryDomain.getInstance().getPerson())).getFavoriteRecipes();
        for(int i = 0; i < recipeList.size(); i++) {
            String recipeID = recipeList.get(i).getRecipeID();
            Boolean status = false;
            for(Recipe favRecipe: favoriteList){
                if(recipeID.equals(favRecipe.getRecipeID())){
                    status = true;
                    break;
                }
            }
            favStatusOfRecipes.add(status);
        }
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.recipeName.setText(recipe.getName());
        holder.recipeCalories.setText("Calorie: " + recipe.getCalorie());
        holder.recipePortion.setText("Portion: " + recipe.getPortion());

        Glide.with(context)
                .load(recipe.getImageUrl())
                .into(holder.recipeImage);
        if(!favStatusOfRecipes.isEmpty()) {
            if (favStatusOfRecipes.get(position)) {
                holder.heartIcon.setImageResource(R.drawable.filled_heart);
            } else {
                holder.heartIcon.setImageResource(R.drawable.empty_heart);
            }
        }

        holder.heartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = (User)(CakeryDomain.getInstance().getPerson());
                favStatusOfRecipes.set(position, !favStatusOfRecipes.get(position));
                if (favStatusOfRecipes.get(position)) {
                    holder.heartIcon.setImageResource(R.drawable.filled_heart);
                    user.addToFavoriteRecipes(recipe);

                } else {
                    holder.heartIcon.setImageResource(R.drawable.empty_heart);
                    user.removeFromFavoriteRecipes(recipe.getRecipeID());
                }
                notifyItemChanged(position);

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecipeDetailActivity.class);
                intent.putExtra("recipeID", recipe.getRecipeID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName, recipeDescription, recipeCalories, recipePortion;
        ImageView recipeImage, heartIcon;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeCalories = itemView.findViewById(R.id.recipe_calories);
            recipePortion = itemView.findViewById(R.id.recipe_portion);
            recipeImage = itemView.findViewById(R.id.recipe_image);
            heartIcon = itemView.findViewById(R.id.heart_icon);
        }
    }
}
