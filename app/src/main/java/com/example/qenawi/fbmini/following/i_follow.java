package com.example.qenawi.fbmini.following;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.qenawi.fbmini.CallBack.i_follow_listner;
import com.example.qenawi.fbmini.R;
import com.example.qenawi.fbmini.RecyclerViewAdapterMainActivity;
import com.example.qenawi.fbmini.items.Post_Sample;
import com.example.qenawi.fbmini.items.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class i_follow extends AppCompatActivity implements RecyclerViewAdapterMainActivity.onClickListner,i_follow_listner
{
    RecyclerView rv;
    RecyclerViewAdapterMainActivity adapter;
    i_follow_listner mCallback;
    int cnt=0;
    ArrayList<String>data;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_follow);
        mCallback=this;
         RecyclerView.LayoutManager layoutManager;
        data=new ArrayList<>();
        rv = (RecyclerView) findViewById(R.id.rv_list);
        layoutManager = new LinearLayoutManager(this);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapterMainActivity(this, this, data);
        rv.setAdapter(adapter);
        fetch_users();
    }
    @Override
    public void onListItemClick(int Clickpos,String action)
    {
        String me=get_my_email();
        if(action=="Follow")
        {
         follow(me,data.get(Clickpos));
        }
        else
        {
            unfollow(me,data.get(Clickpos));
        }

    }
    void follow(final String A,final String B)
    {
        //A Follow B

        final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        final DatabaseReference fdbr = fdb.getReference().child("follow").child(A);

        String pushKey = fdbr.push().getKey();

        fdbr.child(pushKey).child("email").setValue((String)B).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid)
            {
              //  Toast.makeText(getApplicationContext(),A+"Follow"+B,Toast.LENGTH_SHORT).show();
                // add to pref manger
                final DatabaseReference fdbr2 = fdb.getReference().child("my_followers").child(B);
                String pushKey2 = fdbr2.push().getKey();
                fdbr2.child(pushKey2).child("email").setValue(A).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        add_FollowaCC(B);
                        follow_helper(A,B);
                        // add all posts of user B TO A newsFeed
                    }
                });
            }
        });
    }
    void follow_helper(final String A,final String B)
    {
        // fetch B posts id and add theem to A news feed
        FirebaseDatabase fdb=FirebaseDatabase.getInstance();
        final ArrayList<Post_Sample>posts;
        posts=new ArrayList<>();
        final DatabaseReference fdbr2 = fdb.getReference().child("my_posts").child(B);
        fdbr2.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
               for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
               {
                   Post_Sample p_s=dataSnapshot1.getValue(Post_Sample.class);
                   // add it to A news feed
                   posts.add(p_s);
               }
               // add async task luncher call Back
               add_B_to_A_news_feed(A,posts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });


    }
    void add_B_to_A_news_feed(String A,ArrayList<Post_Sample>posts)// async task || service
    {
        Log.v("hako","add posts to my curnt news feed"+posts.size());
        FirebaseDatabase fdp=FirebaseDatabase.getInstance();
        DatabaseReference fdbr2=fdp.getReference().child("newsfeed").child(A);
        String key;
       for (int i=0;i<posts.size();i++)
       {
          key=fdbr2.push().getKey();
          fdbr2.child(key).setValue(posts.get(i));
       }
    }
    void unfollow(String A,String B)
    {
       //A un follow B
        un_FollowaCC(B);
    }
    void fetch_users()
    {
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        DatabaseReference fdbr = fdb.getReference().child("users");
        fdbr.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

            for(DataSnapshot dataSnapshot10:dataSnapshot.getChildren())
            {

                User useritem=dataSnapshot10.getValue(User.class);
                useritem.setEmail(dataSnapshot10.getKey());
                data.add(useritem.getEmail());
            }
            mCallback.OnComplete("","load_complete");
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }
    @Override
    public void OnComplete(Object O, String Tag)
    {
        Log.v("hako",Tag);
        adapter.notifyDataSetChanged();
    }
    String get_my_email()
    {
        // return curnt sign in mail
        return "randaxx5";
    }
    void add_FollowaCC(String value)
    {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString(value,"true").apply();
    }
    void un_FollowaCC(String value)
    {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString(value,"false").apply();
    }
    private String is_following(String value)
    {
    return PreferenceManager.getDefaultSharedPreferences(this).getString(value,"null");
    }
}
// Follow_work
// un follow work