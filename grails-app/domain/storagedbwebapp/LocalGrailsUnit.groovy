package storagedbwebapp

class LocalGrailsUnit {
    long dbId
    String name
    String climate
    int floor
    double price

    LocalGrailsUnit(dbId, name, climate, floor, price)
    {
        this.dbId = dbId
        this.name = name
        this.climate = climate
        this.floor = floor
        this.price = price
    }
}