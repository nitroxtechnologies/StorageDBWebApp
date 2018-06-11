package AWSAccessors;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.math.BigDecimal;
import java.util.Date;

@DynamoDBTable(tableName = "FacilitiesUnitsRecent")
public class FacilityToUnit
{
    private long id;
    private long facilityId;
    private long unitId;
    private Date timeCreated;
    private BigDecimal rateAmount;
    private String rateType;

    public FacilityToUnit()
    {

    }

    @DynamoDBHashKey(attributeName = "id")
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "timeCreated")
    public String getTimeCreated()
    {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated)
    {
        this.timeCreated = timeCreated;
    }

    @DynamoDBAttribute(attributeName = "facilityId")
    public long getFacilityId()
    {
        return facilityId;
    }

    public void setFacilityId(long facilityId)
    {
        this.facilityId = facilityId;
    }

    @DynamoDBAttribute(attributeName = "unitId")
    public long getUnitId()
    {
        return unitId;
    }

    public void setUnitId(long unitId)
    {
        this.unitId = unitId;
    }

    @DynamoDBAttribute(attributeName = "rateAmount")
    public BigDecimal getRateAmount()
    {
        return rateAmount;
    }

    public void setRateAmount(BigDecimal rateAmount)
    {
        this.rateAmount = rateAmount;
    }

    @DynamoDBAttribute(attributeName = "rateType")
    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    @DynamoDBIgnore
    public FacilityToUnit createFromFacilityToUnit(FacilityToUnitHistory other)
    {
        this.id = other.getId();
        this.facilityId = other.getFacilityId();
        this.unitId = other.getUnitId();
        this.timeCreated = other.getTimeCreated();
        this.rateAmount = other.getRateAmount();
        this.rateType = other.getRateType();

        return this;
    }

    public String toString()
    {
        return id + " " + facilityId + " " + unitId + " Cost: " + rateAmount + " Type: " + rateType;
    }
}
