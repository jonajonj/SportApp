package com.example.choupisport;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ExercicesTableDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "exercicestable.db";
    private static final int DATABASE_VERSION = 1;

    public ExercicesTableDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Méthode appelée pendant la création de la base de données
    @Override
    public void onCreate(SQLiteDatabase database) {
        ExercicesTable.onCreate(database);
        ExercicesTable.init(database);
    }

    // Méthode appelée pendant une mise a jour de la base de
    // données, par exemple vous augmentez sa version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        ExercicesTable.onUpgrade(database, oldVersion, newVersion);
    }

}
