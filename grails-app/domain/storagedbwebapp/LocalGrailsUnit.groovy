package storagedbwebapp

class LocalGrailsUnit {
    long dbId
    String name
    BigDecimal width;
    BigDecimal depth;
    BigDecimal height;
    String type
    int floor
    BigDecimal price

    LocalGrailsUnit(dbId, name, width, depth, height, type, floor, price)
    {
        this.dbId = dbId
        this.name = name
        this.width = width
        this.depth = depth
        this.height = height
        this.type = type
        this.floor = floor
        this.price = price
    }
}