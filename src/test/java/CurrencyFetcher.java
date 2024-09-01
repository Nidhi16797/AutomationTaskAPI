import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class CurrencyFetcher {

    @Test
    public void testCurrencyFetcher() {
        try {
            // Step 1: Get the list of currency abbreviations
            URL url = new URL("https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            // Parse the JSON response to get currency abbreviations
            ObjectMapper mapper = new ObjectMapper();
            JsonNode currencyNode = mapper.readTree(content.toString());

            // Extract currency abbreviations
            ArrayList<String> currencyAbbreviations = new ArrayList<>();
            Iterator<Map.Entry<String, JsonNode>> fields = currencyNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                currencyAbbreviations.add(field.getKey());
            }

            // Step 2: Iterate over the currency abbreviations
            for (String abbreviation : currencyAbbreviations) {
                // Fetch currency list for the current abbreviation
                String apiUrl = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/" + abbreviation + ".json";
                URL currencyUrl = new URL(apiUrl);
                HttpURLConnection currencyConnection = (HttpURLConnection) currencyUrl.openConnection();
                currencyConnection.setRequestMethod("GET");

                BufferedReader currencyIn = new BufferedReader(new InputStreamReader(currencyConnection.getInputStream()));
                StringBuilder currencyContent = new StringBuilder();
                while ((inputLine = currencyIn.readLine()) != null) {
                    currencyContent.append(inputLine);
                }
                currencyIn.close();
                currencyConnection.disconnect();

                // Parse the JSON response for the current currency abbreviation
                JsonNode currencyDetailNode = mapper.readTree(currencyContent.toString());
                System.out.println("Currency list for " + abbreviation + ":");
                System.out.println(currencyDetailNode.toPrettyString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


