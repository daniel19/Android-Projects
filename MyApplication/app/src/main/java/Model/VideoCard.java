package Model;

import android.media.Image;

/**
 * VideoCard class that represents GPUs.
 */
public class VideoCard extends Parts {
    public String series;
    public String chipset;
    public int memory;
    public String clockSpeed;

     public VideoCard(String name, double cost, Image image, String series, String chipset,
                      int memory, String clockSpeed) {
        super(name, cost, image);
        this.series = series;
        this.chipset = chipset;
        this.memory = memory;
        this.clockSpeed = clockSpeed;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + series + "\n" + chipset + "\n"
                + memory + "\n" + clockSpeed;
    }
}
