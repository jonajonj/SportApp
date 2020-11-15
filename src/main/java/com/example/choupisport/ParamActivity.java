package com.example.choupisport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;

public class ParamActivity extends AppCompatActivity {

    private static final int SELECT_XML = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param);


        //recuperation données de la base exercices
        ExercicesTableDbHelper dbHelper = new ExercicesTableDbHelper(this);

        //creation des variables de bouton
        Button ButtonDelDb = findViewById(R.id.paramButtonDelDb);
        Button ButtonInitDb = findViewById(R.id.paramButtonInitDb);
        Button paramButtonSelectFile = findViewById(R.id.paramButtonSelectFile);
        Button paramButtonAddExercice = findViewById(R.id.paramButtonAddExercice);


        //gestion netoyage de la base
        ButtonDelDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ExercicesTable.onUpgrade(dbHelper.getWritableDatabase(), 1, 1);
            }
        });


        //gestion init de la base
        ButtonInitDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ExercicesTable.init(dbHelper.getWritableDatabase());

            }
        });

        //gestion init de la base
        paramButtonSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select a file"), SELECT_XML);

            }
        });

        //gestion ajout manuel
        paramButtonAddExercice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //on change d'activité vers le configuration de la seance
                Intent StartActivity = new Intent(ParamActivity.this, AddExerciceActivity.class);
                startActivity(StartActivity);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_XML) {

                Uri selectedImageUri = data.getData();

                //Recuperation du db de lecture la la base
                ExercicesTableDbHelper dbHelper = new ExercicesTableDbHelper(this);
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                try {

                    InputStream is = getContentResolver().openInputStream(selectedImageUri);
                    ExercicesTable.importDataFromXml(db, is);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                db.close(); // Closing database connection

            }
        }

    }
}