package storagedbwebapp

import AWSAccessors.Company
import AWSAccessors.DynamoHandler
import AWSAccessors.Facility
import AWSAccessors.FacilityToUnit
import AWSAccessors.JavaLocalGrailsUnit
import AWSAccessors.Unit

/**
 * Created by spencersharp on 8/19/17.
 */
class LocalGrailsCompanyController
{
    def updateLocalTables()
    {
        DropdownInfo dropdownInfo = DropdownInfo.list().get(0)

        def companies = LocalGrailsCompany.list()
        def facilities = LocalGrailsFacility.list()
        def units = LocalGrailsUnit.list()

        System.out.println("\n\nCOMPANIES INDEX: " + dropdownInfo.companyIndex + "\n\n");
        def company;
        if(companies.size() > dropdownInfo.companyIndex)
        {
            company = dropdownInfo.companyIndex + 1;
        }
        def facility;
        if(facilities.size() > dropdownInfo.facilityIndex)
        {
            facility = dropdownInfo.facilityIndex + 1
        }

        def addFacilities = CompareInfo.list().get(0).addFacilities;

        def removeFacilities = CompareInfo.list().get(0).removeFacilities;
        def compareCompany = dropdownInfo.compareCompaniesIndex;
        DynamoHandler dh = new DynamoHandler();

        def compareCompanies = dh.getCompaniesFromCompanyIds(CompareInfo.list().get(0).companyIds);
        /*def addFacilitiesIds = dh.getFacilitiesFromCompanyID(compareInfo)

        if(dropdownInfo.compareCompaniesIndex >= 0)
        {
            ArrayList<LocalGrailsFacility> result = ArrayList<LocalGrailsFacility>();
            for(LocalGrailsFacility f : addFacilities)
            {
                if(f.)
            }
        }*/

        [facility: facility, addFacilities: addFacilities, removeFacilities: removeFacilities,
         compareCompany: compareCompany, compareCompanies: compareCompanies, company: company,
         units: units, companies: companies, facilities: facilities]
    }
    def updateDropdownList(int companyIndex, int facilityIndex, int climateIndex, int unitIndex, int compareCompaniesIndex)
    {
        DropdownInfo dropdownInfo = DropdownInfo.list().get(0)
        DropdownInfo.executeUpdate('delete from DropdownInfo')

        if(companyIndex > 0)
        {
            dropdownInfo.companyIndex = companyIndex - 1;
        }
        if(facilityIndex > 0)
        {
            dropdownInfo.facilityIndex = facilityIndex - 1;
        }
        if(climateIndex > 0)
        {
            dropdownInfo.climateIndex = climateIndex - 1;
        }
        if(unitIndex > 0)
        {
            dropdownInfo.unitIndex = unitIndex - 1;
        }
        if(compareCompaniesIndex > 0)
        {
            dropdownInfo.compareCompaniesIndex = compareCompaniesIndex - 1;
        }

        new DropdownInfo(companyIndex: dropdownInfo.companyIndex, facilityIndex: dropdownInfo.facilityIndex, climateIndex: dropdownInfo.climateIndex, unitIndex: dropdownInfo.unitIndex, compareCompaniesIndex: dropdownInfo.compareCompaniesIndex).save()
    }
    def index()
    {
        println("----------------");
        println("INDEX")
        println("----------------");
        updateLocalTables()
    }
    /*
    PARAMS NEEDED WHEN LOADED: cID
     */
    def loadFacilities() {
        println("----------------");
        println("LOAD FACILITIES")
        println("CID = " + params.cID)
        println("----------------");
        def facilities = []
        DynamoHandler dh = new DynamoHandler()
        List<Facility> facilitiesArraylist = dh.getFacilitiesFromCompanyID(params.cID as Integer)
        LocalGrailsFacility.executeUpdate('delete from LocalGrailsFacility')
        for (Facility f : facilitiesArraylist) {
            new LocalGrailsFacility(id: f.getId(), name: f.getName()).save()
        }
        updateDropdownList((params.cID as Integer), 0, 0, 0, -1)
        updateLocalTables()
    }
    //Deprecated
    /*
    def loadUnits()
    {
        def companies = LocalGrailsCompany.list()
        def facilities = LocalGrailsFacility.list()
        def units = []

        String type = (params.climate as String);

        if(type.toLowerCase().contains("no"))
        {
            type = "Non-Climate";
        }
        else
        {
            type = "Climate";
        }

        DynamoHandler dh = new DynamoHandler()
        Facility f = dh.getFacilityFromFacilityName(params.fName as String)
        List<Unit> unitsArraylist = dh.getUnitsFromFacilityName(params.fName as String, type)
        LocalGrailsUnit.executeUpdate('delete from LocalGrailsUnit')
        for(Unit u : unitsArraylist)
        {
            new LocalGrailsUnit(id: u.getId(), name: u.getName(), climate: u.getType(), floor: u.getFloor()).save()
        }

        units = LocalGrailsUnit.list()
        System.out.println("LOAD UNITS DROPDOWN - COMPANY: " + f.companyId + " FACILITY: " + params.fID);
//        def company = f.companyId + 1;
        def company = (Integer.parseInt(params.cID) + 2)
        def facility = (Integer.parseInt(params.fID) + 1)
        println("FACILITY: " + facility)

        [companies:companies, company: company, facility: facility, facilities: facilities, units: units]
    }
    */
    /*
    PARAMS NEEDED WHEN LOADED: fName, uName
     */
    def loadUnitTable()
    {
        //new LocalGrailsUnit(id: 0, name: "10x10", climate: "Climate", floor: 0, price: 10.00).save()

        println("----------------");
        println("LOAD UNIT TABLE")
        println("fName: " + params.fName);
        println("fID: " + params.fID);

        DynamoHandler dh = new DynamoHandler()

        Facility f = dh.getFacilityFromFacilityName(params.fName as String)
        println("Facility ID: " + f.getId());
        println("----------------");
        List<Unit> unitsArraylist = dh.getUnitsFromFacilityName(params.fName as String)
        List<FacilityToUnit> facilityToUnitList = dh.getFacilityToUnitsFromFacilityId(f.getId())
        LocalGrailsUnit.executeUpdate('delete from LocalGrailsUnit')
        for(Unit u : unitsArraylist) {
            double price = 0.0;
            boolean canSet = true;
            for (LocalGrailsUnit local : LocalGrailsUnit.list()) {
                if (local.getName().equals(u.getName())) {
                    canSet = false;
                }
            }
            for (FacilityToUnit ftu : facilityToUnitList) {
                if (ftu.unitId == u.getId()) {
                    price = ftu.rateAmount;
                }
            }
            if (canSet)
            {
                System.out.println("UNIT: " + u);
                new LocalGrailsUnit(u.getId(), u.getName(), u.getType(), u.getFloor(), price).save()
            }
        }
        updateDropdownList(-1, (params.fID as Integer), 0, 0, -1)
        /*
        FacilityToUnit ftu = dh.getFacilityToUnitFromNames(params.fName as String, params.uName as String)

        Unit u = dh.getUnitFromUnitName(params.uName as String);
        */
        updateLocalTables()
    }
    /*
    PARAMS NEEDED WHEN LOADED: fID
     */
    def compare()
    {
        CompareInfo compareInfo = CompareInfo.list().get(0);
        CompareInfo.executeUpdate('delete from CompareInfo')
        compareInfo.didUpdate = true;

        if(compareInfo.removeFacilities == null)
        {
            compareInfo.removeFacilities = new ArrayList<Long>();
            compareInfo.addFacilities = new ArrayList<Long>();
            for(LocalGrailsFacility facil : LocalGrailsFacility.list())
            {
                compareInfo.addFacilities.add(facil.id);
            }
            compareInfo.addFacilitiesHash = new HashSet<Long>();
            for(Long l : compareInfo.addFacilities)
            {
                compareInfo.addFacilitiesHash.add(l);
            }
            compareInfo.removeFacilitiesHash = new HashSet<Long>();
            compareInfo.companyIds = new ArrayList<Long>();
            for(LocalGrailsCompany comp : LocalGrailsCompany.list())
            {
                compareInfo.companyIds.add(comp.id);
            }
        }

        if(params.cID != null)
        {
            updateDropdownList(-1, -1, -1, -1, params.cID as Integer)


        }
        else
        {
            DynamoHandler dh = new DynamoHandler();

            String fName = params.fName as String;

            Facility f = dh.getFacilityFromFacilityName(fName);

            long fID = f.getId();

            long companyId = f.getCompanyId();

            //Update compareCompanies


            compareInfo.companyIds.remove(companyId);

            //If fID is in addFacilities
            //We need to add the units to the units model
            if (compareInfo.addFacilitiesHash.contains(fID)) {
                compareInfo.removeFacilitiesHash.add(fID);
                compareInfo.removeFacilities.add(fID);
                compareInfo.addFacilities.remove(fID);
                compareInfo.addFacilitiesHash.remove(fID);
            }
            //If fID is in removeFacilities
            //We need to remove the units from the units model
            else {
                compareInfo.removeFacilitiesHash.remove(fID);
                compareInfo.removeFacilities.remove(fID);
                compareInfo.addFacilities.add(fID);
                compareInfo.addFacilitiesHash.add(fID);
            }

            LocalGrailsUnit.executeUpdate('delete from LocalGrailsUnit')
            ArrayList<JavaLocalGrailsUnit> javaLocalGrailsUnitList = dh.getUnitsFromFacilityIds(compareInfo.removeFacilities);

            for (JavaLocalGrailsUnit u : javaLocalGrailsUnitList) {
                new LocalGrailsUnit(u.id, u.name, u.climate, u.floor, u.price).save()
            }

            new CompareInfo(didUpdate: compareInfo.didUpdate, addFacilities: compareInfo.addFacilities, addFacilitiesHash: compareInfo.addFacilitiesHash,
                    removeFacilities: compareInfo.removeFacilities, removeFacilitiesHash: compareInfo.removeFacilitiesHash,
                    companyIds: compareInfo.companyIds).save(failOnError: true)
        }
        updateLocalTables()
    }


    def render()
    {
        //render "CONTROLLER RENDER"
    }
}
