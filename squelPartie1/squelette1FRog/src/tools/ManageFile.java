package tools;

import java.io.*;
import java.util.ArrayList;

public class ManageFile {

    /**
     * Extrait les lignes d'un fichier vers un ArrayList
     *
     * @param fileName le nom du fichier
     * @return liste dont chaque élement correspond à une ligne
     */
    public static ArrayList<String> extractFile (String fileName) {
        ArrayList<String> linesList = new ArrayList<String>();
        try {
            BufferedReader read = new BufferedReader(new FileReader(fileName));
            String readLine = "";
            while ((readLine = read.readLine()) != null) {
                linesList.add(readLine);
            }
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linesList;
    }

    //Crée un fichier avec les éléments du tableau en param (un éléement = une ligne)

    /**
     *
     * @param fileName le nom du fichier
     * @param tab 
     */
    public static void createFile (String fileName, ArrayList<String> tab) {
        try {
            BufferedWriter write = new BufferedWriter(new FileWriter(fileName));
            for (String s: tab) {
                write.write(s+"\n");
            }
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void rewriteFile (String fileName, int nbLine, String text) {
        ArrayList<String> list = extractFile(fileName);
        list.set(nbLine, text);
        createFile(fileName, list);
    }

    public static void readFile (String fileName) {
        ArrayList<String> list = extractFile(fileName);
        for (String s : list) {
            System.out.println(s);
        }
    }

    public static String getLineFile (String fileName, int nbLine) {
        ArrayList<String> list = extractFile(fileName);
        return list.get(nbLine);
    }

}