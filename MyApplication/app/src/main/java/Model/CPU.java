package Model;

/**
 * CPU class that represents CPUs.
 */
public class CPU extends Parts {
    public String speed;
    public String cores;

    public CPU(String name, String cost, String image, String cores, String speed) {
        super(name, cost, image);
        this.speed =speed;
        this.cores = cores;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + speed + "\n" + cores;
    }
}
