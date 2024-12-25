package com.example.moviewatch;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class WatchlistActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private LinearLayout watchlistContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        sharedPreferences = getSharedPreferences("MovieWatchlist", Context.MODE_PRIVATE);
        watchlistContainer = findViewById(R.id.watchlistContainer);

        displayWatchlist();
    }

    private void displayWatchlist() {
        Set<String> watchlistSet = sharedPreferences.getStringSet("Watchlist", new HashSet<>());
        ArrayList<String> watchlist = new ArrayList<>(watchlistSet);

        if (watchlist.isEmpty()) {
            Toast.makeText(this, "Your watchlist is empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        for (String movieName : watchlist) {
            View movieItemView = createMovieItemView(movieName);
            watchlistContainer.addView(movieItemView);
        }
    }

    private View createMovieItemView(String movieName) {
        LinearLayout itemLayout = new LinearLayout(this);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
        itemLayout.setPadding(8, 8, 8, 8);

        TextView movieNameText = new TextView(this);
        movieNameText.setText(movieName);
        movieNameText.setTextSize(20);
        movieNameText.setTextColor(Color.WHITE);
        movieNameText.setPadding(16, 0, 0, 0);
        movieNameText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        Button removeButton = new Button(this);
        removeButton.setText("Remove");
        removeButton.setOnClickListener(view -> removeFromWatchlist(movieName, itemLayout));

        itemLayout.addView(movieNameText);
        itemLayout.addView(removeButton);

        return itemLayout;
    }

    private void removeFromWatchlist(String movieName, LinearLayout itemLayout) {
        Set<String> watchlistSet = sharedPreferences.getStringSet("Watchlist", new HashSet<>());
        Set<String> updatedSet = new HashSet<>(watchlistSet);

        if (updatedSet.contains(movieName)) {
            updatedSet.remove(movieName);
            sharedPreferences.edit().putStringSet("Watchlist", updatedSet).apply();
            watchlistContainer.removeView(itemLayout);
            Toast.makeText(this, movieName + " removed from Watchlist!", Toast.LENGTH_SHORT).show();
        }
    }
}
