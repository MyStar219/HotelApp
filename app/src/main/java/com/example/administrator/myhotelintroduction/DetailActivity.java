package com.example.administrator.myhotelintroduction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class DetailActivity extends AppCompatActivity {

    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String jsonString = intent.getStringExtra("jsonObject");

        try {
            jsonObject = new JSONObject(jsonString);

            String strUrl = jsonObject.getString("img_url");
            String strPrice = jsonObject.getString("price_formatted");
            String strAddress = jsonObject.getString("title");
            String strStructure = jsonObject.getString("keywords");
            String strDetail = jsonObject.getString("summary");

            ((TextView)findViewById(R.id.id_tvPrice)).setText(strPrice);
            ((TextView)findViewById(R.id.id_tvAddress)).setText(strAddress);
            ((TextView)findViewById(R.id.id_tvStructure)).setText(strStructure);
            ((TextView)findViewById(R.id.id_tvDetail)).setText(strDetail);
            new DownloadImageTask((ImageView) findViewById(R.id.id_imgContent))
                    .execute(strUrl);

        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        findViewById(R.id.id_btnReturn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent1 = new Intent(DetailActivity.this, MainActivity.class);
                //startActivity(intent1);
                finish();
            }
        });
    }

     private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
