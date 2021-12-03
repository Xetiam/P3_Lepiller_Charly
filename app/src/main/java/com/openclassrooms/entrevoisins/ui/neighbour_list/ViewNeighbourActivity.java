package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;


import butterknife.ButterKnife;

//Display profile user with description and their avatar
public class ViewNeighbourActivity extends AppCompatActivity {
    private NeighbourApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
        setContentView(R.layout.activity_view_neighbour);
        ButterKnife.bind(this);
        Neighbour neighbour = (Neighbour) getIntent().getSerializableExtra("neighbour");


        findViewById(R.id.backButton).bringToFront();
        findViewById(R.id.nameTitle).bringToFront();

        ImageView neighbourAvatar = (ImageView) findViewById(R.id.neighbourAvatar);
        Glide.with(this)
                .load(profilePicStringFormatter(neighbour))
                .into(neighbourAvatar);

        bindTextView("   " + neighbour.getName(), findViewById(R.id.neighbourName));

        bindTextView("   " + neighbour.getName(), findViewById(R.id.nameTitle));

        String newAddress = "   " + neighbour.getAddress().replace(";", "à");
        bindTextView(newAddress, findViewById(R.id.neighbourAddress));

        bindTextView("   " + neighbour.getPhoneNumber(), findViewById(R.id.neighbourPhone));

        String neighbourUrl = "www.facebook.fr/" + neighbour.getName();
        bindTextView("   " + neighbourUrl, findViewById(R.id.neighbourUrl));

        bindTextView(neighbour.getAboutMe(), findViewById(R.id.neighbourAboutMe));

        ImageView backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        FloatingActionButton addFavorite = findViewById(R.id.fav);
        if(mApiService.getFavorites().contains(neighbour)){
            addFavorite.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(this, R.color.colorPrimaryDark)))));
            addFavorite.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_baseline_star_24_selected));
        }
        addFavorite.setOnClickListener(v -> {
            if(mApiService.getFavorites().contains(neighbour)){
                mApiService.deleteFavorites(neighbour);
                addFavorite.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.white)))));
                addFavorite.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_baseline_star_24));
            }
            else{
                mApiService.createFavorite(neighbour);
                addFavorite.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark)))));
                addFavorite.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_baseline_star_24_selected));
            }
        });
    }

    private void bindTextView(String text, TextView view) {
        view.setText(text);
    }

    private String profilePicStringFormatter(Neighbour neighbour){
        String str = neighbour.getAvatarUrl();
        str = str.replace("150","500");
        return str;
    }
}

