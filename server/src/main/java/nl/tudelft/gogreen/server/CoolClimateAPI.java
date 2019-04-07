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
        VegetarianMeal();
        LocalProduct();
        UsageofBike();
        UsageofPublicTransport();
        LowerTemperature();
        SolarPanels();
        Recycling();
    }

   /* public static void getRandomData() throws Exception{
        Connection conn = DriverManager.getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));
        int[] inputs = new int[keys.length];
        inputs[0] = 3;
        inputs[1] = 50000;
        boolean[] actions = new boolean[takeActionKeys.length];
        for(int i = 0; i < takeActionKeys.length; i++){
            actions[i] = false;
        }
        Map<String, String> params = calculateFootprintParams(inputs, actions );
        params.put("accept", "application/json");
        params.put("app_id", "93af0470");
        params.put("app_key", "be1dbf535bd450c012e78261cf93c0ad");
        String url = "https://apis.berkeley.edu/coolclimate/footprint-sandbox?";

        String holder = Unirest.get(url).headers(params).asString().getBody();
        //String holder = XML.toJSONObject(Unirest.get(url2).headers(params).asString().getBody()).getJSONObject("response").toString();


        System.out.println(holder );
    }*/
    public static void VegetarianMeal() throws Exception {

        Connection conn = DriverManager.getConnection(
                resource.getString("Postgresql.datasource.url"),
                resource.getString("Postgresql.datasource.username"),
                resource.getString("Postgresql.datasource.password"));

        Map<String, String> params = new HashMap<>();
        params.put("accept", "application/json");
        params.put("app_id", "93af0470");
        params.put("app_key", "be1dbf535bd450c012e78261cf93c0ad");
        String url = "https://apis.berkeley.edu/coolclimate/footprint-sandbox?input_location_mode=2&input_location=New%20York%2C%20NY%2C%20USA&input_income=1&input_size=0&input_footprint_transportation_miles1=13000&input_footprint_transportation_mpg1=6&input_footprint_transportation_fuel1=0&result_takeaction_meat_reduction=1";


        String holder = XML.toJSONObject(Unirest.get(url).headers(params).asString().getBody()).getJSONObject("response").get("result_food_meat").toString();
        String holder1 = XML.toJSONObject(Unirest.get(url).headers(params).asString().getBody()).getJSONObject("response").get("result_food_fruitsveg").toString();

        System.out.println("value meat: " + holder + " and value vegies: " + holder1);
        float holderNum = Float.parseFloat(holder);
        float holder1Num = Float.parseFloat(holder1);
        float difference = (holderNum - holder1Num) * 1000 * 1000;
        float result = difference / 365 / 3;
        System.out.println(result);
        PreparedStatement insertAPI = conn.prepareStatement(resource.getString("qupdateVegetarianMeal"));
        insertAPI.setFloat(1, result);
        insertAPI.execute();
    }


    public static void LocalProduct() throws Exception {

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
    }

    public static void UsageofBike() throws Exception {

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
    }


    public static void UsageofPublicTransport() throws Exception {

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
    }

    public static void LowerTemperature() throws Exception {

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
    }


    public static void SolarPanels() throws Exception {

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

    }

    public static void Recycling() throws Exception {

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


   /* *//**
     * Filled in by user answering questions in the survey and applied to API. Questions receive user
     * information about their current zip code, how many people live in their household (adults,
     * children), annual income, square footage of home, yearly expenditures on each utility
     * (electricity, heating, water), how many days per year the household uses heat and cooling,
     * personal vehicle usage and mpg (up to 3 vehicles) and final questions about how many miles the
     * user acquires through air travel and public transportation.
     *//*
    private static String[] keys = {
            "input_size",
            "input_footprint_shopping_food_fruitvegetables_default"

    };

    *//*private static String[] keys = {
            "input_location",                               // User inputs their zip code
            "input_size",                            // The number of people that live in the user's house
            "input_footprint_household_adults",            // How many adults occupy the user's house
            "input_footprint_household_children",         // How many children occupy the user's house
            "input_income",                              // User inputs their income
            "input_footprint_housing_squarefeet",       // How large is the user's house
            "input_footprint_housing_electricity_dollars",
            "input_footprint_housing_naturalgas_dollars",
            "input_footprint_housing_heatingoil_dollars",
            "input_footprint_housing_watersewage",
            "input_footprint_housing_hdd",                // Days that the house is heated/year
            "input_footprint_housing_cdd",               // Days that the house is cooled/year
            "input_footprint_transportation_miles1",
            "input_footprint_transportation_mpg1",
            "input_footprint_transportation_miles2",
            "input_footprint_transportation_mpg2",
            "input_footprint_transportation_miles3",
            "input_footprint_transportation_mpg3",
            "input_footprint_transportation_airtotal",
            "input_footprint_transportation_publictrans"
    };*//*

    *//**
     * Implemented by the user selecting tasks that have been completed to reduce their annual carbon
     * footprint.These actions include switching to cfl (compact fluorescent light), maintaining
     * maintenance on vehicles, reducing air travel, telecommuting to work, reducing days using the
     * thermostat in winter and summer, riding their bike instead of driving, carpooling, and
     * practicing eco driving.
     *//*
    private static String[] takeActionKeys = {
            "input_takeaction_switch_to_cfl",
            "input_takeaction_maintain_my_vehicles",
            "input_takeaction_more_efficient_vehicle",
            "input_takeaction_reduce_air_travel",
            "input_takeaction_telecommute_to_work",
            "input_takeaction_thermostat_summer",
            "input_takeaction_offset_housing",
            "input_takeaction_energy_star_fridge",
            "input_takeaction_thermostat_winter",
            "input_takeaction_offset_transportation",
            "input_takeaction_ride_my_bike",
            "input_takeaction_purchase_green_electricity",
            "input_takeaction_take_public_transportation",
            "input_takeaction_carpool_to_work",
            "input_takeaction_practice_eco_driving",
    };

    *//**
     * Required keys needed for the API to properly function and are hard coded with values. These
     * keys are invisible to the user but the programmer has the option to add any of the keys to
     * {@link #keys} to receive user input.
     *//*
    private static String[] requiredKeys = {
            "input_location_mode=1",                                        // 1 = Zip code
            "input_changed=0",                                              // Meaningless variable
            "input_footprint_transportation_num_vehicles=0",  // Number of vehicles is assumed to be 3
            "input_footprint_transportation_fuel1=0",        //if 0mpg is input then that car gets deleted
            "input_footprint_transportation_fuel2=0",
            "input_footprint_transportation_fuel3=0",
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
            "input_footprint_shopping_food_fruitvegetables=0",     // Calories per day fruits and veggies
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
            "input_footprint_housing_gco2_per_kwh=1000"

    };


    public static Map<String, String> calculateFootprintParams(int[] inputValues,
                                                        boolean[] takeActionInputs) {

        Map<String, String> params = new HashMap<>();
        // Build Parameter request string
        for (int i = 0; i < inputValues.length; i++) {

            int curValue = inputValues[i];

            params.put(keys[i], String.valueOf(curValue));
        }

        //Adding state value default NY

        params.put("internal_state_abbreviation", "NY");

        //Add required keys with no value
        for (int i = 0; i < requiredKeys.length; i++) {
            params.put(requiredKeys[i], "");
        }
        // Add Take Action keys if they were passed into the function
        if (takeActionInputs != null) {
            String strValue;

            // Add Take Action keys
            for (int i = 0; i < takeActionKeys.length; i++) {
                strValue = takeActionInputs[i] ? "1"
                        : "0"; // This line sets strValue to "1" or "0" based on if takeActionInputs[i] is true or false
                params.put(takeActionKeys[i], "=" + strValue);
            }
        }

        return params;


    }*/

}
