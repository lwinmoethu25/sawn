package com.lmt.sawn.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lmt.sawn.Activity.DetailActivity;
import com.lmt.sawn.MApplication;
import com.lmt.sawn.Model.Header;
import com.lmt.sawn.Model.Location;
import com.lmt.sawn.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lwinmoethu on 5/28/16.
 */
public class RamadamAdapter extends RecyclerView.Adapter<RamadamAdapter.ViewHolder> {
    public static List<Location> ramadam=new ArrayList<Location>();
    public static int pos;
    public String sehri,iftari,day,date;
    MApplication mApplication;
    String txtSehri,txtIftari,txtDate;
    String[] data = new String[2];

    public RamadamAdapter(List<Location> ramadam,MApplication application) {
        this.pos=-1;
        this.ramadam=ramadam;
        this.mApplication=application;
    }


    @Override
    public RamadamAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ramadam,parent,false);
        ViewHolder vh=new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Location location=ramadam.get(position);

        txtSehri=location.close;
        spliltDateandTime(txtSehri,false);

        holder.day.setText("Day "+location.day);
        holder.date.setText(data[0]);
        holder.close.setText(data[1]);

        txtIftari=location.open;
        spliltDateandTime(txtIftari,true);

        holder.open.setText(data[1]);


    }

    public void spliltDateandTime(String timestamp,boolean flag){

        if(flag==true){

            try {
                DateFormat f = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
                Date d = f.parse(timestamp);
                DateFormat time = new SimpleDateFormat("hh:mm a");
                data[1]=time.format(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else{

            try {
                DateFormat f = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
                Date d = f.parse(timestamp);
                DateFormat date = new SimpleDateFormat("dd/MM/yy");
                DateFormat time = new SimpleDateFormat("hh:mm a");
                data[0]=date.format(d);
                data[1]=time.format(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


    }

    @Override
    public int getItemCount() {
        return ramadam.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView day,close,open,date;
        ImageView imgFlag;



        public ViewHolder(final View itemView) {
            super(itemView);

            day=(TextView) itemView.findViewById(R.id.txtDay);
            close=(TextView)itemView.findViewById(R.id.sehri);
            open=(TextView) itemView.findViewById(R.id.iftari);
            date=(TextView) itemView.findViewById(R.id.txtDate);
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    pos = getLayoutPosition();
                   //    notifyDataSetChanged();

                    sehri=ramadam.get(pos).close;
                    spliltDateandTime(sehri,false);

                    sehri=data[1];

                    iftari=ramadam.get(pos).open;
                    spliltDateandTime(iftari,true);

                    iftari=data[1];

                    Context context = view.getContext();
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("day",ramadam.get(pos).day);
                    i.putExtra("date",data[0]);
                    i.putExtra("sehri",sehri);
                    i.putExtra("iftari",iftari);
                    context.startActivity(i);
                }
            });
        }


    }
}
