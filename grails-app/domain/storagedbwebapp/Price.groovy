package storagedbwebapp

class Price {

    BigDecimal val
    String displayPrice
    int color
    //0 = black (normal)
    //1 = Red
    //2 = Green


    static belongsTo = [compareUnit: CompareUnit]

    public String toString()
    {
        String ret = "";
        ret += val + " ";
        ret += displayPrice + " ";
        ret += color;
        return ret;
    }
}