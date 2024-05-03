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
    private static volatile CakeryDomain instance;

    //ArrayList<IngredientInRecipe> ingredientInRecipe = new ArrayList<>(); // IngredientInRecipe tablosu kaldırıldı bu yüzden gerekli değil. İlişkili ingredient ve miktarları Recipe tablosuna bakılarak dolduruluyor
    private FirebaseFirestore db;
    public ArrayList<Recipe> recipeList = new ArrayList<>();
    public ArrayList<Ingredient> ingredientList = new ArrayList<>();

    private User user;

    private CakeryDomain(){
        db = FirebaseFirestore.getInstance();
        readData();
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

    //asynchronous process
    /*başka class observable olursa:
            public void readData(final DataListener dataLoadListener)
    */
    public void readData() {
        readIngredients(new FirebaseListener() {
            @Override
            public void onSuccess() {
                readRecipes(new FirebaseListener() {
                    @Override
                    public void onSuccess() {
                        //printResult();
                        //**dataLoadListener.onDataReceived();  //pagelerin burayı dinlemesinde singlenton değilde observer deseni kullanılabilir mi? düşün
                    }
                });
            }
        });
    }


    //ValueEventListener versiyonunda data değişince otomatik tetikleniyor, bunu bir araştır
    private void readIngredients(final FirebaseListener listener) {
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
                            listener.onSuccess();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void readRecipes(final FirebaseListener listener){
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
                                    Ingredient ingredient = findIngredient(ingredientID);

                                    IngredientInRecipe ingredientInRecipe = new IngredientInRecipe(recipeID, ingredient, amount, unit);
                                    ingredientInRecipeList.add(ingredientInRecipe);
                                }

                                Recipe recipe = new Recipe(recipeID, name, description, ingredientInRecipeList, calorie, portion, status);

                                recipeList.add(recipe); // save the all type recipes

                            }
                            listener.onSuccess();

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

    private void printResult() {

        System.out.println("recipeList size: " + recipeList.size());
        System.out.println("ingredientListSize: " + ingredientList.size());

        for (int i = 0; i < recipeList.size(); i++) {
            Recipe recipe = recipeList.get(i);
            System.out.println(recipe.getRecipeID());
            System.out.println(recipe.getName());
            System.out.println(recipe.getDescription());
            System.out.println(recipe.getPortion().toString());
            System.out.println(recipe.getStatus());
            System.out.println(recipe.getCalorie());

            for (int j = 0; j < recipe.getIngredientInRecipe().size(); j++) {
                IngredientInRecipe ingredientInRecipe = recipe.getIngredientInRecipe().get(j);
                System.out.println(ingredientInRecipe.getRecipeID());
                System.out.println(ingredientInRecipe.getAmount());
                System.out.println(ingredientInRecipe.getUnit());


                //print ingredient:
                Ingredient ingredient = ingredientInRecipe.getIngredient();
                System.out.println(ingredient.getIngredientID());
                System.out.println(ingredient.getName());
                System.out.println(ingredient.isVegan());
                System.out.println(ingredient.getAlternative());
                System.out.println(ingredient.getCategory());
                System.out.println(ingredient.getUrl());
            }
        }
    }

    /************************************************************* USER-FIREBASE PROCESSES ****************************************************************************/

    public void fetchUser(String email, String password, FirebaseListener listener){

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

                                ArrayList<Object> myRecipesObjectList = (ArrayList<Object>) document.get("myRecipes");
                                ArrayList<String> myRecipesData = new ArrayList<>();
                                for (Object recipe : myRecipesObjectList) {
                                    myRecipesData.add(recipe.toString());
                                }

                                ArrayList<Object> favoriteRecipesObjectList = (ArrayList<Object>) document.get("favoriteRecipes");
                                ArrayList<String> favoriteRecipesData = new ArrayList<>();
                                for (Object recipe : myRecipesObjectList) {
                                    favoriteRecipesObjectList.add(recipe.toString());
                                }

                                user = new User(mailAddress, name, surname, password, recipeList, myRecipesData, favoriteRecipesData);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                listener.onSuccess();
                            }
                        } else {
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
                        listener.onSuccess();
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
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
