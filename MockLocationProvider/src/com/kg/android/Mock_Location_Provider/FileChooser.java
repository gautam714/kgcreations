package com.kg.android.Mock_Location_Provider;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kgautam on 4/10/2014.
 */
public class FileChooser extends ListActivity {
    private File currentDir;
    private FileArrayAdapter adapter;
    private String chosenFileName = "";
    private String chosenFilePath = "";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        currentDir = new File(Environment.getExternalStorageDirectory().toString()+"/");
        fill(currentDir);
    }

    private void fill(File file) {
        File[] dirs = file.listFiles();
        List<FileOption> fls = new ArrayList<FileOption>();

        try{
            for(File ff : dirs){
                if(ff.getName().endsWith(".gpx")){
                    fls.add(new FileOption(ff.getName(), ff.getAbsolutePath()));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        Collections.sort(fls);
        adapter = new FileArrayAdapter(FileChooser.this, R.layout.file_view, fls);
        this.setListAdapter(adapter);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
        FileOption o = adapter.getItem(position);
        onFileClick(o);
    }

    private void onFileClick(FileOption o) {
        chosenFileName = o.getName();
        chosenFilePath = o.getPath();
        finish();
    }

    @Override
    public void finish(){
        Intent data = new Intent();
        data.putExtra("RETURN_FILE_NAME", chosenFileName);
        data.putExtra("RETURN_FILE_PATH", chosenFilePath);
        setResult(RESULT_OK, data);
        super.finish();
    }

    @Override
    public void onBackPressed(){
        Intent setIntent = new Intent(FileChooser.this, MainActivity.class);
        startActivity(setIntent);
    }
}
