package Model;

import android.media.Image;

import java.io.Serializable;

/**
 *Abstract class that will
 */
public abstract class Parts implements Serializable{
    public String name;
    public String cost;
    private Image image;

    public Parts(String name, String cost, Image image){
        this.name = name;
        this.cost = cost;
        this.image = image;
    }

    public String toString(){
        return "" + name + "\n" + cost;
    }
}
