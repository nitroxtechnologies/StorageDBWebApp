package AWSAccessors;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by spencersharp on 6/11/18.
 */
public class RDSHandler
{
    Connection connection;

    public ResultSet executeQuery(String query) throws SQLException
    {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        return rs;
    }









    /*
     *      Methods that create from result sets
     */
    private Company createCompanyFromResultSet(ResultSet rs) throws SQLException
    {
        Company newCompany = new Company();
        newCompany.setId(rs.getLong("id"));
        newCompany.setName(rs.getString("name"));
        return newCompany;
    }

    private CompanyToFacility createCompanyToFacilityFromResultSet(ResultSet rs) throws SQLException
    {

    }

    private Facility createFacilityFromResultSet(ResultSet rs) throws SQLException
    {
        Facility facility = new Facility();
        facility.setId(rs.getLong("id"));
        facility.setName(rs.getString("name"));
        facility.setCompanyId(rs.getLong("companyId"));
        facility.setStreetAddress1(rs.getString("streetAddress1"));
        facility.setStreetAddress2(rs.getString("streetAddress2"));
        facility.setCity(rs.getString("city"));
        facility.setState(rs.getString("state"));
        facility.setZip(rs.getString("zip"));
        facility.setCountry(rs.getString("country"));
        facility.setWebsite(rs.getString("website"));
        facility.setSetupFee(rs.getBigDecimal("setupFee"));
        facility.setPercentFull(rs.getBigDecimal("percentFull"));
        facility.setHasRetailStore(rs.getBoolean("hasRetailStore"));
        facility.setHasInsurance(rs.getBoolean("hasInsurance"));
        facility.setHasOnlineBillPay(rs.getBoolean("hasOnlineBillPay"));
        facility.setHasWineStorage(rs.getBoolean("hasWineStorage"));
        facility.setHasKiosk(rs.getBoolean("hasKiosk"));
        facility.setHasOnsiteManagement(rs.getBoolean("hasOnsiteManagement"));
        facility.setHasCameras(rs.getBoolean("hasCameras"));
        facility.setHasVehicleParking(rs.getBoolean("hasVehicleParking"));
        facility.setHasCutLocks(rs.getBoolean("hasCutLocks"));
        facility.setHasOnsiteShipping(rs.getBoolean("hasOnsiteShipping"));
        facility.setHasAutopay(rs.getBoolean("hasAutopay"));
        facility.setHasOnsiteCarts(rs.getBoolean("hasOnsiteCarts"));
        facility.setHasParabolicMirrors(rs.getBoolean("hasParabolicMirrors"));
        facility.setHasMotionLights(rs.getBoolean("hasMotionLights"));
        facility.setHasElectronicLease(rs.getBoolean("hasElectronicLease"));
        facility.setHasPaperlessBilling(rs.getBoolean("hasPaperlessBilling"));
        facility.setMondayOpen(rs.getTime("mondayOpen"));
        facility.setMondayClose(rs.getTime("mondayClose"));
        facility.setTuesdayOpen(rs.getTime("tuesdayOpen"));
        facility.setTuesdayClose(rs.getTime("tuesdayClose"));
        facility.setWednesdayOpen(rs.getTime("wednesdayOpen"));
        facility.setWednesdayClose(rs.getTime("wednesdayClose"));
        facility.setThursdayOpen(rs.getTime("thursdayOpen"));
        facility.setThursdayClose(rs.getTime("thursdayClose"));
        facility.setFridayOpen(rs.getTime("fridayOpen"));
        facility.setFridayClose(rs.getTime("fridayClose"));
        facility.setSaturdayOpen(rs.getTime("saturdayOpen"));
        facility.setSaturdayClose(rs.getTime("saturdayClose"));
        facility.setSundayOpen(rs.getTime("sundayOpen"));
        facility.setSundayClose(rs.getTime("sundayClose"));
        facility.setRating(rs.getString("rating"));
        facility.setPromotions(rs.getString("promotions"));
        return facility;
    }

    private FacilityToUnitHistory createFacilityToUnitFromResultSet(ResultSet rs) throws SQLException
    {

    }

    private FacilityToUnit createFacilityToUnitRecentFromResultSet(ResultSet rs) throws SQLException
    {

    }

    private Unit createUnitFromResultSet(ResultSet rs) throws SQLException
    {

    }





    private String buildValuesOfCompanyInsertQuery(Company company)
    {
        String result = "(" + company.getId() + ", ";
        result += company.getName() + ", ";
        result += company.getWebsite() + ")";

        return result;
    }

    private String buildCompanyInsertQuery(Company company)
    {
        String query = "INSERT INTO Companies VALUES" + buildValuesOfCompanyInsertQuery(company);
        return query;
    }

    private String buildValuesOfCompanyToFacilityInsertQuery(CompanyToFacility companyToFacility)
    {
        String result = "(" + companyToFacility.getId() + ", ";
        result += companyToFacility.getCompanyId() + ", ";
        result += companyToFacility.getFacilityId() + ")";

        return result;
    }

    private String buildCompanyToFacilityInsertQuery(CompanyToFacility companyToFacility)
    {
        String query = "INSERT INTO CompaniesFacilites VALUES" + buildValuesOfCompanyToFacilityInsertQuery(companyToFacility);
        return query;
    }

    private String buildValuesOfFacilityInsertQuery(Facility facility)
    {
        String result = "(" + facility.getId() + ", ";
        result += "'" + facility.getName() + "', ";
        result += "'" + facility.getCompanyId() + "', ";
        result += "'" + facility.getStreetAddress1() + "', ";
        result += "'" + facility.getStreetAddress2() + "', ";
        result += "'" + facility.getCity() + "', ";
        result += "'" + facility.getState() + "', ";
        result += "'" + facility.getZip() + "', ";
        result += "'" + facility.getCountry() + "', ";
        result += "'" + facility.getWebsite() + "', ";
        result += "'" + facility.getSetupFee() + "', ";
        result += "'" + facility.getPercentFull() + "', ";
        result += "'" + facility.hasRetailStore() + "', ";
        result += "'" + facility.hasInsurance() + "', ";
        result += "'" + facility.hasOnlineBillPay() + "', ";
        result += "'" + facility.hasWineStorage() + "', ";
        result += "'" + facility.hasKiosk() + "', ";
        result += "'" + facility.hasOnsiteManagement() + "', ";
        result += "'" + facility.hasCameras() + "', ";
        result += "'" + facility.hasVehicleParking() + "', ";
        result += "'" + facility.hasCutLocks() + "', ";
        result += "'" + facility.hasOnsiteShipping() + "', ";
        result += "'" + facility.getCountry() + "', ";
        result += "'" + facility.hasAutopay() + "', ";
        result += "'" + facility.hasOnsiteCarts() + "', ";
        result += "'" + facility.hasParabolicMirrors() + "', ";
        result += "'" + facility.hasMotionLights() + "', ";
        result += "'" + facility.hasElectronicLease() + "', ";
        result += "'" + facility.hasPaperlessBilling() + "', ";
        result += "'" + facility.getMondayOpen() + "', ";
        result += "'" + facility.getMondayClose() + "', ";
        result += "'" + facility.getTuesdayOpen() + "', ";
        result += "'" + facility.getTuesdayClose() + "', ";
        result += "'" + facility.getWednesdayOpen() + "', ";
        result += "'" + facility.getWednesdayClose() + "', ";
        result += "'" + facility.getThursdayOpen() + "', ";
        result += "'" + facility.getThursdayClose() + "', ";
        result += "'" + facility.getFridayOpen() + "', ";
        result += "'" + facility.getFridayClose() + "', ";
        result += "'" + facility.getSaturdayOpen() + "', ";
        result += "'" + facility.getSaturdayClose() + "', ";
        result += "'" + facility.getSundayOpen() + "', ";
        result += "'" + facility.getSundayClose() + "', ";
        result += "'" + facility.getRating() + "', ";
        result += "'" + facility.getPromotions() + "')";

        return result;
    }

    private String buildFacilityInsertQuery(Facility facility)
    {
        String query = "INSERT INTO Facilities VALUES" + buildValuesOfFacilityInsertQuery(facility);
        return query;
    }

    private String buildValuesOfFacilityToUnitInsertQuery(FacilityToUnitHistory facilityToUnitHistory)
    {

    }

    private String buildFacilityToUnitInsertQuery(FacilityToUnitHistory facilityToUnitHistory)
    {

    }

    private String buildValuesOfFacilityToUnitRecentInsertQuery(FacilityToUnit facilityToUnit)
    {

    }

    private String buildFacilityToUnitRecentInsertQuery(FacilityToUnit facilityToUnit)
    {

    }

    private String buildValuesOfUnitInsertQuery(Unit unit)
    {

    }

    private String buildUnitInsertQuery(Unit unit)
    {

    }












    /*
     *      Version commands
     */

    public void incrementVersion() throws SQLException {
        String query = "SELECT * FROM Version";
        ResultSet rs = executeQuery(query);
        rs.next();
        long val = rs.getLong("val");

        query = "INSERT INTO Version VALUES(1, " + ++val + ")";
        executeQuery(query);
    }

    public long getVersion() throws SQLException
    {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Version";
        ResultSet rs = statement.executeQuery(query);
        rs.next();
        long val = rs.getLong("val");

        return val;
    }












    /*
     *      Simple adds
     */

    public void addCompany(Company c) throws SQLException
    {
        String query = buildCompanyInsertQuery(c);
        executeQuery(query);
    }

    public void addFacility(Facility f)
    {

    }

    public void addFacilityToUnit(FacilityToUnitHistory ftu)
    {

    }

    public void addUnit(Unit u)
    {

    }






    public Company getCompanyFromId(long id) throws SQLException
    {
        String query = "SELECT * FROM Companies WHERE id=" + id;
        ResultSet rs = executeQuery(query);
        rs.next();
        return createCompanyFromResultSet(rs);
    }

    public void addCompanyToFacility(CompanyToFacility ctf) throws SQLException
    {
        String query = buildCompanyToFacilityInsertQuery(ctf);
        executeQuery(query);
    }

    public ArrayList<Facility> getFacilitiesFromCompanyId(long companyId) throws SQLException
    {
        String query = "SELECT * FROM Facilities WHERE companyId=" + companyId;
        ResultSet rs = executeQuery(query);
        ArrayList<Facility> result = new ArrayList<Facility>();
        while(rs.next())
        {
            result.add(createFacilityFromResultSet(rs));
        }
        return result;
    }

    public Company getCompanyFromName(String name) throws SQLException
    {
        String query = "SELECT * FROM Companies WHERE name=" + name;
        ResultSet rs = executeQuery(query);
        rs.next();
        return createCompanyFromResultSet(rs);
    }

    public Facility getFacilityFromFacilityName(String name) throws SQLException
    {
        String query = "SELECT * FROM Facilities WHERE name=" + name;
        ResultSet rs = executeQuery(query);
        rs.next();
        return createFacilityFromResultSet(rs);
    }

    public Company getCompanyFromFacilityId(long facilityId)
    {

    }

    public ArrayList<Unit> getUnitsFromFacilityName(String name)
    {

    }

    public ArrayList<FacilityToUnitHistory> getFacilityToUnitsFromFacilityId(long facilityId)
    {

    }

    public ArrayList<FacilityToUnit> getFacilityToUnitRecentsFromFacilityId(long facilityId)
    {

    }

    public ArrayList<Company> getCompaniesFromCompanyIds(ArrayList<Long> companyIds)
    {

    }

    public ArrayList<JavaLocalGrailsUnit> getUnitsFromFacilityIds(ArrayList<Long> facilityIds)
    {

    }

    //Gets all the Units that match in name, floor, climate
    public ArrayList<Unit> getUnitsWithInfo(ArrayList<JavaLocalGrailsUnit> list)
    {

    }

    public Facility getFacilityFromId(long facilityId)
    {

    }

    public ArrayList<FacilityToUnit> getFacilityToUnitsRecentFromFacilityIdAndIdsToExclude(long facilityId, ArrayList<Long> idsToExclude)
    {

    }

    public FacilityToUnit getFacilityToUnitRecentByFacilityIdAndUnitId(long facilityId, long unitId)
    {

    }

    public ArrayList<FacilityToUnitHistory> getFacilityToUnitsFromFacilityIdAndUnitId(long facilityId, long unitId)
    {

    }

    public ArrayList<Facility> getFacilitiesFromFacilityIds(ArrayList<Long> facilityIds)
    {

    }










    /*
     *      Batch save commands.
     */
    public void batchSaveCompanies(ArrayList<Company> companies) throws SQLException
    {
        String query = "INSERT INTO Companies VALUES";
        for(int i = 0; i < companies.size(); i++)
        {
            Company company = companies.get(i);
            query += buildValuesOfCompanyInsertQuery(company);
            if(i != companies.size() - 1)
            {
                query += ",";
            }
            else
            {
                query += ")";
            }
        }
        executeQuery(query);
    }

    public void batchSaveCompanyToFacilities(ArrayList<CompanyToFacility> companyToFacilities) throws SQLException
    {
        String query = "INSERT INTO CompaniesFacilities VALUES";
        for(int i = 0; i < companyToFacilities.size(); i++)
        {
            CompanyToFacility companyToFacility = companyToFacilities.get(i);
            query += buildValuesOfCompanyToFacilityInsertQuery(companyToFacility);
            if(i != companyToFacilities.size() - 1)
            {
                query += ",";
            }
            else
            {
                query += ")";
            }
        }
        executeQuery(query);
    }

    public void batchSaveFacilities(ArrayList<Facility> facilities) throws SQLException
    {
        String query = "INSERT INTO Facilities VALUES";
        for(int i = 0; i < facilities.size(); i++)
        {
            Facility facility = facilities.get(i);
            query += buildValuesOfFacilityInsertQuery(facility);
            if(i != facilities.size() - 1)
            {
                query += ",";
            }
            else
            {
                query += ")";
            }
        }
        executeQuery(query);
    }

    public void batchSaveFacilityToUnits(ArrayList<FacilityToUnitHistory> facilityToUnitHistories) throws SQLException
    {
        String query = "INSERT INTO FacilitiesUnits VALUES";
        for(int i = 0; i < facilityToUnitHistories.size(); i++)
        {
            FacilityToUnitHistory facilityToUnitHistory = facilityToUnitHistories.get(i);
            query += buildValuesOfFacilityToUnitInsertQuery(facilityToUnitHistory);
            if(i != facilityToUnitHistories.size() - 1)
            {
                query += ",";
            }
            else
            {
                query += ")";
            }
        }
        executeQuery(query);
    }

    public void batchSaveFacilityToUnitsRecent(ArrayList<FacilityToUnit> facilityToUnits) throws SQLException
    {
        String query = "INSERT INTO FacilitiesUnitsRecent VALUES";
        for(int i = 0; i < facilityToUnits.size(); i++)
        {
            FacilityToUnit facilityToUnit = facilityToUnits.get(i);
            query += buildValuesOfFacilityToUnitRecentInsertQuery(facilityToUnit);
            if(i != facilityToUnits.size() - 1)
            {
                query += ",";
            }
            else
            {
                query += ")";
            }
        }
        executeQuery(query);
    }

    public void batchSaveUnits(ArrayList<Unit> units) throws SQLException
    {
        String query = "INSERT INTO Units VALUES";
        for(int i = 0; i < units.size(); i++)
        {
            Unit unit = units.get(i);
            query += buildValuesOfUnitInsertQuery(unit);
            if(i != units.size() - 1)
            {
                query += ",";
            }
            else
            {
                query += ")";
            }
        }
        executeQuery(query);
    }

    public void batchSaveValues(ArrayList<Value> values) throws SQLException
    {
        String query = "INSERT INTO Values VALUES";
        for(int i = 0; i < values.size(); i++)
        {
            Value value = values.get(i);
            query += buildValuesOfValue(value);
            if(i != values.size() - 1)
            {
                query += ",";
            }
            else
            {
                query += ")";
            }
        }
        executeQuery(query);
    }




    /*
     *      Batch delete commands
     */













    /*
     *      Get max commands
     */


    public long getMaxFacilityToUnitId()
    {

    }

    public long getMaxUnitId()
    {

    }


}
