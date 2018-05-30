package AWSAccessors;

public class JavaLocalGrailsUnit {
    public long id;
    public String name;
    public String climate;
    public int floor;
    public double price;
    public long facilityId;

    public JavaLocalGrailsUnit(long id, String name, String climate, int floor, double price, long facilityId)
    {
        this.id = id;
        this.name = name;
        this.climate = climate;
        this.floor = floor;
        this.price = price;
        this.facilityId = facilityId;
    }
}