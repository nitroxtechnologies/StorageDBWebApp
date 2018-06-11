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

    private Company createCompanyFromResultSet(ResultSet rs) throws SQLException
    {
        Company newCompany = new Company();
        newCompany.setId(rs.getLong("id"));
        newCompany.setName(rs.getString("name"));
        return newCompany;
    }

    private String buildCompanyInsertQuery(Company company)
    {
        String query = "INSERT INTO Companies VALUES(" + company.getId() + ", " + company.getName() + ")";
        return query;
    }

    private String buildCompanyToFacilityInsertQuery(CompanyToFacility ctf)
    {
        String query = "INSERT INTO CompaniesFacilites VALUES(";
        query += ctf.getId() + ", ";
        query += ctf.getCompanyId() + ", ";
        query += ctf.getFacilityId() + ", ";
        return query;
    }

    public ResultSet executeQuery(String query) throws SQLException
    {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        return rs;
    }

    public void incrementVersion() throws SQLException {
        String query = "SELECT * FROM Version";
        ResultSet rs = executeQuery(query);
        long val = rs.getLong("val");

        query = "INSERT INTO Version VALUES(1, " + ++val + ")";
        executeQuery(query);
    }

    public long getVersion() throws SQLException
    {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Version";
        ResultSet rs = statement.executeQuery(query);
        long val = rs.getLong("val");

        return val;
    }

    public void addCompany(Company c) throws SQLException
    {
        String query = buildCompanyInsertQuery(c);
        executeQuery(query);
    }

    public Company getCompanyFromId(long id) throws SQLException
    {
        String query = "SELECT * FROM Companies WHERE id=" + id;
        ResultSet rs = executeQuery(query);
        return createCompanyFromResultSet(rs);
    }

    public void addCompanyToFacility(CompanyToFacility ctf) throws SQLException
    {
        String query = buildCompanyToFacilityInsertQuery(ctf);
        executeQuery(query);
    }

    public ArrayList<Facility> getFacilitiesFromCompanyId(long companyId)
    {
        String query = "SELECT * FROM Companies WHERE companyId=" + companyId;
    }

    public Company getCompanyFromName(String name)
    {

    }

    public Facility getFacilityFromFacilityName(String name)
    {

    }

    public Company getCompanyFromFacilityId(long facilityId)
    {

    }

    public ArrayList<Unit> getUnitsFromFacilityName(String name)
    {

    }

    public ArrayList<FacilityToUnit> getFacilityToUnitsFromFacilityId(long facilityId)
    {

    }

    public ArrayList<FacilityToUnitRecent> getFacilityToUnitRecentsFromFacilityId(long facilityId)
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

    public ArrayList<FacilityToUnitRecent> getFacilityToUnitsRecentFromFacilityIdAndIdsToExclude(long facilityId, ArrayList<Long> idsToExclude)
    {

    }

    public FacilityToUnitRecent getFacilityToUnitRecentByFacilityIdAndUnitId(long facilityId, long unitId)
    {

    }

    public ArrayList<FacilityToUnit> getFacilityToUnitsFromFacilityIdAndUnitId(long facilityId, long unitId)
    {

    }

    public void batchDeleteFacilityToUnitsRecent(ArrayList<FacilityToUnitRecent> toDelete)
    {

    }

    public void batchSaveUnits(ArrayList<Unit> toSave)
    {

    }

    public void batchSaveFacilityToUnitsRecent(ArrayList<FacilityToUnitRecent> toSave)
    {

    }

    public void batchSaveValues(ArrayList<Value> values)
    {

    }

    public void batchSaveFacilityToUnits(/*ArrayList<Long> idsNotOverwritten, */ArrayList<FacilityToUnit> facilityToUnits)
    {
        //Old code:
        /*
        for (FacilityToUnit ftu : facilityToUnits) {
            if (ftu == null) {
                System.out.println("NULL ELEMENT FOUND");
            }
        }
        mapper.batchSave(facilityToUnits);
        */
    }

    public ArrayList<Facility> getFacilitiesFromFacilityIds(ArrayList<Long> facilityIds)
    {

    }

    public long getMaxFacilityToUnitId()
    {

    }

    public long getMaxUnitId()
    {

    }

    public void addFacility(Facility f)
    {

    }

    public void addFacilityToUnit(FacilityToUnit ftu)
    {

    }

    public void addUnit(Unit u)
    {

    }
}
