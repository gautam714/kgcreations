package com.kg.android.Mock_Location_Provider;

import android.location.Location;
import android.os.Environment;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kgautam on 4/9/2014.
 */
public class GPXParser {
    public static List<Location> decodeGPX(File file){
        List<Location> list = new ArrayList<Location>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            FileInputStream fileInputStream = new FileInputStream(file);
            Document document = documentBuilder.parse(fileInputStream);
            Element elementRoot = document.getDocumentElement();
            NodeList nodelist_trkpt = elementRoot.getElementsByTagName("trkpt");
            for(int i = 0; i < nodelist_trkpt.getLength(); i++){
                Node node = nodelist_trkpt.item(i);
                NamedNodeMap attributes = node.getAttributes();

                String newLatitude = attributes.getNamedItem("lat").getTextContent();
                Double newLatitudeDouble = Double.parseDouble(newLatitude);

                String newLongitude = attributes.getNamedItem("lon").getTextContent();
                Double newLongitudeDouble = Double.parseDouble(newLongitude);

                String newLocationName = newLatitude + ":" + newLongitude;
                Location newLocation = new Location(newLocationName);
                newLocation.setLatitude(newLatitudeDouble);
                newLocation.setLongitude(newLongitudeDouble);
                list.add(newLocation);
            }
            fileInputStream.close();
        }catch(ParserConfigurationException e){
            e.printStackTrace();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(SAXException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return list;
    }
}
