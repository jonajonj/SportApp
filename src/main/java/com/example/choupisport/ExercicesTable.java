package com.example.choupisport;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.provider.BaseColumns;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.InputSource;



public class ExercicesTable {

    public static final String XML_BALISE_EXERCICE          = "exercices";
    public static final String XML_BALISE_TYPE              = "type";
    public static final String XML_BALISE_DESCRIPTION       = "description";


    /* Inner class that defines the table contents */
    public static class ExercicesEntry implements BaseColumns {
        public static final String TABLE_NAME = "exercices";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_DESCRIPTION = "description";
    }


    // Instruction SQL de création de la base de données
    private static final String DATABASE_CREATE = "create table "
            + ExercicesEntry.TABLE_NAME
            + "("
            + ExercicesEntry._ID + " integer primary key autoincrement, "
            + ExercicesEntry.COLUMN_TYPE + " text not null, "
            + ExercicesEntry.COLUMN_DESCRIPTION + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(ExercicesTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + ExercicesEntry.TABLE_NAME);
        onCreate(database);
    }

    public static void addExercice(SQLiteDatabase database,String type,String description) {

        ContentValues values = new ContentValues();
        values.clear();
        values.put(ExercicesTable.ExercicesEntry.COLUMN_TYPE, type);
        values.put(ExercicesTable.ExercicesEntry.COLUMN_DESCRIPTION, description);

        database.insert(ExercicesEntry.TABLE_NAME, null, values);
    }

    public static void importDataFromXml(SQLiteDatabase db,InputStream is) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(new InputSource(is));

            NodeList nodeList = document.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;

                    // Get the value of all sub-elements.
                    String sType        = elem.getElementsByTagName(XML_BALISE_TYPE).item(0).getChildNodes().item(0).getNodeValue();
                    String sDescription = elem.getElementsByTagName(XML_BALISE_DESCRIPTION).item(0).getChildNodes().item(0).getNodeValue();

                    //add exercice in base
                    ExercicesTable.addExercice(db,sType,sDescription);

                }
            }
        }catch (SAXException e){e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}
        catch (ParserConfigurationException e){e.printStackTrace();}
    }

    public static void init(SQLiteDatabase database){

        ExercicesTable.addExercice(database,"Dorsaux","A plat ventre, jambes au sol, paumes de main devant la tête. Lever les paumes et aller chercher bras tendus devant. 20 fois");
        ExercicesTable.addExercice(database,"Bras","Écarter les mains un peu plus loin que l’axe des épaules. Plier les jambes puis descendre le haut du corps et remonter.  15 fois");
        ExercicesTable.addExercice(database,"Bras","Prendre deux bouteilles d’eau. Tendre les bras devant soi puis les écarter, toujours bras tendus. 20 fois");
        ExercicesTable.addExercice(database,"Fessiers","Toujours avoir les bras et les jambes tendus. Faire une extension de hanche en balançant en arrière la jambe. 15 fois");
        ExercicesTable.addExercice(database,"Quadriceps","Mettre les pieds à côté de ceux de la chaise, joindre les mains, regarder devant soi. Toucher la chaise avec les fesses sans s’asseoir et remonter. 15 fois");
        ExercicesTable.addExercice(database,"Abdominaux","Jambes fléchies, bras tendus entre les cuisses, monter les omoplates et le buste en même temps. 20 fois");
        ExercicesTable.addExercice(database,"Dorsaux","S’allonger, alterner bras tendus devant et derrière. Garder les bras bien tendus et relever le buste en gardant toujours les pieds au sol. 20 fois");
        ExercicesTable.addExercice(database,"Gainage","Garder les bras tendus, contracter les fessiers et les abdos pour avoir une bonne posture. 1 minute");
        ExercicesTable.addExercice(database,"Fessiers","Allongé sur le dos soulever les fesses, tendre une jambe pendant une seconde avant de la reposer. 15 fois par jambe");
        ExercicesTable.addExercice(database,"Bras","Bras sur la chaise, jambes tendues, descendre en sortant les coudes et remonter jusqu’à avoir les bras tendus. 15 fois");
        ExercicesTable.addExercice(database,"Fessiers","A genoux, bras tendus, ouvrir la jambe sur le côté comme “lorsqu’un chien fait pipi”. 20 fois");
        ExercicesTable.addExercice(database,"Bras","Avec deux bouteilles d’eau à l’envers, l’une reste le long du corps, l’autre est placée derrière soi et on la monte bras tendu tout en étant aligné. 20 fois par bras");
        ExercicesTable.addExercice(database,"Ischios-jambiers","Mains sur les hanches, faire un pas en avant. Avec la jambe opposée, avoir un bref appui au sol et revenir. 20 fois par jambe");
        ExercicesTable.addExercice(database,"Abdominaux","Tête relevée, mains droite tendue, plier la jambe droite sur le genou gauche et avec le coude gauche plié toucher le genou droit. 20 fois");
        ExercicesTable.addExercice(database,"Dorsaux","A plat ventre, bras tendus, pieds au sol, lever les bras tendus à 10 cms du sol et faire des battements réguliers. 50 fois");
        ExercicesTable.addExercice(database,"Gainage","Bras et jambes tendus, toucher l’épaule gauche avec la main droite et inversement. Ne pas oublier de contracter les fessiers et les abdos. 20 fois");
        ExercicesTable.addExercice(database,"Quadriceps","Monter sur une chaise et pousser avec la jambe droite (la cuisse travaille). La gauche (où le mollet travaille) doit rester bien tendue. 15 fois par jambe");
        ExercicesTable.addExercice(database,"Mollets","Mains sur le mur, bras et jambes tendus, talons au sol, fessiers contractés, lever les pieds et pousser sur les mollets jusqu’aux orteils. 20 fois");
        ExercicesTable.addExercice(database,"Ischios-jambiers","Avec une télécommande au sol comme repère, mains sur les hanches, faire un pas en arrière, toucher brièvement le sol avec le genou et revenir. 15 fois");
        ExercicesTable.addExercice(database,"Fessiers","Allongé sur le côté, bras droit plié au sol, main sur la hanche gauche, monter la jambe droite toujours tendue. 25 fois par côté");
        ExercicesTable.addExercice(database,"Abdominaux","Allongé sur le dos, ramener les jambes, tenir la bouteille d’eau à l’horizontale et la monter, bras tendus. Les pieds ne bougent pas. 20 fois");
        ExercicesTable.addExercice(database,"Dorsaux","A plat ventre, mains au sol, jambes tendues et monter. Contracter les fessiers en même temps que la montée. 20 fois");
        ExercicesTable.addExercice(database,"Gainage","Allongé sur le dos, monter le bassin, contracter les ischios et regarder en l’air. 40 secondes");
        ExercicesTable.addExercice(database,"Bras","Mettre deux bouteilles d’eau à l’envers devant soi et tendre les bras. Regarder droit devant. 45 secondes");
        ExercicesTable.addExercice(database,"Quadriceps","Poser un manche à balai derrière le cou, mains à l’équilibre, pieds dans l’alignement du bassin, dos droit et descendre. 40 fois");
        ExercicesTable.addExercice(database,"Adducteurs","Pieds parallèles, mains sur les hanches, monter le genou et aller chercher sur le côté. 20 fois");
        ExercicesTable.addExercice(database,"Cuisses","Dos à la chaise, compter deux pas. Sur le troisième, placer la jambe droite sur la chaise et les mains sur les hanches. Regarder droit devant. Descendre et remonter jambe tendue. 15 fois par jambe");
        ExercicesTable.addExercice(database,"Abdominaux","Talons au sol, assis, joindre les mains et faire des twists de gauche à droite. Ne pas croiser les pieds et les garder parallèles. 15 fois par côté");
        ExercicesTable.addExercice(database,"Quadriceps","Mains sur les hanches, jambe droite pliée, la gauche derrière. Descendre et toucher brièvement le sol en poussant bien sur la jambe avant et en tendant l’arrière. 15 fois");
        ExercicesTable.addExercice(database,"Bras","Écarter les jambes, monter le manche à balai jusqu’en haut en inspirant et en gardant les bras tendus. Expirer en redescendant. 20 fois");
        ExercicesTable.addExercice(database,"Mollets","Manche à balai sur la nuque, mains bien écartées, pieds dans l’alignement du bassin, monter 1’’ sur les mollets et redescendre. 30 fois");
        ExercicesTable.addExercice(database,"Quadriceps","Mains sur les hanches, accomplir une fente avant suivi d’une fente arrière. Le genou ne touche pas le sol. 15 fois par jambe");
        ExercicesTable.addExercice(database,"Abdominaux","Sur le dos, jambes pliées, bras tendus le long du corps. Avec les bras toujours tendus, aller chercher devant en glissant sur la chaussure, le plus loin possible. 20 fois");
        ExercicesTable.addExercice(database,"Gainage","S’allonger sur le côté, avec un alignement jambes, bassin et buste. Faire des petits mouvements, avec un appui sur l’avant-bras droit et la main sur la hanche gauche. 45 secondes par côté");
        ExercicesTable.addExercice(database,"Fessiers","Se mettre à quatre pattes, mains et genoux au sol et donner un grand coup de pied en arrière, la jambe pliée. 15 fois par jambe");
        ExercicesTable.addExercice(database,"Bras","Pompes debout. Se coller à un pan de mur et compter trois pas. Se mettre face au mur, bras tendus et descendre en basculant vers l’avant puis remonter. 20 fois");
        ExercicesTable.addExercice(database,"Cuisses","Tenir le manche à balai à deux mains sous la nuque. Prendre un écart de jambes un peu plus large que le bassin. Ecarter les pieds vers l’extérieur et descendre (squat). 20 fois");
        ExercicesTable.addExercice(database,"Bras","Avec le manche à balai, prendre un écart de jambes bien plus large que le bassin. Bras tendus, buste en avant et mains au niveau du bas des cuisses et monter, toujours bras tendus. 15 fois");
        ExercicesTable.addExercice(database,"Cardio","Se tenir droit, mains sur les hanches et sautiller en écartant les pieds, sur la pointe. Toujours regarder droit devant. 50 fois");
        ExercicesTable.addExercice(database,"Abdominaux","Partir assis, les genoux pliés prendre la bouteille et la faire passer de droite à gauche. Pieds au sol ou relevés. Le bouteille ne doit pas toucher le sol. 20 fois de chaque côté");
        ExercicesTable.addExercice(database,"Dorsaux","A plat ventre, les pieds au sol. Tenir le balai derrière la nuque puis tendre les bras en avant. 15 fois");
        ExercicesTable.addExercice(database,"Gainage","Partir bras tendus, les jambes jointes, contracter les abdos, serrer les fesses et descendre le bassin. L’objectif est de faire des montées de genoux, mais très doucement, en gardant les genoux dans l’axe. 20 fois");
        ExercicesTable.addExercice(database,"Bras","Prendre deux bouteilles d’eau, écarter les jambes, le dos plié en avant, bras tendus. Regarder devant, monter les bouteilles d’eau sur les côtés en gardant les bras tendus et revenir. 20 fois");
        ExercicesTable.addExercice(database,"Mollets","En appui au sol, bras tendus, les pieds sur la pointe puis avancer sur la pointe. 20 fois");
        ExercicesTable.addExercice(database,"Quadriceps","Faire la chaise en plaquant son dos au mur, tenir les deux bouteilles d’eau bras tendus vers le bas et regarder droit devant. 30 secondes");
        ExercicesTable.addExercice(database,"Cardio","Sautiller avec des petites montées de genoux et de la fréquence. 50 secondes");
        ExercicesTable.addExercice(database,"Cardio","Commencer les mains sur les hanches, la jambe droite en avant, la gauche un peu derrière. Puis faire des petites fentes dynamiques en regardant toujours devant soi. Bien gérer sa respiration. 50 secondes");
        ExercicesTable.addExercice(database,"Abdominaux","Sur le dos, jambes relevées à 90°, tête relevée, bras tendus. L’objectif est d’aller toucher les chaussures près du talon. 20 fois");
        ExercicesTable.addExercice(database,"Dorsaux","A plat ventre, pieds au sol, relever le buste, tendre les bras et faire des petits battements croisés. 30 secondes");
        ExercicesTable.addExercice(database,"Gainage","Contracter les abdos et les fessiers. En appui sur les avant-bras, jambes tendues. 1 minute 15");
        ExercicesTable.addExercice(database,"Cardio","Partir debout, les mains jointes, descendre sur la chaise, la toucher et remonter en faisant un petit saut en s’aidant des mains. 20 fois");
        ExercicesTable.addExercice(database,"Bras","Pompes complètes. Pieds un peu écartés, mains écartées à largeur d’épaule et descendre. Puis remonter. 15 fois");
        ExercicesTable.addExercice(database,"Cardio","Mettre les mains devant soi, une jambe derrière, tendue, puis la monter, les bras servant de balance. 15 fois par jambe");
        ExercicesTable.addExercice(database,"Cardio","Pieds joints, mains sur les hanches et enchaîner les petits sauts dynamiques, droite, gauche, jambes tendues. 45 secondes");
        ExercicesTable.addExercice(database,"Cardio","Prendre deux bouteilles d’eau. Une jambe en avant, l’autre en retrait. Puis alterner, bras tendu, bras fléchi. Garder le rythme, serrer les dents et regarder devant soi. Puis changer de jambe. 30 secondes par jambe");
        ExercicesTable.addExercice(database,"Abdominaux","Allongé sur le dos, jambes pliées, bras le long du corps, relever la tête et les omoplates. Faire un mouvement de pédalo en ayant la jambe tendue à chaque fois à la fin. 20 fois par jambe");
        ExercicesTable.addExercice(database,"Gainage","Allongé sur le côté, coude droit posé au sol, bien aligner les jambes, le bassin et le buste. Lever le corps, passer le bras gauche sous le buste et revenir bras tendu. 15 fois de chaque côté");
        ExercicesTable.addExercice(database,"Fessiers","Jambes fléchies, adosser les épaules sur une chaise protégée. Mains sur la poitrine, monter et tenir 1”, fessiers contractés, en ressortant le bassin. Redescendre. 25 fois");
        ExercicesTable.addExercice(database,"Bras","Poser les avant-bras sur le mur. Reculer, jambes tendues. Ensuite, déplier les bras et revenir vers le mur. 20 fois");
        ExercicesTable.addExercice(database,"Fessiers","Aligner l’écart des jambes sur le bassin, mains sur les hanches, jambes tendues, croiser une jambe derrière et revenir. Ne pas aller trop loin. 15 fois par jambe");
        ExercicesTable.addExercice(database,"Cardio","Mains tendues derrière les fesses, monter les talons sur les fesses de manière dynamique. Prendre un bon rythme et bien respirer. 45 secondes");
        ExercicesTable.addExercice(database,"Cardio","Mains sur les hanches, sauter à pieds joints comme avec une corde à sauter. Appuyer brièvement au sol en ayant les jambes tendues. 45 secondes");
        ExercicesTable.addExercice(database,"Abdominaux","Allongé sur le dos, bras à la verticale, jambes pliées à 90°, descendre le bras droit tendu et en même temps la jambe gauche tendue (sans que les deux ne touchent le sol). Tenir 1” et revenir. Alterner. 20 fois");
        ExercicesTable.addExercice(database,"Dorsaux","A plat ventre, jambes tendues au sol et immobiles. Mains derrière les oreilles, monter le buste doucement en regardant droit devant. 20 fois");
        ExercicesTable.addExercice(database,"Gainage","Abdos et fessiers contractés, bassin rentré, mains au sol, bras et jambes tendues, lancer une jambe sur le côté opposé. Alterner. Garder un bon rythme sans se mettre dans le rouge. 15 fois par jambe");
        ExercicesTable.addExercice(database,"Mollets","Debout devant une chaise, poser trois doigts dessus sans s’appuyer. Lever une jambe et monter sur le mollet. Tenir 1” avant de redescendre. 15 fois");
        ExercicesTable.addExercice(database,"Cardio","Mains derrière la tête et coudes sortis. Joindre les pieds puis les écarter et revenir. Regarder droit devant et bien respirer. 50 secondes");
        ExercicesTable.addExercice(database,"Cardio","Mains derrière la tête. Coudes bien sortis, effectuer des petites montées de genoux en mode dynamique. 40 secondes");
        ExercicesTable.addExercice(database,"Bras","Trois doigts posés sur la chaise, plier les jambes, avancer le buste, tête bien alignée. Monter sur le côté le bras tendu en tenant une bouteille d’eau à l’envers. Tenir 1” et redescendre. 20 fois par bras");
        ExercicesTable.addExercice(database,"Cardio","Jambes écartées et pliées, une main tendu devant au sol, l’autre derrière. Se relever et réécarter les jambes en changeant de bras. 30 secondes");
        ExercicesTable.addExercice(database,"Etirement","Mains sur le mur, bras et jambes tendues, avancer une jambe pliée, l’autre reste tendue. Se pencher en avant en gardant le talon au sol et en amenant le poids du corps sur la jambe de devant. 15 secondes par jambe");
        ExercicesTable.addExercice(database,"Etirement","Debout, pieds alignés sur les épaules, s’appuyer sur le mur, plier un genou et ramener le talon sur la fesse. Regarder droit devant. 15 secondes par jambe");
        ExercicesTable.addExercice(database,"Etirement","Buste droit, jambes en grand écart, bras tendus devant, avancer le buste vers le sol. Bien relâcher et respirer tranquillement. 2 fois 15 secondes");
        ExercicesTable.addExercice(database,"Etirement","Jambe tendue sur une chaise, l’autre est tendue au sol. Pieds bien droits et alignés. Ramener le pied et les orteils vers soi. Garder les bras le long du corps et fléchir légèrement le buste. 15 secondes par jambe");
        ExercicesTable.addExercice(database,"Etirement","Assis, bras tendus derrière, jambes écartées un peu plus largement que le bassin. Ramener le genou droit au sol devant le pied gauche et alterner. 20 mouvements");
        ExercicesTable.addExercice(database,"Etirement","Assis, passer une jambe au-dessus de la jambe opposée, talon au sol. Mettre un bras autour du genou et le ramener vers la poitrine. L’autre bras reste tendu. 15 secondes par côté");
        ExercicesTable.addExercice(database,"Etirement","A genoux, bras tendus. Poser le bassin au sol en gardant les jambes tendues. Regarder devant, respirer. Sentir l’étirement des abdos. Tenir 5 à 6”. Ramener les fesses sur les talons, bras tendus, rentrer la tête entre les genoux. 8 à 10 fois");
        ExercicesTable.addExercice(database,"Etirement","A genoux, avancer une jambe avec le pied plus en avant que l’aplomb du genou. Mains sur les hanches, avancer le genou. 15 secondes par jambe");
        ExercicesTable.addExercice(database,"Abdominaux","Sur le dos, plier les jambes, les bras le long du corps et relever la tête et les omoplates. Puis faire des petits battements avec les bras tout en contractant les abdos. 40 battements");
        ExercicesTable.addExercice(database,"Dorsaux","A plat ventre, jambes tendues au sol, bras écartés en équerre. L’objectif est de monter le haut du corps sur une seconde puis de redescendre. 20 fois");
        ExercicesTable.addExercice(database,"Gainage","Contracter les abdos et les fessiers avec un bon alignement entre les jambes, le bassin et le buste. Partir bras et jambes tendus et toucher le bouchon de la bouteille d’eau opposée. 40 secondes");
        ExercicesTable.addExercice(database,"Fessiers","Se mettre à quatre pattes. Plier le coude droit qui va toucher le genou gauche puis tendre le bras droit et la jambe gauche en même temps. Revenir et répéter le mouvement. Inverser. 15 fois par jambe");
        ExercicesTable.addExercice(database,"Fessiers","Prendre un support ou poser sa main. Prendre appui sur la main gauche et tendre sur le côté la jambe droite puis, sans la poser, la lever devant en pliant le genou. 20 fois");
        ExercicesTable.addExercice(database,"Cardio","Poser une bouteille d’eau par terre et calculer cinq pas et en poser une deuxième. Puis faire un 8 entre les bouteilles avec des petits pas de course, avant, arrière. 40 secondes");
        ExercicesTable.addExercice(database,"Bras","Genoux au sol, croiser les jambes. Ramener un peu les mains vers l’intérieur. Plier les bras, descendre puis remonter. 10 fois");
        ExercicesTable.addExercice(database,"Cardio","Espacer deux bouteilles de 5 pas. Se mettre au milieu et toucher une bouteille puis l’autre en descendant bien. 40 secondes");
        ExercicesTable.addExercice(database,"Dorsaux","A plat ventre, jambes tendues, une bouteille derrière la nuque, lever légèrement le buste et venir toucher le sol avec la bouteille. Revenir, toujours avec le buste relevé. 30 secondes");
        ExercicesTable.addExercice(database,"Gainage","Bras et jambes tendus, abdos et fessiers contractés, bassin rentré, prendre la bouteille d’eau sur la droite et la ramener sur la gauche en passant sous le bras. Alterner. 30 fois");
        ExercicesTable.addExercice(database,"Cardio","Espacer deux bouteilles d’eau de 5 pieds. Mains devant, jambes pliées, sauter de l’une à l’autre en latéral (pas vers le haut). Mettre du rythme. 30 fois");
        ExercicesTable.addExercice(database,"Quadriceps","Mains sur les hanches, partir en fente avant, puis dans la foulée en fente arrière sur le côté en croisant. 30 fois par jambe");
        ExercicesTable.addExercice(database,"Cardio","Espacer deux bouteilles d’eau de 5 pieds, se mettre un peu en retrait. Sauter de l’une à l’autre dans un bon rythme. Essayer d’être coordonné avec les bras. 30 fois");
        ExercicesTable.addExercice(database,"Cardio","Une bouteille dans chaque main. Courir sur place à un bon rythme. Regarder droit devant et coordonner bien les bras en gérant la position des bouteilles. 30 secondes");
        ExercicesTable.addExercice(database,"Dorsaux","A plat ventre, jambes tendues au sol, lever le buste, plaquer le manche à balai sur la poitrine. Tendre les bras devant et revenir. 30 secondes");
        ExercicesTable.addExercice(database,"Gainage","Sur les coudes, jambes tendues, abdos et fessiers contractés, aligner le bassin. Lever une jambe tendue après l’autre, toujours bien dans l’alignement. 30 secondes");
        ExercicesTable.addExercice(database,"Adducteurs","Dos et mains au sol, sur les talons, bouteille d’eau à la verticale serrée entre les genoux, relever la tête et monter le bassin en serrant bien la bouteille. Tenir 1” en haut avant de redescendre. 30 secondes");
        ExercicesTable.addExercice(database,"Adducteurs","A côté d’une bouteille posée à 4 pas du mur sur lequel tendre les bras, monter sur les pointes de pied, bassin rentré. Faire un mouvement circulaire au-dessus de la bouteille et revenir au départ. 30 secondes");
        ExercicesTable.addExercice(database,"Ischios-jambiers","Se tenir de dos à 4 pas de la chaise. Manche à balai sur la nuque, placer le pied droit sur la chaise. Puis descendre avec la jambe gauche et remonter. 30 secondes");
        ExercicesTable.addExercice(database,"Ischios-jambiers","Manche à balai dans les mains, actionner une fente avant en tendant également les bras devant et ramener. Alterner. 30 secondes");
        ExercicesTable.addExercice(database,"Bras","Manche à balai dans les mains, actionner une fente avant en tendant également les bras devant et ramener. Alterner. 30 secondes");
        ExercicesTable.addExercice(database,"Dorsaux","A plat ventre, jambes tendues au sol, relever la tête et le buste. Une main sous le menton, tendre l’autre devant et alterner. 30 secondes");
        ExercicesTable.addExercice(database,"Fessiers","A quatre pattes, bras tendus, monter une jambe tendue et faire de petits mouvements de haut en bas en la gardant tendue. 30 secondes par jambe");
        ExercicesTable.addExercice(database,"Bras","A genoux, bras tendus devant sur un manche à balai, dos droit, avancer le buste et regarder devant. Monter bras tendus jusqu’en haut et redescendre toujours bras tendus. 30 secondes");
        ExercicesTable.addExercice(database,"Gainage","Bras et jambes tendus, abdos et fessiers contractés, bassin bien aligné. Pieds joints, les écarter un peu et revenir. 30 secondes");
        ExercicesTable.addExercice(database,"Cardio","Écarter les torchons de l’espace de 2 pieds. Se poster à côté du premier, passer au-dessus et faire 4 montées de genoux dynamiques entre les 2 avant de passer au-dessus de l’autre. 30 secondes");
        ExercicesTable.addExercice(database,"Abdominaux","Assis, jambes relevées mais non croisées, passer la bouteille d’eau dans le dos puis en-dessous des jambes. 30 secondes par sens");
        ExercicesTable.addExercice(database,"Dorsaux","A plat ventre, jambes tendues, bras tendus sur le manche à balai, tête toujours au sol, monter pendant 1” les bras et les jambes en même temps. 30 secondes");
        ExercicesTable.addExercice(database,"Gainage","Bras et jambes tendus, abdos et fessiers contractés, bassin rentré, pieds joints. Effectuer de petits talons-fesses en rythme. 30 secondes");
        ExercicesTable.addExercice(database,"Quadriceps","Devant la chaise, lever et plier une jambe. S’asseoir et remonter aussitôt en gardant la jambe pliée. 30 secondes par jambe");
        ExercicesTable.addExercice(database,"Ischios-jambiers","Mains sur les hanches, jambes dans la largeur du bassin, faire de petits squat-jumps dynamiques. Toujours finir bien jambes tendues. 30 secondes");
        ExercicesTable.addExercice(database,"Cardio","Effectuer 5 petites montées de genou, coordonnées avec les bras, suivies immédiatement de 5 talons-fesses. 30 secondes");
        ExercicesTable.addExercice(database,"Cardio","Toucher avec la main gauche le genou droit qui vient d’être monté en regardant droit devant. Alterner et garder le rythme. 30 secondes");
        ExercicesTable.addExercice(database,"Abdominaux","S’allonger sur le dos, plier les jambes, relever la tête et les omoplates, mains au sol, près des fesses. Lever les jambes à la perpendiculaire, les déplier puis les replier sans qu’elles touchent le sol. Ne pas creuser le dos. 30 fois");
        ExercicesTable.addExercice(database,"Gainage","Se positionner en appui au sol sur les coudes, contracter les abdos et les fessiers, rentrer le bassin. Tendre le bras gauche puis le droit. 30 secondes");
        ExercicesTable.addExercice(database,"Fessiers","Mettre le pied droit sur la chaise. Prendre appui pour monter sur la chaise puis lever le genou gauche. Puis changer de jambe. 30 fois par jambe");
        ExercicesTable.addExercice(database,"Bras","Écarter les pieds, largeur de bassin. Ils ne doivent pas bouger pendant l’exercice. Prendre les bouteilles en main, puis bouger les bras de manière hyper dynamique en regardant devant soi. 30 secondes");
        ExercicesTable.addExercice(database,"Fessiers","S’allonger sur le dos, les talons sur la chaise, les mains tendues le long du corps, tête posée. L’objectif est de monter le bassin, tenir une seconde en haut et redescendre vite. 30 fois");
        ExercicesTable.addExercice(database,"Cardio","Mains sur les hanches, partir en fente, faire un squat, revenir en fente, enchaîner par un squat et ainsi de suite. 30 secondes");
        ExercicesTable.addExercice(database,"Cardio","Écarter les bras et les jambes puis les ramener en même temps, dans un bon rythme. 30 fois");
        ExercicesTable.addExercice(database,"Abdominaux","Se mettre sur le dos, les jambes levées et pliées, les mains tendues sur les talons. L’objectif est de déplier les bras et les jambes en même temps puis de revenir. 30 secondes");
        ExercicesTable.addExercice(database,"Dorsaux","A plat ventre, bras et jambes tendues. Lever les bras et les jambes en même temps et les croiser sans qu’ils touchent le sol. 30 secondes");
        ExercicesTable.addExercice(database,"Gainage","Partir au sol bras et jambes tendues, contracter les abdos et les fessiers, descendre le bassin pour avoir un bon alignement. Ramener et plier la jambe gauche devant sans que le genou touche le sol et faire la même chose avec la jambe droite. Puis ramener les deux jambes à la position initiale et alterner. 30 secondes");
        ExercicesTable.addExercice(database,"Ischios-jambiers","Se mettre jambes écartées, un peu plus que la largeur du bassin. Écarter les pieds vers l’extérieur, positionner les mains derrière la tête. Descendre en amenant la main droite au talon gauche. Remonter puis redescendre, en inversant. 30 secondes");
        ExercicesTable.addExercice(database,"Cardio","Mains sur les hanches, sauter et tourner d’un quart de tour. Prendre un petit temps de suspension entre chaque saut. 30 secondes");
        ExercicesTable.addExercice(database,"Cardio","Monter la jambe droite et la faire revenir vers l’intérieur. La main gauche vient toucher le pied droit. Puis faire la même chose de l’autre côté. 30 secondes");
        ExercicesTable.addExercice(database,"Bras","Écarter les jambes, avancer le buste en gardant le dos droit jusqu’à sentir une tension au niveau des adducteurs. Positionner les bras tendus en gardant les bouteilles d’eau à l’horizontale. Monter les bouteilles devant soi, bras tendus, l’une après l’autre. 30 secondes");
        ExercicesTable.addExercice(database,"Cardio","Prendre le manche à balai, le poser sous la nuque, les mains de part et d’autre du manche. Écarter les jambes, largeur du bassin. Descendre puis, à la remontée, toucher la fesse avec le talon. Garder le dos bien droit et gérer sa respiration. 30 fois");
        ExercicesTable.addExercice(database,"Abdominaux","Sur le dos, bras tendus le long du corps, mains au sol et tête relevée. Lever les jambes tendues et effectuer de petits battements, de plus en plus vite. 40 secondes");
        ExercicesTable.addExercice(database,"Dorsaux","A plat ventre, pieds au sol, prendre les bouteilles à l’horizontale. Soulever, tendre les bras et revenir. 40 secondes");
        ExercicesTable.addExercice(database,"Gainage","Sur les coudes, abdos et fessiers contractés, bassin rentré, passer la jambe gauche tendue au-dessus de la droite, toucher le sol et revenir. Puis alterner. 40 secondes");
        ExercicesTable.addExercice(database,"Bras","Bras tendus, bouchons des bouteilles vers l’extérieur. Monter, bras pliés, au-dessus des pectoraux puis plus haut, bras tendus, en tournant les bouchons vers l’intérieur. 40 secondes");
        ExercicesTable.addExercice(database,"Cardio","En position fente avant, une jambe au sol, l’autre sur la pointe des pieds, manche à balai sous la nuque. Faire des fentes sautées dynamiques, vives au sol. 40 secondes");
        ExercicesTable.addExercice(database,"Bras","Plier une jambe devant soi. Monter les deux bouteilles à la verticale en même temps, bras tendus devant. Mettre les bouteilles à l’horizontale et redescendre les bras. 40 secondes");
        ExercicesTable.addExercice(database,"Bras","Pieds joints, bouteilles sous les oreilles. En écartant les jambes, monter un bras, ramener et faire pareil avec l’autre bras. En rythme. 40 secondes");
        ExercicesTable.addExercice(database,"Bras","Jambes dans la largeur du bassin, tendre le bras droit devant soi avec la bouteille puis le gauche, descendre et remonter. Finir sur un petit saut, jambe tendue. 40 secondes");
        ExercicesTable.addExercice(database,"Dorsaux","A plat ventre, jambes tendues, bras tendus au-dessus du sol, manche à balai en mains. Toucher la jambe droite avec la main droite en gardant les bras tendus. Idem pour la gauche. 40 secondes");
        ExercicesTable.addExercice(database,"Gainage","Abdos et fessiers contractés, bassin rentré, bras et jambes tendus. Lever successivement le bras droit devant puis le gauche, puis talon-fesse gauche et droit. Recommencer. 40 secondes");
        ExercicesTable.addExercice(database,"Bras","Placé sur le bord de la chaise, jambes fléchies, descendre et remonter. Dans la foulée, tendre les jambes, redescendre et remonter. Puis alterner. 40 secondes");
        ExercicesTable.addExercice(database,"Ischios-jambiers","Manche à balai sous la nuque, jambes dans l’alignement du bassin, descendre brièvement sur la chaise en regardant devant. Remonter avec un genou devant. Alterner. 40 secondes");
        ExercicesTable.addExercice(database,"Cardio","Faire une ligne avec un torchon, se placer sur un côté. Faire 3 montées de genou et basculer de l’autre côté de la ligne. Regarder devant en coordonnant les bras. 40 secondes");
        ExercicesTable.addExercice(database,"Cardio","Effectuer une série de talons-fesses, mettre du rythme. 40 secondes");
        ExercicesTable.addExercice(database,"Cardio","Espacer 2 torchons d’un pied. Jambes un peu fléchies au-dessus d’un torchon, mains sur les hanches, faire un pas chassé de gauche à droite. Bien tendre la jambe qui part vers l’extérieur. 40 secondes");
        ExercicesTable.addExercice(database,"Abdominaux","Assis, jambes pliées, bouteilles dans les mains, bouchons vers le haut. Lever les jambes et monter les bouteilles à l'horizontale. Puis lever les bras en l'air. Redescendre. Regarder toujours devant. 40 secondes");
        ExercicesTable.addExercice(database,"Gainage","Abdos et fessiers contractés, bassin rentré, bras et jambes tendus. Descendre sur les coudes l'un après l'autre et remonter. 40 secondes");
        ExercicesTable.addExercice(database,"Cardio","Debout, bras tendus, bouchons des bouteilles vers le haut, effectuer de petites montées de genoux dynamiques. Regarder droit devant. 40 secondes");
        ExercicesTable.addExercice(database,"Fessiers","En appui sur les bras tendus, lever le bassin. Lever une jambe tendue après l'autre. 40 secondes");
        ExercicesTable.addExercice(database,"Cardio","Espacer 2 bouteilles de 6 pieds. Faire 5 petits sauts, jambes tendues. Courir rapidement vers l'autre bouteille, la contourner et revenir en courant, mais en arrière. Après 5 nouveaux sauts, repartir dans l'autre sens. 40 secondes");
        ExercicesTable.addExercice(database,"Cardio","Espacer 2 bouteilles de 5 pieds. Effectuer une série de fentes, petites mais dynamiques, en allant de l'une à l'autre bouteille. 40 secondes");
        ExercicesTable.addExercice(database,"Dorsaux","À plat ventre, jambes et bras tendus au sol, tête relevée, monter en même temps le bras droit tendu et la jambe gauche. Alterner. 40 secondes");
        ExercicesTable.addExercice(database,"Gainage","En appui sur le coude droit, jambes, bassin et buste alignés, passer la bouteille loin derrière le buste sans toucher le sol. Revenir bras tendu en l'air. 40 secondes par côté");
        ExercicesTable.addExercice(database,"Bras","Jambes écartées, bras tendus, dos bien droit, plier les jambes et monter un sac à dos lesté de 3 bouteilles devant la bouche, coudes bien sortis. Redescendre et enchaîner. 40 secondes");
        ExercicesTable.addExercice(database,"Quadriceps","Serrer très fort devant le sac à dos toujours lesté, écarter les jambes un peu plus que la largeur du bassin. Écarter les pieds bien vers l'extérieur. Descendre et remonter. 40 secondes jambes tendues.");
        ExercicesTable.addExercice(database,"Mollets","Se tenir à 4 pieds du mur. En appui dessus, monter et descendre les talons sans jamais toucher le sol. 40 secondes");
        ExercicesTable.addExercice(database,"Cardio","Avec 2 bouteilles dans les mains, bras tendus le long du corps, pieds joints, effectuer une fente pied gauche, revenir, enchaîner sur une fente pied droit. Revenir. Écarter les pieds. Revenir et recommencer. 40 secondes");
        ExercicesTable.addExercice(database,"Dorsaux","A plat ventre, les jambes tendues et les mains levées devant soi. Lever les jambes, puis les écarter et les ramener. 40 secondes");
        ExercicesTable.addExercice(database,"Gainage","Partir en appui au sol sur les pieds et les mains, bras et jambes tendues, abdos et fessiers contractés. Plier les genoux sans toucher le sol et revenir, les bras restent tendus. 40 secondes");
        ExercicesTable.addExercice(database,"Cuisses","Prendre deux bouteilles. Écarter les jambes largeur de bassin, bras tendus, tenir les bouteilles avec les bouchons vers l'extérieur. Faire une petite fente arrière et, en même temps, plier les bras pour monter les bouteilles au-dessus des pectoraux. 40 secondes par jambe");
        ExercicesTable.addExercice(database,"Fessiers","Partir genou au sol, bras tendus. Pendant 20 secondes lancer une jambe en arrière, sans que le genou touche le sol. Puis, pendant 20 secondes, lever la jambe sur le côté. Alterner les jambes");
        ExercicesTable.addExercice(database,"Cardio","Faire un mouvement de bras devant avec les bouteilles, avec, en simultané, de petites montées de genou. 40 secondes");
        ExercicesTable.addExercice(database,"Cardio","Squat jumps. Prendre deux bouteilles. Écarter les torchons de la longueur d'un pied. Positionner un pied au milieu, un pied à l'extérieur. Sauter pour que ce dernier se retrouve à l'intérieur et l'autre à l'extérieur. Tenir les bouteilles, bras tendus. 40 secondes");
        ExercicesTable.addExercice(database,"Cardio","Faire six montées de genou, un saut pieds joints par-dessus le torchon puis revenir tout de suite en arrière avec un nouveau saut pieds joints. 40 secondes");
        ExercicesTable.addExercice(database,"Abdominaux","S'allonger sur le dos, prendre les bouteilles d'eau, et tendre les bras derrière la tête. Puis plier les jambes, les ramener devant, ainsi que les bras, toujours tendus. Ni les bras, ni les jambes ne touchent le sol. 40 secondes");
        ExercicesTable.addExercice(database,"Bras","Mettre trois bouteilles d'eau dans le sac à dos. Passer le manche entre les lanières du sac. Écarter les jambes, bras tendus. Plier un peu les jambes et monter les bras, toujours tendus, devant soi, jusqu'à la hauteur des yeux. Puis revenir et remonter. 40 secondes");
        ExercicesTable.addExercice(database,"Fessiers","Sur le dos, plier les jambes, se mettre sur les talons. Prendre les bouteilles et tendre les bras derrière la tête. Monter le bassin et en même temps, monter les bouteilles bras tendus devant. Maintenir une demi-seconde en haut puis redescendre. 40 secondes");
        ExercicesTable.addExercice(database,"Ischios-jambiers","Sur le dos, plier les jambes, se mettre sur les talons. Prendre les bouteilles et tendre les bras derrière la tête. Monter le bassin et en même temps, monter les bouteilles bras tendus devant. Maintenir une demi-seconde en haut puis redescendre. 40 secondes");
        ExercicesTable.addExercice(database,"Cardio","Faire des cloche-pieds au-dessus du torchon. 20 secondes par jambe");
        ExercicesTable.addExercice(database,"Cardio","Faire deux montées de genou, côté gauche du torchon, et à la troisième, partir à droite puis revenir. 40 secondes par jambe");
        ExercicesTable.addExercice(database,"Abdominaux","Assis, jambes pliées au-dessus du sol, mains pliées juste au-dessus des pectoraux, lever les vers le ciel en tenant une bouteille. 40 secondes");
        ExercicesTable.addExercice(database,"Dorsaux","A plat ventre, jambes tendues au sol, bras pliés, faire de petits mouvements bras tendus avec la bouteille de bas en haut, sans toucher le sol. 50 secondes");
        ExercicesTable.addExercice(database,"Bras","Dos droit, bras tendus sur la chaise, jambes pliées, les lancer, tendues, vers l'arrière. Regrouper, se redresser en sortant le bassin, bras tendus en arrière. 25 fois");
        ExercicesTable.addExercice(database,"Bras","Pieds dans la largeur du bassin, bouteilles en mains jambes et bras pliés, regarder devant. Tendre les bras vers l'arrière et les ramener devant, pliés. 30 fois");
        ExercicesTable.addExercice(database,"Ischios-jambiers","Pieds dans la largeur du bassin, bras tendus sur le manche à balai, effectuer une grande fente avant avec la jambe droite et montez bras tendus en même temps. Alterner. 20 fois");
        ExercicesTable.addExercice(database,"Cuisses","Faire la chaise (30''). Se relever, faire des quarts de squat, mains croisées devant (20 fois) puis recommencer la chaise (30''). ");
        ExercicesTable.addExercice(database,"Cuisses","Manche à balai derrière la nuque, jambes un peu plus écartées que la largeur du bassin, descendre, regrouper jambes tendues et recommencer. 1 minute 15");
        ExercicesTable.addExercice(database,"Abdominaux","Assis, pieds sur les talons, la bouteille sur la nuque, lever les pieds et monter les bras tendus avant de les ramener sur la nuque. 30 secondes");
        ExercicesTable.addExercice(database,"Gainage","Abdos et fessiers contractés, bassin rentré, tenir sur les coudes, puis sur les bras, puis sur un côté sans toucher le sol avec les genoux et un bras tendu en l'air. Idem de l'autre côté. 20 secondes par phase");
        ExercicesTable.addExercice(database,"Fessiers","Adossé à une chaise, pieds et jambes dans la largeur du bassin, mains croisées sur la poitrine, monter le bassin puis un genou et revenir. Tendre la jambe avant de revenir. 5 fois par jambe");
        ExercicesTable.addExercice(database,"Cardio","Espacer 3 torchons d'un pied. Faites 3 appuis à côté du premier, puis 2 entre le 1er et le 2e. Idem entre le 2e et le 3e puis de nouveau 3 appuis au-delà du 3e. Repartez dans l'autre sens, en rythme. 40 secondes");
        ExercicesTable.addExercice(database,"Quadriceps","Collé à la chaise, lever la jambe droite, bras tendus devant. Comptez 3'' pour descendre et s’asseoir. Reposer la jambe et enchaîner immédiatement un petit saut pieds joints. Recommencer en rythme. 5 fois par jambe");
        ExercicesTable.addExercice(database,"Cardio","Effectuer 10 montées de genoux, puis 6 belles fentes avant dans la foulée et enchaîner sur 10 talons-fesses. Recommencer le mouvement, monter en intensité. 6 mouvements");
        ExercicesTable.addExercice(database,"Abdominaux","Sur le dos, tête et omoplates relevées, tendre le manche à balai un peu à l'arrière de la tête. Lever les jambes et faire de petits battements, jambes tendues, sans toucher le sol. 30 secondes");
        ExercicesTable.addExercice(database,"Gainage","Abdos et fessiers contractés, bassin rentré, bras tendus, jambes pliées au départ, tendre les jambes et pousser le bassin en arrière en gardant les jambes tendues. Revenir et recommencer. 30 secondes");
        ExercicesTable.addExercice(database,"Mollets","Jambes dans la largeur du bassin, tenir le manche à balai au-dessus de la tête avec les bras pliés. Descendre les bras en montant en même temps sur les mollets et revenir dans la position initiale. 16 fois");
        ExercicesTable.addExercice(database,"Cuisses","Jambes dans la largeur du bassin, bras tendus sur le manche à balai devant soi. Faire une fente avant conséquente avec le pied droit. Monter et tourner en même temps les bras tendus vers la gauche. Revenir. 10 fois");
        ExercicesTable.addExercice(database,"Bras","Jambes dans la largeur du bassin, monter, bras tendu, le sac à dos lesté et garder le bras opposé le long du corps. Tenir une demi-seconde. Revenir. 7 fois");
        ExercicesTable.addExercice(database,"Cardio","Espacer 4 bouteilles de 2 pieds. À côté de la 1re, faire 3 fentes. Puis 2 fentes entre la 1re et la 2e, entre la 2e et la 3e et la 3e et la 4e. Puis de nouveau 3 fentes au-delà de la 4e. Repartir dans l'autre sens. 30 secondes");
        ExercicesTable.addExercice(database,"Cardio","Espacer 2 torchons d'1 pied. Faire 2 petites montées de genoux entre les deux. À la 3e, écarter la jambe à droite au-delà du torchon. Enchaîner avec 2 montées au milieu et écarter cette fois la jambe à gauche. 30 secondes");
        ExercicesTable.addExercice(database,"Abdominaux","Assis, lever les pieds. Se tourner avec la bouteille dans les mains à gauche, à droite et au-dessus. Finir bien bras tendus en l'air. Recommencer. 30 secondes");
        ExercicesTable.addExercice(database,"Dorsaux","À plat ventre, fessiers contractés, jambes tendues au sol, lever les bras tendus sur le manche à balai et faire de petits mouvements de haut en bas. 30 secondes");
        ExercicesTable.addExercice(database,"Gainage","Dos droit, bras tendus, jambes pliées sans toucher le sol avec les genoux, aller chercher un appui latéral avec la jambe droite. Idem à gauche, toujours bras tendus. 10 fois par jambe");
        ExercicesTable.addExercice(database,"Quadriceps","Jambes dans la largeur du bassin, regarder droit devant. Mains tendues devant, descendre dos droit et, en remontant, allez toucher une main avec un pied (ou un genou). 10 fois par jambe");
        ExercicesTable.addExercice(database,"Bras","Genoux un peu pliés, dos droit, bras tendus sur un sac à dos lesté, monter le sac jusqu'en haut des pectoraux et redescendre tranquillement, bras tendus. 15 fois");
        ExercicesTable.addExercice(database,"Cardio","Jambes légèrement écartées, effectuer de petits bondissements au-dessus du torchon, en allant très vite au sol et en arrivant sur la pointe ou l'avant des pieds. 30 secondes");
        ExercicesTable.addExercice(database,"Cuisses","Sac sur le dos lesté, dos droit, mains sur les hanches. Faire une fente arrière conséquente, descendre le genou sans qu'il touche le sol et ramener le devant soi. 7 fois par jambe");
        ExercicesTable.addExercice(database,"Cardio","Espacer 2 torchons de 4 pieds. Faire 3 montées de genoux dynamiques sur le côté du 1er. Puis 10 au milieu puis de nouveau 3 sur le côté du 2e. Recommencer dans l'autre sens. 30 secondes");
        ExercicesTable.addExercice(database,"Dorsaux","A plat ventre, bras et jambes tendus au sol, la tête étant relevée. Lever la jambe et le bras droits et tenir une seconde. Puis changer de côté. 30 secondes");
        ExercicesTable.addExercice(database,"Gainage","S'installer au sol sur le côté, en appui sur le pied et l'avant-bras. Prendre la bouteille, la passer sous le bras et la remonter en tendant le bras. 30 secondes par côté");
        ExercicesTable.addExercice(database,"Ischios-jambiers","S'installer dos au sol, les talons sur la chaise, tête posée au sol, bras le long du corps. Monter le bassin en restant sur les talons. En haut, bien contracter les fessiers. Tenir une seconde et redescendre. 10 fois");
        ExercicesTable.addExercice(database,"Bras","Exercice de pompes. Au sol, en appui sur les genoux et les mains, descendre sans toucher, tenir une demi-seconde puis pousser. 7 fois");
        ExercicesTable.addExercice(database,"Cardio","Pieds dans l'axe, rapprocher les jambes au maximum. Prendre le manche à balai bras tendus. Monter les bras au-dessus de la tête, et en même temps, écarter les jambes. Puis regrouper. 30 secondes");
        ExercicesTable.addExercice(database,"Quadriceps","S'asseoir sur la chaise, tendre les bras devant soi avec le balai, soulever la jambe gauche et se lever de la chaise uniquement avec la force de la jambe droite en gardant les bras tendus. Poser la jambe gauche, redescendre tranquillement. Et recommencer. 8 fois par jambe");
        ExercicesTable.addExercice(database,"Cardio","Enchaîner dix montées de genou, un squat, bloquer une demi-seconde, dix talons-fesses, un squat, bloquer une demi-seconde et après, dix montées jambes tendues, un squat, bloquer puis recommencer le mouvement. 4 mouvements");
        ExercicesTable.addExercice(database,"Abdominaux","S'allonger sur le dos. Relever la tête, monter les bras tendus devant soi et plier les jambes pour faire un angle à 90°. Déplier une jambe après l'autre, en gardant celle qui est fixe à 90°. 30 secondes");
        ExercicesTable.addExercice(database,"Gainage","Dans un sac à dos, mettre deux bouteilles. S'installer sur les coudes, jambes tendues, contracter les abdos et les fessiers. Tenir 15 secondes de manière statique et 15 secondes, monter les jambes tendues, l'une après l'autre.");
        ExercicesTable.addExercice(database,"Bras","Prendre le sac lesté au niveau de la lanière supérieure, pieds dans l'axe, jambes écartées largeur de bassin, et monter le bras tendu jusqu'à l'horizontale, dans un angle de 45°, pendant une demi-seconde. 8 fois");
        ExercicesTable.addExercice(database,"Cardio","Aligner deux torchons au sol, écartés de deux pieds. Enchaîner des sauts pieds joints, deux sur le premier côté, trois dans l'intervalle et deux à nouveau sur l'autre côté. 30 secondes");
        ExercicesTable.addExercice(database,"Bras","Partir bras tendus, en appui sur la chaise, et jambes tendues, bassin sorti. Ramener le genou devant. 30 secondes");
        ExercicesTable.addExercice(database,"Fessiers","Partir bras tendus, en appui sur la chaise, et jambes tendues, bassin sorti. Ramener le genou devant. 30 secondes");
        ExercicesTable.addExercice(database,"Ischios-jambiers","Partir bras tendus, en appui sur la chaise, et jambes tendues, bassin sorti. Ramener le genou devant. 30 secondes");
        ExercicesTable.addExercice(database,"Cardio","Espacer deux torchons de deux pieds. Faire un petit saut vertical. À l'atterrissage, un pied touche la droite. Revenir, refaire un saut et, cette fois, toucher à gauche, puis revenir. 30 secondes ");
        ExercicesTable.addExercice(database,"Cardio","Espacer trois bouteilles de trois pieds, poser un torchon au milieu des intervalles. Faire des montées de genou dynamiques, de droite à gauche puis de gauche à droite. Au-dessus des bouteilles, exagérer les montées de genou, au-dessus des torchons, faire de petites montées de genou rasantes. 30 secondes");
        ExercicesTable.addExercice(database,"Gainage","Abdos et fessiers contractés, bassin rentré, jambes et bras tendus, avancer le bras droit puis le gauche et revenir. Recommencer. 30 secondes");
        ExercicesTable.addExercice(database,"Cuisses","Mains sur les hanches, jambes dans la largeur du bassin, sac à dos lesté, faire une fente de côté à gauche et revenir. Alterner. Laisser toujours l'autre pied dans l'axe. 7 fois");
        ExercicesTable.addExercice(database,"Cuisses","Sac lesté sur le dos, pieds dans l'axe, jambe droite pliée sur une chaise, monter sur une jambe, l'autre reste tendue. Redescendre. Finir le geste avec un genou devant, et redescendre. 7 fois par jambe");
        ExercicesTable.addExercice(database,"Cardio","Manche à balai sur la nuque, jambe gauche devant et la droite à l'arrière, faire 2 fentes dynamiques et changer le sens. 30 secondes");
        ExercicesTable.addExercice(database,"Cardio","Bras tendus sur une chaise, jambes tendues derrière soi, bassin rentré. Faire un petit saut vers l'avant et regrouper. Puis sauter en l'air. Recommencer. 10 fois");
        ExercicesTable.addExercice(database,"Dorsaux","À plat ventre, jambes tendues au sol, tête et buste un peu levés, bras pliés et levés, poser la bouteille devant soi avec le bras gauche sans toucher le sol. Et la reprendre avec le bras droit. Poser la près de soi. Recommencer. 30 secondes");
        ExercicesTable.addExercice(database,"Gainage","Abdos et fessiers contractés, bassin rentré, jambes et bras tendus, lever en alternance un bras en l'air devant soi. 30 secondes");
        ExercicesTable.addExercice(database,"Cuisses","Plaquer sur le ventre le sac à dos lesté. Dos bien plaqué contre le mur, faire la chaise, pieds dans l'axe, jambes à 90 degrés. 30 secondes");
        ExercicesTable.addExercice(database,"Cardio","Espacer 3 torchons d'un pied. Se poser à gauche du 1er, faire 2 appuis dans l'intervalle entre le 1er et le 2e, le 2e et le 3e, puis au-delà du 3e. Revenir en un seul bond au delà du 1er. Recommencer. 30 secondes");
        ExercicesTable.addExercice(database,"Bras","À genoux, pieds, coudes et paumes au sol, bassin rentré. Avec un sac à dos lesté, monter à la force des bras sur ses mains jusqu'à avoir les bras tendus en inspirant et revenir. 8 fois");
        ExercicesTable.addExercice(database,"Quadriceps","Manche à balai sur la nuque, se tenir à 4 pieds dos à la chaise, pied gauche dessus, dos droit. Monter et descendre en levant le manche à balai, bras tendus en l'air à la fin du mouvement. 7 fois");
        ExercicesTable.addExercice(database,"Cuisses","Espacer 2 torchons de 3 pieds. Pieds dans l'axe, jambes légèrement pliées et écartées de la largeur du bassin, sauter au delà du 2e torchon. 10 fois");
        ExercicesTable.addExercice(database,"Gainage","Abdos et fessiers contractés, bassin rentré, jambes et bras tendus, placez le sac à dos lesté le côté gauche. Avec le bras droit, le ramener sur la droite. Idem avec le bras gauche. 30 secondes");
        ExercicesTable.addExercice(database,"Cardio","Placer 2 torchons en croix. Avec un balai sur la nuque, mettre le pied droit devant, le gauche derrière. Mains sur les hanches, sauter en écartant les jambes. Revenir avec le pied gauche devant et le droit derrière. Enchaîner. 30 secondes");
        ExercicesTable.addExercice(database,"Bras","Jambes un peu fléchies, manche à balai passé dans les lanières du sac à dos lesté, monter le manche au niveau des pectoraux et enchaîner bras tendus en l'air. Redescendre tranquillement, bras tendus. 12 fois");
        ExercicesTable.addExercice(database,"Cuisses","Proche de la chaise, dos droit, mains sur les hanches, lever une jambe, descendre en 2'' et remonter tout seul, à la force de la jambe au sol. Faire un petit saut. 8 fois");
        ExercicesTable.addExercice(database,"Cardio","Espacer 3 torchons de 2 pieds. Genoux un peu fléchis, pieds de part et d'autre du 1er torchon, sauter en latéral au-dessus du 2e puis du 3e. Repartir dans l'autre sens, avec 2 montées de genoux entre les torchons. Recommencer le mouvement. 30 secondes");
        ExercicesTable.addExercice(database,"Abdominaux","Sur le dos, lever les jambes tendues, bras tendus derrière la tête avec le manche à balai. Plier et déplier les jambes sans toucher le sol. 30 secondes");
        ExercicesTable.addExercice(database,"Dorsaux","À plat ventre, tête et buste un peu levés, jambes tendues levées, lever les bras tendus devant soi puis sur les côtés. Revenir et recommencer. 30 secondes");
        ExercicesTable.addExercice(database,"Gainage","Abdos et fessiers contractés, bassin rentré, lever une jambe et la bouger, en la gardant tendue, de bas en haut. À la moitié de l'effort, passer à l'autre jambe. 40 secondes");
        ExercicesTable.addExercice(database,"Bras","Un bras tendu en appui sur la chaise, jambes légèrement pliées, dos droit, monter le sac à dos lesté avec l'autre bras jusqu'en haut des pectoraux. 10 fois");
        ExercicesTable.addExercice(database,"Cardio","Faire 3 séries de 50 montées de genoux, en augmentant l'allure toutes les 10 montées. ");
        ExercicesTable.addExercice(database,"Fessiers","Jambes dans la largeur du bassin, mains en appui sur la chaise, monter la jambe gauche devant soi, avancer le buste et déplier la même jambe toute droite derrière soi, la tendre. Tenir 1 demi-seconde. Revenir. 10 fois");
        ExercicesTable.addExercice(database,"Ischios-jambiers","Jambes dans la largeur du bassin, mains en appui sur la chaise, monter la jambe gauche devant soi, avancer le buste et déplier la même jambe toute droite derrière soi, la tendre. Tenir 1 demi-seconde. Revenir. 10 fois");
        ExercicesTable.addExercice(database,"Cardio","Jambes serrées, genoux pliés, bras tendus devant soi avec 1 bouteille dans chaque main, écarter en même temps les bras tendus et les jambes (un squat). Revenir toujours bras tendus. 30 secondes");
        ExercicesTable.addExercice(database,"Cardio","Espacer 3 torchons de 2 pieds. Faire un appui dans chaque intervalle entre les torchons. Se tourner et recommencer en allant à un bon rythme. 30 secondes");
        ExercicesTable.addExercice(database,"Abdominaux","Avec un sac lesté, s'asseoir, partir sur les talons, prendre le sac puis lever les talons et aller de droite à gauche avec le sac sans qu'il touche le sol. 30 secondes");
        ExercicesTable.addExercice(database,"Dorsaux","A plat ventre, jambes tendues au sol, fessiers contractés. Passer la bouteille d'une main à l'autre sans que les bras, tendus, ne touchent le sol. 30 secondes");
        ExercicesTable.addExercice(database,"Gainage","Bien serrer le sac lesté. Partir bras et jambes tendus, pieds joints. En gardant les jambes tendues, aller à droite avec la jambe droite, à gauche avec la gauche. Puis ramener la droite au milieu, ensuite la gauche et ainsi de suite. 30 secondes");
        ExercicesTable.addExercice(database,"Cuisses","Mettre le sac lesté sur le ventre, écarter les jambes plus que la largeur du bassin, mettre les pieds à 10 h 10, bien serrer le sac contre soi. Le dos droit, regarder devant soi et descendre en trois secondes et remonter. Les pieds restent au sol. 12 fois");
        ExercicesTable.addExercice(database,"Cardio","Se mettre en position de fente, en faire une deuxième. Puis se mettre en position pour un squat jump. Une fois le squat jump réalisé, enchaîner avec les deux fentes, et refaire l'ensemble. 30 secondes");
        ExercicesTable.addExercice(database,"Bras","Écarter les jambes, plus que la largeur du bassin, les pieds dans l'axe. Passer le manche à balai dans les lanières du sac. Tenir le sac bras tendus entre les jambes et le monter en pliant les coudes jusqu'au menton. Bien freiner à la descente. 12 fois");
        ExercicesTable.addExercice(database,"Cardio","Aligner trois torchons au sol. Faire deux montées de genou, à la troisième, toucher de l'autre côté des torchons, puis revenir. Le tout en avançant et en reculant le long des torchons et en gardant une bonne dynamique. 30 secondes");
        ExercicesTable.addExercice(database,"Cardio","Poser au sol trois torchons en forme de U. Faire un saut pieds joints vers l'avant, puis un saut pieds joints vers la droite, revenir au centre, faire un nouveau saut vers l'avant. Puis revenir en sautant vers l'arrière, nouveau saut à droite, revenir au centre, saut vers l'arrière au point de départ. 30 secondes");
        ExercicesTable.addExercice(database,"Abdominaux","Sur le dos, jambes tendues au sol, tête et épaules relevées, mains en arrière sur le sol, venir toucher avec elles la chaussure droite puis recommencer et toucher la gauche. 30 secondes");
        ExercicesTable.addExercice(database,"Dorsaux","À plat ventre, fessiers contractés, jambes tendues levées, mains soulevées sous le menton, faire de petits mouvements du buste de bas en haut. 30 secondes");
        ExercicesTable.addExercice(database,"Gainage","Abdos et fessiers contractés, bassin rentré, bras et jambes tendus, plier vos jambes sans que les genoux ne touchent le sol. Enchaîner. 30 secondes");
        ExercicesTable.addExercice(database,"Cuisses","Faire la chaise, dos bien plaqué au mur, pieds dans l'axe et le sac à dos lesté posé sur les genoux. Tenir 25''. Se lever. Jambes légèrement pliées, sauter jambes tendues 5 fois.");
        ExercicesTable.addExercice(database,"Bras","Jambes un peu plus écartées que le bassin, mains devant soi, descendre jambes pliées et basculer en posant au sol la main droite devant soi, puis la gauche jusqu'à arriver en position de gainage. Tenir 1'' et revenir, de la même manière, dans la position de départ. 10 fois");
        ExercicesTable.addExercice(database,"Cardio","Debout, dos droit, pieds joints, 1 bouteille (0,5 l pour les débutants, 1,5 l pour les autres) dans vos mains jointes près de vous, écartez les jambes tout en tendant les bras devant vous. Ramenez. 30 secondes");
        ExercicesTable.addExercice(database,"Cardio","Bras et jambes tendus au sol, en position de gainage, regrouper les jambes devant soi et finir droit en regardant devant. Ajouter un petit saut à la fin. 8 fois");
        ExercicesTable.addExercice(database,"Cardio","Pieds dans l'axe, jambes dans la largeur du bassin, monter le genou gauche et venir le toucher avec le coude droit. Alterner en mettant du rythme. 30 secondes");
        ExercicesTable.addExercice(database,"Abdominaux","Sur le dos, jambes tendues, bras tendus le long du corps, tête légèrement levée. Monter une jambe tendue, redescendre sans toucher le sol, monter l'autre. Recommencer. 30 secondes");
        ExercicesTable.addExercice(database,"Cuisses","Mains sur les hanches, faire une fente avant. Enchaîner sur une fente arrière et faire un petit saut sur ses 2 pieds. 10 fois par jambe");
        ExercicesTable.addExercice(database,"Bras","Dos à la chaise, sac à dos lesté sur le ventre, jambes tendues devant soi, bras tendus sur la chaise, descendre et remonter bras tendus. 8 fois");
        ExercicesTable.addExercice(database,"Cardio","Espacer 3 torchons de 2 pieds. Faire 2 montées de genou entre chaque torchon et revenir en talons-fesses entre chaque torchon. Recommencer. 30 secondes");
        ExercicesTable.addExercice(database,"Cardio","Espacer 2 torchons de 2 pieds. Dos droit, genoux pliés, jambes dans la largeur du bassin, sauter au-delà du 2e torchon. Atterrir sur 1 pied et stabiliser. Se retourner et recommencer en atterrissant sur l'autre pied. 8 fois par jambe");
        ExercicesTable.addExercice(database,"Cardio","Jambes dans la largeur du bassin, genoux légèrement pliés, sauter avec l'aide des bras et se tourner vers la gauche en même temps. Descendre bien et retourner dans la position initiale. Cette fois, tourner à droite. 30 secondes");
        ExercicesTable.addExercice(database,"Dorsaux","À plat ventre, fessiers contractés, buste, tête et épaules légèrement relevées, tendre les bras devant soi sur la bouteille. La balancer, de droite à gauche, et inversement. 30 secondes");
        ExercicesTable.addExercice(database,"Gainage","Abdos et fessiers contractés, bassin rentré, bras et jambes tendus, monter le genou droit vers l'extérieur, bras toujours tendus. Alterner. Revenir. 30 secondes");
        ExercicesTable.addExercice(database,"Cuisses","Bouteilles dans les mains, se tenir à 3 pieds dos à la chaise et poser le pied gauche dessus. Bras tendus, dos droit, descendre en 2'' et remonter en 1''. Regarder droit devant. 10 fois par jambe");
        ExercicesTable.addExercice(database,"Fessiers","Allongé, sac à dos lesté et plaqué sur le ventre, talons sur la chaise, tête levée, monter le bassin le plus haut possible. Tenir 1'', redescendre. 12 fois");
        ExercicesTable.addExercice(database,"Cuisses","Espacer 2 torchons de 3 pieds. Genoux pliés, jambes dans la largeur du bassin, bouteilles en main, sauter au-delà du 2e torchon. Faire rapidement une petite course en arrière pour revenir au départ. Recommencer. 30 secondes");
        ExercicesTable.addExercice(database,"Mollets","Sac lesté sur le dos, se tenir à 4 pieds du mur, bras tendus dessus, bassin rentré. Monter la jambe gauche pliée en relevant la pointe du pied vers soi et monter brièvement sur la pointe du pied droit. 15 fois par jambe");
        ExercicesTable.addExercice(database,"Cardio","Espacer 2 torchons d'1 pied. Se placer au milieu, pieds joints, bras tendus devant soi. Faire 2 sauts, jambes tendues, puis écarter en position de quart de squat. Faire 2 sauts dans cette position et revenir pieds joints. Recommencer. 30 secondes");
        ExercicesTable.addExercice(database,"Abdominaux","Sur le dos, jambes pliées et ramenées vers soi, tête et épaules légèrement levées, monter les bouteilles de haut en bas en gardant les bras tendus, sans jamais leur faire toucher le sol. 30 secondes");
        ExercicesTable.addExercice(database,"Dorsaux","À plat ventre, abdos et fessiers contractés, jambes levées, tête relevée, un bras dans le dos, l'autre au milieu du manche à balai tendu devant soi. Faire de petits mouvements de bas en haut sans toucher le sol. 20 secondes");
        ExercicesTable.addExercice(database,"Bras","Jambes dans la largeur du bassin, genoux légèrement pliés, sac à dos lesté, monter les bras tendus, en serrant le manche à balai avec la paume des mains vers le haut. En haut, ouvrir les mains, tenir 1 demi-seconde, redescendre. 10 fois");
        ExercicesTable.addExercice(database,"Cuisses","Espacer 2 torchons de 2 pieds. Sac sur le dos lesté, mains sur les sangles, jambes dans la largeur du bassin, faire une fente avant, poser brièvement le genou au sol, revenir. Alterner. 8 fois par jambe");
        ExercicesTable.addExercice(database,"Bras","Jambes écartées un peu plus que la largeur du bassin, pieds à 10h10, sac à dos lesté devant soi, bras pliés sur les côtés du sac, descendre et remonter jusqu'à tendre les bras en l'air. 8 fois");
        ExercicesTable.addExercice(database,"Cuisses","Jambes écartées un peu plus que la largeur du bassin, pieds à 10h10, sac à dos lesté devant soi, bras pliés sur les côtés du sac, descendre et remonter jusqu'à tendre les bras en l'air. 8 fois");
        ExercicesTable.addExercice(database,"Cardio","Faire un carré avec 4 torchons, sauter à pieds joints devant. Revenir au milieu puis enchaîner les sauts sur le côté droit, derrière et à gauche en revenant toujours au milieu entre chaque. 30 secondes");
        ExercicesTable.addExercice(database,"Cardio","Faites 10 petites montées de genoux. Puis en position de squat, sauter 4 fois en maîtrisant bien sa réception. Enchaîner sur 4 fentes. Recommencer tout le mouvement. 4 fois");
        ExercicesTable.addExercice(database,"Abdominaux","Prendre deux bouteilles d'eau. Se mettre sur le dos, jambes pliées. Tenir les bouteilles, bras tendus, derrière la tête, sans qu'elles touchent le sol. Les ramener devant soi, toujours bras tendus, et en même temps, ramener une jambe. Se remettre en position et répéter les gestes avec l'autre jambe. 30 secondes");
        ExercicesTable.addExercice(database,"Dorsaux","A plat ventre, jambes tendues au sol. Prendre le manche à balai, plier les bras puis les tendre et les monter. 30 secondes");
        ExercicesTable.addExercice(database,"Gainage","Partir bras et jambes tendus en position de gainage. La main droite va toucher le dos pendant une seconde puis revenir à la position initiale. Alterner avec la main gauche. 30 secondes");
        ExercicesTable.addExercice(database,"Cardio","Se positionner pieds dans l'axe en serrant les jambes. Tenir les bouteilles d'eau, bras tendus, de part et d'autre. Écarter les jambes avec un petit saut et en même temps, écarter les bouteilles, toujours bras tendus. 30 secondes");
        ExercicesTable.addExercice(database,"Bras","Partir bras tendus en appui sur la chaise, et jambes tendus, le bassin rentré. Faire une montée du genou gauche, une autre du genou droit et descendre en faisant une pompe. 10 fois");
        ExercicesTable.addExercice(database,"Bras","Dans le sac à dos lesté. Pieds dans l'axe, écartés un peu plus que la largeur du bassin. Tenir le sac avec la lanière supérieure, plier un peu les genoux. Faire remonter le sac devant soi puis le faire descendre entre les jambes, toujours bras tendus. 12 fois");
        ExercicesTable.addExercice(database,"Cuisses","Sac à dos lesté. Pieds dans l'axe, écartés un peu plus que la largeur du bassin. Tenir le sac avec la lanière supérieure, plier un peu les genoux. Faire remonter le sac devant soi puis le faire descendre entre les jambes, toujours bras tendus. 12 fois");
        ExercicesTable.addExercice(database,"Cardio","Espacer les trois torchons d'un pied. Partir latéralement vers la droite, en faisant deux montées de genou dans l'intervalle. Arrivé au bout, ne prendre qu'un appui, bloquer, et repartir dans l'autre sens. 30 secondes");
        ExercicesTable.addExercice(database,"Cardio","Pieds dans l'axe, écartés largeur de bassin. Plier légèrement les genoux pour être en position de quart de squat. Utiliser les bras pour bondir vers l'avant et revenir avec trois petits sauts très dynamiques. 30 secondes");
        ExercicesTable.addExercice(database,"Gainage","Contracter les abdos, les fessiers et rentrer le bassin. Première position : bras et jambes tendus. Puis tenir sur les coudes. Puisse mettre en position latérale sur le coude droit. Puis se mettre sur le coude gauche. 20 secondes par côté");
        ExercicesTable.addExercice(database,"Ischios-jambiers","Mettre le sac lesté bien serré sur le ventre et s'installer avec le haut du dos en appui sur la chaise, les jambes pliées, les pieds dans l'axe. Bien monter pour ressortir le bassin et enchaîner. Bien contracter les fessiers à la fin du mouvement. 8 fois");
        ExercicesTable.addExercice(database,"Bras","Partir pieds dans l'axe, jambes écartées largeur de bassin. Prendre le sac lesté avec la lanière supérieure, le monter bras tendu sur le côté, tenir une demi-seconde, le passer devant soi, toujours bras tendu, puis redescendre. 7 fois");
        ExercicesTable.addExercice(database,"Cuisses","S'installer contre un mur façon chaise. Puis se lever et se mettre en position pour faire des squats. Pieds dans l'axe, jambes écartées largeur de bassin, et descendre puis remonter. Une fois cinq squats réalisés, se remettre contre le mur dans la position de la chaise. 20 + 15 secondes");
        ExercicesTable.addExercice(database,"Cardio","Se mettre en fente, sur l'avant des pieds, regarder devant, utiliser ses bras et enchaîner les fentes larges. La jambe arrière est toujours tendue. 30 secondes");
        ExercicesTable.addExercice(database,"Cardio","Partir jambes et bras tendus, comme pour un gainage. Contracter les abdos et les fessiers et se regrouper devant. Faire alors un saut jambes tendues en finissant avec les bras au-dessus de la tête. 8 fois");

    }

}