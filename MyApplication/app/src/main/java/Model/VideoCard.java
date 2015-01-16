package Model;

/**
 * VideoCard class that represents GPUs.
 */
public class VideoCard extends Parts {
    public String series;
    public String chipset;
    public String memory;
    public String clockSpeed;

     public VideoCard(String name, String cost, String image, String series, String chipset,
                      String memory, String clockSpeed) {
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
