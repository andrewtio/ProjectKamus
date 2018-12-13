package com.andrew.associate.projectkamus2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class KamusAdapter extends RecyclerView.Adapter<KamusAdapter.KamusHolder>{

    private ArrayList<KamusModel> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;

    public KamusAdapter(Context context){
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public KamusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kamus_row,parent,false);
        return new KamusHolder(view);
    }
    public void addItem(ArrayList<KamusModel> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull KamusHolder holder, int position) {
        holder.textViewKata.setText(mData.get(position).getKata());
        holder.textViewDeskripsi.setText(mData.get(position).getDeskripsi());
    }

    @Override
    public int getItemViewType(int position){
        return 0;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class KamusHolder extends RecyclerView.ViewHolder{
        private TextView textViewKata;
        private TextView textViewDeskripsi;

        public KamusHolder (View itemView){
            super(itemView);
            textViewKata = itemView.findViewById(R.id.txt_kata);
            textViewDeskripsi = itemView.findViewById(R.id.txt_des);
        }
    }
}
