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
    List prices
    String time

    static hasMany = [prices: Price]

    public String toString()
    {
        String ret = "";
        for(Price p : prices)
        {
            ret += p.val + " ";
        }
        return ret;
    }

    static mapping = {
        prices cascade: 'all-delete-orphan'
    }
}
