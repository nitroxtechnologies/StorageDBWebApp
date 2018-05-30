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
    public static ArrayList<java.lang.Long> makeArrayList(List<Long> list)
    {
        ArrayList<java.lang.Long> retList = new ArrayList<>();

        for(Long aLong : list)
        {
            retList.add(aLong.val);
        }

        return retList;
    }

    def updateLocalTables()
    {
        DynamoHandler dh = new DynamoHandler();
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

        def addFacilities = AddFacility.list()

        def removeFacilities = RemoveFacility.list()

        def compareCompany = dropdownInfo.compareCompaniesIndex + 2;

        def compareCompanies = CompareCompany.list()

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
            new LocalGrailsFacility(dbId: f.getId(), name: f.getName()).save()
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
            new LocalGrailsUnit(dbId: u.getId(), name: u.getName(), climate: u.getType(), floor: u.getFloor()).save()
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
        //new LocalGrailsUnit(dbId: 0, name: "10x10", climate: "Climate", floor: 0, price: 10.00).save()

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
                if (local.name.equals(u.getName())) {
                    canSet = false;
                }
            }
            for (FacilityToUnit ftu : facilityToUnitList) {
                if (ftu.getUnitId() == u.getId()) {
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
        System.out.println("----------\nCompare block start");

        if(CompareCompany.list().size() == 0)
        {
            for(LocalGrailsCompany comp : LocalGrailsCompany.list())
            {
                new CompareCompany(dbId: comp.dbId, name: comp.name).save()
            }
        }
        DynamoHandler dh = new DynamoHandler();
        if(params.cID != null)
        {
            System.out.println("\n\nCOMPARE: CID\n\n");

            updateDropdownList(-1, -1, -1, -1, params.cID as Integer)

            Company c = dh.getCompanyFromName(params.cName as String);

            ArrayList<Facility> facilities = dh.getFacilitiesFromCompanyID(c.getId());
            AddFacility.executeUpdate('delete from AddFacility')

            ArrayList<RemoveFacility> removeFacilities = RemoveFacility.list();

            for(Facility facility : facilities)
            {
                boolean hasAlreadyBeenAdded = false;
                for(RemoveFacility f : removeFacilities) //Check if its in removeFacilities
                {
                    if(facility.getId() == f.dbId)
                    {
                        hasAlreadyBeenAdded = true;
                    }
                }

                //If its not, add it to addFacilities
                if(!hasAlreadyBeenAdded)
                    new AddFacility(dbId: facility.getId(), name: facility.getName()).save();
            }
        }
        else
        {
            System.out.println("\n\nCOMPARE: FID\n\n");
            String fName = params.fName as String;

            Facility f = dh.getFacilityFromFacilityName(fName);

            long fID = f.getId();

            long companyId = f.getCompanyId();

            //Update compareCompanies
            boolean isAlreadyAdded = false;
            ArrayList<RemoveFacility> removeFacilities = RemoveFacility.list();
            for(RemoveFacility facility : removeFacilities) //Check if its in removeFacilities
            {
                System.out.println("GOTTA CHECK THE LOOP");
                System.out.println("REMOVE FACILITY ID: " + facility.dbId);
                System.out.println("FACILITY ID: " + f.getId() + "\n");
                if(facility.dbId == f.getId())
                {
                    System.out.println("FOUND THE OFFENDER");
                    isAlreadyAdded = true;
                }
            }
            System.out.println("REMOVE FACILITIES SIZE: " + removeFacilities.size());
            //If fID is not in removeFacilities
            //We need to add the units to the units model
            if (!isAlreadyAdded) {
                System.out.println("FOUND A NEW ONE");
                new RemoveFacility(dbId: f.getId(), name: f.getName()).save();

                boolean anyLeftForCompany = false;

                ArrayList<Facility> facilities = dh.getFacilitiesFromCompanyID(companyId);

                for(Facility facility : facilities)
                {
                    if(facility.getCompanyId() == companyId)
                    {
                        //if there is a facility for this company that is not in remove facilities
                        //then there is something left for the company
                        boolean canFindInRemoveFacilities = false;
                        for(RemoveFacility facil : removeFacilities)
                            if(facil.dbId == facility.getId())
                                canFindInRemoveFacilities = true;
                        if(!canFindInRemoveFacilities && facility.getId() != fID)
                            anyLeftForCompany = true;
                    }
                }

                if(!anyLeftForCompany)
                {
                    CompareCompany.executeUpdate("delete CompareCompany c where c.dbId = :badId",[badId:companyId])
                }
            }
            //If fID is in removeFacilities
            //We need to remove the units from the units model
            else {
                System.out.println("ALREADY GOT THIS ONE");
                RemoveFacility.executeUpdate("delete RemoveFacility c where c.dbId = :badId",[badId:fID])

                boolean isCompanyAleadyInCompareCompanies = false;
                for(CompareCompany cc : CompareCompany.list())
                {
                    if(cc.dbId == companyId)
                    {
                        isCompanyAleadyInCompareCompanies = true;
                    }
                }
                if(!isCompanyAleadyInCompareCompanies)
                    new CompareCompany(dbId: companyId, name: dh.getCompanyFromId(companyId).getName()).save();
            }

            LocalGrailsUnit.executeUpdate('delete from LocalGrailsUnit')
            AddFacility.executeUpdate('delete from AddFacility')

            ArrayList<Long> removeFacilityIds = new ArrayList<>();

            for(RemoveFacility rf : RemoveFacility.list())
            {
                removeFacilityIds.add(rf.dbId);
            }

            ArrayList<JavaLocalGrailsUnit> javaLocalGrailsUnitList = dh.getUnitsFromFacilityIds(removeFacilityIds);

            for (JavaLocalGrailsUnit u : javaLocalGrailsUnitList) {
                new LocalGrailsUnit(u.id, u.name, u.climate, u.floor, u.price).save()
            }
        }

        System.out.println("Compare block end\n----------");
        updateLocalTables()
    }


    def input() {

    }


    def render()
    {
        //render "CONTROLLER RENDER"
    }
}