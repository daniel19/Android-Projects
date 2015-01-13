package Model;

import android.media.Image;

/**
 * Power is a class that represents a power supply.
 */
public class Power extends Parts {
    public String series;
    public String form;
    public String efficiency;
    public String watts;
    public String modularStatus;

    public Power(String name, String cost, Image image, String series, String form, String efficiency,
                 String watts, String modularStatus) {
        super(name, cost, image);
        this.series =series;
        this.form = form;
        this.efficiency = efficiency;
        this.watts = watts;
        this.modularStatus = modularStatus;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + series + "\n" + form + "\n"
                + efficiency + "\n" + watts + "\n" + modularStatus;
    }
}
