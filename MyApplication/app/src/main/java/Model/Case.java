package Model;

/**
 * Case class that represents a computer case;
 */
public class Case extends Parts {
    public String type;
    public String countEXT;
    public String countINT;
    public String powerSupply;

    public Case(String name, String cost, String image, String type, String countEXT, String countINT,
                String powerSupply) {
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
