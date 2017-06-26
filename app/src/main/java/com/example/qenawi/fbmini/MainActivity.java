package com.example.qenawi.fbmini;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.qenawi.fbmini.following.i_follow;
import com.google.firebase.auth.FirebaseAuth;
public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    ImageView newsfeed,addPost,Follow;
    private String email_head;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Toast.makeText(this,"user exist",Toast.LENGTH_SHORT).show();
            String mail1=FirebaseAuth.getInstance().getCurrentUser().getEmail();
             email_head=mail1.substring(0, mail1.indexOf('@'));

        }
        else
        {
            Toast.makeText(this,"user dont exist exist",Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(this,Login.class));
           // return;
        }
      //startActivity(new Intent(this,Rigst.class));
        newsfeed=(ImageView)findViewById(R.id.news_feed_img);
        addPost=(ImageView)findViewById(R.id.add_post_img);
        Follow=(ImageView)findViewById(R.id.follow_img);
        Follow.setOnClickListener(this);
        newsfeed.setOnClickListener(this);
        addPost.setOnClickListener(this);
    }
    @Override
    public void onClick(View view)
    {
        if(view.getId()==newsfeed.getId()){startActivity
                (
                new Intent(this,News_Feed.class).putExtra("emailxx7",email_head)
        )
        ;}
        else if(view.getId()==addPost.getId()){startActivity(new Intent(this,AddPost.class)
                .putExtra("emailxx7",email_head)
        );}
        else if(view.getId()==Follow.getId()){startActivity(new Intent(this,i_follow.class)
                .putExtra("emailxx7",email_head)
        );}
    }
    @Override
    protected void onDestroy()
    {
     //    this.startService(new Intent(this, news_feed_listner.class));
           super.onDestroy();
    //     isMyServiceRunning(news_feed_listner.class);
    }
    @Override
    protected void onStart()
    {
       // stopService(new Intent(this, news_feed_listner.class) );
        super.onStart();
    }
}
