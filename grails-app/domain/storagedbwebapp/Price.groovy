package storagedbwebapp

class Price {

    BigDecimal val
    String displayPrice
    int color
    //0 = black (normal)
    //1 = Red
    //2 = Green


    static belongsTo = CompareUnit

    static constraints = {
    }
}