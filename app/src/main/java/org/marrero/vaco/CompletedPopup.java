package org.marrero.vaco;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CompletedPopup extends Dialog {

    private  String title;
    private Button rejouer,suivant;
    private TextView titleView;
    private TextView niveauView;
    private String niv_actuel;
    @SuppressLint("MissingInflatedId")
    public CompletedPopup(@NonNull Context context) {
        super(context, androidx.appcompat.R.style.Theme_AppCompat_Dialog);

        setContentView(R.layout.completed);

        this.title = "Mon titre";
        this.rejouer = findViewById(R.id.back_home);
        this.suivant = findViewById(R.id.next_level);
        this.niv_actuel = "Niveau";



    }

    public Button getRejouer() {
        return rejouer;
    }

    public Button getSuivant() {
        return suivant;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setNiv_actuel(String niv_actuel)
    {
        this.niv_actuel = niv_actuel;
    }
    public void build()
    {
        show();
        titleView.setText(title);
        niveauView.setText(niv_actuel);
    }

}
