package com.nida.app_cakery;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CakeryDomain {
    //ArrayList<IngredientInRecipe> ingredientInRecipe = new ArrayList<>();
    FirebaseFirestore db;
    ArrayList<Recipe> recipeList = new ArrayList<>();
    ArrayList<Ingredient> ingredientList = new ArrayList<>();

    User user;


    public CakeryDomain(){
        db = FirebaseFirestore.getInstance(); //firebase connection
        readIngredients();
        readRecipes();
    }


    //ValueEventListener versiyonunda data değişince otomatik tetikleniyor, bunu bir araştır
    private void readIngredients() {
        db.collection("Ingredient")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String ingredientID = document.getId();
                                String name = document.getString("name");
                                boolean isVegan = document.getBoolean("vegan");
                                String alternative = document.getString("alternative");
                                String category = document.getString("category");

                                String url = document.getString("url");
                                Ingredient ingredient = new Ingredient(ingredientID, name, isVegan,alternative, category, url);

                                ingredientList.add(ingredient);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void readRecipes(){
        db.collection("Recipe")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String recipeID = document.getId();
                                String name = document.getString("name");
                                String description = document.getString("description");
                                ArrayList<IngredientInRecipe> ingredientInRecipeList = new ArrayList<>();
                                double calorie = document.getDouble("calorie");
                                long portion = document.getLong("portion");
                                String status = document.getString("status");


                                Map<String, Object> ingredientsMap = (Map<String, Object>) document.getData().get("Ingredients");
                                for (Map.Entry<String, Object> entry : ingredientsMap.entrySet()) {
                                    String ingredientID = entry.getKey();
                                    Map<String, Object> ingredientData = (Map<String, Object>) entry.getValue();

                                    double amount = ((Number) ingredientData.get("amount")).doubleValue();

                                    String unit = (String) ingredientData.get("unit");
                                    Ingredient ingredient = null;
                                    ingredient =  findIngredient(ingredientID);


                                    IngredientInRecipe ingredientInRecipe = new IngredientInRecipe(recipeID, ingredient, amount, unit);
                                    ingredientInRecipeList.add(ingredientInRecipe);




                                }

                                Recipe recipe = new Recipe(recipeID, name, description, ingredientInRecipeList, calorie, portion, status);

                                if(status.equals("default")){
                                    recipeList.add(recipe); //sadece defaultlar atılcakdı dimi!! kafam çok iyi şuan :)
                                }

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }


    private Ingredient findIngredient(String ingredientID){
        for(int i = 0; i < ingredientList.size(); i++){
            Ingredient ingredient = ingredientList.get(i);
            if(ingredient.getIngredientID().equals(ingredientID)) {
                System.out.println(ingredient.getIngredientID());
                System.out.println(ingredient.getName());
                return ingredient;
            }
        }
        return null;
    }

    /*****************************************************************************************************************************************************************************/



    //write data:
    public void addIngredient(){
        Ingredient ingredient89 = new Ingredient("11", "carrot", true, "", "vegetables", "https://cdn11.bigcommerce.com/s-kc25pb94dz/images/stencil/1280x1280/products/271/762/Carrot__40927.1634584458.jpg?c=2");
        addToFirebase("Ingredient", "89", ingredient89);
    }

    public void addToFirebase(String collectionpath, String documentPath, Object object){

        db.collection(collectionpath).document(documentPath)
                .set(object)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    public void deleteIngredient() {
        db.collection("Ingredient").document("2")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    public void updateIngredient(String documentId, String newName) {
        // Create a Map to hold the new data
        Map<String, Object> newData = new HashMap<>();
        newData.put("name", newName);

        // Update the document
        db.collection("Ingredient").document(documentId)
                .update(newData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    public void fetchIngredientImage(String documentId, ImageView imageView) {
        db.collection("Ingredient").document(documentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String imageUrl = documentSnapshot.getString("url");
                            if (imageUrl != null && !imageUrl.isEmpty()) {
                                // Picasso, Glide gibi kütüphaneleri kullanarak URL'den resmi ekrana bastırabilirsiniz
                                Picasso.get().load(imageUrl).into(imageView); // Burada imageView, resmin gösterileceği ImageView'i temsil eder
                            } else {
                                Log.d(TAG, "Image URL is empty or null.");
                            }
                        } else {
                            Log.d(TAG, "Document does not exist.");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error fetching document: " + e.getMessage());
                    }
                });
    }
}
