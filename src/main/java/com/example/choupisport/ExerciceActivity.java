package com.example.choupisport;

import androidx.appcompat.app.AppCompatActivity;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class ExerciceActivity extends AppCompatActivity {

    private  int        iNoExercice   = 0;
    private  int        iNoSerie      = 0;

    private Exercices exercicesSelected = new Exercices();


    String[] ExeciceType = new String[Exercices.MAX_EXERCICE];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercice);

        //on recupere les types d'exercies de l'activité de config
        for (int i = 0; i < Exercices.MAX_EXERCICE; i++) {
            ExeciceType[i] = getIntent().getStringExtra(Integer.toString(i));
        }


        TextView TypeExercice           = findViewById(R.id.exerciceTypeExercice);
        TextView DecriptionExercice     = findViewById(R.id.exerciceDecriptionExercice);
        TextView noSerie                = findViewById(R.id.exerciceNoSerie);
        TextView noExercice             = findViewById(R.id.exerciceNoExercice);
        Button  ButtonNext              = findViewById(R.id.exerciceNext);
        Button  ButtonStop              = findViewById(R.id.exerciceStop);


        ExercicesTableDbHelper dbHelper = new ExercicesTableDbHelper(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();


        //on vient choisir les exercices dans la BDD
        for (int i = 0; i < Exercices.MAX_EXERCICE; i++) {

            String query = "SELECT " + "*" +
                    " FROM " + ExercicesTable.ExercicesEntry.TABLE_NAME +
                    " WHERE " + ExercicesTable.ExercicesEntry.COLUMN_TYPE + " = " + "\"" + ExeciceType[i] + "\"" +
                    " ORDER BY RANDOM()";

            Cursor cursor = db.rawQuery(query, null);

            if(cursor.getCount()>0) {

                cursor.moveToFirst();

                String type = cursor.getString(cursor.getColumnIndex(ExercicesTable.ExercicesEntry.COLUMN_TYPE));
                String description = cursor.getString(cursor.getColumnIndex(ExercicesTable.ExercicesEntry.COLUMN_DESCRIPTION));

                exercicesSelected.addByValue(type,description);

            }
        }


        TypeExercice.setText(exercicesSelected.get(0).type);
        DecriptionExercice.setText(exercicesSelected.get(0).description);

        noExercice.setText(Integer.toString(iNoExercice+1));
        noSerie.setText(Integer.toString(iNoSerie+1));

        //gestion bouton next
        ButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iNoExercice ++;

                if (iNoExercice < Exercices.MAX_EXERCICE && iNoExercice < exercicesSelected.size() ) {

                    TypeExercice.setText(exercicesSelected.Exercices.get(iNoExercice).type);
                    DecriptionExercice.setText(exercicesSelected.Exercices.get(iNoExercice).description);

                    noExercice.setText(Integer.toString(iNoExercice+1));

                }else{

                    iNoExercice = 0;

                    TypeExercice.setText(exercicesSelected.Exercices.get(iNoExercice).type);
                    DecriptionExercice.setText(exercicesSelected.Exercices.get(iNoExercice).description);

                    noExercice.setText(Integer.toString(iNoExercice+1));

                    iNoSerie++;
                    noSerie.setText(Integer.toString(iNoSerie+1));
                }

            }
        });


        //gestion bouton param
        ButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


    }


}