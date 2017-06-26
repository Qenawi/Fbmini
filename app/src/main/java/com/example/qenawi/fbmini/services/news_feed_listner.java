package com.example.qenawi.fbmini.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.example.qenawi.fbmini.News_Feed;
import com.example.qenawi.fbmini.R;
import com.example.qenawi.fbmini.items.Post_Sample;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by QEnawi on 4/18/2017.
 */

public class news_feed_listner extends MyBaseTaskService
{
    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
        //in4lize objects
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        String flag=PreferenceManager.getDefaultSharedPreferences(this).getString("active","null");
            taskStarted();
            checkchat();
            return  START_STICKY;

    }
    private void checkchat()
    {

        final String email="randaxx5";
        final FirebaseDatabase fdb=FirebaseDatabase.getInstance();
        DatabaseReference fdbr0=fdb.getReference().child("newsfeed").child(email);
        Query query=fdbr0.orderByChild("date").limitToLast(20);
        query.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                Notift(dataSnapshot.getValue(Post_Sample.class).getDate());
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
    }//check_if_chat_room_exist
    void Notift(String data2)
    {
        Intent intent = new Intent(getApplicationContext(), News_Feed.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder b = new NotificationCompat.Builder(getApplicationContext());
        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setTicker("Fbminii")
                .setContentTitle("Y S Panda")
                .setContentText("u Got new Posts")
                .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setContentInfo("🐼 "+data2);
        NotificationManager notificationManager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, b.build());
    }

    @Override
    public void onDestroy()
    {
        Toast.makeText(getApplicationContext(),"serv destroied",Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

}
