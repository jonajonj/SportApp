package com.example.choupisport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.ArrayList;


public class ConfigExercicesActivity extends AppCompatActivity {

    private Spinner[]    saisieExerciceType             = new Spinner[Exercices.MAX_EXERCICE];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_exercices);

        //init des spinners
        saisieExerciceType[0]     = (Spinner)findViewById(R.id.spinnerTypeExercice1);
        saisieExerciceType[1]     = (Spinner)findViewById(R.id.spinnerTypeExercice2);
        saisieExerciceType[2]     = (Spinner)findViewById(R.id.spinnerTypeExercice3);
        saisieExerciceType[3]     = (Spinner)findViewById(R.id.spinnerTypeExercice4);
        saisieExerciceType[4]     = (Spinner)findViewById(R.id.spinnerTypeExercice5);
        saisieExerciceType[5]     = (Spinner)findViewById(R.id.spinnerTypeExercice6);
        saisieExerciceType[6]     = (Spinner)findViewById(R.id.spinnerTypeExercice7);
        saisieExerciceType[7]     = (Spinner)findViewById(R.id.spinnerTypeExercice8);
        saisieExerciceType[8]     = (Spinner)findViewById(R.id.spinnerTypeExercice9);
        saisieExerciceType[9]     = (Spinner)findViewById(R.id.spinnerTypeExercice10);

        //Recuperation du db de lecture la la base
        ExercicesTableDbHelper dbHelper = new ExercicesTableDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //requete pour recuperer les differents types d'exercices present dans la base
        String query = "SELECT DISTINCT " + ExercicesTable.ExercicesEntry.COLUMN_TYPE + " FROM " + ExercicesTable.ExercicesEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);

        //On met tous les resulats de la base dans un array liste
        ArrayList<String> sSpinnerList = new ArrayList<String>();
        sSpinnerList.add("NA");
        while(cursor.moveToNext()) {
            String type = cursor.getString(cursor.getColumnIndex(ExercicesTable.ExercicesEntry.COLUMN_TYPE));
            sSpinnerList.add(type);
        }

        //MAJ de la liste deroulante des sipnner avec les infos recupérées
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, sSpinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for (int i = 0; i < Exercices.MAX_EXERCICE; i++){
            saisieExerciceType[i].setAdapter(adapter);
        }

    }



    public void onClickStartExercice(View view) {

        // Le premier paramètre est le nom de l'activité actuelle
        // Le second est le nom de l'activité de destination
        Intent nextActivity = new Intent(this, ExerciceActivity.class);

        //on envoie les types d'exercice selectionné a l'activitée suivante
        for (int i = 0; i < Exercices.MAX_EXERCICE; i++) {
            nextActivity.putExtra(Integer.toString(i), saisieExerciceType[i].getSelectedItem().toString());
        }
        // Puis on lance l'intent !

        if (saisieExerciceType[0].getSelectedItem().toString() != "NA") {
            startActivity(nextActivity);
        }

    }


}