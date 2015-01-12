package Model;

import android.media.Image;

/**
 *Abstract class that will
 */
public abstract class Parts {
    public String name;
    public double cost;
    private Image image;

    public Parts(String name, double cost, Image image){
        this.name = name;
        this.cost = cost;
        this.image = image;
    }

    public String toString(){
        return "" + name + "\n" + cost;
    }
}
