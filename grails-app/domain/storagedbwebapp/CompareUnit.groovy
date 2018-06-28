package storagedbwebapp

class CompareUnit {

    long dbId
    String name
    BigDecimal width;
    BigDecimal depth;
    BigDecimal height;
    String type
    String rateType
    int floor
    String time

    List prices

    static hasMany = [prices: Price]

    static mapping = {
        prices cascade: 'all-delete-orphan'
    }
}
