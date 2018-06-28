package storagedbwebapp

import java.text.SimpleDateFormat

class Price {

    BigDecimal val
    String displayPrice
    int color
    String time
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
        this.time = "ERROR TIME";
    }
    static belongsTo = [compareUnit: CompareUnit]

    public void setTime(Date time)
    {
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
        ft.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.time = ft.format(time);
    }

    public String toString()
    {
        String ret = "";
        ret += val + " ";
        ret += displayPrice + " ";
        ret += color;
        return ret;
    }
}