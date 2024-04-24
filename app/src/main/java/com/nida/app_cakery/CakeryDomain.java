package com.nida.app_cakery;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CakeryDomain {
    FirebaseFirestore db;
    public CakeryDomain(){
        db = FirebaseFirestore.getInstance();
    }


}

