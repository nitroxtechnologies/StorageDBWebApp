package AWSAccessors;

import java.math.BigDecimal;

public class JavaLocalGrailsUnit {
    public long id;
    public String name;
    public double width;
    public double depth;
    public double height;
    public String type;
    public int floor;
    public BigDecimal price;
    public long facilityId;
    public String timeCreated;

    public String rateType;

    public JavaLocalGrailsUnit()
    {

    }

    public JavaLocalGrailsUnit(long id, String name, double width, double depth, double height, String type, int floor, BigDecimal price, long facilityId, String rateType, String timeCreated)
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
        this.timeCreated = timeCreated;
    }

    public String toString()
    {
        return "ID: " + id + " NAME: " + name + " WIDTH: " + width + " DEPTH: " + depth + " HEIGHT: " + height + " TYPE: " + type + " FLOOR: " + floor + " PRICE: " + price + " FACILITYID: " + facilityId + " RATETYPE: " + rateType;
    }
}