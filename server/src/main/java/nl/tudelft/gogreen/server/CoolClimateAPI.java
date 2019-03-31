package nl.tudelft.gogreen.server;

import com.mashape.unirest.http.Unirest;
import org.json.XML;

import java.util.HashMap;
import java.util.Map;

public class CoolClimateAPI {

    public static float VegetarianMeal() {

        try {
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
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public static float LocalProduct() {

        try {
            Map<String, String> params = getParams();

            String url = getUrl();


            String holder = XML.toJSONObject(Unirest.get(url).headers(params).asString().getBody()).getJSONObject("response").get("input_takeaction_go_organic_myprodcost").toString();


            float holderNum = Float.parseFloat(holder) * 1000 * 1000;
            float result = holderNum / 365 / 3;
            System.out.println(result);
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public static float UsageofBike() {

        try {
            Map<String, String> params = getParams();

            String url = getUrl();


            String holder = XML.toJSONObject(Unirest.get(url).headers(params).asString().getBody()).getJSONObject("response").get("result_takeaction_ride_my_bike_driveghgs").toString();


            float holderNum = Float.parseFloat(holder) * 1000 * 1000;
            float result = holderNum / 365; //result in grams per day
            System.out.println(result);
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public static float UsageofPublicTransport() {

        try {
            Map<String, String> params = getParams();

            String url = getUrl();


            String holder = XML.toJSONObject(Unirest.get(url).headers(params).asString().getBody()).getJSONObject("response").get("result_transport_total").toString();
            String holder1 = XML.toJSONObject(Unirest.get(url).headers(params).asString().getBody()).getJSONObject("response").get("input_takeaction_take_public_transportation_gco2bus").toString();


            float holderNum = Float.parseFloat(holder) * 1000 * 1000 / 365 /100;
            float holderNum1 = Float.parseFloat(holder) * 6;
            float result = holderNum - holderNum1; //result in grams per day
            System.out.println("the total is: " + holderNum + " and the public transport one: " + holderNum1 + " and the result is: " + result);
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public static float LowerTemperature() {

        try {
            Map<String, String> params = getParams();

            String url = getUrl();


            String holder = XML.toJSONObject(Unirest.get(url).headers(params).asString().getBody()).getJSONObject("response").get("input_footprint_housing_electricity_kwh").toString();

            float holderNum = Float.parseFloat(holder)/365; //grams saved per each kwH by lowering temperature
            float save = 911/10/30; //grams of C02 saved by lowering temperature per day
            float result = holderNum * save ; //result in grams per day
            System.out.println(result);
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return -1;
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

}
