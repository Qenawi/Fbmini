package com.example.qenawi.fbmini;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.qenawi.fbmini.items.POST;
import com.example.qenawi.fbmini.items.Post_Sample;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class News_Feed extends AppCompatActivity {
    private ArrayList<POST> data;
    private RecyclerView rv;
    private NewsFeedRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__feed);
        String my_email="randaxx5";//get current logged in user
        data = new ArrayList<>();
        rv = (RecyclerView) findViewById(R.id.news_feed);
        adapter = new NewsFeedRecyclerViewAdapter( this,data);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        final FirebaseDatabase fdb=FirebaseDatabase.getInstance();
           DatabaseReference fdbr0=fdb.getReference().child("newsfeed").child(my_email);
        Query query=fdbr0.orderByChild("date").limitToLast(20);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                Post_Sample p=dataSnapshot.getValue(Post_Sample.class);
                add_to_rv(p.getId());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    void add_to_rv(final String id)
    {
        Log.v("horax",id);
        final FirebaseDatabase fdb=FirebaseDatabase.getInstance();
        final   DatabaseReference fdbr=fdb.getReference().child("posts").child(id);
        fdbr.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                if (dataSnapshot.getValue()!=null)
                {
                    POST f = dataSnapshot.getValue(POST.class);
                    data.add(0,f);
                  //  Log.v("horax", f.getDate() + "  d  " + f.getMail() + "  f " + f.getText());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
