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

    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        User user = ((User)(CakeryDomain.getInstance().getPerson()));
        Ingredient ingredient = ingredients.get(position);
        holder.ingredientName.setText(ingredient.getName());
        holder.ingredientCheckbox.setChecked(user.getIngredientStatus().get(position));

        holder.ingredientCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int adapterPos = holder.getAdapterPosition();
            user.getIngredientStatus().set(adapterPos, isChecked);

            String ingredientID = ingredients.get(adapterPos).getIngredientID();
            if(user.getIngredientStatus().get(adapterPos)) {
                user.addIngredientToInventory(ingredientID);
            } else{
                user.removeIngredientFromInventory(ingredientID);
            }
        });
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
