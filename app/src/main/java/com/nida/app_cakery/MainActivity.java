package com.nida.app_cakery;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.nida.app_cakery.Activity.LoginActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CakeryDomain ck = new CakeryDomain();
        //ck.readData(this); observable yaparsam böyle olacak ve DataListener implement etmeyi unutma

        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}