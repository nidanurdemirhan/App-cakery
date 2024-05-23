package com.nida.app_cakery.Domain;

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
import com.nida.app_cakery.Listeners.FirebaseListener;
import com.nida.app_cakery.Models.Admin;
import com.nida.app_cakery.Models.Ingredient;
import com.nida.app_cakery.Models.IngredientInRecipe;
import com.nida.app_cakery.Models.Person;
import com.nida.app_cakery.Models.Recipe;
import com.nida.app_cakery.Models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CakeryDomain {
    private static volatile CakeryDomain instance;

    //ArrayList<IngredientInRecipe> ingredientInRecipe = new ArrayList<>(); // IngredientInRecipe tablosu kaldırıldı bu yüzden gerekli değil. İlişkili ingredient ve miktarları Recipe tablosuna bakılarak dolduruluyor
    private FirebaseFirestore db;
    public ArrayList<Recipe> recipeList = new ArrayList<>();
    public ArrayList<Ingredient> ingredientList = new ArrayList<>();

    private Person person;

    private CakeryDomain(){
        db = FirebaseFirestore.getInstance();
    }

    public static CakeryDomain getInstance(){
        CakeryDomain result = instance;
        if (result == null) {
            synchronized (CakeryDomain.class) {
                if (instance == null) {
                    instance = new CakeryDomain();
                }
            }
        }
        return instance;
    }

    //ValueEventListener versiyonunda data değişince otomatik tetikleniyor, bunu bir araştır
    public void readIngredients(final FirebaseListener listener) {
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
                            listener.onTaskCompleted();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void readRecipes(final FirebaseListener listener){
        recipeList.clear();
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

                                // URL'leri okuma
                                String imageUrl = document.getString("url");

                                Map<String, Object> ingredientsMap = (Map<String, Object>) document.getData().get("Ingredients");
                                for (Map.Entry<String, Object> entry : ingredientsMap.entrySet()) {
                                    String ingredientID = entry.getKey();
                                    Map<String, Object> ingredientData = (Map<String, Object>) entry.getValue();

                                    double amount = ((Number) ingredientData.get("amount")).doubleValue();
                                    String unit = (String) ingredientData.get("unit");
                                    Ingredient ingredient = findIngredient(ingredientID);

                                    IngredientInRecipe ingredientInRecipe = new IngredientInRecipe(recipeID, ingredient, amount, unit);
                                    ingredientInRecipeList.add(ingredientInRecipe);
                                }

                                Recipe recipe = new Recipe(recipeID, name, description, ingredientInRecipeList, calorie, portion, status, imageUrl);

                                // URL'leri Recipe nesnesine ekleme
                                recipe.setImageUrl(imageUrl);

                                recipeList.add(recipe);
                            }
                            listener.onTaskCompleted();

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
                return ingredient;
            }
        }
        return null;
    }

    /************************************************************* USER-FIREBASE PROCESSES ****************************************************************************/

    public void fetchPerson(String email, String password, FirebaseListener listener) {
        db.collection("Admin")
                .whereEqualTo("mailAddress", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String mailAddress = document.getString("mailAddress");
                                    String name = document.getString("name");
                                    String surname = document.getString("surname");
                                    String password = document.getString("password");

                                    ArrayList<String> requestListData = new ArrayList<>();

                                    ArrayList<Object> favoriteRecipesObjectList = (ArrayList<Object>) document.get("requestList");
                                    for (Object recipe : favoriteRecipesObjectList) {
                                        requestListData.add(recipe.toString());
                                    }

                                    person = new Admin(mailAddress, name, surname, password, requestListData);
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    listener.onTaskCompleted();

                                }
                            }else{
                                fetchUser(email, password, listener);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    private void fetchUser(String email, String password, FirebaseListener listener){ //pasword alınmamalı firebaseden çekilöeli: güvenlik
        db.collection("User")
                .whereEqualTo("mailAddress", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String mailAddress = document.getString("mailAddress");
                                String name = document.getString("name");
                                String surname = document.getString("surname");
                                String password = document.getString("password");

                                ArrayList<String> myRecipesData = new ArrayList<>();
                                ArrayList<String> favoriteRecipesData = new ArrayList<>();

                                ArrayList<Object> myRecipesObjectList = (ArrayList<Object>) document.get("myRecipes");
                                for (Object recipe : myRecipesObjectList) {
                                    myRecipesData.add(recipe.toString());
                                }

                                ArrayList<Object> favoriteRecipesObjectList = (ArrayList<Object>) document.get("favoriteRecipes");
                                for (Object recipe : favoriteRecipesObjectList) {
                                    favoriteRecipesData.add(recipe.toString());
                                }

                                ArrayList<String> ingredientsInInventory = (ArrayList<String>) document.get("ingredientsInInventory");

                                person = new User(mailAddress, name, surname, password, recipeList, favoriteRecipesData, myRecipesData, ingredientsInInventory);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                listener.onTaskCompleted();

                            }
                        } else {
                            Log.d(TAG, "There is any person in the system.", task.getException());
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    /******************************************************* FOR ALL OBJECTS ********************************************************************************************/

    public void saveObject(String collectionpath, String documentPath, Object object, FirebaseListener listener){

        db.collection(collectionpath).document(documentPath)
                .set(object)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        listener.onTaskCompleted();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
/***************************************************************************************************************************************************************************/

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

    public void addRecipe(Recipe recipe) {

        Map<String, Object> recipeData = new HashMap<>();
        recipeData.put("recipeID", recipe.getRecipeID());
        recipeData.put("name", recipe.getName());
        recipeData.put("description", recipe.getDescription());
        recipeData.put("calorie", recipe.getCalorie());
        recipeData.put("portion", recipe.getPortion());
        recipeData.put("status", recipe.getStatus());

        Map<String, Map<String, Object>> ingredientsMap = new HashMap<>();
        for (IngredientInRecipe ingredientInRecipe : recipe.getIngredientInRecipe()) {
            Map<String, Object> ingredientData = new HashMap<>();
            ingredientData.put("amount", ingredientInRecipe.getAmount());
            ingredientData.put("unit", ingredientInRecipe.getUnit());
            ingredientsMap.put(ingredientInRecipe.getIngredientID(), ingredientData);
        }
        recipeData.put("ingredientsInRecipe", ingredientsMap);

        db.collection("Recipe").document(recipe.getRecipeID()).set(recipeData)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "Recipe added successfully"))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding recipe: " + e.getMessage()));
        
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
    /******************************************************************* GETTER-SETTER *********************************************************************/
    public Person getPerson() {
        return person;
    }

    public void setPerson(User person) {
        this.person = person;
    }

    public ArrayList<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(ArrayList<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(ArrayList<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }
}
