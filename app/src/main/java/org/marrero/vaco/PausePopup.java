package org.marrero.vaco;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.TextView;

public class PausePopup extends Dialog {

    //fields
    private  String title;
    private Button yesButton,noButton;
    private TextView titleView;

    //constructor
    public PausePopup(Activity activity)
    {
        super(activity, androidx.appcompat.R.style.Theme_AppCompat_DayNight);
        setContentView(R.layout.pause_popup_template);

        this.title = "Mon titre";
        this.noButton = findViewById(R.id.noButton);
        this.yesButton = findViewById(R.id.yesButton);
        this.titleView = findViewById(R.id.pauses);
    }

    public void setTitle(String title) { this.title = title;}
    public Button getYesButton(){ return yesButton; }
    public Button getNoButton(){ return noButton; }

    public void build()
    {
        show();
        titleView.setText(title);
    }



}
