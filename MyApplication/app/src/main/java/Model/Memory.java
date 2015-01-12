package Model;

import android.media.Image;

/**
 * Memory class that is a representation of computer memory.
 */
public class Memory extends Parts{
    public String speed;
    public String type;
    public int cas;
    public String modules;
    public int size;
    public double pricePerGB;

    public Memory(String name, double cost,Image image, String speed, String type, int cas,
                  String modules, int size, double pricePerGB) {
        super(name, cost, image);
        this.speed = speed;
        this.type = type;
        this.cas = cas;
        this.modules = modules;
        this.size = size;
        this.pricePerGB = pricePerGB;

    }

    @Override
    public String toString() {
        return super.toString() + "\n" + speed + "\n" + type + "\n"
                + cas + "\n" + modules + "\n" + size + "\n" + pricePerGB;
    }
}
