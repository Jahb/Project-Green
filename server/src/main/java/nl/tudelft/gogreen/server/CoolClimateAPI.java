package nl.tudelft.gogreen.server;

import com.mashape.unirest.http.Unirest;
import org.json.XML;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CoolClimateAPI {

    private static ResourceBundle resource = ResourceBundle.getBundle("db");

    public static void fetchApiData() throws Exception {
        //VegetarianMeal();
        LocalProduct();
        UsageofBike();
        UsageofPublicTransport();
        LowerTemperature();
        SolarPanels();
        Recycling();
    }

    public static void getRandomData() throws Exception {
        Connection conn = DriverManager.getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));



        Map<String, String> params = new HashMap<>();
        params.put("accept", "application/json");
        params.put("app_id", "93af0470");
        params.put("app_key", "be1dbf535bd450c012e78261cf93c0ad");
        String url = createUrl();


        String holder = XML.toJSONObject(Unirest.get(url).headers(params).asString().getBody()).getJSONObject("response").toString();

        System.out.println(holder);
    }

    public static float VegetarianMeal(String input_footprint_shopping_food_fruitvegetables) throws Exception {

        Connection conn = DriverManager.getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));
        VMmapping(input_footprint_shopping_food_fruitvegetables);
        Map<String, String> params = new HashMap<>();
        params.put("accept", "application/json");
        params.put("app_id", "93af0470");
        params.put("app_key", "be1dbf535bd450c012e78261cf93c0ad");
        String url = createUrl();


        String holder = XML.toJSONObject(Unirest.get(url).headers(params).asString().getBody()).getJSONObject("response").get("result_food_fruitsveg").toString();


        System.out.println(holder);
        float holderNum = Float.parseFloat(holder);

        float result = holderNum / 365 / 3;
        System.out.println(result);
        PreparedStatement insertAPI = conn.prepareStatement(resource.getString("qupdateVegetarianMeal"));
        insertAPI.setFloat(1, result);
        insertAPI.execute();

        return result;
    }


    public static float LocalProduct() throws Exception {

        Connection conn = DriverManager.getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

        Map<String, String> params = getParams();

        String url = getUrl();


        String holder = XML.toJSONObject(Unirest.get(url).headers(params).asString().getBody()).getJSONObject("response").get("input_takeaction_go_organic_myprodcost").toString();


        float holderNum = Float.parseFloat(holder) * 1000 * 1000;
        float result = holderNum / 365 / 3;
        System.out.println(result);
        PreparedStatement insertAPI = conn.prepareStatement(resource.getString("qupdateLocalProduct"));
        insertAPI.setFloat(1, result);
        insertAPI.execute();

        return result;
    }

    public static float UsageofBike() throws Exception {

        Connection conn = DriverManager.getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

        Map<String, String> params = getParams();

        String url = getUrl();


        String holder = XML.toJSONObject(Unirest.get(url).headers(params).asString().getBody()).getJSONObject("response").get("result_takeaction_ride_my_bike_driveghgs").toString();


        float holderNum = Float.parseFloat(holder) * 1000 * 1000;
        float result = holderNum / 365; //result in grams per day
        System.out.println(result);

        PreparedStatement insertAPI = conn.prepareStatement(resource.getString("qupdateUsageofBike"));
        insertAPI.setFloat(1, result);
        insertAPI.execute();

        return result;
    }


    public static float UsageofPublicTransport() throws Exception {

        Connection conn = DriverManager.getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

        Map<String, String> params = getParams();

        String url = getUrl();


        String holder = XML.toJSONObject(Unirest.get(url).headers(params).asString().getBody()).getJSONObject("response").get("result_transport_total").toString();
        String holder1 = XML.toJSONObject(Unirest.get(url).headers(params).asString().getBody()).getJSONObject("response").get("input_takeaction_take_public_transportation_gco2bus").toString();

        float holderNum = Float.parseFloat(holder) * 1000 * 1000 / 365 / 100;
        float holderNum1 = Float.parseFloat(holder) * 6;
        float result = holderNum - holderNum1; //result in grams per day
        System.out.println("the total is: " + holderNum + " and the public transport one: " + holderNum1 + " and the result is: " + result);

        PreparedStatement insertAPI = conn.prepareStatement(resource.getString("qupdateUsageofPublicTransport"));
        insertAPI.setFloat(1, result);
        insertAPI.execute();

        return result;
    }

    public static float LowerTemperature() throws Exception {

        Connection conn = DriverManager.getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

        Map<String, String> params = getParams();

        String url = getUrl();


        String holder = XML.toJSONObject(Unirest.get(url).headers(params).asString().getBody()).getJSONObject("response").get("input_footprint_housing_electricity_kwh").toString();

        float holderNum = Float.parseFloat(holder) / 365; //grams saved per each kwH by lowering temperature
        float save = 911 / 10 / 30; //grams of C02 saved by lowering temperature per day
        float result = holderNum * save; //result in grams per day
        System.out.println(result);
        PreparedStatement insertAPI = conn.prepareStatement(resource.getString("qupdateLowerTemperature"));
        insertAPI.setFloat(1, result);
        insertAPI.execute();

        return result;
    }


    public static float SolarPanels() throws Exception {

        Connection conn = DriverManager.getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

        Map<String, String> params = getParams();

        String url = getUrl();


        String holder = XML.toJSONObject(Unirest.get(url).headers(params).asString().getBody()).getJSONObject("response").get("result_takeaction_purchase_green_electricity_kwhCO2yr").toString();

        float holderNum = Float.parseFloat(holder) * 1000 * 1000; //transform from tones to grams
        float result = holderNum / 365; //transform from yearly to daily

        PreparedStatement insertAPI = conn.prepareStatement(resource.getString("qupdateSolarPanels"));
        insertAPI.setFloat(1, result);
        insertAPI.execute();

        return result;
    }

    public static float Recycling() throws Exception {

        Connection conn = DriverManager.getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

        Map<String, String> params = getParams();

        String url = getUrl();


        String holder = XML.toJSONObject(Unirest.get(url).headers(params).asString().getBody()).getJSONObject("response").get("result_watersewage").toString();

        float holderNum = Float.parseFloat(holder) * 1000 * 1000; //transform from tones to grams
        float result = holderNum / 365 / 3; //transform from yearly to daily
        System.out.println(result);
        PreparedStatement insertAPI = conn.prepareStatement(resource.getString("qupdateRecycling"));
        insertAPI.setFloat(1, result);
        insertAPI.execute();

        return result;
    }

    public static Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("accept", "application/json");
        params.put("app_id", "93af0470");
        params.put("app_key", "be1dbf535bd450c012e78261cf93c0ad");
        return params;
    }

    public static String getUrl() {
        return "https://apis.berkeley.edu/coolclimate/footprint-sandbox?input_location_mode=2&input_location=New%20York%2C%20NY%2C%20USA&input_income=1&input_size=0&input_footprint_transportation_miles1=13000&input_footprint_transportation_mpg1=6&input_footprint_transportation_fuel1=0&result_takeaction_meat_reduction=1";
    }

    /**
     * Filled in by user answering questions in the survey and applied to API. Questions receive user
     * information about their current zip code, how many people live in their household (adults,
     * children), annual income, square footage of home, yearly expenditures on each utility
     * (electricity, heating, water), how many days per year the household uses heat and cooling,
     * personal vehicle usage and mpg (up to 3 vehicles) and final questions about how many miles the
     * user acquires through air travel and public transportation.
     */


    private static String[] keys = {
            "input_location=",                               // User inputs their zip code
            "input_size=",                            // The number of people that live in the user's house
            "input_footprint_household_adults=",            // How many adults occupy the user's house
            "input_footprint_household_children=",
            "input_income=",                              // User inputs their income
            "input_footprint_housing_squarefeet=",       // How large is the user's house
            "input_footprint_housing_electricity_dollars=",
            "input_footprint_housing_watersewage=",
            "input_footprint_housing_cdd=",               // Days that the house is cooled/year
            "input_footprint_transportation_miles1=",
            "input_footprint_transportation_mpg1=",
            "input_footprint_transportation_miles2=",
            "input_footprint_transportation_mpg2=",
            "input_footprint_transportation_miles3=",
            "input_footprint_transportation_mpg3=",
            "input_footprint_transportation_airtotal=",
            "input_footprint_transportation_publictrans=",
            "input_footprint_shopping_food_fruitvegetables="


    };

    /**
     * Implemented by the user selecting tasks that have been completed to reduce their annual carbon
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
            "input_takeaction_take_public_transportation=0",
            "input_takeaction_carpool_to_work=0",
            "input_takeaction_practice_eco_driving=0",
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
            "input_footprint_transportation_num_vehicles=3",  // Number of vehicles is assumed to be 3
            "input_footprint_transportation_fuel1=1",        //if 0mpg is input then that car gets deleted
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
            "input_footprint_transportation_groundtype=simple", // Total miles for public transportation
            "input_footprint_housing_electricity_type=0",                   // In $/year
            "input_footprint_housing_cleanpercent=0",                       // Assume no clean energy
            "input_footprint_housing_naturalgas_type=0",                    // In $/year
            "input_footprint_housing_heatingoil_type=0",                    // In $/year
            "input_footprint_housing_heatingoil_dollars_per_gallon=4", //Average, heating oil is $4/gallon
            "input_footprint_shopping_food_meattype=simple",           // Meat consumed by the user
            "input_footprint_shopping_food_meatfisheggs=0", // Calories eaten daily of meat, fish and eggs
            "input_footprint_shopping_food_dairy=0",                      // Calories eaten daily of dairy
            "input_footprint_shopping_food_otherfood=0",                    // Calories per day other food
            "input_footprint_shopping_food_cereals=0",                      // Calories per day cereals
            "input_footprint_shopping_goods_default_furnitureappliances=0",// Furniture/appliances cost/yr
            "input_footprint_shopping_goods_default_clothing=0",            // Clothing cost/year
            "input_footprint_shopping_goods_default_other_entertainment=0", // Entertainment cost/year
            "input_footprint_shopping_goods_default_other_office=0",        // Office supplies cost/year
            "input_footprint_shopping_goods_default_other_personalcare=0",  // Personal care cost/year
            "input_footprint_shopping_goods_default_other_autoparts=0",     // Auto cost/year
            "input_footprint_shopping_goods_default_other_medical=0",
            "input_footprint_shopping_goods_type=advanced",
            "input_footprint_shopping_goods_total=0",        // No input by the user, sum of the subtotals
            "input_footprint_shopping_services_type=simple",
            "input_footprint_shopping_services_total=0", // How much the user spends on services per year
            "input_footprint_housing_gco2_per_kwh=1000",
            "input_footprint_housing_naturalgas_dollars=0",
            "input_footprint_housing_heatingoil_dollars=0",
            "input_footprint_housing_hdd=0"



    };
    public static String getLocation(){
        return "NY";
    }
    public static String getInputSize(){
        return "2";
    }
    public static String getIncome(){
        return "100";
    }
    public static String getSquarefeet(){
        return "10";
    }
    public static String getElectrictyBill(){
        return "10";
    }


    public static void VMmapping(String input_footprint_shopping_food_fruitvegetables){
        calculateTotal(getLocation(),getInputSize(),getIncome(), getSquarefeet(),
                getElectrictyBill(),"0" , "0",
                "0","0",
                input_footprint_shopping_food_fruitvegetables);
    }


    public static void calculateTotal(String location, String inputSize, String input_income,
                                      String input_footprint_housing_squarefeet, String input_footprint_housing_electricity_dollars, String input_footprint_housing_cdd,
                                      String input_footprint_transportation_miles1, String input_footprint_transportation_airtotal,
                                      String input_footprint_transportation_publictrans, String input_footprint_shopping_food_fruitvegetables) {

        keys[0] += location;// User inputs their zip code
        keys[1] += inputSize;
        keys[2] += inputSize;
        keys[3] += 0;
        keys[4] += input_income;
        keys[5] += input_footprint_housing_squarefeet;
        keys[6] += input_footprint_housing_electricity_dollars;
        keys[7] += 0;
        keys[8] += input_footprint_housing_cdd;
        keys[9] += input_footprint_transportation_miles1;
        keys[10] += 30;
        keys[11] += 0;
        keys[12] += 0;
        keys[13] += 0;
        keys[14] += 0;
        keys[15] += input_footprint_transportation_airtotal;
        keys[16] += input_footprint_transportation_publictrans;
        keys[17] += input_footprint_shopping_food_fruitvegetables;



    }

    public static String createUrl() {
        String result = "https://apis.berkeley.edu/coolclimate/footprint?";
        for (int i = 0; i < keys.length; i++) {

            result += keys[i] + "&";


        }
        for (int i = 0; i < requiredKeys.length; i++) {


            result += requiredKeys[i] + "&";


        }

        for(int i = 0; i < takeActionKeys.length; i++){
            if (i == takeActionKeys.length - 1) result += takeActionKeys[i];

            result += takeActionKeys[i] + "&";
        }
        return result;
    }


}
