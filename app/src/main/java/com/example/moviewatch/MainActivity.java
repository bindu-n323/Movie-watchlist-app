package com.example.moviewatch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private LinearLayout movieListContainer;
    private ArrayList<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MovieWatchlist", Context.MODE_PRIVATE);

        String[] movieNames = {
                "Inception", "The Dark Knight", "Interstellar", "Pulp Fiction", "Forrest Gump",
                "The Matrix", "Avengers: Endgame", "Gladiator", "The Shawshank Redemption", "The Godfather",
                "Jurassic Park", "Titanic", "The Lion King", "Avatar", "Harry Potter and the Sorcerer's Stone",
                "The Lord of the Rings", "The Social Network", "Fight Club", "Toy Story", "The Silence of the Lambs"
        };

        int[] movieImages = {
                R.drawable.inception, R.drawable.dark_knight, R.drawable.interstellar, R.drawable.pulp_fiction,
                R.drawable.forrest_gump, R.drawable.the_matrix, R.drawable.avengers_endgame, R.drawable.gladiator,
                R.drawable.shawshank_redemption, R.drawable.godfather, R.drawable.jurassic_park, R.drawable.titanic,
                R.drawable.lion_king, R.drawable.avatar, R.drawable.harry_potter, R.drawable.lord_of_the_rings,
                R.drawable.social_network, R.drawable.fight_club, R.drawable.toy_story, R.drawable.silence_of_the_lambs
        };

        movieList = new ArrayList<>();
        for (int i = 0; i < movieNames.length; i++) {
            movieList.add(new Movie(movieNames[i], movieImages[i]));
        }

        movieListContainer = findViewById(R.id.movieListContainer);
        displayMovies();

        Button viewWatchlistButton = findViewById(R.id.viewWatchlistButton);
        viewWatchlistButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, WatchlistActivity.class);
            startActivity(intent);
        });
    }

    private void displayMovies() {
        for (Movie movie : movieList) {
            View movieItemView = createMovieItemView(movie);
            movieListContainer.addView(movieItemView);
        }
    }

    private View createMovieItemView(Movie movie) {
        LinearLayout itemLayout = new LinearLayout(this);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
        itemLayout.setPadding(8, 8, 8, 8);

        ImageView movieImage = new ImageView(this);
        movieImage.setImageResource(movie.getImageResource());
        movieImage.setLayoutParams(new LinearLayout.LayoutParams(300, 300)); // Increase size
        movieImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        TextView movieNameText = new TextView(this);
        movieNameText.setText(movie.getName());
        movieNameText.setTextSize(20);
        movieNameText.setTextColor(Color.WHITE);
        movieNameText.setPadding(24, 0, 0, 0);
        movieNameText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        Button addButton = new Button(this);
        addButton.setText("Add");
        addButton.setOnClickListener(view -> addToWatchlist(movie.getName()));

        itemLayout.addView(movieImage);
        itemLayout.addView(movieNameText);
        itemLayout.addView(addButton);

        return itemLayout;
    }

    private void addToWatchlist(String movieName) {
        Set<String> watchlistSet = sharedPreferences.getStringSet("Watchlist", new HashSet<>());
        Set<String> updatedSet = new HashSet<>(watchlistSet);

        if (updatedSet.contains(movieName)) {
            Toast.makeText(this, movieName + " is already in your Watchlist!", Toast.LENGTH_SHORT).show();
        } else {
            updatedSet.add(movieName);
            sharedPreferences.edit().putStringSet("Watchlist", updatedSet).apply();
            Toast.makeText(this, movieName + " added to Watchlist!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, WatchlistActivity.class);
            startActivity(intent);
        }
    }
}
