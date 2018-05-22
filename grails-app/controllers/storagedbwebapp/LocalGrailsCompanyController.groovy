package storagedbwebapp

import AWSAccessors.Company
import AWSAccessors.DynamoHandler
import AWSAccessors.Facility

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


        def companies = []
        companies.add(new LocalGrailsCompany(id: params.cID, name: params.cName)) // retain selection
        [companies:companies, facilities: facilities]
    }


    def render()
    {
        //render "CONTROLLER RENDER"
    }
}
