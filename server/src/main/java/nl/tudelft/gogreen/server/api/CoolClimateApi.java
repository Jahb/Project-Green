package nl.tudelft.gogreen.server.api;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import nl.tudelft.gogreen.server.Main;
import org.json.XML;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("checkstyle:Indentation")
public class CoolClimateApi {

    /**
     * Filled in by user answering questions in the survey and applied to API.
     * Questions receive user
     * information about their current zip code,
     * how many people live in their household (adults,
     * children), annual income, square footage of home,
     * yearly expenditures on each utility
     * (electricity, heating, water),
     * how many days per year the household uses heat and cooling,
     * personal vehicle usage and mpg (up to 3 vehicles)
     * and final questions about how many miles the
     * user acquires through air travel and public transportation.
     */


    private static String[] keys = {
            "input_location=",       // User inputs their zip code
            "input_size=",        // The number of people that live in the user's house
            "input_footprint_household_adults=",   // How many adults occupy the user's house
            "input_footprint_household_children=",
            "input_income=",                      // User inputs their income
            "input_footprint_housing_squarefeet=",       // How large is the user's house
            "input_footprint_housing_electricity_dollars=",
            "input_footprint_housing_cdd=",               // Days that the house is cooled/year
            "input_footprint_transportation_miles1=",
            "input_footprint_transportation_mpg1=",
            "input_footprint_housing_electricity_kwh=",
            "input_footprint_shopping_food_fruitvegetables="


    };

    private static String[] keysLT = {
            "input_location=NY",    // User inputs their zip code
            "input_size=10",     // The number of people that live in the user's house
            "input_footprint_household_adults=10", // How many adults occupy the user's house
            "input_footprint_household_children=0",
            "input_income=10",        // User inputs their income
            "input_footprint_housing_squarefeet=10",       // How large is the user's house
            "input_footprint_housing_electricity_dollars=0",
            "input_footprint_housing_cdd=",               // Days that the house is cooled/year
            "input_footprint_transportation_miles1=20",
            "input_footprint_transportation_mpg1=30",
            "input_footprint_housing_electricity_kwh=0",
            "input_footprint_shopping_food_fruitvegetables=0"


    };

    private static String[] keys2 = {
            "input_location=",                 // User inputs their zip code
            "input_size=",        // The number of people that live in the user's house
            "input_footprint_household_adults=",  // How many adults occupy the user's house
            "input_footprint_household_children=",
            "input_income=",                              // User inputs their income
            "input_footprint_housing_squarefeet=",       // How large is the user's house
            "input_footprint_housing_electricity_dollars=",
            "input_footprint_housing_cdd=",       // Days that the house is cooled/year
            "input_footprint_transportation_miles1=",
            "input_footprint_transportation_mpg1=",
            "input_footprint_housing_electricity_kwh=",
            "input_footprint_shopping_food_fruitvegetables="


    };

    /**
     * Implemented by the user selecting tasks that have been completed
     * to reduce their annual carbon
     * footprint.These actions include switching to cfl (compact fluorescent light), maintaining
     * maintenance on vehicles, reducing air travel, telecommuting to work, reducing days using the
     * thermostat in winter and summer, riding their bike instead of driving, carpooling, and
     * practicing eco driving.
     */
    private static String[] takeActionKeys = {
            "input_takeaction_switch_to_cfl=0",
            "input_takeaction_maintain_my_vehicles=0",
            "input_takeaction_more_efficient_vehicle=0",
            "input_takeaction_reduce_air_travel=0",
            "input_takeaction_telecommute_to_work=0",
            "input_takeaction_thermostat_summer=0",
            "input_takeaction_offset_housing=0",
            "input_takeaction_energy_star_fridge=0",
            "input_takeaction_thermostat_winter=0",
            "input_takeaction_offset_transportation=0",
            "input_takeaction_ride_my_bike=0",
            "input_takeaction_purchase_green_electricity=0",
            "input_takeaction_carpool_to_work=0",
            "input_takeaction_practice_eco_driving=0",
            "input_takeaction_take_public_transportation=1",
            "input_takeaction_take_public_transportation_type=0",
            "input_takeaction_take_public_transportation_gco2bus=1",
            "input_takeaction_take_public_transportation_mpg=30",

    };

    /**
     * Required keys needed for the API to properly function and are hard coded with values. These
     * keys are invisible to the user but the programmer has the option to add any of the keys to
     * {@link #keys} to receive user input.
     */
    private static String[] requiredKeys = {
            "input_location_mode=1",                                        // 1 = Zip code
            "internal_state_abbreviation=NY",
            "input_changed=0",                                              // Meaningless variable
            "input_footprint_transportation_num_vehicles=3",
            "input_footprint_transportation_fuel1=1",
            "input_footprint_transportation_fuel2=1",
            "input_footprint_transportation_fuel3=1",
            "input_footprint_transportation_miles4=0",
            "input_footprint_transportation_mpg4=0",
            "input_footprint_transportation_fuel4=0",
            "input_footprint_transportation_miles5=0",
            "input_footprint_transportation_mpg5=0",
            "input_footprint_transportation_fuel5=0",
            "input_footprint_transportation_miles6=0",
            "input_footprint_transportation_mpg6=0",
            "input_footprint_transportation_fuel6=0",
            "input_footprint_transportation_miles7=0",
            "input_footprint_transportation_mpg7=0",
            "input_footprint_transportation_fuel7=0",
            "input_footprint_transportation_miles8=0",
            "input_footprint_transportation_mpg8=0",
            "input_footprint_transportation_fuel8=0",
            "input_footprint_transportation_miles9=0",
            "input_footprint_transportation_mpg9=0",
            "input_footprint_transportation_fuel9=0",
            "input_footprint_transportation_miles10=0",
            "input_footprint_transportation_mpg10=0",
            "input_footprint_transportation_fuel10=0",
            "input_footprint_transportation_airtype=simple", // Total miles covered for air
            "input_footprint_transportation_groundtype=simple",
            "input_footprint_housing_electricity_type=0",       // In $/year
            "input_footprint_housing_cleanpercent=0",
            "input_footprint_housing_naturalgas_type=0",
            "input_footprint_housing_heatingoil_type=0",
            "input_footprint_housing_heatingoil_dollars_per_gallon=4",
            //Average, heating oil is $4/gallon
            "input_footprint_shopping_food_meattype=simple",           // Meat consumed by the user
            "input_footprint_shopping_food_meatfisheggs=0",
            // Calories eaten daily of meat, fish and eggs
            "input_footprint_shopping_food_dairy=0",
            // Calories eaten daily of dairy
            "input_footprint_shopping_food_otherfood=0",
            // Calories per day other food
            "input_footprint_shopping_food_cereals=0",
            // Calories per day cereals
            "input_footprint_shopping_goods_default_furnitureappliances=0",
            // Furniture/appliances cost/yr
            "input_footprint_shopping_goods_default_clothing=0",            // Clothing cost/year
            "input_footprint_shopping_goods_default_other_entertainment=0",
            // Entertainment cost/year
            "input_footprint_shopping_goods_default_other_office=0",
            // Office supplies cost/year
            "input_footprint_shopping_goods_default_other_personalcare=0",
            "input_footprint_shopping_goods_default_other_autoparts=0",     // Auto cost/year
            "input_footprint_shopping_goods_default_other_medical=0",
            "input_footprint_shopping_goods_type=advanced",
            "input_footprint_shopping_goods_total=0",
            // No input by the user, sum of the subtotals
            "input_footprint_shopping_services_type=simple",
            "input_footprint_shopping_services_total=0",
            // How much the user spends on services per year
            "input_footprint_housing_gco2_per_kwh=1000",
            "input_footprint_housing_naturalgas_dollars=0",
            "input_footprint_housing_heatingoil_dollars=0",
            "input_footprint_housing_hdd=0",
            "input_footprint_shopping_goods_clothing=6666660",
            "input_footprint_housing_watersewage=20",
            "input_footprint_transportation_miles2=0",
            "input_footprint_transportation_mpg2=0",
            "input_footprint_transportation_miles3=0",
            "input_footprint_transportation_mpg3=0",
            "input_footprint_transportation_airtotal=0",
            "input_footprint_transportation_publictrans=0"


    };


    /**
     * fetches the API Data.
     *
     * @param feature   the feature input
     * @param userinput the user input
     * @return returns the needed data
     * @throws SQLException raises error when unable to access database
     */
    public static float fetchApiData(String feature, String userinput,
                                     int id) throws SQLException, UnirestException {
        switch (feature) {
            case "Local Product":
                return localProduct();
            case "Solar Panels":
                return solarPanels(id);
            case "Vegetarian Meal":
                return vegetarianMeal(userinput, id);
            case "Usage of Bike":
                return usageofBike(userinput);
            case "Usage of Public Transport":
                return usageofPublicTransport(userinput);
            case "Lower Temperature":
                return lowerTemperature(userinput, id);
            case "smoking":
                return smoking(userinput);
            case "recycling":
                return recycling(userinput);
            case "cfl":
                return cfl(userinput);
            default:
                return -1;
        }
    }


    /**
     * Returns the float from vegetarianMeal.
     *
     * @param inputfootprintshoppingfoodfruitvegetables the footprint
     * @return the result points
     * @throws SQLException raises error if unable to access database
     */
    public static float vegetarianMeal(String inputfootprintshoppingfoodfruitvegetables, int id)
            throws SQLException, UnirestException {


        vmmapping(inputfootprintshoppingfoodfruitvegetables, id);

        Map<String, String> params = new HashMap<>();
        params.put("accept", "application/json");
        params.put("app_id", "93af0470");
        params.put("app_key", "be1dbf535bd450c012e78261cf93c0ad");
        String url = createUrl();


        String holder = XML.toJSONObject(Unirest.get(url).headers(params).asString().getBody())
                .getJSONObject("response").get("result_food_fruitsveg").toString();


        float result = Float.parseFloat(holder);


        System.out.println(result);
        keysRemapping();
        return result;
    }

    /**
     * Returns the number of points of localProduct.
     *
     * @return points
     * @throws SQLException raises error if unable to access database
     */
    public static float localProduct() throws UnirestException {


        Map<String, String> params = getParams();

        String url = getUrl();


        String holder = XML.toJSONObject(Unirest.get(url).headers(params).asString()
                .getBody()).getJSONObject("response")
                .get("input_takeaction_go_organic_myprodcost").toString();

        float holderNum = Float.parseFloat(holder) * 1000;
        float result = holderNum / 3;
        System.out.println(result);

        keysRemapping();

        return result;
    }

    /**
     * Retrieve the number of points of UsageBike.
     *
     * @param km number of km
     * @return the points
     * @throws UnirestException raises error if unable to access database
     */
    public static float usageofBike(String km) throws UnirestException {


        Map<String, String> params = getParams();

        String url = getUrl();


        String holder = XML.toJSONObject(Unirest.get(url).headers(params)
                .asString().getBody()).getJSONObject("response")
                .get("result_takeaction_ride_my_bike_driveghgs").toString();


        float holderNum = Float.parseFloat(holder) * 1000;
        float result = holderNum / 365 * Integer.parseInt(km); //result in grams per day
        System.out.println(result);

        keysRemapping();
        return result;
    }

    /**
     * Retrieves the points of usageofPublicTransport.
     *
     * @param inputmiles the number of miles
     * @return the points
     */
    public static float usageofPublicTransport(String inputmiles) {


        float carC02 = 118; //average car c02 emissions upon https://www.delijn.be/en/overdelijn/organisatie/zorgzaam-ondernemen/milieu/co2-uitstoot-voertuigen.html

        float bus = 75; // average bus c02 emissions upon https://www.delijn.be/en/overdelijn/organisatie/zorgzaam-ondernemen/milieu/co2-uitstoot-voertuigen.html

        keysRemapping();
        return (carC02 - bus) * Float.parseFloat(inputmiles);
    }

    /**
     * Retrieves the points of lowerTemperature.
     *
     * @param inputfootprinthousingcdd the footprint
     * @return the points
     */
    public static float lowerTemperature(String inputfootprinthousingcdd,
                                         int id) throws SQLException, UnirestException {

        System.out.println("In lower temperature with input: " + inputfootprinthousingcdd);

        ltmapping(inputfootprinthousingcdd, id);

        Map<String, String> params = new HashMap<>();
        params.put("accept", "application/json");
        params.put("app_id", "93af0470");
        params.put("app_key", "be1dbf535bd450c012e78261cf93c0ad");
        String url = createUrlLT(inputfootprinthousingcdd);


        String holder = XML.toJSONObject(Unirest.get(url)
                .headers(params).asString().getBody()).getJSONObject("response")
                .get("result_takeaction_thermostat_summer_kwhuse").toString();
        keysLT = keys;

        float result = Float.parseFloat(holder) * 800;

        if (Integer.valueOf(inputfootprinthousingcdd) != result / 8)
            return Float.parseFloat(inputfootprinthousingcdd) * 8;
        System.out.println("The result is: " + result);
        keysRemapping();
        return result;
    }

    /**
     * Retrieves the points for solarPanels.
     *
     * @return the points
     * @throws SQLException raises error if unable to access database
     */
    public static float solarPanels(int id) throws SQLException, UnirestException {


        spmapping(id);
        Map<String, String> params = new HashMap<>();
        params.put("accept", "application/json");
        params.put("app_id", "93af0470");
        params.put("app_key", "be1dbf535bd450c012e78261cf93c0ad");
        String url = createUrl();


        String holder = XML.toJSONObject(Unirest
                .get(url).headers(params).asString().getBody())
                .getJSONObject("response").get("result_electricity_direct").toString();

        float result = Float.parseFloat(holder) * 1000;
        System.out.println(result / 15);
        keysRemapping();
        return result / 15;
    }

    public static void keysRemapping() {
        keys = keys2;
    }

    /**
     * Retrieves the points from recycling.
     *
     * @param kg the number of kg
     * @return the points
     */
    public static float recycling(String kg) {

        float carbon = Float.parseFloat("6"); //avg C02 consumption per kg of waste https://timeforchange.org/plastic-bags-and-plastic-bottles-CO2-emissions

        return carbon * Float.parseFloat(kg);
    }

    /**
     * Points for smoking.
     *
     * @param numberofCigarretes number of cigarretes
     * @return the points
     */
    public static float smoking(String numberofCigarretes) {


        float carbon = Float.parseFloat("0.6"); //avg C02 consumption per cigarretes upon http://palebluedot.llc/carbon-copy/2015/10/14/the-carbon-footprint-of-cigarettes


        return carbon * Float.parseFloat(numberofCigarretes);
    }

    /**
     * Points for cfl.
     *
     * @param numberOfcfl number of cfl
     * @return the points
     */
    public static float cfl(String numberOfcfl) {


        float carbon = Float.parseFloat("350"); //avg C02 consumption per led per day upon https://www.flickr.com/photos/carbonquilt/8229755738

        return carbon * Float.parseFloat(numberOfcfl);
    }

    /**
     * Requests for the API.
     *
     * @return the params
     */
    private static Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("accept", "application/json");
        params.put("app_id", "93af0470");
        params.put("app_key", "be1dbf535bd450c012e78261cf93c0ad");
        return params;
    }

    public static String getUrl() {
        return "https://apis.berkeley.edu/coolclimate/footprint-sandbox?input_location_mode=2&input_location=New%20York%2C%20NY%2C%20USA&input_income=1&input_size=0&input_footprint_transportation_miles1=13000&input_footprint_transportation_mpg1=6&input_footprint_transportation_fuel1=0&result_takeaction_meat_reduction=1";
    }


    private static String getLocation() {
        return "NY";
    }


    private static String getInputSize(int id) throws SQLException {
        Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));

        PreparedStatement insertData = conn.prepareStatement(Main.resource.getString("qInputSize"));
        insertData.setInt(1, id);
        ResultSet rs = insertData.executeQuery();

        int save = 1;
        while (rs.next()) {
            save = rs.getInt(1);
        }
        conn.close();
        return Integer.toString(save);

    }

    private static String getIncome(int id) throws SQLException {
        Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));

        PreparedStatement insertData = conn.prepareStatement(Main.resource.getString("qIncome"));
        insertData.setInt(1, id);
        ResultSet rs = insertData.executeQuery();

        int save = 0;
        while (rs.next()) {
            save = rs.getInt(1);
        }
        conn.close();
        return Integer.toString(save);
    }

    private static String getSquarefeet(int id) throws SQLException {
        Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));

        PreparedStatement insertData =
                conn.prepareStatement(Main.resource.getString("qSquarefeet"));
        insertData.setInt(1, id);
        ResultSet rs = insertData.executeQuery();

        int save = 0;
        while (rs.next()) {
            save = rs.getInt(1);
        }
conn.close();
        return Integer.toString(save);
    }

    private static String getElectrictyBill(int id) throws SQLException {
        Connection conn = DriverManager.getConnection(
                Main.resource.getString("Postgresql.datasource.url"),
                Main.resource.getString("Postgresql.datasource.username"),
                Main.resource.getString("Postgresql.datasource.password"));

        PreparedStatement insertData =
                conn.prepareStatement(Main.resource.getString("qElectricityBill"));
        insertData.setInt(1, id);
        ResultSet rs = insertData.executeQuery();

        int save = 0;
        while (rs.next()) {
            save = rs.getInt(1);
        }
        conn.close();
        return Integer.toString(save);
    }

    private static void vmmapping(String inputFootprintShoppingFoodFruitvegetables,
                                  int id) throws SQLException {
        calculateTotal(getInputSize(id), getIncome(id), getSquarefeet(id),
                getElectrictyBill(id), "0",
                inputFootprintShoppingFoodFruitvegetables, "0");
    }

    private static void ltmapping(String inputFootprintHousingCdd, int id) throws SQLException {
        calculateTotal(getInputSize(id), getIncome(id), getSquarefeet(id),
                getElectrictyBill(id), inputFootprintHousingCdd,
                "0", "0");
    }

    private static void spmapping(int id) throws SQLException {
        calculateTotal(getInputSize(id), getIncome(id), getSquarefeet(id),
                getElectrictyBill(id), "0",
                "0", getElectrictyBill(id));
    }


    private static void calculateTotal(String inputSize, String inputIncome,
                                       String inputFootprintHousingSquarefeet,
                                       String inputFootprintHousingElectricityDollars,
                                       String inputFootprintHousingCdd,
                                       String inputFootprintShoppingFoodFruitvegetables,
                                       String electrictyWhats) {

        keys[0] += "NY";// User inputs their zip code
        keys[1] += inputSize;
        keys[2] += inputSize;
        keys[3] += 0;
        keys[4] += inputIncome;
        keys[5] += inputFootprintHousingSquarefeet;
        keys[6] += inputFootprintHousingElectricityDollars;
        keys[7] += inputFootprintHousingCdd;
        keys[8] += "0";
        keys[9] += 30;
        keys[11] += inputFootprintShoppingFoodFruitvegetables;
        keys[10] += electrictyWhats;


    }

    private static String createUrlLT(String input) {
        StringBuilder result = new StringBuilder("https://apis.berkeley.edu/coolclimate/footprint?");

        for (int i = 0; i < keysLT.length; i++) {
            if (i == 7) result.append(keysLT[i]).append(input).append("&");

            result.append(keysLT[i]).append("&");


        }
        buildUrl(result, keys);

        for (int i = 0; i < takeActionKeys.length; i++) {
            if (i == takeActionKeys.length - 1) {
                result.append(takeActionKeys[i]);
                break;
            }

            result.append(takeActionKeys[i]).append("&");
        }

        return result.toString();

    }

    private static String createUrl() {
        StringBuilder result = new StringBuilder("https://apis.berkeley.edu/coolclimate/footprint?");

        for (String key : keys) {

            result.append(key).append("&");


        }
        for (String requiredKey : requiredKeys) {


            result.append(requiredKey).append("&");


        }

        for (int i = 0; i < takeActionKeys.length; i++) {
            if (i == takeActionKeys.length - 1) {
                result.append(takeActionKeys[i]);
                break;
            }

            result.append(takeActionKeys[i]).append("&");
        }

        return result.toString();

    }

    private static StringBuilder buildUrl(StringBuilder result, String[]... strings) {
        for (String[] k : strings) {
            for (String key : k) {

                result.append(key).append("&");


            }
        }
        return result;
    }

}
