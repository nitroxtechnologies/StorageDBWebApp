import AWSAccessors.Company
import AWSAccessors.RDSHandler
import storagedbwebapp.DropdownInfo
import storagedbwebapp.LocalGrailsCompany
import storagedbwebapp.LocalGrailsCompanyController

class BootStrap {

    def init = { servletContext ->
        RDSHandler rds = new RDSHandler();
        ArrayList<Company> companies = rds.getAllCompanies();
        for(Company company : companies)
        {
            System.out.println(company);
            new LocalGrailsCompany(dbId: company.getId(), name: company.getName()).save(failOnError: true)
        }
        new DropdownInfo(companyIndex: 0, facilityIndex: 0, climateIndex: 0, unitIndex: 0, compareCompaniesIndex: 0, facilityId: 0, userType: "Guest", username: "Guest").save(failOnError: true)
    }
    def destroy = {
    }
}