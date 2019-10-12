package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UserFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);
      final   LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
        Intent intent = getIntent();
        String activeUsername = intent.getStringExtra("username");

        setTitle(activeUsername + "'s Feed");

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Image");

        query.whereEqualTo("username", activeUsername);
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

           if (e == null){

               if (list.size()>0){

                   for (ParseObject object: list){

                       ParseFile file = (ParseFile) object.get("image");
                       file.getDataInBackground(new GetDataCallback() {
                           @Override
                           public void done(byte[] bytes, ParseException e) {

                               if (e == null &&  bytes!=null){
                                   Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                                   ImageView imageView = new ImageView(getApplicationContext());

                                   imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                           ViewGroup.LayoutParams.MATCH_PARENT,
                                           ViewGroup.LayoutParams.WRAP_CONTENT));


                                   imageView.setImageBitmap(bitmap);

                                   linearLayout.addView(imageView);
                               }
                           }
                       });
                   }

               }

           }
            }
        });




    }
}
