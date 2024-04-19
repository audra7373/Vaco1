package org.marrero.vaco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class FailedPopu extends Dialog {
    private  String title;
    private Button rejouer,quitter;
    private TextView titleView;

    @SuppressLint("MissingInflatedId")
    public FailedPopu(@NonNull Context context) {
        super(context, androidx.appcompat.R.style.Theme_AppCompat_Dialog);

        setContentView(R.layout.failed);

        this.title = "Mon titre";
        this.rejouer = findViewById(R.id.replay);
        this.quitter = findViewById(R.id.retouner_home);



    }

    public Button getRejouer() {
        return rejouer;
    }

    public Button getQuitter() {
        return quitter;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void build()
    {
        show();
        titleView.setText(title);
    }
    public void play_mode()
    {

    }
}