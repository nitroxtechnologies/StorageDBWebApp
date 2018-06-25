package storagedbwebapp

import AWSAccessors.Company
import AWSAccessors.Facility
import AWSAccessors.FacilityToUnitHistory
import AWSAccessors.FacilityToUnit
import AWSAccessors.JavaLocalGrailsUnit
import AWSAccessors.RDSHandler
import AWSAccessors.Unit
import AWSAccessors.User
import AWSAccessors.Value
import org.apache.tomcat.jni.Local

import java.math.RoundingMode
import java.sql.ResultSet
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by spencersharp on 8/19/17.
 */
class LocalGrailsCompanyController
{
    static final BigDecimal PRICE_DIFFERENCE_TO_SKIP = new BigDecimal(""+123456.0);

    def updateLocalTables()
    {
        DropdownInfo dropdownInfo = DropdownInfo.list().get(0)

        def companies = LocalGrailsCompany.list()
        def facilities = LocalGrailsFacility.list()
        def units = CompareUnit.list()

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

        if(RemoveFacility.list().size() > 0)
            removeFacilities.remove(0);

        def compareCompany = dropdownInfo.compareCompaniesIndex;

        def compareCompanies = CompareCompany.list()

        def compareFacility = null

        if(RemoveFacility.list().size() > 0)
            compareFacility = RemoveFacility.list().get(0)

        if(compareFacility == null)
            compareFacility = new LocalGrailsFacility(dbId: Long.MAX_VALUE, name: "None")

        [facility: facility, addFacilities: addFacilities, removeFacilities: removeFacilities,
         compareCompany: compareCompany, compareCompanies: compareCompanies, company: company,
         compareFacility: compareFacility, units: units, companies: companies, facilities: facilities]
    }

    def updateDropdownList(int companyIndex, int facilityIndex, int climateIndex, int unitIndex, int compareCompaniesIndex, long facilityId, String userType, String username)
    {
        DropdownInfo dropdownInfo = DropdownInfo.list().get(0)
        DropdownInfo.executeUpdate('delete from DropdownInfo')

        if(companyIndex >= 0)
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
            dropdownInfo.compareCompaniesIndex = compareCompaniesIndex;
        }
        if(facilityId >= 0)
        {
            dropdownInfo.facilityId = facilityId;
        }

        if(userType.length() > 0)
        {
            dropdownInfo.userType = userType;
        }

        if(username.length() > 0)
        {
            dropdownInfo.username = username;
        }

        new DropdownInfo(companyIndex: dropdownInfo.companyIndex, facilityIndex: dropdownInfo.facilityIndex, climateIndex: dropdownInfo.climateIndex, unitIndex: dropdownInfo.unitIndex, compareCompaniesIndex: dropdownInfo.compareCompaniesIndex, facilityId: dropdownInfo.facilityId, userType: dropdownInfo.userType, username: dropdownInfo.username).save()
    }
    def index()
    {
        CompareCompany.executeUpdate('delete from CompareCompany')
        RemoveFacility.executeUpdate('delete from RemoveFacility')
        AddFacility.executeUpdate('delete from AddFacility')

        ArrayList<Price> prices = new ArrayList<>();

        String name = "thing";
        String climate = "cold";
        int floor = 9;


//        new CompareUnit(dbId: 5, name: name, climate: climate, floor: floor, prices: prices).save(failOnError:true, flush: true)

//        System.out.println(CompareUnit.list().get(0));

        def result = CompareUnit.findByDbId(5);

        for(CompareUnit compareUnit : result)
        {
            System.out.println("COMPAREUNIT: " + compareUnit);
        }

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
        RDSHandler rds = new RDSHandler()
        List<Facility> facilitiesArraylist = rds.getFacilitiesFromCompanyId(params.cID as Long)
        LocalGrailsFacility.executeUpdate('delete from LocalGrailsFacility')
        for (Facility f : facilitiesArraylist) {
            new LocalGrailsFacility(dbId: f.getId(), name: f.getName()).save()
        }
        updateDropdownList((params.cID as Integer), 0, 0, 0, 0, -1l, "", "")
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

        RDSHandler rds = new RDSHandler()

        Facility f = rds.getFacilityFromFacilityName(params.fName as String)
        println("Facility ID: " + f.getId());
        println("----------------");
        List<Unit> unitsArraylist = rds.getUnitsFromFacilityName(params.fName as String)
        List<FacilityToUnit> facilityToUnitList = rds.getFacilityToUnitsFromFacilityId(f.getId())
        CompareUnit.executeUpdate('delete from CompareUnit')
        System.out.println("UNITSARRAYLIST SIZE: "  + unitsArraylist.size());
        System.out.println("FACILITYTOUNIT SIZE: "  + facilityToUnitList.size());
        for(Unit u : unitsArraylist) {
            for (FacilityToUnit ftu : facilityToUnitList) {

                if (ftu.getUnitId() == u.getId()) {
                    System.out.println("MATCHED");
                    ArrayList<Price> prices = new ArrayList<Price>();
                    prices.add(new Price(val: ftu.getRateAmount(), color: 0));
                    String rateType = ftu.getRateType();
                    System.out.println("RATE AMOUNT IS: " + ftu.getRateAmount());
                    if(rateType == null || rateType.length()==0)
                        rateType = "standard";
                    //System.out.println("RATE TYPE NOW: " + rateType);
                    new CompareUnit(dbId:  u.getId(), name: u.getName(), width: u.getWidth(), depth: u.getDepth(), height: u.getHeight()==null?new BigDecimal(0.0):u.getHeight(), type: u.getType(), rateType: rateType, floor: u.getFloor(), prices: prices, time: ftu.getDateCreatedString()).save(failOnError:true)
                }
            }
        }
        updateDropdownList(-1, (params.fID as Integer), 0, 0, 0, f.getId(), "", "")
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

        if(CompareCompany.list().size() == 0 && RemoveFacility.list().size() <= 1)
        {
            long index = 1;
            for(LocalGrailsCompany comp : LocalGrailsCompany.list())
            {
                new CompareCompany(dbId: comp.dbId, index: index++, name: comp.name).save()
            }
        }
        RDSHandler rds = new RDSHandler();
        if(params.cID != null)
        {
            System.out.println("\n\nCOMPARE: CID - " + (params.cID as Integer) + "\n\n");

            updateDropdownList(0, 0, 0, 0, (params.cID as Integer)+1, -1l, "", "")

            Company c = rds.getCompanyFromName(params.cName as String);

            ArrayList<Facility> facilities = rds.getFacilitiesFromCompanyId(c.getId());
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

            Facility f = rds.getFacilityFromFacilityName(fName);

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

                ArrayList<Facility> facilities = rds.getFacilitiesFromCompanyId(companyId);

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
                    for (CompareCompany cc : CompareCompany.list())
                    {
                        if(cc.dbId > companyId)
                        {
                            cc.index--;
                            cc.save();
                        }
                        if(cc.dbId == companyId)
                        {
                            cc.delete();
                        }
                    }
                    //CompareCompany.executeUpdate("delete CompareCompany c where c.dbId = :badId",[badId:companyId])
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
                {
                    Company c = rds.getCompanyFromId(companyId);
                    long newIndex = 1;
                    for(CompareCompany cc : CompareCompany.list())
                    {
                        newIndex++;
                    }
                    new CompareCompany(dbId: companyId, index: newIndex, name: c.getName()).save();
                }
            }

            LocalGrailsUnit.executeUpdate('delete from LocalGrailsUnit')
            AddFacility.executeUpdate('delete from AddFacility')
            CompareUnit.executeUpdate('delete from CompareUnit')

            ArrayList<Long> removeFacilityIds = new ArrayList<>();

            for(RemoveFacility rf : RemoveFacility.list())
            {
                removeFacilityIds.add(rf.dbId);
                System.out.println("RFID: " + rf.dbId);
            }

            ArrayList<JavaLocalGrailsUnit> javaLocalGrailsUnitList = rds.getUnitsFromFacilityIds(removeFacilityIds);
            System.out.println("UNITS IN TABLE: " + javaLocalGrailsUnitList.size());
            HashMap<Long, HashSet<Long>> alreadyAddedTemps = new HashMap<>();
            long idOfBaseFacility = removeFacilityIds.get(0);
            for(Long rfId : removeFacilityIds)
            {
                for(JavaLocalGrailsUnit local : javaLocalGrailsUnitList)
                {
                    System.out.println("LOCAL FACILITY ID: " + local.facilityId);

                    def result = CompareUnit.findByDbId(local.id);

                    CompareUnit found;
                    for(CompareUnit compareUnit : result)
                    {
                        found = compareUnit;
                    }
                    if(found == null)
                    {
                        List<Price> prices = new ArrayList<>();
                        found = new CompareUnit(dbId: local.id, name: local.name, width: local.width, depth: local.depth, height: local.height==null?new BigDecimal(0.0):local.height, type: local.type, rateType: local.rateType, floor: local.floor, prices: prices, time: local.dateCreatedString);
                    }
                    if(local.facilityId == rfId)
                    {
                        found.prices.add(new Price(val: local.price, color: 0));
                        if(rfId != idOfBaseFacility)
                        {
                            BigDecimal price = new BigDecimal("12732136");
                            for(JavaLocalGrailsUnit local2 : javaLocalGrailsUnitList)
                            {
                                if(local2.id == local.id && local2.facilityId == idOfBaseFacility)
                                {
                                    System.out.println("XXXXXXXXXXX");
                                    price = local2.price;
                                }
                            }
                            if(!price.equals(new BigDecimal("12732136")))
                            {
                                BigDecimal val = local.price.subtract(price);
                                long color = 0;
                                if(val.compareTo(new BigDecimal(""+0)) > 0)
                                    color = 1;
                                else
                                    color = 2;
                                found.prices.add(new Price(val: val, color: color));
                            }
                            else
                            {
                                found.prices.add(new Price(val: PRICE_DIFFERENCE_TO_SKIP));
                            }
                        }
                    }
                    else
                    {
                        boolean hasOne = false;
                        for(JavaLocalGrailsUnit local2 : javaLocalGrailsUnitList)
                        {
                            if(local2.id == local.id && local2.facilityId == rfId)
                            {
                                hasOne = true;
                            }
                        }
                        if(!hasOne)
                        {
                            HashSet<Long> foundIds;
                            if(alreadyAddedTemps.get(rfId) == null)
                            {
                                foundIds = new HashSet<>();
                            }
                            else
                            {
                                foundIds = alreadyAddedTemps.get(rfId);
                            }
                            if(!foundIds.contains(local.id))
                            {
                                foundIds.add(local.id);
                                found.prices.add(new Price(val: PRICE_DIFFERENCE_TO_SKIP, color: 0));
                                if(removeFacilityIds.size() > 1)
                                    if(rfId != removeFacilityIds.get(0))// && rfId != removeFacilityIds.get(1))
                                        found.prices.add(new Price(val: PRICE_DIFFERENCE_TO_SKIP, color: 0));
                                alreadyAddedTemps.put(rfId, foundIds);
                            }
                        }
                    }
                    found.save(failOnError:true, flush: true);
                }
            }

            for (JavaLocalGrailsUnit u : javaLocalGrailsUnitList) {
                new LocalGrailsUnit(u.id, u.name, u.width, u.depth, u.height, u.type, u.floor, u.price).save()
            }
        }

        System.out.println("Compare block end\n----------\n\n\n\n\n");
        updateLocalTables()
    }

    def save()
    {
        int index = 0;
        Collection keys = params.keySet();
        JavaLocalGrailsUnit temp;
        ArrayList<JavaLocalGrailsUnit> list = new ArrayList<>();
        long facilityId = DropdownInfo.list().get(0).facilityId;
        System.out.println("FACILITY ID IS: " + facilityId);
        double MAGIC_NUMBER = Math.random();
        for(Object key : keys)
        {
            if(key.equals("controller"))
                break;
            System.out.println(params.get(key));

            int mod = index % 8;
            switch(mod)
            {
                case 0:
                    temp = new JavaLocalGrailsUnit();
                    temp.name = (String) params.get(key);
                    break;
                case 1:
                    temp.width = Double.parseDouble((String) params.get(key));
                    break;
                case 2:
                    temp.depth = Double.parseDouble((String) params.get(key));
                    long thing = (long) (temp.width * 2);
                    if(thing%2 == 1l)
                    {
                        temp.name = ((thing-1)/2) + ".5'x";
                    }
                    else
                    {
                        temp.name = (thing/2) + "'x";
                    }
                    thing = (long) (temp.depth * 2);
                    if(thing%2 == 1l)
                    {
                        temp.name += ((thing-1)/2) + ".5'";
                    }
                    else
                    {
                        temp.name += (thing/2) + "'";
                    }
                    break;
                case 3:
                    temp.floor = Integer.parseInt(""+params.get(key));
                    break;
                case 4:
                    temp.type = "" + params.get(key);
                    while((""+temp.type.charAt(temp.type.length()-1)).equals(" "))
                    {
                        temp.type = temp.type.substring(0,temp.type.length()-1);
                    }
                    break;
                case 5:
                    break;
                case 6:
                    String price = (String) params.get(key);
                    System.out.println("the price is right: " + price);
                    if(price.equals(""))
                    {
                        temp.price = new BigDecimal(""+MAGIC_NUMBER);
                    }
                    else
                    {
                        temp.price = new BigDecimal((String) params.get(key));
                        System.out.println("the price is wrong? " + temp.price);
                    }
                    temp.facilityId = facilityId;
                    break;
                case 7:
                    String rateType = (String) params.get(key);
                    if(rateType == null || rateType.length() == 0)
                        rateType = "standard";
                    temp.rateType = rateType;
                    list.add(temp);
                    System.out.println(temp);
                    break;
            }
            index++;

        }
        System.out.println("------------");




        //NEW IDEA FOR CODE STRUCTURE
        //Get all the FTU ids for the facility, this way we know what IDs to use for our new FTUs

        //Find all the matching FTUs for our JavaLocalGrailsUnits - DO BY SEARCHING FACILITYID?
        //For the JavaLocalGrailsUnits with the magic number price, we must
            //Remove their ids from the possible list for new IDs?
            //Add them into the batch write at the end?

        //Go through all JavaLocalGrailsUnits
            //For each one, we must find a matching unit or make a new unit
            //DONT FORGET TO CHECK EXISTING UNITS WE'RE GONNA ADD
        //Once we finish this, we have a list of all the units we need to add as well as the FTUs we must add
        //Then just write them to the database
        //Save the new max IDs



        RDSHandler rds = new RDSHandler();
        //Gets all the Units that match in name, floor, climate
        List<Unit> unitList = rds.getUnitsWithInfo(list);
        System.out.println("NUMBER OF FOUND UNITS: " + unitList.size());
        for(Unit u : unitList)
        {
            System.out.println(u);
        }
        System.out.println("----------");

        //Set up the ArrayLists to save
        ArrayList<Unit> newUnits = new ArrayList<Unit>();
        ArrayList<FacilityToUnit> newFacilityToUnits = new ArrayList<FacilityToUnit>();
        //Get max IDs to use later
        long newIdFTU = rds.getMaxFacilityToUnitId();
        long historyMaxId = rds.getMaxFacilityToUnitHistoryId();
        long newIdUnit = rds.getMaxUnitId();

        //Sets ids of all JavaLocalGrailsUnits that have corresponding units to correct id
        for(JavaLocalGrailsUnit javaLocalGrailsUnit : list)
        {
            //Try and find a unit with same
            Unit foundUnit = null;
            for(Unit u : unitList)
            {
                //See if they match up
                if(u.isEqualToJavaLocalGrailsUnit(javaLocalGrailsUnit))
                {
                    foundUnit = u;
                    break;
                }
                else
                {
                    System.out.println("NOT FOUND");
                }
            }
            if(foundUnit != null)
            {
                javaLocalGrailsUnit.id = foundUnit.id;
            }
            else
            {
                System.out.println("MAJOR FINDING ERROR!!!!");
            }
        }

        ArrayList<Long> onesToNotChange = new ArrayList<Long>();
        ArrayList<JavaLocalGrailsUnit> listCopy = new ArrayList<JavaLocalGrailsUnit>();
        for(JavaLocalGrailsUnit javaLocalGrailsUnit : list)
        {
            System.out.println(javaLocalGrailsUnit.id);
            if(javaLocalGrailsUnit.price.equals(new BigDecimal(""+MAGIC_NUMBER)))
            {
                System.out.println("ONE THAT SHOULD NOT BE CHANGED");
                onesToNotChange.add(javaLocalGrailsUnit.id);
            }
            else
            {
                listCopy.add(javaLocalGrailsUnit);
            }
        }
        list = listCopy;

        //Now check all FTUs for facilityId == facility && unitId != magic number dudes id (USE <> FOR NOT EQUAL TO aka !=)
        //If they meet these criteria add them to this list
        //With this list we know which IDs to overwrite, and which IDs that will be unchanged (magic number dudes)
        ArrayList<FacilityToUnit> facilityToUnits = rds.getFacilityToUnitsFromFacilityIdAndIdsToExclude(facilityId, onesToNotChange);
        System.out.println("ONES TO OVERWRITE: " + facilityToUnits.size());
        //NOW SAVE ALL GUYS WITH THESE IDS TO OLD TABLE
        ArrayList<FacilityToUnitHistory> oldFacilityToUnits = new ArrayList<FacilityToUnitHistory>();
        ArrayList<Long> facilityToUnitIds = new ArrayList<Long>();
        for(int i = 0; i < facilityToUnits.size(); i++)
        {
            System.out.println(facilityToUnits.get(i));
            facilityToUnitIds.add(facilityToUnits.get(i).id);
            FacilityToUnitHistory toAdd = new FacilityToUnitHistory().createFromFacilityToUnit(facilityToUnits.get(i));
            toAdd.setId(++historyMaxId);
            oldFacilityToUnits.add(toAdd);
        }
        System.out.println("AMOUNT TO WRITE TO OLD TABLE IN FACILITYTOUNIT FORM: " + oldFacilityToUnits.size());
        System.out.println("AMOUNT THAT HAVE TO BE CREATED FOR: " + list.size());

        for(JavaLocalGrailsUnit javaLocalGrailsUnit : list)
        {
            //Try and find a unit with same
            Unit foundUnit = null;
            for(Unit u : unitList)
            {
                //See if they match up
                if(u.isEqualToJavaLocalGrailsUnit(javaLocalGrailsUnit))
                {
                    foundUnit = u;
                    break;
                }
                //If they don't AND THIS IS THE RIGHT PAIR, then create a new unit

                //Make a new FTU
            }
            for(Unit u : newUnits)
            {
                if(u.isEqualToJavaLocalGrailsUnit(javaLocalGrailsUnit))
                {
                    foundUnit = u;
                    break;
                }
            }
            if(foundUnit == null)
            {
                foundUnit = new Unit();
                foundUnit.id = ++newIdUnit;
                foundUnit.name = javaLocalGrailsUnit.name;
                foundUnit.width = javaLocalGrailsUnit.width;
                foundUnit.depth = javaLocalGrailsUnit.depth;
                foundUnit.height = javaLocalGrailsUnit.height;
                foundUnit.type = javaLocalGrailsUnit.type;
                foundUnit.floor = javaLocalGrailsUnit.floor;
                newUnits.add(foundUnit);
            }
            FacilityToUnit toAdd = new FacilityToUnit();
            if(facilityToUnitIds.size() > 0)
            {
                toAdd.id = facilityToUnitIds.remove(facilityToUnitIds.size()-1);
            }
            else
            {
                toAdd.id = ++newIdFTU;
            }

            toAdd.facilityId = javaLocalGrailsUnit.facilityId;
            toAdd.unitId = foundUnit.id;

            toAdd.dateCreated = new Date();
            toAdd.rateAmount = javaLocalGrailsUnit.price;
            if(javaLocalGrailsUnit.rateType == null)
            {
                javaLocalGrailsUnit.rateType = "standard";
            }
            toAdd.rateType = javaLocalGrailsUnit.rateType;
            newFacilityToUnits.add(toAdd);
        }
        System.out.println("--------");

        //Delete old ones
        rds.batchDeleteFacilityToUnits(facilityToUnits);
        //Write old ones to history
        rds.batchSaveFacilityToUnitsHistory(oldFacilityToUnits);
        //Write new one to cur
        rds.batchSaveFacilityToUnits(newFacilityToUnits);
        rds.batchSaveUnits(newUnits);
        System.out.println("AMOUNT WE ARE ADDING: " + newFacilityToUnits.size());

        //Update old FTUs instead of making new ones
        //If we go over, increment on max FTU id to make new FTUs for table
        //If we're under, use BatchWriteItem in amounts of exactly 25 to delete all the FTUs not being used
        //Can just call batchSave


        //Find all FTUs that will be overwritten (facilityId, unitId, rateType)
        //Write them all to the non recent table
        //rds.batchSaveFacilityToUnitsHistory(toBackup);

        Facility f = rds.getFacilityFromId(facilityId);

        chain(action:"loadUnitTable", params:[fID: facilityId, fName: f.getName()]);
    }

    def input() {
        RDSHandler rds = new RDSHandler();

        Collection keys = params.keySet();
        if (keys.size() > 2) {
            rds.executeQuery("TRUNCATE TABLE URLs")
            for (Object key : keys) {
                if (!(params.get(key).equals("localGrailsCompany") || params.get(key).equals("input")
                        || params.get(key).equals("") || params.get(key).equals("reset"))) {
                    println(params.get(key))
                    rds.executeQuery("INSERT INTO URLs VALUES ('" + params.get(key) + "')")
                }
            }
        }
        ResultSet resultSet = rds.executeQuery("SELECT * FROM URLs");
        ArrayList<String> urlResults = new ArrayList<String>();
        while(resultSet.next())
        {
            urlResults.add(resultSet.getString("URL"));
        }
        [urlist: urlResults]

    }

    def edit() {

        println("----------------");
        println("LOAD UNIT TABLE")
        println("fName: " + params.fName);
        println("fID: " + params.fID);

        RDSHandler rds = new RDSHandler()

        Facility f = rds.getFacilityFromFacilityName(params.fName as String)
        println("Facility ID: " + f.getId());
        println("----------------");
        List<Unit> unitsArraylist = rds.getUnitsFromFacilityName(params.fName as String)
        List<FacilityToUnit> facilityToUnitList = rds.getFacilityToUnitsFromFacilityId(f.getId())
        CompareUnit.executeUpdate('delete from CompareUnit')
        System.out.println("UNITSARRAYLIST SIZE: "  + unitsArraylist.size());
        System.out.println("FACILITYTOUNIT SIZE: "  + facilityToUnitList.size());
        for(Unit u : unitsArraylist) {
            System.out.println("LOOP");
            for (FacilityToUnit ftu : facilityToUnitList) {
                if (ftu.getUnitId() == u.getId()) {
                    ArrayList<Price> prices = new ArrayList<Price>();
                    prices.add(new Price(val: ftu.getRateAmount(), color: 0));
                    String rateType = ftu.getRateType();
                    if(rateType.length() == 0 || rateType == null)
                    {
                        rateType = "standard";
                    }
                    new CompareUnit(dbId:  u.getId(), name: u.getName(), width: u.getWidth(), depth: u.getDepth(), height: u.getHeight()==null?new BigDecimal(0.0):u.getHeight(), type: u.getType(), rateType: rateType, floor: u.getFloor(), prices: prices, time: ftu.getDateCreatedString()).save()
                }
            }
        }
        updateDropdownList(0, (params.fID as Integer), 0, 0, 0, f.getId(), "", "")
        /*
        FacilityToUnit ftu = dh.getFacilityToUnitFromNames(params.fName as String, params.uName as String)

        Unit u = dh.getUnitFromUnitName(params.uName as String);
        */
        updateLocalTables()
    }

    def graph() {
        RDSHandler rds = new RDSHandler();
        long facilityId = DropdownInfo.list().get(0).facilityId;
        System.out.println("FACILITY ID: " + facilityId);

        JavaLocalGrailsUnit temp = new JavaLocalGrailsUnit();
        temp.name = (String) params.uName;
        System.out.println("UNIT NAME: " + temp.name);
        temp.floor = Integer.parseInt((String) params.uFloor);
        temp.type = (String) params.uType;
        System.out.println("UNIT TYPE: " + temp.type);
        temp.rateType = (String) params.rateType;
        temp.facilityId = facilityId;
        ArrayList<JavaLocalGrailsUnit> tempList = new ArrayList<JavaLocalGrailsUnit>();
        tempList.add(temp);
        ArrayList<Unit> matchingUnits = rds.getUnitsWithInfo(tempList);
        System.out.println("NUM UNITS FOUND: " + matchingUnits.size());
        System.out.println(matchingUnits);
        long unitId = matchingUnits.get(0).getId();
        temp.id = unitId;

        String facilityName = rds.getFacilityFromId(facilityId).getName();

        ArrayList<Double> prices = new ArrayList<Double>();
        ArrayList<String> dates = new ArrayList<String>();

        ArrayList<FacilityToUnitHistory> historicalPrices = rds.getFacilityToUnitsHistoryWithInfo(tempList);
        FacilityToUnit mostRecent = rds.getFacilityToUnitByFacilityIdAndUnitId(facilityId, unitId);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        while(historicalPrices.size() > 0)
        {
            Date minDate = new Date(Long.MAX_VALUE);
            double price = 0.0;
            int index = 0;
            for(int i = 0; i < historicalPrices.size(); i++)
            {
                Date date = historicalPrices.get(i).dateCreated;
                if(date.compareTo(minDate) < 0)
                {
                    minDate = date;
                    price = historicalPrices.get(i).rateAmount;
                    index = i;
                }
            }
            String toSend = dateFormat.format(minDate);
            dates.add(toSend);
            prices.add(price);
            System.out.println(index + " " + historicalPrices.size());
            System.out.println(historicalPrices.remove(index));
        }
        prices.add(mostRecent.rateAmount);
        dates.add(dateFormat.format(mostRecent.dateCreated));

        System.out.println(prices);
        System.out.println(dates);
        [prices: prices, dates: dates, facilityName: facilityName]
    }

    def verify()
    {
        RDSHandler rds = new RDSHandler();
        String type = rds.getUserTypeFromLogin(params.username as String, params.password as String);

        if(type.equals("Admin"))
        {
            System.out.println("HE AN ADMIN");
        }
        else if(type.equals("Standard"))
        {
        }
        else
        {
            type = "Guest";
            chain(action:"failedLogin", params:[username: params.username, type: type]);
        }
        chain(action:"landing", params:[username: params.username, type: type]);
    }

    def failedLogin()
    {

    }

    def landing()
    {
        System.out.println("TYPE IS: "  + params.type);
        updateDropdownList(-1, 0, 0, 0, 0, -1l, params.type as String, params.username as String)
        RDSHandler rds = new RDSHandler();
        def companies = LocalGrailsCompany.list()
        [companies: companies, username: params.username, type: params.type]
    }

    def addUser()
    {
        /*
        RDSHandler rds = new RDSHandler();
        User user = new User();
        long maxId = rds.getMaxUserId();
        user.setId(maxId+1);
        user.setType(params.type as String);
        user.setUsername(params.username as String);
        user.setPassword(params.password as String);
        rds.addUser(user);
        */
        RDSHandler rds = new RDSHandler();
        User temp = new User();
        temp.setId(params.id as Long);
        temp.setType(params.type as String);
        temp.setFirstName(params.firstName as String);
        temp.setLastName(params.lastName as String);
        temp.setUsername(params.username as String);
        temp.setPassword(params.password as String);
        temp.setIsActive(true);
        rds.updateUser(temp);
    }

    //Params needed: none
    def showUsers()
    {
        System.out.println("SHOW EM");
        boolean shouldSet = false;
        if(params.id != null)
        {
            shouldSet = true;
        }
        RDSHandler rds = new RDSHandler();
//        ArrayList<User> userList = rds.getActiveUsers();
        ArrayList<User> userList = rds.getAllUsers();
        ArrayList<LocalUser> localUsers = new ArrayList<LocalUser>();
        System.out.println("BEFORE: " + userList);
        for(User user : userList)
        {
            if(user.isActive())
                localUsers.add(LocalUser.createFromUser(user));
        }
        def users = localUsers;
        def username = DropdownInfo.list().get(0).username;
        System.out.println("USERS: " + users);
        def maxId = rds.getMaxUserId();
        [users: userList, username: username, maxId: maxId]
    }

    def saveUsers()
    {
        RDSHandler rds = new RDSHandler();
        long maxUserId = rds.getMaxUserId();
        ArrayList<User> users = new ArrayList<User>();
        int index = 0;
        Collection keys = params.keySet();

        User temp = null;

        for(Object key : keys)
        {
            if(key.equals("controller"))
                break;
            String string = params.get(key) as String;
            System.out.println(string);
            String[] result = string.split("\\*");
            User user = new User();
            user.setId(Long.parseLong(result[0]));
            maxUserId = Math.max(user.getId(),maxUserId);
            user.setType(result[1]);
            user.setFirstName(result[2]);
            user.setLastName(result[3]);
            user.setUsername(result[4]);
            user.setPassword(result[5]);
            user.setDateCreated(result[6]);
            user.setDateUpdated(result[7]);
            user.setIsActive(true);
            users.add(user);
        }

        //Delete all users that are in my list
        //Update all users in table to isActive = 0
        //Add all users in my list

        rds.batchDeleteUsers(users);
        rds.makeAllUsersInactive();
        rds.batchSaveUsers(users);

        chain(action:"showUsers");
    }

    def render()
    {
        //render "CONTROLLER RENDER"
    }
}