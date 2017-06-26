package com.example.qenawi.fbmini;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qenawi.fbmini.items.POST;

import java.util.ArrayList;

/**
 * Created by QEnawi on 3/11/2017.
 */

public class NewsFeedRecyclerViewAdapter extends RecyclerView.Adapter<NewsFeedRecyclerViewAdapter.MainVIewHOlder> {
    //objects needed from activity
    private Context context;;
   private ArrayList<POST>Post_list;
   //const
    public NewsFeedRecyclerViewAdapter(Context C, ArrayList<POST>D)
    {
        context=C;
        Post_list=D;

    }
    //basic Fn
    @Override
    public MainVIewHOlder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context parent_C=parent.getContext();
        int Layoutidforitem=R.layout.simple_item1;
        LayoutInflater inflater=LayoutInflater.from(parent_C);
        boolean shouldAttachToParentImmediately = false;
        View view=inflater.inflate(Layoutidforitem,parent,shouldAttachToParentImmediately);
       MainVIewHOlder hOlder=new MainVIewHOlder(view);
        return  hOlder;
    }

    @Override
    public void onBindViewHolder(MainVIewHOlder holder, int p)
    {

        holder.bind(Post_list.get(p).getMail(),Post_list.get(p).getText(),Post_list.get(p).getDate());
    }

    @Override
    public int getItemCount()
    {
        if(Post_list==null){return  0;}
            return Post_list.size();
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
    class MainVIewHOlder extends RecyclerView.ViewHolder
    {
        //objects in item

        TextView name,text,date;


        //constructor
        public MainVIewHOlder(View itemView)
        {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.auther);
            text=(TextView)itemView.findViewById(R.id.post_content);
            date=(TextView)itemView.findViewById(R.id.Date);

        }

        void bind(String name,String text,String date)
        {
            this.name.setText(name);
            this.text.setText(text);
            this.date.setText(date);
        }

    }
}
