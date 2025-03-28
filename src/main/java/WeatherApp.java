import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


/* Retrieve weather data from API - this backend logic will fetch the latest weather
data from the external API and return it. The GUI will display the data to the user. */

public class WeatherApp {
    public static JSONObject getWeatherData (String locationName) {
        JSONArray locationData = getLocationData(locationName);

        //get latitude and longitude data
        JSONObject location = (JSONObject) locationData.get(0);
        double latitude = (double) location.get("latitude");
        double longitude = (double) location.get("longitude");

        //build API request wth location coordinates
        String urlString = "https://api.open-meteo.com/v1/forecast?latitude="+
                latitude + "&longitude=" +
                longitude + "&hourly=temperature_2m,weather_code,wind_speed_10m,relative_humidity_2m";

        try {
            //Call API and get response
            HttpURLConnection conn = fetchApiResponse(urlString);

            //check response status code
            if (conn.getResponseCode() !=200) {
                System.out.println("Error: Could not connect to API");
            }

            //store result json data
            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());

            while (scanner.hasNext()) {
                //read and store
                resultJson.append(scanner.nextLine());
            }
            scanner.close();
            conn.disconnect();

            //parse through the data
            JSONParser parser = new JSONParser();
            JSONObject resultJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

            //get hourly data
            JSONObject hourly = (JSONObject) resultJsonObj.get("hourly");

            //get current hour
            JSONArray time = (JSONArray) hourly.get("time");
            int index = findIndexOfCurrentTime(time);

            //get temperature
            JSONArray temperatureData = (JSONArray) hourly.get("temperature_2m");
            double temperature = (double) temperatureData.get(index);

            //get weather code
            JSONArray weatherCode = (JSONArray) hourly.get("weather_code");
            String weatherCondition = convertWeatherCode((long) weatherCode.get(index));

            //get humidity
            JSONArray relativeHumdity = (JSONArray) hourly.get("relative_humidity_2m");
            long humidity = (long) relativeHumdity.get(index);

            //get windspeed
            JSONArray windspeedData = (JSONArray) hourly.get("wind_speed_10m");
            double windspeed = (double) windspeedData.get(index);

            //build weather json data object to access in the frontend
            JSONObject weatherData = new JSONObject();
            weatherData.put("temperature", temperature);
            weatherData.put("weather_condition", weatherCondition);
            weatherData.put("humidity", humidity);
            weatherData.put("windspeed", windspeed);

            return weatherData;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static JSONArray getLocationData(String locationName) {
        //replace space to "+" to use the API
        locationName = locationName.replaceAll(" ", "+");

        //build API url with location parameter
        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" +
        locationName + "&count=10&language=en&format=json";

        try {
            //call API and get a response
            HttpURLConnection conn = fetchApiResponse(urlString);

            //check response status code
            if (conn.getResponseCode()!= 200) {
                System.out.println("Error, could not connect to the API");
                return null;
            } else {
                //store the API result
                StringBuilder resultJson = new StringBuilder();
                //use scanner to read the json data that is returned from the API
                Scanner scanner = new Scanner(conn.getInputStream());

                //read and store data to stringbuilder
                while(scanner.hasNext()) {
                    resultJson.append(scanner.nextLine());
                }
                scanner.close();
                conn.disconnect();

                //parse json string into json object
                JSONParser parser = new JSONParser();
                JSONObject resultsJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

                //get the list of location data the API generated from the location name
                JSONArray locationData = (JSONArray) resultsJsonObj.get("results");
                return locationData;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //couldn't find location
        return null;
    }

    private static HttpURLConnection fetchApiResponse(String urlString) {
        try {
            //attempt to create connection
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //set request method to get
            conn.setRequestMethod("GET");

            //connect to the API
            conn.connect();
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }

        //couldnt make a connection
        return null;
    }

    private static int findIndexOfCurrentTime(JSONArray timeList) {
        String currentTime = getCurrentTime();
        for (int i = 0; i<timeList.size(); i++) {
            String time = (String) timeList.get(i);
            if(time.equalsIgnoreCase(currentTime)) {
                return i;
            }
        }
        return 0;
    }
    public static String getCurrentTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        //set output format to be 2000-01-13T00:00 (this is how it is read in the  API)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");
        String formattedDateTime = formatter.format(currentDateTime);
        return formattedDateTime;
    }
    //convert weather code to something readable
    private static String convertWeatherCode(long weathercode) {
        String weatherCondition = "";
        switch ((int) weathercode) {
            case 0:
                weatherCondition = "Clear";
                break;
            case 1:
                weatherCondition = "Mainly Clear";
                break;
            case 2:
                weatherCondition = "Partly Cloudy";
                break;
            case 3:
                weatherCondition = "Overcast";
                break;
            case 45:
                weatherCondition = "Fog";
                break;
            case 48:
                weatherCondition = "Depositing Rime Fog";
                break;
            case 51:
                weatherCondition = "Light Drizzle";
                break;
            case 53:
                weatherCondition = "Moderate Drizzle";
                break;
            case 55:
                weatherCondition = "Dense Drizzle";
                break;
            case 56:
                weatherCondition = "Light Freezing Drizzle";
                break;
            case 57:
                weatherCondition = "Dense Freezing Drizzle";
                break;
            case 61:
                weatherCondition = "Slight Rain";
                break;
            case 63:
                weatherCondition = "Moderate Rain";
                break;
            case 65:
                weatherCondition = "Heavy Rain";
                break;
            case 66:
                weatherCondition = "Light Freezing Rain";
                break;
            case 67:
                weatherCondition = "Heavy Freezing Rain";
                break;
            case 71:
                weatherCondition = "Slight Snow Fall";
                break;
            case 73:
                weatherCondition = "Moderate Snow Fall";
                break;
            case 75:
                weatherCondition = "Heavy Snow Fall";
                break;
            case 77:
                weatherCondition = "Snow Grains";
                break;
            case 80:
                weatherCondition = "Slight Rain Showers";
                break;
            case 81:
                weatherCondition = "Moderate Rain Showers";
                break;
            case 82:
                weatherCondition = "Violent Rain Showers";
                break;
            case 85:
                weatherCondition = "Slight Snow Showers";
                break;
            case 86:
                weatherCondition = "Heavy Snow Showers";
                break;
            case 95:
                weatherCondition = "Thunderstorm";
                break;
            case 96:
                weatherCondition = "Thunderstorm With Slight Hail";
                break;
            case 99:
                weatherCondition = "Thunderstorm With Heavy Hail";
                break;
            default:
                weatherCondition = "Error";

        }
        return weatherCondition;
    }
}
