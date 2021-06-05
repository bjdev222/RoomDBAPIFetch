package com.wolcnore.miskkainternshala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wolcnore.miskkainternshala.Pojo;
import com.wolcnore.miskkainternshala.R;
import com.wolcnore.miskkainternshala.Utils;

import java.util.List;

public class WorldDataAdapter extends RecyclerView.Adapter<WorldDataAdapter.WorldDataHolder> {

    List<Pojo> list;
    Context context;

    public WorldDataAdapter(List<Pojo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public WorldDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.datashow_layout,parent,false);
        return  new WorldDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorldDataHolder holder, int position) {

        holder.countryName.setText(list.get(position).getName());
        holder.capital.setText(list.get(position).getCapital());
        holder.region.setText(list.get(position).getRegion());
        holder.subRegion.setText(list.get(position).getSubregion());
        holder.population.setText(list.get(position).getPopulation()+"");

        List<String> listBorder=list.get(position).getBorders();
        String concatenatedString = "";
        String delimiter = ", ";

        for(String word: listBorder){
            concatenatedString += concatenatedString.equals("") ? word : delimiter + word;
        }
        holder.border.setText(concatenatedString );

        List<Pojo.Language> languageList=list.get(position).getLanguages();
        holder.language.setText(languageList.get(0).getName());

        String url=list.get(position).getFlag();
        Utils.fetchSvg(context, url, holder.flag);



    }

    @Override
    public int getItemCount() {
        if(list!=null) {
            return list.size();
        }
        else {
            return  0;
        }

    }

    class WorldDataHolder extends RecyclerView.ViewHolder{

        TextView countryName,capital,region,subRegion,population,border,language;
        ImageView flag;
        public WorldDataHolder(@NonNull View itemView) {
            super(itemView);

            countryName=itemView.findViewById(R.id.countryName);
            capital=itemView.findViewById(R.id.capital);
            region=itemView.findViewById(R.id.region);
            subRegion=itemView.findViewById(R.id.subRegion);
            population=itemView.findViewById(R.id.population);
            border=itemView.findViewById(R.id.border);
            language=itemView.findViewById(R.id.language);

            flag=itemView.findViewById(R.id.flag);

        }
    }
}
