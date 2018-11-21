package com.androfast.server.appgpsubicacion.controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androfast.server.appgpsubicacion.R;
import com.androfast.server.appgpsubicacion.negocio.Usuario;

import java.util.List;

/**
 * Created by Belal on 1/27/2017.
 */

public class NameAdapter extends ArrayAdapter<Usuario> {

    //storing all the names in the list
    private List<Usuario> names;

    //context object
    private Context context;

    //constructor
    public NameAdapter(Context context, int resource, List<Usuario> names) {
        super(context, resource, names);
        this.context = context;
        this.names = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //getting the layoutinflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //getting listview itmes
        View listViewItem = inflater.inflate(R.layout.gps, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textView5);
        ImageView imageViewStatus = (ImageView) listViewItem.findViewById(R.id.imageView2);

        //getting the current name
        Usuario name = names.get(position);

        //setting the name to textview
        textViewName.setText(name.getCodigo());

        //if the synced status is 0 displaying
        //queued icon
        //else displaying synced icon
        if (name.getStatus() == 0)
            imageViewStatus.setBackgroundResource(R.drawable.stopwatch);
        else
            imageViewStatus.setBackgroundResource(R.drawable.success);

        return listViewItem;
    }
}
