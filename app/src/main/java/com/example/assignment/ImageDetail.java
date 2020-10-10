package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class ImageDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        ImageView image = findViewById(R.id.image);
        TextView author_name = findViewById(R.id.author_name);

        try {
            JSONObject object = new JSONObject(getIntent().getStringExtra("JSONArray"));
            author_name.setText(object.getString("title"));
            Picasso.with(ImageDetail.this).load(object.getString("image")).resize(250, 250).placeholder(R.drawable.place_holder).into(image);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
