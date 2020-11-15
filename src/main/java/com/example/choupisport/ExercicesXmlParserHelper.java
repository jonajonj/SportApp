package com.example.choupisport;

import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class ExercicesXmlParserHelper {

    private Exercices Exercices;
    private Exercice Exercice;
    private String text;

    public static final String XML_BALISE_EXERCICE          = "exercice";
    public static final String XML_BALISE_TYPE              = "type";
    public static final String XML_BALISE_DESCRIPTION       = "description";

    public Exercices getExercices() {
        return Exercices;
    }

    public Exercices parse(InputStream is) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser  parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tagname = parser.getName();

                switch (eventType) {

                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase(XML_BALISE_EXERCICE)) {
                            // create a new instance of employee
                            Exercice = new Exercice();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:

                        if (tagname.equalsIgnoreCase(XML_BALISE_EXERCICE)) {
                            // add employee object to list
                            Exercices.add(Exercice);

                        }  else if (tagname.equalsIgnoreCase(XML_BALISE_TYPE)) {
                            Exercice.type = text;

                        } else if (tagname.equalsIgnoreCase(XML_BALISE_DESCRIPTION)) {
                            Exercice.description = text;
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}

        return Exercices;
    }
}
