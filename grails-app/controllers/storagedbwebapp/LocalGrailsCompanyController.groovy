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
        println("CONTROLLER RAN!!")
        def companies = []
        companies = LocalGrailsCompany.list()
        [companies:companies]
        /*
        def facilities = []
        facilities = LocalGrailsFacility.list()
        [facilities:facilities]

        def units = []
        units = LocalGrailsUnit.list()
        [units:units]*/
    }
    def loadFacilities()
    {
        long id = (long) params['id']
        println("WEIRD CONTROLLER");
        def facilities = []
        DynamoHandler dh = new DynamoHandler();
        ArrayList<Facility> facilitiesArraylist = dh.getFacilitiesFromCompanyID(0);
        for(Facility f : facilitiesArraylist)
        {
            new LocalGrailsFacility(id: f.getId(), name: f.getName()).save()
        }
        facilities = LocalGrailsFacility.list()
        [facilities:facilities]
    }
    def save()
    {

    }
    def renderSpecial(Company c)
    {
        //render c.id + " " + c.name + "\n"
    }
    def render()
    {
        //render "CONTROLLER RENDER"
    }
}
