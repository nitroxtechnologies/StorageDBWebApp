package AWSAccessors;

public class JavaLocalGrailsUnit {
    public long id;
    public String name;
    public double width;
    public double depth;
    public double height;
    public String type;
    public int floor;
    public double price;
    public long facilityId;

    public String rateType;

    public JavaLocalGrailsUnit()
    {

    }

    public JavaLocalGrailsUnit(long id, String name, double width, double depth, double height, String type, int floor, double price, long facilityId, String rateType)
    {
        this.id = id;
        this.name = name;
        this.width = width;
        this.depth = depth;
        this.height = height;
        this.type = type;
        this.floor = floor;
        this.price = price;
        this.facilityId = facilityId;
        this.rateType = rateType;
    }

    public String toString()
    {
        return id + " " + name + " " + width + " " + depth + " " + height + " " + type + " " + type + " " + floor + " " + price + " " + facilityId + " " + rateType;
    }
}