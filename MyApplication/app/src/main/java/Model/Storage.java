package Model;

import android.media.Image;

/**
 * Storage class is a representation of physical storage.
 */
public class Storage extends Parts{
    public String series; //can be empty/null
    public String form;
    public String type;
    public String capacity;
    public String cache;
    public String pricePerGB;

    public Storage(String name, String cost, Image image, String series, String form, String type,
                   String capacity, String hasCache, String pricePerGB) {
        super(name, cost, image);
        this.series =series;
        this.form = form;
        this.type = type;
        this.capacity = capacity;
        this.cache = hasCache;
        this.pricePerGB = pricePerGB;

    }

    @Override
    public String toString() {
        return super.toString() + "\n" + series + "\n" + type + "\n"
                + form + "\n" + capacity + "\n" + cache + "\n" + pricePerGB;
    }
}
