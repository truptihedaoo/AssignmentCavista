package com.example.assignment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import org.json.JSONObject;

public class GridAdapterFinal extends BaseAdapter {
    private Context mContext;
    private JSONArray authorList;

    public GridAdapterFinal(Context context, JSONArray authorList) {
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

                try {
                    Picasso.with(mContext).load(SingleProduct.getString("image").trim()).placeholder(R.drawable.place_holder).into(AuthorImageView);
                } catch (Exception e) {

                }

            AuthorImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        mContext.startActivity(new Intent(mContext, ImageDetail.class).putExtra("JSONArray", String.valueOf(SingleProduct)));

                }
            });

        return gridView;
    }
}
