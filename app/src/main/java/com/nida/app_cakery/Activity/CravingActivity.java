package com.nida.app_cakery.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nida.app_cakery.Adapters.RecipeAdapter;
import com.nida.app_cakery.Domain.CakeryDomain;
import com.nida.app_cakery.Listeners.FirebaseListener;
import com.nida.app_cakery.Models.Recipe;
import com.nida.app_cakery.R;

import java.util.ArrayList;

public class CravingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private ArrayList<Recipe> recipeList = new ArrayList<>();
    public static String answer = "";
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.craving_test_page);
        answer = "";
        count = 0;
        setUpNavigationButtons();
        questionAndAnswer();
    }

    private void setUpNavigationButtons() {
        ImageButton btnCart = findViewById(R.id.btnCart);
        ImageButton btnFav = findViewById(R.id.btnFavorites);
        ImageButton btnHome = findViewById(R.id.btnHome);
        ImageButton btnAdd = findViewById(R.id.btnMyRecipes);
        ImageButton btnList = findViewById(R.id.btnAllRecipes);

        btnCart.setOnClickListener(v -> startActivity(new Intent(CravingActivity.this, CartActivity.class)));
        btnFav.setOnClickListener(v -> startActivity(new Intent(CravingActivity.this, FavoriteActivity.class)));
        btnHome.setOnClickListener(v -> startActivity(new Intent(CravingActivity.this, HomeActivity.class)));
        btnAdd.setOnClickListener(v -> startActivity(new Intent(CravingActivity.this, AddActivity.class)));
        btnList.setOnClickListener(v -> startActivity(new Intent(CravingActivity.this, ListActivity.class)));
    }

    private void questionAndAnswer() {
        String[][] QAs = {
                {"What would you prefer:\nSweet or Salt?", "Sweet", "Salt"}, //""
                {"What would you prefer:\nCake, Cookie or Other?", "Cake", "Cookie", "Other"}, //0
                {"What would you prefer:\nCupcake, Cake, Cheesecake?", "Cupcake", "Cake", "Cheesecake"}, //00
                {"What would you prefer:\nNuts, Chocolate or Fruits?", "Nuts", "Chocolate", "Fruit"}, //01
                {"What would you prefer:\nTartlet, Pudding, or Siruped?", "Tartlet", "Pudding", "Siruped"}, //02
                {"What would you prefer:\nPatty, Pastry Bun or Snacks?", "Patty", "Pastry Bun", "Snacks"}, //1
                {"What would you prefer:\nCheese, Vegetables or Meat?", "Cheese", "Vegetables", "Meat"}, //10, 11
                {"What would you prefer:\nCracker, Bagel or Rusk?", "Cracker", "Bagel", "Rusk"} //12
        };

        Button answer1 = findViewById(R.id.btnTestAnswer1);
        Button answer2 = findViewById(R.id.btnTestAnswer2);
        Button answer3 = findViewById(R.id.btnTestAnswer3);
        TextView question = findViewById(R.id.tvQuestion);
        TextView questionNumber = findViewById(R.id.tvQuestionNumber);

        if (count == 0) {
            answer3.setVisibility(View.INVISIBLE);
            question.setText(QAs[0][0]);
            questionNumber.setText("Question 1");
            answer1.setText(QAs[0][1]);
            answer2.setText(QAs[0][2]);
        }

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer3.setVisibility(View.VISIBLE);
                if (answer.equals("")) {
                    answer = "0";
                    setQuestionAnswer(QAs, answer1, answer2, answer3, question, 1);
                } else if (answer.equals("0")) {
                    answer = "00";
                    setQuestionAnswer(QAs, answer1, answer2, answer3, question, 2);
                } else if (answer.equals("1")) {
                    answer = "10";
                    setQuestionAnswer(QAs, answer1, answer2, answer3, question, 6);
                } else if (answer.equals("00")) {
                    answer = "000"; //ID: 11
                } else if (answer.equals("01")) {
                    answer = "010";
                } else if (answer.equals("02")) {
                    answer = "020"; //4
                } else if (answer.equals("10")) {
                    answer = "100"; //ID = 12
                } else if (answer.equals("11")) {
                    answer = "110";
                } else if (answer.equals("12")) {
                    answer = "120";
                }
                count++;
                if (count == 1) {
                    questionNumber.setText("Question 2");
                } else if (count == 2) {
                    questionNumber.setText("Question 3");
                }
                else if (count == 3) {
                    Intent intent = new Intent(CravingActivity.this, CravingResultActivity.class);
                    startActivity(intent);
                }
            }

        });
        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer3.setVisibility(View.VISIBLE);
                if (answer.equals("")) {
                    answer = "1";
                    setQuestionAnswer(QAs, answer1, answer2, answer3, question, 5);
                } else if (answer.equals("0")) {
                    answer = "01";
                    setQuestionAnswer(QAs, answer1, answer2, answer3, question, 3);
                } else if (answer.equals("1")) {
                    answer = "11";
                    setQuestionAnswer(QAs, answer1, answer2, answer3, question, 6);
                } else if (answer.equals("00")) {
                    answer = "001"; //ID = 1,2, 7
                } else if (answer.equals("01")) {
                    answer = "011"; //ID = 3,
                } else if (answer.equals("02")) {
                    answer = "021";
                } else if (answer.equals("10")) {
                    answer = "101"; //ID = 12
                } else if (answer.equals("11")) {
                    answer = "111";
                } else if (answer.equals("12")) {
                    answer = "121";
                }
                count++;
                if (count == 1) {
                    questionNumber.setText("Question 2");
                } else if (count == 2) {
                    questionNumber.setText("Question 3");
                }
                else if (count == 3) {
                    Intent intent = new Intent(CravingActivity.this, CravingResultActivity.class);
                    startActivity(intent);
                }
            }
        });
        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer3.setVisibility(View.VISIBLE);
                if (answer.equals("0")) {
                    answer = "02";
                    setQuestionAnswer(QAs, answer1, answer2, answer3, question, 4);
                } else if (answer.equals("1")) {
                    answer = "12";
                    setQuestionAnswer(QAs, answer1, answer2, answer3, question, 7);
                } else if (answer.equals("00")) {
                    answer = "002"; //ID = 8,
                } else if (answer.equals("01")) {
                    answer = "012";
                } else if (answer.equals("02")) {
                    answer = "022"; //ID = 6
                } else if (answer.equals("10")) {
                    answer = "102";
                } else if (answer.equals("11")) {
                    answer = "112";
                } else if (answer.equals("12")) {
                    answer = "122"; //ID = 9
                }
                count++;
                if (count == 1) {
                    questionNumber.setText("Question 2");
                } else if (count == 2) {
                    questionNumber.setText("Question 3");
                }
                else if (count == 3) {
                    Intent intent = new Intent(CravingActivity.this, CravingResultActivity.class);
                    startActivity(intent);
                }
            }
        });
    }



    private void setQuestionAnswer(String[][] arrayQA, Button ans1, Button ans2, Button ans3, TextView q, int i) {
        q.setText(arrayQA[i][0]);
        ans1.setText(arrayQA[i][1]);
        ans2.setText(arrayQA[i][2]);
        ans3.setText(arrayQA[i][3]);
    }
}
