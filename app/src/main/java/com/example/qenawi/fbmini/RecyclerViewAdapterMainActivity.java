package com.example.qenawi.fbmini;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by QEnawi on 3/11/2017.
 */

public class RecyclerViewAdapterMainActivity extends RecyclerView.Adapter<RecyclerViewAdapterMainActivity.MainVIewHOlder> {
    //objects needed from activity
    Context context;;
    onClickListner mOnClickListener;
    ArrayList<String>user_list;
   //const
    public RecyclerViewAdapterMainActivity(Context C, onClickListner L, ArrayList<String>D)
    {
        context=C;
        mOnClickListener=L;
        user_list=D;

    }
    //basic Fn
    @Override
    public MainVIewHOlder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context parent_C=parent.getContext();
        int Layoutidforitem=R.layout.simple_item0;
        LayoutInflater inflater=LayoutInflater.from(parent_C);
        boolean shouldAttachToParentImmediately = false;
        View view=inflater.inflate(Layoutidforitem,parent,shouldAttachToParentImmediately);
       MainVIewHOlder hOlder=new MainVIewHOlder(view);
        return  hOlder;
    }

    @Override
    public void onBindViewHolder(MainVIewHOlder holder, int p)
    {
        holder.bind(user_list.get(p));
    }

    @Override
    public int getItemCount()
    {
        if(user_list==null){return  0;}
            return user_list.size();
    }

    @Override
    public long getItemId(int position)
    {
        return  position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    //view Holder------------------------------------------------------------------------
    class MainVIewHOlder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        //objects in item
        ImageView movie_img;
        TextView name;
        Switch Fbtn;

        //constructor
        public MainVIewHOlder(View itemView)
        {
            super(itemView);
            movie_img = (ImageView)itemView.findViewById(R.id.list_item_img);
            name=(TextView)itemView.findViewById(R.id.textView);
            Fbtn=(Switch)itemView.findViewById(R.id.switch1);
            Fbtn.setOnClickListener(this);
        }

        void bind(String name)
        {
              //  Glide.with(context).load(base).fitCenter().into(movie_img);
            this.name.setText(name);
            if (is_following(name).equals("true"))
            {
                Fbtn.setChecked(true);
            }
        }

        @Override
        public void onClick(View view)
        {
             int Clickpos = getAdapterPosition();
            // mOnClickListener.onListItemClick(Clickpos);
            if (view.getId()==Fbtn.getId())
            {
                if (Fbtn.isChecked())
                {
                    mOnClickListener.onListItemClick(Clickpos,"Follow");
                    Toast.makeText(context,"Follow "+user_list.get(Clickpos),Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mOnClickListener.onListItemClick(Clickpos,"unFollow");
                    Toast.makeText(context," unfollow "+user_list.get(Clickpos),Toast.LENGTH_SHORT).show();
                }


            }
        }
    }
    //listner inter face
    public interface onClickListner
    {
        void onListItemClick(int Clickpos,String Action);
    }
    private String is_following(String value)
    {
       return   PreferenceManager.getDefaultSharedPreferences(context).getString(value,"null");
    }
}
