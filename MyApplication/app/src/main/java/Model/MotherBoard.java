package Model;

import android.media.Image;

/**
 * MotherBoard class is a object representation of loaded motherboards.
 */
public class MotherBoard extends Parts {
    public String socket;
    public String formFactor;
    public String ramSlots;
    public String maxRam;

    public MotherBoard(String name, String cost, Image image, String socket, String formFactor,
                        String ramSlots, String maxRam) {
        super(name, cost, image);
        this.socket = socket;
        this.formFactor = formFactor;
        this.ramSlots = ramSlots;
        this.maxRam = maxRam;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + socket + "\n" + formFactor + "\n"
                + ramSlots + "\n" + maxRam;
    }
}
