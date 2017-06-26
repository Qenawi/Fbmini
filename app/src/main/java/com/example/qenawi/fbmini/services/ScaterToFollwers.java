package com.example.qenawi.fbmini.services;

import android.content.Intent;
import android.os.IBinder;

import com.example.qenawi.fbmini.items.Post_Sample;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by QEnawi on 5/14/2017.
 */

public class ScaterToFollwers extends MyBaseTaskService
{
   private ArrayList<String>list;
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
        list=new ArrayList<>();
        //in4lize objects
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        taskStarted();
        Post_Sample post = (Post_Sample) intent.getExtras().getSerializable("POST");
        String my_acc = get_my_email();
        get_my_followers(my_acc,post);
        return START_STICKY;
    }
void get_my_followers(String A,final Post_Sample post)
{
    final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
    final DatabaseReference fdbr = fdb.getReference().child("my_followers").child(A);
    fdbr.addListenerForSingleValueEvent(new ValueEventListener()
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
            {
           String tmp=dataSnapshot1.child("email").getValue(String.class);
           list.add(tmp);
            }
            add_to_followers_news_feed(list,post);
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    });
}
    private void add_to_followers_news_feed(ArrayList<String>followers, Post_Sample post)
    {
        final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        final DatabaseReference fdbr = fdb.getReference().child("newsfeed");
        String key;
        for (int i=0;i<followers.size();i++)
        {
         key=fdbr.child(followers.get(i)).push().getKey();
         fdbr.child(followers.get(i)).child(key).setValue(post);
        }
    taskCompleted();
    }
    private String get_my_email()
    {
        // return curnt sign in mail
        return "randaxx5";
    }
}
