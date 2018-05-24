package storagedbwebapp

import AWSAccessors.Company
import AWSAccessors.DynamoHandler
import AWSAccessors.Facility
import AWSAccessors.FacilityToUnit
import AWSAccessors.Unit

/**
 * Created by spencersharp on 8/19/17.
 */
class LocalGrailsCompanyController
{
    def updateLocalTables()
    {
        DropdownInfo dropdownInfo = DropdownInfo.list().get(0)

        def companies = LocalGrailsCompany.list()
        def facilities = LocalGrailsFacility.list()
        def units = LocalGrailsUnit.list()

        def company;
        if(companies.size() > dropdownInfo.companyIndex)
        {
            company = companies.get(dropdownInfo.companyIndex);
        }
        def facility;
        if(facilities.size() > dropdownInfo.facilityIndex)
        {
            facility = facilities.get(dropdownInfo.facilityIndex)
        }
        def unit;
        if(units.size() > dropdownInfo.unitIndex)
        {
            unit = units.get(dropdownInfo.unitIndex)
        }

        [company: company, facility: facility, unit: unit, companies: companies, facilities: facilities, units: units]
    }
    def updateDropdownList(int companyIndex, int facilityIndex, int climateIndex, int unitIndex)
    {
        DropdownInfo dropdownInfo = DropdownInfo.list().get(0)
        DropdownInfo.executeUpdate('delete from DropdownInfo')

        if(companyIndex > 0)
        {
            dropdownInfo.companyIndex = companyIndex - 1;
        }
        if(facilityIndex > 0)
        {
            dropdownInfo.facilityIndex = facilityIndex - 1;
        }
        if(climateIndex > 0)
        {
            dropdownInfo.climateIndex = climateIndex - 1;
        }
        if(unitIndex > 0)
        {
            dropdownInfo.unitIndex = unitIndex - 1;
        }

        new DropdownInfo(companyIndex: dropdownInfo.companyIndex, facilityIndex: dropdownInfo.facilityIndex, climateIndex: dropdownInfo.climateIndex, unitIndex: dropdownInfo.unitIndex).save()
    }
    def index()
    {
        println("----------------");
        println("INDEX")
        println("----------------");
        updateLocalTables()
    }
    /*
    PARAMS NEEDED WHEN LOADED: cID
     */
    def loadFacilities()
    {
        println("----------------");
        println("LOAD FACILITIES")
        println("CID = " + params.cID)
        println("----------------");
        def facilities = []
        DynamoHandler dh = new DynamoHandler()
        List<Facility> facilitiesArraylist = dh.getFacilitiesFromCompanyID(params.cID as Integer)
        LocalGrailsFacility.executeUpdate('delete from LocalGrailsFacility')
        for(Facility f : facilitiesArraylist)
        {
            new LocalGrailsFacility(id: f.getId(), name: f.getName()).save()
        }
        updateDropdownList((params.cID as Integer), 0, 0, 0)
        updateLocalTables()
    }
    //Deprecated
    /*
    def loadUnits()
    {
        def companies = LocalGrailsCompany.list()
        def facilities = LocalGrailsFacility.list()
        def units = []

        String type = (params.climate as String);

        if(type.toLowerCase().contains("no"))
        {
            type = "Non-Climate";
        }
        else
        {
            type = "Climate";
        }

        DynamoHandler dh = new DynamoHandler()
        Facility f = dh.getFacilityFromFacilityName(params.fName as String)
        List<Unit> unitsArraylist = dh.getUnitsFromFacilityName(params.fName as String, type)
        LocalGrailsUnit.executeUpdate('delete from LocalGrailsUnit')
        for(Unit u : unitsArraylist)
        {
            new LocalGrailsUnit(id: u.getId(), name: u.getName(), climate: u.getType(), floor: u.getFloor()).save()
        }

        units = LocalGrailsUnit.list()
        System.out.println("LOAD UNITS DROPDOWN - COMPANY: " + f.companyId + " FACILITY: " + params.fID);
//        def company = f.companyId + 1;
        def company = (Integer.parseInt(params.cID) + 2)
        def facility = (Integer.parseInt(params.fID) + 1)
        println("FACILITY: " + facility)

        [companies:companies, company: company, facility: facility, facilities: facilities, units: units]
    }
    */
    /*
    PARAMS NEEDED WHEN LOADED: fName, uName
     */
    def loadUnitTable()
    {
        //new LocalGrailsUnit(id: 0, name: "10x10", climate: "Climate", floor: 0, price: 10.00).save()

        println("----------------");
        println("LOAD UNIT TABLE")
        println("fName: " + params.fName);
        println("fID: " + params.fID);


        DynamoHandler dh = new DynamoHandler()

        Facility f = dh.getFacilityFromFacilityName(params.fName as String)
        println("Facility ID: " + f.getId());
        println("----------------");
        List<Unit> unitsArraylist = dh.getUnitsFromFacilityName(params.fName as String)
        List<FacilityToUnit> facilityToUnitList = dh.getFacilityToUnitsFromFacilityId(f.getId())
        LocalGrailsUnit.executeUpdate('delete from LocalGrailsUnit')
        for(Unit u : unitsArraylist) {
            double price = 0.0;
            boolean canSet = true;
            for (LocalGrailsUnit local : LocalGrailsUnit.list()) {
                if (local.getName().equals(u.getName())) {
                    canSet = false;
                }
            }
            for (FacilityToUnit ftu : facilityToUnitList) {
                if (ftu.unitId == u.getId()) {
                    price = ftu.rateAmount;
                }
            }
            if (canSet)
            {
                new LocalGrailsUnit(id: u.getId(), name: u.getName(), climate: u.getType(), floor: u.getFloor(), price: price).save()
            }
        }
        updateDropdownList(0, (params.fID as Integer), 0, 0)
        /*
        FacilityToUnit ftu = dh.getFacilityToUnitFromNames(params.fName as String, params.uName as String)

        Unit u = dh.getUnitFromUnitName(params.uName as String);
        */
        updateLocalTables()
    }


    def render()
    {
        //render "CONTROLLER RENDER"
    }
}
