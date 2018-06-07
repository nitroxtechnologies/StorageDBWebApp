package storagedbwebapp

class LocalGrailsUnit {
    long dbId
    String name
    double width;
    double depth;
    double height;
    String climate
    int floor
    double price

    LocalGrailsUnit(dbId, name, width, depth, height, climate, floor, price)
    {
        this.dbId = dbId
        this.name = name
        this.width = width
        this.depth = depth
        this.height = height
        this.climate = climate
        this.floor = floor
        this.price = price
    }
}