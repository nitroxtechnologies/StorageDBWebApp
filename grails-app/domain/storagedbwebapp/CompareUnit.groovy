package storagedbwebapp

class CompareUnit implements Comparable<storagedbwebapp.CompareUnit>{

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

    public int compareTo(CompareUnit other)
    {
        int c = width.compareTo(other.width);
        if(c < 0)
        {
            return -1;
        }
        else if(c > 0)
        {
            return 1;
        }

        c = depth.compareTo(other.depth);
        if(c < 0)
        {
            return -1;
        }
        else if(c > 0)
        {
            return 1;
        }

        c = type.compareTo(other.type);
        if(c < 0)
        {
            return 1;
        }
        else if(c > 0)
        {
            return -1;
        }

        if(floor > other.floor)
        {
            return -1;
        }
        else if(floor < other.floor)
        {
            return 1;
        }
        return 0;
    }
}
