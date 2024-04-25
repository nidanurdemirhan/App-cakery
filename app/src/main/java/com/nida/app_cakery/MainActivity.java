package com.nida.app_cakery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {


    /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CakeryDomain cd = new CakeryDomain();
        //cd.addIngredient();
        //cd.deleteIngredient();
        //cd.updateIngredient("5", "pineapple");
        cd.readIngredients();


    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ImageView bileşeninin referansını alın
        ImageView imageView = findViewById(R.id.imageView);

        // Belirli bir belgeye ait olan resmi yüklemek ve göstermek için metodları çağırın
        fetchIngredientImageAndUpdateView("1"); // Belge ID'sini değiştirmeniz gerekebilir
    }

    private String ingredientImageUrl;

    private void fetchIngredientImageAndUpdateView(String documentId) {
        // MainActivity içinde
        ImageView imageView = findViewById(R.id.imageView);
        CakeryDomain cakeryDomain = new CakeryDomain();
        cakeryDomain.fetchIngredientImage(documentId, imageView);

    }

    public void updateImageView(String imageUrl) {
        this.ingredientImageUrl = imageUrl;
        showImage();
    }

    private void showImage() {
        // ImageView nesnesi oluşturun
        ImageView imageView = findViewById(R.id.imageView);

        // Picasso, Glide gibi kütüphaneleri kullanarak URL'den resmi ekrana bastırabilirsiniz
        Picasso.get().load(ingredientImageUrl).into(imageView); // Burada imageView, resmin gösterileceği ImageView'i temsil eder
    }
}