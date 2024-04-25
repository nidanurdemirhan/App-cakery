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
import com.nida.app_cakery.Model.Ingredient;

import java.util.HashMap;
import java.util.Map;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class CakeryDomain {
    FirebaseFirestore db;
    public CakeryDomain(){
        db = FirebaseFirestore.getInstance();
    } //firbase connection

    //write data:
    public void addIngredient(){
        Ingredient ingredient = new Ingredient("apple");

        db.collection("Ingredient").document("5")
                .set(ingredient)
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

    public void readIngredients() {
        db.collection("Ingredient")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Retrieve the data from the document
                                String name = document.getString("name");
                                // Print the data to the console
                                System.out.println("Name: " + name);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
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

