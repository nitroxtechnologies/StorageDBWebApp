package AWSAccessors;

public class JavaLocalGrailsUnit {
    public long id;
    public String name;
    public String type;
    public int floor;
    public double price;
    public long facilityId;

    public String rateType;

    public JavaLocalGrailsUnit()
    {

    }

    public JavaLocalGrailsUnit(long id, String name, String type, int floor, double price, long facilityId, String rateType)
    {
        this.id = id;
        this.name = name;
        this.type = type;
        this.floor = floor;
        this.price = price;
        this.facilityId = facilityId;
        this.rateType = rateType;
    }
}