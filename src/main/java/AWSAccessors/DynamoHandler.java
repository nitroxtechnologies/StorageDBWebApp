package AWSAccessors;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.*;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class DynamoHandler
{
    DynamoDBMapper mapper;

    public DynamoHandler() throws IOException
    {
        Scanner sc = new Scanner(new File("DataFiles/credentials.txt"));

        //String AWS_ACCESS_KEY_ID = sc.nextLine();
        //String AWS_SECRET_ACCESS_KEY = sc.nextLine();
        HashMap hm = new HashMap<String,String>();
        hm.put("AWS_ACCESS_KEY_ID",sc.nextLine());
        hm.put("AWS_SECRET_ACCESS_KEY",sc.nextLine());
        System.out.println("ACCESS KEY" + hm.get("AWS_ACCESS_KEY_ID"));
        setEnv(hm);
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_WEST_1).withCredentials(new EnvironmentVariableCredentialsProvider())
                .build();

        //Company c = new Company();
        //c.setName("Qualcomm");
        //c.setId(16);

        mapper = new DynamoDBMapper(client);
        incrementVersion();

        //mapper.save(c);
    }

    protected static void setEnv(Map<String, String> newenv)
    {
        try
        {
            Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
            Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
            theEnvironmentField.setAccessible(true);
            Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
            env.putAll(newenv);
            Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
            theCaseInsensitiveEnvironmentField.setAccessible(true);
            Map<String, String> cienv = (Map<String, String>)     theCaseInsensitiveEnvironmentField.get(null);
            cienv.putAll(newenv);
        }
        catch (NoSuchFieldException e)
        {
            try {
                Class[] classes = Collections.class.getDeclaredClasses();
                Map<String, String> env = System.getenv();
                for(Class cl : classes) {
                    if("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
                        Field field = cl.getDeclaredField("m");
                        field.setAccessible(true);
                        Object obj = field.get(env);
                        Map<String, String> map = (Map<String, String>) obj;
                        map.clear();
                        map.putAll(newenv);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    //Version code
    public void incrementVersion()
    {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedScanList<Version> scanVersions = mapper.scan(Version.class,scanExpression);
        long maxVersion = 0;
        for(Version v : scanVersions)
        {
            if(v.getVersion()>maxVersion)
                maxVersion = v.getVersion();
            mapper.delete(v);
        }
        Version version = new Version(maxVersion+1);
        mapper.save(version);
    }

    public long getVersion()
    {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedScanList<Version> scanVersions = mapper.scan(Version.class,scanExpression);
        long maxVersion = 0;
        for(Version v : scanVersions)
        {
            if(v.getVersion()>maxVersion)
                maxVersion = v.getVersion();
        }
        return maxVersion;
    }

    public void addCompany(Company c)
    {
        mapper.save(c);
    }

    public Company getCompanyFromId(long id)
    {
        Company c = mapper.load(Company.class,id);
        return c;
    }

    public void addCompanyToFacility(CompanyToFacility ctf)
    {
        mapper.save(ctf);
    }

    public List<Facility> getFacilitiesFromCompanyID(long id)
    {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withN(""+id));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("companyId = :val1").withExpressionAttributeValues(eav);

        List scanResult = mapper.scan(Facility.class, scanExpression);

        return scanResult;
    }

    public Company getCompanyFromName(String name)
    {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(""+name));

        Map<String,String> ean = new HashMap<>();
        ean.put("#n","name");

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("#n = :val1").withExpressionAttributeValues(eav).withExpressionAttributeNames(ean);

        List scanResult = mapper.scan(Company.class, scanExpression);

        Company c = (Company) scanResult.get(0);

        return c;
    }

    public Facility getFacilityFromFacilityName(String name)
    {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(""+name));

        Map<String,String> ean = new HashMap<>();
        ean.put("#n","name");

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("#n = :val1").withExpressionAttributeValues(eav).withExpressionAttributeNames(ean);

        List scanResult = mapper.scan(Facility.class, scanExpression);

        Facility f = (Facility) scanResult.get(0);

        return f;
    }

    public Company getCompanyFromFacilityId(long fID)
    {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withN(""+fID));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("id = :val1").withExpressionAttributeValues(eav);

        List scanResult = mapper.scan(Facility.class, scanExpression);

        Facility f = (Facility) scanResult.get(0);

        long companyId = f.getCompanyId();

        eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withN(""+companyId));

        scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("id = :val1").withExpressionAttributeValues(eav);

        scanResult = mapper.scan(Facility.class, scanExpression);

        Company c = (Company) scanResult.get(0);

        return c;
    }

    public List<Unit> getUnitsFromFacilityName(String name)
    {
        Facility f = getFacilityFromFacilityName(name);

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withN(""+f.getId()));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("facilityId = :val1").withExpressionAttributeValues(eav);

        List scanResult = mapper.scan(FacilityToUnitRecent.class,scanExpression);

        //System.out.println("\n\nUNITS: " + scanResult + "\n\n");

        eav = new HashMap<String, AttributeValue>();

        String filterExpression = "";

        System.out.println("SEARCH ID: " + f.getId() + " SIZE: " + scanResult.size());

        for(int i = 0; i < scanResult.size(); i++)
        {
            long unitId = ((FacilityToUnitRecent)scanResult.get(i)).getUnitId();
            filterExpression += ("id = :val" + (i+1));
            eav.put(":val"+(i+1), new AttributeValue().withN(""+unitId));
            if(i != scanResult.size() - 1)
            {
                filterExpression += " OR ";
            }
        }
        //System.out.println("\n\nFILTER EXPRESSION: " + filterExpression + "\n\n");

        if(scanResult.size() > 0) {
            scanResult = new ArrayList();

            scanExpression = new DynamoDBScanExpression()
                    .withFilterExpression(filterExpression).withExpressionAttributeValues(eav);

            scanResult.addAll(mapper.scan(Unit.class, scanExpression));
        }

        return scanResult;
    }

    public Unit getUnitFromUnitName(String name)
    {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(""+name));

        Map<String,String> ean = new HashMap<>();
        ean.put("#n","name");

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("#n = :val1").withExpressionAttributeValues(eav).withExpressionAttributeNames(ean);

        List scanResult = mapper.scan(Unit.class, scanExpression);

        Unit u = (Unit) scanResult.get(0);

        return u;
    }

    public List<FacilityToUnit> getFacilityToUnitsFromFacilityId(long facilityId)
    {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withN(""+facilityId));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("facilityId = :val1").withExpressionAttributeValues(eav);

        List scanResult = mapper.scan(FacilityToUnit.class, scanExpression);

        return scanResult;
    }

    public List<FacilityToUnit> getFacilityToUnitRecentsFromFacilityId(long facilityId)
    {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withN(""+facilityId));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("facilityId = :val1").withExpressionAttributeValues(eav);

        List scanResult = mapper.scan(FacilityToUnitRecent.class, scanExpression);

        return scanResult;
    }

    public List<Company> getCompaniesFromCompanyIds(ArrayList<Long> companyIds)
    {
        if(companyIds == null || companyIds.size() == 0)
        {
            return new ArrayList<Company>();
        }

        String filterExpression = "";
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();

        for(int i = 0; i < companyIds.size(); i++)
        {
            long facilityId = companyIds.get(i);
            filterExpression += ("id = :val" + (i+1));
            eav.put(":val"+(i+1), new AttributeValue().withN(""+facilityId));
            if(i != companyIds.size() - 1)
            {
                filterExpression += " OR ";
            }
        }

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(filterExpression).withExpressionAttributeValues(eav);
        List companies = mapper.scan(Company.class, scanExpression);

        return companies;
    }

    //DEPRECATED
    public FacilityToUnit getFacilityToUnitFromNames(String facilityName, String unitName)
    {
        Facility f = getFacilityFromFacilityName(facilityName);

        Unit u = getUnitFromUnitName(unitName);

        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withN(""+u.getId()));
        eav.put(":val2", new AttributeValue().withN(""+f.getId()));

        //System.out.println("QUERY\nFACILITY ID: " + f.getId() + " UNIT ID: " + u.getId() + "\nFACILITY NAME: " + facilityName + " UNIT NAME: " + unitName);

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("unitId = :val1 AND facilityId = :val2").withExpressionAttributeValues(eav);

        List scanResult = mapper.scan(FacilityToUnit.class, scanExpression);

        return ((FacilityToUnit)scanResult.get(0));
    }

    public ArrayList<JavaLocalGrailsUnit> getUnitsFromFacilityIds(ArrayList<Long> facilityIds)
    {
        if(facilityIds.size() == 0)
        {
            return new ArrayList<JavaLocalGrailsUnit>();
        }

        String filterExpression = "";
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();

        for(int i = 0; i < facilityIds.size(); i++)
        {
            long facilityId = facilityIds.get(i);
            filterExpression += ("facilityId = :val" + (i+1));
            eav.put(":val"+(i+1), new AttributeValue().withN(""+facilityId));
            if(i != facilityIds.size() - 1)
            {
                filterExpression += " OR ";
            }
        }

        System.out.println("FILTER EXPRESSION: " + filterExpression);

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(filterExpression).withExpressionAttributeValues(eav);
        List facilityToUnitsResult = mapper.scan(FacilityToUnitRecent.class, scanExpression);

        ArrayList<JavaLocalGrailsUnit> result = new ArrayList<>();
        filterExpression = "";
        eav = new HashMap<String,AttributeValue>();

        for(int i = 0; i < facilityToUnitsResult.size(); i++)
        {
            long unitId = ((FacilityToUnitRecent)facilityToUnitsResult.get(i)).getUnitId();
            filterExpression += ("id = :val" + (i+1));
            eav.put(":val"+(i+1), new AttributeValue().withN(""+unitId));
            if(i != facilityToUnitsResult.size() - 1)
            {
                filterExpression += " OR ";
            }
        }

        scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(filterExpression).withExpressionAttributeValues(eav);
        List unitsResult = mapper.scan(Unit.class, scanExpression);

        //Temporary: Code is inefficient
        for(Object o : facilityToUnitsResult)
        {
            FacilityToUnitRecent ftu = (FacilityToUnitRecent) o;
            for(Object o2 : unitsResult)
            {
                Unit u = (Unit) o2;
                if(ftu.getUnitId() == u.getId())
                {
                    result.add(new JavaLocalGrailsUnit(u.getId(), u.getName(), u.getWidth(), u.getDepth(), u.getHeight(), u.getType(), u.getFloor(), ftu.getRateAmount(), ftu.getFacilityId(), ftu.getRateType()));
                }
            }
        }

        return result;
    }

    //Gets all the Units that match in name, floor, climate
    public List<Unit> getUnitsWithInfo(ArrayList<JavaLocalGrailsUnit> list)
    {
        if(list.size() == 0)
        {
            return new ArrayList<Unit>();
        }

        String filterExpression = "";
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();

        Map<String,String> ean = new HashMap<>();
        ean.put("#n","name");
        ean.put("#t","type");

        for(int i = 0; i < list.size()*3; i+=3)
        {
            filterExpression += ("#n = :val" + (i+1));
            filterExpression += (" AND floor = :val" + (i+2));
            filterExpression += (" AND #t = :val" + (i+3));
            eav.put(":val"+(i+1), new AttributeValue().withS(""+list.get(i/3).name));
            eav.put(":val"+(i+2), new AttributeValue().withN(""+list.get(i/3).floor));
            eav.put(":val"+(i+3), new AttributeValue().withS(""+list.get(i/3).type));
            if(i != list.size()*3 - 3)
            {
                filterExpression += " OR ";
            }
        }

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(filterExpression).withExpressionAttributeValues(eav).withExpressionAttributeNames(ean);
        List units = mapper.scan(Unit.class, scanExpression);

        return units;
    }

    public List<FacilityToUnitRecent> getFacilityToUnitsRecentFromFacilityIdAndIdsToExclude(long facilityId, ArrayList<Long> idsToExclude)
    {
        String filterExpression = "facilityId = :val1";
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withN(""+facilityId));

        for(int i = 0; i < idsToExclude.size(); i++)
        {
            filterExpression += " AND unitId <> :val" + (i+2);
            eav.put(":val"+(i+2), new AttributeValue().withN(""+idsToExclude.get(i)));
        }

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(filterExpression).withExpressionAttributeValues(eav);
        List result = mapper.scan(FacilityToUnitRecent.class, scanExpression);

        return result;
    }

    public FacilityToUnitRecent getFacilityToUnitRecentByFacilityIdAndUnitId(long facilityId, long unitId)
    {
        String filterExpression = "facilityId = :val1 AND unitId = :val2";
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withN(""+facilityId));
        eav.put(":val2", new AttributeValue().withN(""+unitId));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(filterExpression).withExpressionAttributeValues(eav);
        List result = mapper.scan(FacilityToUnitRecent.class, scanExpression);

        return (FacilityToUnitRecent)result.get(0);
    }

    public List<FacilityToUnit> getFacilityToUnitsFromFacilityIdAndUnitId(long facilityId, long unitId)
    {
        String filterExpression = "facilityId = :val1 AND unitId = :val2";
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withN(""+facilityId));
        eav.put(":val2", new AttributeValue().withN(""+unitId));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(filterExpression).withExpressionAttributeValues(eav);
        List result = mapper.scan(FacilityToUnit.class, scanExpression);

        return result;
    }

    public void batchDeleteFacilityToUnitsRecent(ArrayList<FacilityToUnitRecent> toDelete)
    {
        mapper.batchDelete(toDelete);
    }

    public void batchSaveUnits(ArrayList<Unit> toSave)
    {
        mapper.batchSave(toSave);
    }

    public void batchSaveFacilityToUnitsRecent(ArrayList<FacilityToUnitRecent> toSave)
    {
        mapper.batchSave(toSave);
    }

    public void batchSaveValues(ArrayList<Value> values)
    {
        mapper.batchSave(values);
    }

    //Find all FTUs that will be overwritten (facilityId, unitId, rateType)
    //Write them all to the non recent table
    public void batchSaveFacilityToUnits(/*ArrayList<Long> idsNotOverwritten, */ArrayList<FacilityToUnit> facilityToUnits)
    {
        for(FacilityToUnit ftu : facilityToUnits)
        {
            if(ftu == null)
            {
                System.out.println("NULL ELEMENT FOUND");
            }
        }
        mapper.batchSave(facilityToUnits);

        /*
        if(facilityToUnits.size() > 0)
        {

            String filterExpression = "";
            Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();

            for(int i = 0; i < idsNotOverwritten.size(); i++)
            {
                filterExpression += "id = :val" + (i+1);
                eav.put(":val"+(i+1), new AttributeValue().withN(""+idsNotOverwritten.get(i)));
                if(i != idsNotOverwritten.size() - 1)
                {
                    filterExpression += " OR ";
                }
            }

            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                    .withFilterExpression(filterExpression).withExpressionAttributeValues(eav);
            List oldFacilityToUnits = mapper.scan(FacilityToUnitRecent.class, scanExpression);

            //Delete all of these
            //Write all of new ones?

            for(int i = 0; i < facilityToUnits.size()*3; i+=3)
            {
                filterExpression += ("facilityId = :val" + (i+1));
                filterExpression += ("AND unitId = :val" + (i+2));
                filterExpression += ("AND rateType = :val" + (i+3));
                eav.put(":val"+(i+1), new AttributeValue().withN(""+facilityToUnits.get(i/3).getFacilityId()));
                eav.put(":val"+(i+1), new AttributeValue().withN(""+facilityToUnits.get(i/3).getUnitId()));
                eav.put(":val"+(i+1), new AttributeValue().withS(""+facilityToUnits.get(i/3).getRateType()));
                if(i != facilityToUnits.size()*3 - 3)
                {
                    filterExpression += " OR ";
                }
            }

            scanExpression = new DynamoDBScanExpression()
                    .withFilterExpression(filterExpression).withExpressionAttributeValues(eav);
            List oldFacilityToUnits = mapper.scan(FacilityToUnitRecent.class, scanExpression);

            for(Object oldFTU : oldFacilityToUnits)
            {
                boolean canFindOneWithId = false;
                boolean canFindMatch = false;
                //If can't find one with id
                    //Remove from FacilityToUnitRecent
                //If can't find match
                    //Error?
            }
        }
        */
    }

    public List<Facility> getFacilitiesFromFacilityIds(ArrayList<Long> facilityIds)
    {
        if(facilityIds == null || facilityIds.size() == 0)
        {
            return new ArrayList<Facility>();
        }

        String filterExpression = "";
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();

        for(int i = 0; i < facilityIds.size(); i++)
        {
            long facilityId = facilityIds.get(i);
            filterExpression += ("id = :val" + (i+1));
            eav.put(":val"+(i+1), new AttributeValue().withN(""+facilityId));
            if(i != facilityIds.size() - 1)
            {
                filterExpression += " OR ";
            }
        }

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(filterExpression).withExpressionAttributeValues(eav);
        List facilities = mapper.scan(Facility.class, scanExpression);

        return facilities;
    }

    public long getMaxFacilityToUnitId()
    {
        Value value = mapper.load(Value.class, "maxFacilityToUnitId");

        return value.getValue();
    }

    public long getMaxUnitId()
    {
        Value value = mapper.load(Value.class, "maxUnitId");

        return value.getValue();
    }

    public void addFacility(Facility f)
    {
        mapper.save(f);
    }

    public void addFacilityToUnit(FacilityToUnit ftu)
    {
        mapper.save(ftu);
    }

    public void addUnit(Unit u)
    {
        mapper.save(u);
    }
}
