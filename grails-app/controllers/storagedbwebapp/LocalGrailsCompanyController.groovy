package storagedbwebapp

import AWSAccessors.Company
import AWSAccessors.DynamoHandler
import AWSAccessors.Facility
import AWSAccessors.FacilityToUnit
import AWSAccessors.Unit

/**
 * Created by spencersharp on 8/19/17.
 */
class LocalGrailsCompanyController {
    def index()
    {
        println("index controller")
        def companies = []
        companies = LocalGrailsCompany.list()
        [companies:companies]
    }
    def loadFacilities()
    {
        println("CID = " + params.cID)
        println("Loading facilities....")
        def facilities = []
        DynamoHandler dh = new DynamoHandler()
        List<Facility> facilitiesArraylist = dh.getFacilitiesFromCompanyID(params.cID as Integer)
        LocalGrailsFacility.executeUpdate('delete from LocalGrailsFacility')
        for(Facility f : facilitiesArraylist)
        {
            new LocalGrailsFacility(id: f.getId(), name: f.getName()).save()
        }
        facilities = LocalGrailsFacility.list()


        def companies = LocalGrailsCompany.list()

        def company = (Integer.parseInt(""+params.cID) + 1)
        println("Company ID =" + company)
        [companies:companies, company: company, facilities: facilities]
//        render(view: "main",  model: [companies:companies, company: company, facilities: facilities])
    }
    def loadUnits()
    {
        def companies = LocalGrailsCompany.list()
        def facilities = LocalGrailsFacility.list()
        def units = []

        DynamoHandler dh = new DynamoHandler()
        Facility f = dh.getFacilityFromFacilityName(params.fName as String)
        List<Unit> unitsArraylist = dh.getUnitsFromFacilityName(params.fName as String)
        LocalGrailsUnit.executeUpdate('delete from LocalGrailsUnit')
        for(Unit u : unitsArraylist)
        {
            new LocalGrailsUnit(id: u.getId(), name: u.getName()).save()
        }

        units = LocalGrailsUnit.list()
        System.out.println("LOAD UNITS DROPDOWN - COMPANY: " + f.companyId + " FACILITY: " + params.fID);
//        def company = f.companyId + 1;
        def company = (Integer.parseInt(params.cID) + 2)
        def facility = (Integer.parseInt(params.fID) + 1)
        println("FACILITY: " + facility)



        [companies:companies, company: company, facility: facility, facilities: facilities, units: units]
    }
    def loadUnitTable()
    {
        def companies = LocalGrailsCompany.list()
        def facilities = LocalGrailsFacility.list()
        def units = LocalGrailsUnit.list()

        FacilityToUnit ftu = dh.getFacilityToUnitFromNames(params.fName as String, params.unitName as String)

        Unit u = dh.getUnitFromName(params.unitName as String);

        def unitPrice = ftu.getRateAmount();
        def unitName = u.getName();
        def unitFloor = u.getFloor();

        [companies: companies, facilities: facilities, units: units, unitPrice: unitPrice, unitName: unitName, unitFloor: unitFloor]


        //In order for this code to compile, I need to come
    }


    def render()
    {
        //render "CONTROLLER RENDER"
    }
}
