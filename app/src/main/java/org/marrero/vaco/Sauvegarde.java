package org.marrero.vaco;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Sauvegarde {

    private static final String FICHIER_SAUVEGARDE = "sauvegarde.txt";  // For text-based storage
    // private static final String FICHIER_SAUVEGARDE = "sauvegarde.json"; // For JSON storage

    public static void sauvegarder(Context context, int niveau) {
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FICHIER_SAUVEGARDE, Context.MODE_PRIVATE))) {
            outputStreamWriter.write(String.valueOf(niveau));
            Log.i("Sauvegarde", "Niveau sauvegardé dans le fichier");
        } catch (IOException e) {
            Log.e("Sauvegarde", "Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    public static int chargerNiveau(Context context) {
        int niveau = 1; // Default level if file not found
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.openFileInput(FICHIER_SAUVEGARDE)))) {
            String niveauString = bufferedReader.readLine();
            if (niveauString != null) {
                niveau = Integer.parseInt(niveauString);
                Log.i("Sauvegarde", "Niveau chargé à partir du fichier : " + niveau);
            }
        } catch (IOException e) {
            Log.e("Sauvegarde", "Erreur lors du chargement : " + e.getMessage());
        }
        return niveau;
    }

    public static boolean fichierSauvegardeExiste(Context context) {
        return context.getFileStreamPath(FICHIER_SAUVEGARDE).exists();
    }

    public static void supprimerFichierSauvegarde(Context context) {
        context.deleteFile(FICHIER_SAUVEGARDE);
        Log.i("Sauvegarde", "Fichier de sauvegarde supprimé");
    }

    // For JSON-based storage
    /*
    public static void sauvegarderJSON(Context context, int niveau) {
        try {
            JSONObject data = new JSONObject();
            data.put("niveauActuel", niveau);
            try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FICHIER_SAUVEGARDE, Context.MODE_PRIVATE))) {
                outputStreamWriter.write(data.toString());
            }
        } catch (JSONException | IOException e) {
            Log.e("Sauvegarde", "Erreur lors de la sauvegarde JSON : " + e.getMessage());
        }
    }

    public static int chargerNiveauJSON(Context context) {
        int niveau = 1;
        try {
            String jsonString = readFile(context, FICHIER_SAUVEGARDE);
            if (!jsonString.isEmpty()) {
                JSONObject data = new JSONObject(jsonString);
                niveau = data.getInt("niveauActuel");
            }
        } catch (JSONException | IOException e) {
            Log.e("Sauvegarde", "Erreur lors du chargement JSON : " + e.getMessage());
        }
        return niveau;
    }

    private static String readFile(Context context, String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        // ... (Implement reading file contents into the StringBuilder)
        return sb.toString();
    }
    */
}
