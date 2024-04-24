package com.nida.app_cakery;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nida.app_cakery.Model.Ingredient;

public class CakeryDomain {
    FirebaseFirestore db;
    public CakeryDomain(){
        db = FirebaseFirestore.getInstance();
    } //firbase connection

    //write data:
    public void addIngredient(){
        Ingredient ingredient = new Ingredient("banana");

        db.collection("Ingredient").document("3")
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
}

