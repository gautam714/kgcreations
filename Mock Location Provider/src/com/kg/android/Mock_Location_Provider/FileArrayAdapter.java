package com.kg.android.Mock_Location_Provider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kgautam on 4/10/2014.
 */
public class FileArrayAdapter extends ArrayAdapter<FileOption> {

    private Context c;
    private int id;
    private List<FileOption> items;

    public FileArrayAdapter(Context context, int textViewResourceId, List<FileOption> objects) {
        super(context, textViewResourceId, objects);
        c = context;
        id = textViewResourceId;
        items = objects;
    }

    public FileOption getItem(int i){
        return items.get(i);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if(null == v){
            LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(id, null);
        }
        final FileOption o = items.get(position);
        if(null != o){
            TextView fileTextView = (TextView)v.findViewById(R.id.file_text_view);
            if(null != fileTextView)
                fileTextView.setText(o.getName());
        }
        return v;
    }
}
