package com.example.assignment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private JSONArray authorList;

    public GridAdapter(Context context, JSONArray authorList) {
        this.mContext = context;
        this.authorList = authorList;
    }

    @Override
    public int getCount() {
        return authorList.length();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public JSONObject getItem(int position) {
        JSONObject currentJson = new JSONObject();
        try {
            currentJson = authorList.getJSONObject( position );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentJson;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View gridView = inflater.inflate( R.layout.grid_adapter, null );
        JSONObject SingleProduct = getItem( position );
        Log.d("SingleProduct",String.valueOf(SingleProduct));
        ImageView AuthorImageView = (ImageView) gridView.findViewById( R.id.author_image );
//        TextView AuthorName = (TextView) gridView.findViewById( R.id.author_name );
        try {
//            AuthorName.setText( SingleProduct.getString( "title" ) );
            JSONArray jsonArray = SingleProduct.getJSONArray("images");

//            new GetImageFromUrl( AuthorImageView ).execute( jsonArray.getJSONObject(0).getString("link") );

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    Picasso.with(mContext).load(jsonArray.getJSONObject(i).getString("link").trim()).resize(250, 250).placeholder(R.drawable.place_holder).into(AuthorImageView);


                } catch (Exception e) {

                }
            }
            AuthorImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        mContext.startActivity(new Intent(mContext, ImageDetail.class).putExtra("image", jsonArray.getJSONObject(0).getString("link")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


/*
        try {
//            AuthorName.setText( SingleProduct.getString( "title" ) );
            JSONArray jsonArray = SingleProduct.getJSONArray("images");

//            new GetImageFromUrl( AuthorImageView ).execute( jsonArray.getJSONObject(0).getString("link") );
            try {
                Picasso.with(mContext).load(jsonArray.getJSONObject(0).getString("link").trim()).resize(250, 250).placeholder(R.drawable.place_holder).into(AuthorImageView);
                AuthorImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            mContext.startActivity(new Intent(mContext,ImageDetail.class).putExtra("image",jsonArray.getJSONObject(0).getString("link")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            } catch (Exception e) {

            }

//            new GetImageFromUrl( AuthorImageView ).execute( "https://picsum.photos/300/300?image=" + SingleProduct.getString( "id" ) );
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        return gridView;
    }
}
