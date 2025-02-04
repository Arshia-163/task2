import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherApp {
    private static final String API_KEY = "818e72ff3dfaa3b257d747b5b9f933b2"; 
    private static final String CITY = "Delhi";
    private static final String URL_STRING = "https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&appid=" + API_KEY + "&units=metric";

    public static void main(String[] args) {
        try {
            String jsonResponse = getWeatherData(URL_STRING);
            parseAndDisplayWeather(jsonResponse);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static String getWeatherData(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HTTP GET Request Failed with Error Code: " + responseCode);
        }
        
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        
        return response.toString();
    }

    private static void parseAndDisplayWeather(String jsonResponse) {
        JSONObject jsonObj = new JSONObject(jsonResponse);
        String city = jsonObj.getString("name");
        JSONObject main = jsonObj.getJSONObject("main");
        double temperature = main.getDouble("temp");
        int humidity = main.getInt("humidity");
        JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);
        String description = weather.getString("description");
        
        System.out.println("Weather in " + city + ":");
        System.out.println("Temperature: " + temperature + "°C");
        System.out.println("Humidity: " + humidity + "%");
        System.out.println("Condition: " + description);
    }
}
