package com.lmt.sawn.Adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lmt.sawn.MApplication;
import com.lmt.sawn.Model.Header;
import com.lmt.sawn.Model.Location;
import com.lmt.sawn.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lwinmoethu on 5/25/16.
 */
public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    public static List<Header> countries=new ArrayList<Header>();
    public static int country_pos;
    public static String pref_country,pref_url,pref_code;
    MApplication mApplication;

    public CountryAdapter(List<Header> country,MApplication application) {
        this.country_pos=-1;
        this.countries=country;
        this.mApplication=application;
    }


    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country,parent,false);
        ViewHolder vh=new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(CountryAdapter.ViewHolder holder, int position) {

        Header header=countries.get(position);


        if (country_pos==position) {
            holder.card.setCardBackgroundColor(Color.parseColor("#009688"));
            holder.countryName.setTextColor(Color.WHITE);
        }else{
            holder.card.setCardBackgroundColor(Color.WHITE);
            holder.countryName.setTextColor(Color.parseColor("#009688"));
        ;
        }
        holder.countryName.setText(header.countryName);

        switch (header.countryCode){

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
        return countries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView countryName;
        CardView card;
        ImageView imgFlag;



        public ViewHolder(final View itemView) {
            super(itemView);

            countryName=(TextView) itemView.findViewById(R.id.countryName);
            card=(CardView) itemView.findViewById(R.id.card_country);
            imgFlag=(ImageView) itemView.findViewById(R.id.imgFlag);
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    country_pos = getLayoutPosition();
                     pref_country=countries.get(country_pos).countryName;
                     pref_url=countries.get(country_pos).downloadUrl;
                     pref_code=countries.get(country_pos).getCountryCode();
                     mApplication.saveStringSharedPreferrence("country",pref_country);
                     mApplication.saveStringSharedPreferrence("url",pref_url);
                     mApplication.saveStringSharedPreferrence("code",pref_code);
                     notifyDataSetChanged();
                     //Toast.makeText(view.getContext(),pref_url,Toast.LENGTH_SHORT).show();

                }
            });
        }


    }
}
