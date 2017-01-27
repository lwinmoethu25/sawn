package com.lmt.sawn.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lmt.sawn.Activity.DivisionActivity;
import com.lmt.sawn.MApplication;
import com.lmt.sawn.Model.Location;
import com.lmt.sawn.R;

import org.mmaug.mmfont.utils.FontUtils;
import org.mmaug.mmfont.utils.Fonts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lwinmoethu on 5/27/16.
 */
public class DivisionAdapter extends RecyclerView.Adapter<DivisionAdapter.ViewHolder> {

    public static List<Location> divisions=new ArrayList<Location>();
    public static int division_pos;
    public static String pref_division;
    MApplication mapplication;
    Context mContext;

    public DivisionAdapter(List<Location> division, MApplication application, Context context) {
        this.division_pos=-1;
        this.divisions=division;
        this.mapplication=application;
        this.mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_division,parent,false);
        ViewHolder vh=new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Location city=divisions.get(position);


        if (division_pos==position) {
            holder.card.setCardBackgroundColor(Color.parseColor("#009688"));
            holder.division.setTextColor(Color.WHITE);
        }else{
            holder.card.setCardBackgroundColor(Color.WHITE);
            holder.division.setTextColor(Color.parseColor("#009688"));;

        }
        holder.division.setText(city.division);




        switch (mapplication.getStringSharedPreferrence("code")){

            case "my":
                holder.imgFlag.setImageResource(R.drawable.my);
                break;

            case "mm":
                holder.imgFlag.setImageResource(R.drawable.mm);
                break;

            case "sg":
                holder.imgFlag.setImageResource(R.drawable.sg);
                break;

            case "th":
                holder.imgFlag.setImageResource(R.drawable.th);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return divisions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView division;
        CardView card;
        ImageView imgFlag;

        public ViewHolder(View itemView) {
            super(itemView);

            division=(TextView) itemView.findViewById(R.id.divisionName);
            card=(CardView) itemView.findViewById(R.id.card_division);
            imgFlag=(ImageView) itemView.findViewById(R.id.imgFlag);

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    division_pos = getLayoutPosition();
                    pref_division=divisions.get(division_pos).division;
                    mapplication.saveStringSharedPreferrence("division",pref_division);
                    notifyDataSetChanged();
                    Toast.makeText(view.getContext(),pref_division,Toast.LENGTH_SHORT).show();
                }
            });



        }
    }
}
