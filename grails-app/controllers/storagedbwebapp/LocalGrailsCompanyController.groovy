package storagedbwebapp

import AWSAccessors.Company

/**
 * Created by spencersharp on 8/19/17.
 */
class LocalGrailsCompanyController {
    def index()
    {
        println("CONTROLLER RAN")
        def companies = []
        companies = LocalGrailsCompany.list()
        [companies:companies]
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
