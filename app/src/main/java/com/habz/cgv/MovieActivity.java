package com.habz.cgv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity {

    public TextView txtTitle, txtoverview, txtdate, txtvote;
    public ImageView bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(getIntent().getStringExtra("title"));

        txtdate = (TextView) findViewById(R.id.txtDate);
        txtdate.setText(getIntent().getStringExtra("date"));

        txtoverview = (TextView) findViewById(R.id.txtDescrip);
        txtoverview.setText(getIntent().getStringExtra("overview"));

        txtvote = (TextView) findViewById(R.id.txtrating);
        txtvote.setText(getIntent().getStringExtra("vote"));

        bg = (ImageView) findViewById(R.id.bg);
        Picasso.with(this)
                .load("https://image.tmdb.org/t/p/w300_and_h450_bestv2" + getIntent().getStringExtra("bg"))
                .resize(200, 300)
                .into(bg);

    }
}
