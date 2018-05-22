package storagedbwebapp

import AWSAccessors.Company
import AWSAccessors.DynamoHandler
import AWSAccessors.Facility
import AWSAccessors.Unit

/**
 * Created by spencersharp on 8/19/17.
 */
class LocalGrailsCompanyController {
    def index()
    {
        def companies = []
        companies = LocalGrailsCompany.list()
        [companies:companies]
    }
    def loadFacilities()
    {
        println(params.cID)
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

        [companies:companies, facilities: facilities]
    }
    def loadUnits()
    {
        def companies = LocalGrailsCompany.list()
        def facilities = LocalGrailsFacility.list()
        def units = []

        DynamoHandler dh = new DynamoHandler()
        List<Unit> unitsArraylist = dh.getUnitsFromFacilityName(params.fName as String)

        for(Unit u : unitsArraylist)
        {
            new LocalGrailsUnit(id: u.getId(), name: u.getName()).save()
        }

        units = LocalGrailsUnit.list()

        [companies:companies, facilities: facilities, units: units]
    }


    def render()
    {
        //render "CONTROLLER RENDER"
    }
}
