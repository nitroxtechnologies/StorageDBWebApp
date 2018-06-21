import AWSAccessors.Company
import AWSAccessors.RDSHandler
import storagedbwebapp.DropdownInfo
import storagedbwebapp.LocalGrailsCompany
import storagedbwebapp.LocalGrailsCompanyController

class BootStrap {

    def init = { servletContext ->
            RDSHandler rds = new RDSHandler();
            ArrayList<Long> companyIds = new ArrayList<Long>();
            for(long i = 0; i <= 14; i++)
            {
                companyIds.add(i);
            }
            ArrayList<Company> companies = rds.getCompaniesFromCompanyIds(companyIds);
            for(int i = 0; i <= 14; i++)
            {
                new LocalGrailsCompany(dbId: companies.get(i).getId(), name: companies.get(i).getName()).save(failOnError: true)
                System.out.println(companies.get(i));
            }
        new DropdownInfo(companyIndex: 0, facilityIndex: 0, climateIndex: 0, unitIndex: 0, compareCompaniesIndex: 0, facilityId: 0, userType: "Guest", username: "Guest").save(failOnError: true)
    }
    def destroy = {
    }
}