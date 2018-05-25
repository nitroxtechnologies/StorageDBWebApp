import AWSAccessors.Company
import AWSAccessors.DynamoHandler
import storagedbwebapp.CompareInfo
import storagedbwebapp.DropdownInfo
import storagedbwebapp.LocalGrailsCompany
import storagedbwebapp.LocalGrailsCompanyController

class BootStrap {

    def init = { servletContext ->
            DynamoHandler dh = new DynamoHandler();
            for(int i = 0; i <= 14; i++)
            {
                Company c = dh.getCompanyFromId(i);
                new LocalGrailsCompany(id: c.getId(), name: c.getName()).save()
            }
        new DropdownInfo(companyIndex: 0, facilityIndex: 0, climateIndex: 0, unitIndex: 0).save()
        new CompareInfo()
    }
    def destroy = {
    }
}
