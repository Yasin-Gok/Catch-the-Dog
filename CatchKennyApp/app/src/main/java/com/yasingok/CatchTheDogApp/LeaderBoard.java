package com.yasingok.CatchTheDogApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import com.yasingok.catchkennyapp.R;

public class LeaderBoard extends AppCompatActivity {

    Button back_btn;
    ListView easy_list, medium_list, hard_list;
    String seviye, paket_ismi;
    SharedPreferences veritabani;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        paket_ismi = "com.yasingok.CatchTheDogApp";
        veritabani = this.getSharedPreferences(paket_ismi, Context.MODE_PRIVATE);

        ArrayList<Integer> easyScores = new ArrayList<>();
        ArrayList<Integer> mediumScores = new ArrayList<>();
        ArrayList<Integer> hardScores = new ArrayList<>();

        int new_point = upload_data("point_key");
        seviye = getIntent().getStringExtra("zorluk_key");
        if (seviye != null) {
            if (seviye.equals("easy")) {addScore(easyScores, new_point);}
            else if (seviye.equals("medium")) {addScore(mediumScores, new_point);}
            else if (seviye.equals("hard")) {addScore(hardScores, new_point);}
        }

        System.out.println("New point: " + new_point);
        System.out.println("New seviye: " + seviye);

        ArrayAdapter<Integer> easyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, easyScores);
        ArrayAdapter<Integer> mediumAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mediumScores);
        ArrayAdapter<Integer> hardAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hardScores);

        back_btn = findViewById(R.id.back_btn);
        easy_list = findViewById(R.id.ListEasy);
        medium_list = findViewById(R.id.ListMedium);
        hard_list = findViewById(R.id.ListHard);

        easy_list.setAdapter(easyAdapter);
        medium_list.setAdapter(mediumAdapter);
        hard_list.setAdapter(hardAdapter);

        easyAdapter.notifyDataSetChanged();
        mediumAdapter.notifyDataSetChanged();
        hardAdapter.notifyDataSetChanged();
    }

    public void back(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addScore(ArrayList<Integer> scores, int newScore) {
        if (!scores.contains(newScore)) {
            scores.add(newScore);
            Collections.sort(scores, Collections.reverseOrder()); // Puanları sırala
            if (scores.size() > 10) {
                scores.subList(10, scores.size()).clear(); // 10'dan sonraki puanları temizle
            }
        }
    }

    public int upload_data(String score_key){
        String zorluk, puanStr;
        zorluk = getIntent().getStringExtra("zorluk_key");
        puanStr = getIntent().getStringExtra(score_key);
        int yeni_puan = 0;

        if (zorluk != null && puanStr != null) {
            int puan = Integer.parseInt(puanStr);
            yeni_puan = puan;
        }
        return yeni_puan;
    }
}