package storagedbwebapp

class CompareUnit {

    long dbId
    String name
    double width;
    double depth;
    double height;
    String climate
    int floor
    List prices

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
