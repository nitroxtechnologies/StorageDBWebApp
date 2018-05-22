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


        long id = 0
        println("ID" + id)
        println("WEIRD CONTROLLER")
        def facilities = []
        DynamoHandler dh = new DynamoHandler()
        List<Facility> facilitiesArraylist = dh.getFacilitiesFromCompanyID(0)
        LocalGrailsFacility.executeUpdate('delete from LocalGrailsFacility')
        for(Facility f : facilitiesArraylist)
        {
            new LocalGrailsFacility(id: f.getId(), name: f.getName()).save()
        }
        facilities = LocalGrailsFacility.list()


        def companies = []
        companies = LocalGrailsCompany.list()

        [companies:companies, facilities: facilities]
    }


    def render()
    {
        //render "CONTROLLER RENDER"
    }
}
