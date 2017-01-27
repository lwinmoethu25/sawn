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

import com.lmt.sawn.MApplication;
import com.lmt.sawn.Model.Location;
import com.lmt.sawn.R;

import org.mmaug.mmfont.utils.FontUtils;
import org.mmaug.mmfont.utils.Fonts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lwinmoethu on 5/26/16.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder>{

    public static List<Location> cities=new ArrayList<Location>();
    public static int city_pos;
    public static String pref_city,pref_location;
    MApplication mapplication;
    Context mContext;

    public CityAdapter(List<Location> city, MApplication application, Context context) {
        this.city_pos=-1;
        this.cities=city;
        this.mapplication=application;
        this.mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city,parent,false);
        ViewHolder vh=new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Location city=cities.get(position);


        if (city_pos==position) {
            holder.card.setCardBackgroundColor(Color.parseColor("#009688"));
            holder.division.setTextColor(Color.WHITE);
            holder.location.setTextColor(Color.WHITE);
        }else{
            holder.card.setCardBackgroundColor(Color.WHITE);
            holder.division.setTextColor(Color.parseColor("#009688"));
            holder.location.setTextColor(Color.parseColor("#009688"));

        }
            holder.division.setText(city.division);
            holder.location.setText(city.town);
            FontUtils utils=new FontUtils(mContext);
            utils.setTypeFace(Fonts.MY_MM,holder.location);




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
        return cities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView division,location;
        CardView card;
        ImageView imgFlag;

        public ViewHolder(View itemView) {
            super(itemView);

            division=(TextView) itemView.findViewById(R.id.txtDivision);
            location=(TextView) itemView.findViewById(R.id.txtLocation);
            card=(CardView) itemView.findViewById(R.id.card_city);
            imgFlag=(ImageView) itemView.findViewById(R.id.imgFlag);

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    city_pos = getLayoutPosition();
                    pref_city=cities.get(city_pos).lid;
                    pref_location=cities.get(city_pos).location;
                    mapplication.saveStringSharedPreferrence("city",cities.get(city_pos).town);
                    mapplication.saveStringSharedPreferrence("city_pos",pref_city);
                    mapplication.saveStringSharedPreferrence("location",pref_location);
                    notifyDataSetChanged();
                    Toast.makeText(view.getContext(),pref_city,Toast.LENGTH_SHORT).show();
                }
            });



        }
    }

}
