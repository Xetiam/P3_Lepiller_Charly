package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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


        findViewById(R.id.nameTitle).bringToFront();

        ImageView neighbourAvatar = findViewById(R.id.neighbourAvatar);
        Glide.with(this)
                .load(neighbour.profilePicStringFormatter(neighbour))
                .placeholder(getDrawable(R.drawable.placeholder))
                .into(neighbourAvatar);

        bindTextView(neighbour.getName(), findViewById(R.id.neighbourName));
        bindTextView(neighbour.getName(), findViewById(R.id.nameTitle));
        bindTextView(neighbour.getAddress(), findViewById(R.id.neighbourAddress));
        bindTextView(neighbour.getPhoneNumber(), findViewById(R.id.neighbourPhone));
        bindTextView(neighbour.getUrl(), findViewById(R.id.neighbourUrl));
        bindTextView(neighbour.getAboutMe(), findViewById(R.id.neighbourAboutMe));

        Toolbar mActionBar = findViewById(R.id.toolbar);
        mActionBar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24));
        mActionBar.setNavigationOnClickListener(v -> finish());

        FloatingActionButton addFavorite = findViewById(R.id.fav);

        if (mApiService.getFavorites().contains(neighbour)) {
            addFavorite.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(this, R.color.colorPrimaryDark)))));
            addFavorite.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_baseline_star_24_selected));
        }
        addFavorite.bringToFront();
        addFavorite.setOnClickListener(v -> {
            if (mApiService.getFavorites().contains(neighbour)) {
                mApiService.deleteFavorites(neighbour);
                addFavorite.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.white)));
                addFavorite.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_baseline_star_24));
            }
            else {
                mApiService.createFavorite(neighbour);
                addFavorite.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark)))));
                addFavorite.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_baseline_star_24_selected));
            }
        });

    }

    private void bindTextView(String text, TextView view) {
        view.setText(text);
    }
}

