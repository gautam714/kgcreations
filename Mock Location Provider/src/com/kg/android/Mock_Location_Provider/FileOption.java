package com.kg.android.Mock_Location_Provider;

/**
 * Created by kgautam on 4/10/2014.
 */
public class FileOption implements Comparable<FileOption>{
    private String name;
    private String path;

    public FileOption(String n, String p){
        name = n;
        path = p;
    }

    public String getName(){
        return name;
    }

    public String getPath(){
        return path;
    }

    @Override
    public int compareTo(FileOption o) {
        if(this.name != null)
            return this.name.toLowerCase().compareTo(o.getName().toLowerCase());
        else
            throw new IllegalArgumentException();
    }
}
