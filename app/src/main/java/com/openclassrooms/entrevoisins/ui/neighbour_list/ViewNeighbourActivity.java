package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;


import butterknife.ButterKnife;

//Display profile user with description and their avatar
public class ViewNeighbourActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_neighbour);
        ButterKnife.bind(this);
        Neighbour neighbour = (Neighbour) getIntent().getSerializableExtra("neighbour");


        findViewById(R.id.backButton).bringToFront();
        findViewById(R.id.nameTitle).bringToFront();

        ImageView neighbourAvatar = (ImageView) findViewById(R.id.neighbourAvatar);
        Glide.with(this)
                .load(neighbour.getAvatarUrl())
                .into(neighbourAvatar);

        bindTextView(neighbour.getName(), findViewById(R.id.neighbourName));

        bindTextView(neighbour.getName(), findViewById(R.id.nameTitle));

        String newAdress = neighbour.getAddress().replace(";", "à");
        bindTextView(newAdress, findViewById(R.id.neighbourAdress));

        bindTextView(neighbour.getPhoneNumber(), findViewById(R.id.neighbourPhone));

        String neighbourUrl = "www.facebook.fr/" + neighbour.getName();
        bindTextView(neighbourUrl, findViewById(R.id.neighbourUrl));

        bindTextView(neighbour.getAboutMe(), findViewById(R.id.neighbourAboutMe));

        ImageView backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void bindTextView(String text, TextView view) {
        view.setText(text);
    }
}
