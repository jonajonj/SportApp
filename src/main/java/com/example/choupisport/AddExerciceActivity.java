package com.example.choupisport;

import androidx.appcompat.app.AppCompatActivity;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddExerciceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercice);

        EditText TypeExercice           = findViewById(R.id.addExerciceTypeExercice);
        EditText DecriptionExercice     = findViewById(R.id.addExerciceDecriptionExercice);
        Button ButtonAdd                = findViewById(R.id.exerciceNext);

        //Recuperation du db de lecture la la base
        ExercicesTableDbHelper dbHelper = new ExercicesTableDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //gestion bouton param
        ButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ExercicesTable.addExercice(db,TypeExercice.getText().toString(),DecriptionExercice.getText().toString());

                db.close();

                finish();

            }

        });
    }
}