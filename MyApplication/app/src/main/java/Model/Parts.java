package Model;

import java.io.Serializable;

/**
 *Abstract class that will
 */
public abstract class Parts implements Serializable{
    public String name;
    public String cost;
    public String imageURL;

    public Parts(String name, String cost, String imageURL){
        this.name = name;
        this.cost = cost;
        this.imageURL = "http://" + imageURL.substring(2);



    }

    public String toString(){
        return "" + name + "\n" + cost;
    }
}
