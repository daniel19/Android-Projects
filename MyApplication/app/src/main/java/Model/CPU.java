package Model;

import android.media.Image;

/**
 * CPU class that represents CPUs.
 */
public class CPU extends Parts {
    public double speed;
    public int cores;

    public CPU(String name, double cost, Image image, int cores, double speed) {
        super(name, cost, image);
        this.speed =speed;
        this.cores = cores;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + speed + "\n" + cores;
    }
}
