package com.nida.app_cakery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nida.app_cakery.Domain.CakeryDomain;
import com.nida.app_cakery.Models.User;
import com.nida.app_cakery.R;
import com.nida.app_cakery.Models.Ingredient;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private Context context;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Boolean> ingredientStatus;

    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredients) {
        //normal ingredients taraması yaparken aynı zamanda inventoryde var mı yok mu ona bakıcam sonrası allah kerim :)
        this.context = context;
        this.ingredients = ingredients;
        this.ingredientStatus = new ArrayList<>(ingredients.size());
        for (int i = 0; i < ingredients.size(); i++) {
            ArrayList<String> ingredientsInInventory = ((User) (CakeryDomain.getInstance().getPerson())).getIngredientsInInventory();
            if (ingredientsInInventory.contains(ingredients.get(i).getIngredientID())){
                ingredientStatus.add(true);
            } else{
                ingredientStatus.add(false);
            }
        }
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.ingredientName.setText(ingredient.getName());
        holder.ingredientCheckbox.setChecked(ingredientStatus.get(position));

        holder.ingredientCheckbox.setOnCheckedChangeListener((buttonView, isChecked) ->
                ingredientStatus.set(holder.getAdapterPosition(), isChecked));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName;
        CheckBox ingredientCheckbox;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingredient_name);
            ingredientCheckbox = itemView.findViewById(R.id.ingredient_checkbox);
        }
    }
}
