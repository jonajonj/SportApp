package com.example.choupisport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private  Exercices  ExercicesList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creation des variables de bouton
        Button ButtonStart = findViewById(R.id.mainButtonStart);
        Button ButtonParam = findViewById(R.id.mainButtonParam);
        Button ButtonInfo = findViewById(R.id.mainButtonInfo);


        //gestion bouton param
        ButtonParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //on change d'activité vers le parametrage
                Intent paramActivity = new Intent(MainActivity.this, ParamActivity.class);
                startActivity(paramActivity);
            }
        });

        //gestion bouton start
        ButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //on change d'activité vers le configuration de la seance
                Intent StartActivity = new Intent(MainActivity.this, ConfigExercicesActivity.class);
                startActivity(StartActivity);
            }
        });

        //gestion bouton info
        ButtonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //on change d'activité vers le configuration de la seance
                Intent StartActivity = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(StartActivity);
            }
        });


        //ExercicesList = Exercices.getInstance();


    }


}