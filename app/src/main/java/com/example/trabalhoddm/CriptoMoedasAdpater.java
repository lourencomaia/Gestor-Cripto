package com.example.trabalhoddm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CriptoMoedasAdpater extends BaseAdapter {
    private final Context context;
    public List<Moeda> moedasLista;

    public CriptoMoedasAdpater(Context context, List<Moeda> moedasLista) {
        this.context = context;
        this.moedasLista = moedasLista;
    }

    @Override
    public int getCount() {
        return  moedasLista != null ? moedasLista.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context).
                inflate(R.layout.item_moeda,viewGroup,false);
        TextView txtName = rootView.findViewById(R.id.name);
        ImageView image = rootView.findViewById(R.id.image);

        txtName.setText(moedasLista.get(i).getName());
        image.setImageResource(moedasLista.get(i).getImage());

        return rootView ;
    }
}
