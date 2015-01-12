package Model;

import android.media.Image;

/**
 * Case class that represents a computer case;
 */
public class Case extends Parts {
    public String type;
    public int countEXT;
    public int countINT;
    public int powerSupply;

    public Case(String name, double cost, Image image, String type, int countEXT, int countINT,
                int powerSupply) {
        super(name, cost, image);
        this.type = type;
        this.countEXT = countEXT;
        this.countINT = countINT;
        this.powerSupply = powerSupply;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + type + "\n" + countEXT + "\n" +
                countINT + "\n" + powerSupply;
    }
}
