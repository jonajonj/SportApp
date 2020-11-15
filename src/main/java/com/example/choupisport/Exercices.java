package com.example.choupisport;

import java.util.ArrayList;

public class Exercices {

    public static final int MAX_EXERCICE = 10;

    public ArrayList<Exercice>   Exercices          = new ArrayList<Exercice>() ;

    public Exercices(){
    }

    public void add(Exercice exercice) {

        Exercices.add(exercice);
    }

    public  void addByValue(String type, String description) {

        Exercice exercice = new Exercice();

        exercice.type = type;
        exercice.description = description;

        Exercices.add(exercice);
    }

    public void clear() {

        Exercices.clear();
    }

    public Exercice get(int index) {

        return Exercices.get(index);
    }

    public int size() {

        return Exercices.size();
    }

}
