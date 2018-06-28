package storagedbwebapp

class Price {

    BigDecimal val
    String displayPrice
    int color
    //0 = black (normal)
    //1 = Red
    //2 = Green


    public Price(BigDecimal val, int color)
    {
        if(val.compareTo(new BigDecimal("0.00")) == 0)
        {
            displayPrice = "-";
        }
        else
        {
            displayPrice = val.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        }
        if(displayPrice.equals("123456.00"))
        {
            displayPrice = "error";
        }
        this.color = color;
        this.val = val;
    }
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