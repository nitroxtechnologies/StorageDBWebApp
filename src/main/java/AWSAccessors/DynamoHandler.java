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
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
