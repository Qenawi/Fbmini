package com.example.qenawi.fbmini;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.qenawi.fbmini.items.POST;
import com.example.qenawi.fbmini.items.Post_Sample;
import com.example.qenawi.fbmini.services.ScaterToFollwers;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AddPost extends AppCompatActivity
{
    Button supmit;
    EditText post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        final String myemail="randaxx5";// get current logged in user email
        post=(EditText)findViewById(R.id.post_edit_text);
        supmit=(Button)findViewById(R.id.submit);
        supmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
              add_to_my_posts(post.getText().toString(),myemail);
               post.setText("");
            }
        });
      // publish_post(new POST("010", "randaxx5", "tmp", "helloxxxxxxxxxxxwadasdasd"));
    }
    void add_to_my_posts(final String text,final String A)
    {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+12:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("dd:HH:mm:ss a");
//----------------------------------------------------------------------------
        String localTime = date.format(currentLocalTime);
      final  FirebaseDatabase fdp=FirebaseDatabase.getInstance();
        String key;
        DatabaseReference fdpr=fdp.getReference().child("posts");
        key=fdpr.push().getKey();
        POST p=new POST(localTime,A,"tmp",text);
        p.setId(key);
        final Post_Sample post_sample=new Post_Sample(p.getId(),p.getDate());
        fdpr.child(key).setValue(p).addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid)
            {
                DatabaseReference fdpr=fdp.getReference().child("my_posts").child(A);
                fdpr.child(fdpr.push().getKey()).setValue(post_sample).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        DatabaseReference fdpr0=fdp.getReference().child("newsfeed").child(A);
                        fdpr0.child(fdpr0.push().getKey()).setValue(post_sample).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                publish_post(post_sample);
                            }
                        });// post aded to my news feed 2.1
                    }
                });// post aded to my_posts 2
            }
        }); // post added to posts 1
    }
    void publish_post(Post_Sample post) // add to my followers newsfeed 3
    {
        // start uplood service
                 startService(new Intent(getApplicationContext(), ScaterToFollwers.class)
                .putExtra("POST",post)
                .setAction("ACTION"));
    }

}

