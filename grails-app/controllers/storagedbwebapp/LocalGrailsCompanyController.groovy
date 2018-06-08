package storagedbwebapp

import AWSAccessors.Company
import AWSAccessors.DynamoHandler
import AWSAccessors.Facility
import AWSAccessors.FacilityToUnit
import AWSAccessors.FacilityToUnitRecent
import AWSAccessors.JavaLocalGrailsUnit
import AWSAccessors.Unit
import AWSAccessors.Value

import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * Created by spencersharp on 8/19/17.
 */
class LocalGrailsCompanyController
{
    static final double PRICE_DIFFERENCE_TO_SKIP = 123456.0;

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

    def updateDropdownList(int companyIndex, int facilityIndex, int climateIndex, int unitIndex, int compareCompaniesIndex, long facilityId)
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
            dropdownInfo.compareCompaniesIndex = compareCompaniesIndex;
        }

        new DropdownInfo(companyIndex: dropdownInfo.companyIndex, facilityIndex: dropdownInfo.facilityIndex, climateIndex: dropdownInfo.climateIndex, unitIndex: dropdownInfo.unitIndex, compareCompaniesIndex: dropdownInfo.compareCompaniesIndex, facilityId: facilityId).save()
    }
    def index()
    {
        CompareCompany.executeUpdate('delete from CompareCompany')
        RemoveFacility.executeUpdate('delete from RemoveFacility')
        AddFacility.executeUpdate('delete from AddFacility')

        ArrayList<Price> prices = new ArrayList<>();

        prices.add(new Price(val: 77.0));

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
        DynamoHandler dh = new DynamoHandler()
        List<Facility> facilitiesArraylist = dh.getFacilitiesFromCompanyID(params.cID as Integer)
        LocalGrailsFacility.executeUpdate('delete from LocalGrailsFacility')
        for (Facility f : facilitiesArraylist) {
            new LocalGrailsFacility(dbId: f.getId(), name: f.getName()).save()
        }
        updateDropdownList((params.cID as Integer), 0, 0, 0, 0, -1l)
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
        List<FacilityToUnitRecent> facilityToUnitList = dh.getFacilityToUnitRecentsFromFacilityId(f.getId())
        CompareUnit.executeUpdate('delete from CompareUnit')
        System.out.println("UNITSARRAYLIST SIZE: "  + unitsArraylist.size());
        System.out.println("FACILITYTOUNIT SIZE: "  + facilityToUnitList.size());
        for(Unit u : unitsArraylist) {
            System.out.println("LOOP");
            for (FacilityToUnitRecent ftu : facilityToUnitList) {
                if (ftu.getUnitId() == u.getId()) {
                    ArrayList<Price> prices = new ArrayList<Price>();
                    prices.add(new Price(val: ftu.getRateAmount(), color: 0));
                    new CompareUnit(dbId:  u.getId(), name: u.getName(), width: u.getWidth(), depth: u.getDepth(), height: u.getHeight(), climate: u.getType(), floor: u.getFloor(), prices: prices).save()
                }
            }
        }
        updateDropdownList(0, (params.fID as Integer), 0, 0, 0, -1l)
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
        DynamoHandler dh = new DynamoHandler();
        if(params.cID != null)
        {
            System.out.println("\n\nCOMPARE: CID - " + (params.cID as Integer) + "\n\n");

            updateDropdownList(0, 0, 0, 0, (params.cID as Integer)+1, -1l)

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
                    Company c = dh.getCompanyFromId(companyId);
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

            ArrayList<JavaLocalGrailsUnit> javaLocalGrailsUnitList = dh.getUnitsFromFacilityIds(removeFacilityIds);
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
                        found = new CompareUnit(dbId: local.id, name: local.name, width: local.width, depth: local.depth, height: local.height, climate: local.type, floor: local.floor, prices: prices);
                    }
                    if(local.facilityId == rfId)
                    {
                        found.prices.add(new Price(val: local.price, color: 0));
                        if(rfId != idOfBaseFacility)
                        {
                            double price = 12732136;
                            for(JavaLocalGrailsUnit local2 : javaLocalGrailsUnitList)
                            {
                                if(local2.id == local.id && local2.facilityId == idOfBaseFacility)
                                {
                                    System.out.println("XXXXXXXXXXX");
                                    price = local2.price;
                                }
                            }
                            if(price != 12732136)
                            {
                                double val = local.price - price;
                                val = val * 100;
                                val = Math.round(val);
                                val = val / 100;
                                int color = 0;
                                if(val > 0)
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

            int mod = index % 7;
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
                    while(temp.type.charAt(temp.type.length()-1) == ' ')
                    {
                        temp.type = temp.type.substring(0,temp.type.length()-1);
                    }
                    break;
                case 5:
                    break;
                case 6:
                    String price = (String) params.get(key);
                    if(price.equals(""))
                    {
                        temp.price = MAGIC_NUMBER;
                    }
                    else
                    {
                        temp.price = Double.parseDouble(price);
                    }
                    temp.facilityId = facilityId;
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



        DynamoHandler dh = new DynamoHandler();
        //Gets all the Units that match in name, floor, climate
        List<Unit> unitList = dh.getUnitsWithInfo(list);
        System.out.println("NUMBER OF FOUND UNITS: " + unitList.size());
        for(Unit u : unitList)
        {
            System.out.println(u);
        }
        System.out.println("----------");

        //Set up the ArrayLists to save
        ArrayList<Unit> newUnits = new ArrayList<Unit>();
        ArrayList<FacilityToUnitRecent> newFacilityToUnits = new ArrayList<FacilityToUnitRecent>();
        //Get max IDs to use later
        long newIdFTU = dh.getMaxFacilityToUnitId();
        long newIdUnit = dh.getMaxUnitId();

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
            if(javaLocalGrailsUnit.price == MAGIC_NUMBER)
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
        ArrayList<FacilityToUnitRecent> facilityToUnits = dh.getFacilityToUnitsRecentFromFacilityIdAndIdsToExclude(facilityId, onesToNotChange);
        System.out.println("ONES TO OVERWRITE: " + facilityToUnits.size());
        //NOW SAVE ALL GUYS WITH THESE IDS TO OLD TABLE
        ArrayList<FacilityToUnit> oldFacilityToUnits = new ArrayList<FacilityToUnit>();
        ArrayList<Long> facilityToUnitIds = new ArrayList<Long>();
        for(int i = 0; i < facilityToUnits.size(); i++)
        {
            System.out.println(facilityToUnits.get(i));
            facilityToUnitIds.add(facilityToUnits.get(i).id);
            oldFacilityToUnits.add(new FacilityToUnit().createFromFacilityToUnitRecent(facilityToUnits.get(i)));
        }
        System.out.println("AMOUNT TO WRITE TO OLD TABLE IN FACILITYTOUNIT FORM: " + oldFacilityToUnits.size());
        System.out.println("AMOUNT THAT HAVE TO BE CREATED FOR: " + list.size());
        dh.batchSaveFacilityToUnits(oldFacilityToUnits);

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
            FacilityToUnitRecent toAdd = new FacilityToUnitRecent();
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

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String dateString = dateFormat.format(date);
            toAdd.timeCreated = dateString;
            toAdd.rateAmount = javaLocalGrailsUnit.price;
            toAdd.rateType = javaLocalGrailsUnit.rateType;
            newFacilityToUnits.add(toAdd);
        }
        System.out.println("--------");
        ArrayList<FacilityToUnitRecent> toDelete = new ArrayList<FacilityToUnit>();
        for(int i = 0; i < facilityToUnitIds.size(); i++)
        {
            FacilityToUnitRecent newFTU = new FacilityToUnitRecent();
            newFTU.id = facilityToUnitIds.get(i);
            toDelete.add(newFTU);
            System.out.println(newFTU);
        }
        System.out.println("AMOUNT TO DELETE: " + toDelete.size());
        System.out.println("AMOUNT WE ARE ADDING: " + newFacilityToUnits.size());
        dh.batchDeleteFacilityToUnitsRecent(toDelete);

        //Update old FTUs instead of making new ones
        //If we go over, increment on max FTU id to make new FTUs for table
        //If we're under, use BatchWriteItem in amounts of exactly 25 to delete all the FTUs not being used
        //Can just call batchSave

        dh.batchSaveUnits(newUnits);
        //Find all FTUs that will be overwritten (facilityId, unitId, rateType)
        //Write them all to the non recent table
        dh.batchSaveFacilityToUnitsRecent(newFacilityToUnits);

        Value maxUnitId = new Value();
        maxUnitId.name = "maxUnitId";
        maxUnitId.value = newIdUnit;

        Value maxFacilityToUnitId = new Value();
        maxFacilityToUnitId.name = "maxFacilityToUnitId";
        maxFacilityToUnitId.value = newIdFTU;

        ArrayList<Value> values = new ArrayList<Value>();
        values.add(maxUnitId);
        values.add(maxFacilityToUnitId);

        dh.batchSaveValues(values);

        Facility f = dh.getFacilityFromId(facilityId);

        chain(action:"loadUnitTable", params:[fID: facilityId, fName: f.getName()]);
    }

    def input() {

    }

    def edit() {

        println("----------------");
        println("LOAD UNIT TABLE")
        println("fName: " + params.fName);
        println("fID: " + params.fID);

        DynamoHandler dh = new DynamoHandler()

        Facility f = dh.getFacilityFromFacilityName(params.fName as String)
        println("Facility ID: " + f.getId());
        println("----------------");
        List<Unit> unitsArraylist = dh.getUnitsFromFacilityName(params.fName as String)
        List<FacilityToUnitRecent> facilityToUnitList = dh.getFacilityToUnitRecentsFromFacilityId(f.getId())
        CompareUnit.executeUpdate('delete from CompareUnit')
        System.out.println("UNITSARRAYLIST SIZE: "  + unitsArraylist.size());
        System.out.println("FACILITYTOUNIT SIZE: "  + facilityToUnitList.size());
        for(Unit u : unitsArraylist) {
            System.out.println("LOOP");
            for (FacilityToUnitRecent ftu : facilityToUnitList) {
                if (ftu.getUnitId() == u.getId()) {
                    ArrayList<Price> prices = new ArrayList<Price>();
                    prices.add(new Price(val: ftu.getRateAmount(), color: 0));
                    new CompareUnit(dbId:  u.getId(), name: u.getName(), width: u.getWidth(), depth: u.getDepth(), height: u.getHeight(), climate: u.getType(), floor: u.getFloor(), prices: prices).save()
                }
            }
        }
        updateDropdownList(0, (params.fID as Integer), 0, 0, 0, f.getId())
        /*
        FacilityToUnit ftu = dh.getFacilityToUnitFromNames(params.fName as String, params.uName as String)

        Unit u = dh.getUnitFromUnitName(params.uName as String);
        */
        updateLocalTables()
    }

    def graph() {
        long facilityId = 4;
        long unitId = 0;
        String facilityName = "Stowaway Storage/Lakeway Self Storage";

        ArrayList<Double> prices = new ArrayList<Double>();
        ArrayList<String> dates = new ArrayList<String>();

        DynamoHandler dh = new DynamoHandler();

        ArrayList<FacilityToUnit> historicalPrices = dh.getFacilityToUnitsFromFacilityIdAndUnitId(facilityId, unitId);
        FacilityToUnitRecent mostRecent = dh.getFacilityToUnitRecentByFacilityIdAndUnitId(facilityId, unitId);

        while(historicalPrices.size() > 0)
        {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date minDate = new Date(Long.MAX_VALUE);
            double price = 0.0;
            int index = 0;
            for(int i = 0; i < historicalPrices.size(); i++)
            {
                Date date = dateFormat.parse(historicalPrices.get(i).timeCreated);
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
        dates.add(mostRecent.timeCreated);

        System.out.println(prices);
        System.out.println(dates);
        [prices: prices, dates: dates, facilityName: facilityName]

    }


    def render()
    {
        //render "CONTROLLER RENDER"
    }
}