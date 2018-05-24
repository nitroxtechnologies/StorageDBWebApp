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

        Company c = new Company();
        c.setName("Qualcomm");
        c.setId(16);

        mapper = new DynamoDBMapper(client);

        mapper.save(c);
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

    public List<Unit> getUnitsFromFacilityName(String name)
    {
        Facility f = getFacilityFromFacilityName(name);

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withN(""+f.getId()));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("facilityId = :val1").withExpressionAttributeValues(eav);

        List scanResult = mapper.scan(FacilityToUnit.class,scanExpression);

        eav = new HashMap<String, AttributeValue>();

        String filterExpression = "";
        Map<String,String> ean = new HashMap<>();
        ean.put("#t","type");

        System.out.println("SEARCH ID: " + f.getId() + " SIZE: " + scanResult.size());

        for(int i = 0; i < scanResult.size(); i++)
        {
            long unitId = ((FacilityToUnit)scanResult.get(i)).getUnitId();
            filterExpression += ("id = :val" + (i+1));
            eav.put(":val"+(i+1), new AttributeValue().withN(""+unitId));
            if(i != scanResult.size() - 1)
            {
                filterExpression += " OR ";
            }
        }

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

    public FacilityToUnit getFacilityToUnitFromNames(String facilityName, String unitName)
    {
        Facility f = getFacilityFromFacilityName(facilityName);

        Unit u = getUnitFromUnitName(unitName);

        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withN(""+u.getId()));
        eav.put(":val2", new AttributeValue().withN(""+f.getId()));

        System.out.println("QUERY\nFACILITY ID: " + f.getId() + " UNIT ID: " + u.getId());

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("unitId = :val1 AND facilityId = :val2").withExpressionAttributeValues(eav);

        List scanResult = mapper.scan(FacilityToUnit.class, scanExpression);

        return ((FacilityToUnit)scanResult.get(0));
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
