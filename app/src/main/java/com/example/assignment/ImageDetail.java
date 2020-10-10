package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        String imagelink = getIntent().getStringExtra("image");
        ImageView image = findViewById(R.id.image);
        Picasso.with(ImageDetail.this).load(imagelink.trim()).resize(250, 250).placeholder(R.drawable.place_holder).into(image);

    }
}
