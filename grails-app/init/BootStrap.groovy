import AWSAccessors.Company
import AWSAccessors.DynamoHandler
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
    }
    def destroy = {
    }
}
