package Model;

import android.media.Image;

/**
 * Storage class is a representation of physical storage.
 */
public class Storage extends Parts{
    public String series; //can be empty/null
    public String form;
    public String type;
    public int capacity;
    public boolean hasCache;
    public double pricePerGB;

    public Storage(String name, double cost, Image image, String series, String form, String type,
                   int capacity, boolean hasCache, double pricePerGB) {
        super(name, cost, image);
        this.series =series;
        this.form = form;
        this.type = type;
        this.capacity = capacity;
        this.hasCache = hasCache;
        this.pricePerGB = pricePerGB;

    }

    @Override
    public String toString() {
        return super.toString() + "\n" + series + "\n" + type + "\n"
                + form + "\n" + capacity + "\n" + hasCache + "\n" + pricePerGB;
    }
}
