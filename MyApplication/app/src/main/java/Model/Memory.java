package Model;

import android.media.Image;

/**
 * Memory class that is a representation of computer memory.
 */
public class Memory extends Parts{
    public String speed;
    public String type;
    public String cas;
    public String modules;
    public String size;
    public String pricePerGB;

    public Memory(String name, String cost,Image image, String speed, String type, String cas,
                  String modules, String size, String pricePerGB) {
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
