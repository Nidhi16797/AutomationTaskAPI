import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class CurrencyFetcher2 {

    private static final String BASE_URL = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies.json";

    @Test
    public void testCurrencyExchangePairs() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Fetch and parse the JSON directly from the URL
            JsonNode currencyNode = mapper.readTree(new URL(BASE_URL));

            // Extract currency abbreviations
            ArrayList<String> currencyAbbreviations = new ArrayList<>();
            Iterator<Map.Entry<String, JsonNode>> fields = currencyNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                currencyAbbreviations.add(field.getKey());
            }

            // Variable to store the number of currency pairs for the first currency
            Integer initialCurrencyPairCount = null;

            // Step 2: Iterate over the currency abbreviations
            for (int i = 0; i < currencyAbbreviations.size(); i++) {
                String abbreviation = currencyAbbreviations.get(i);

                // Fetch currency list for the current abbreviation
                String apiUrl = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/" + abbreviation + ".json";
                JsonNode currencyDetailNode = mapper.readTree(new URL(apiUrl));
                int currencyPairCount = currencyDetailNode.size(); // Number of currency pairs

                // On the first iteration, save the number of currency pairs
                if (i == 0) {
                    initialCurrencyPairCount = currencyPairCount;
                    System.out.println("Initial currency pair count: " + initialCurrencyPairCount);
                } else {
                    // On subsequent iterations, assert that the number of currency pairs is the same
                    Assert.assertEquals("Currency pair count mismatch for " + abbreviation +
                                    ". Expected " + initialCurrencyPairCount + ", but got " + currencyPairCount,
                            initialCurrencyPairCount, Integer.valueOf(currencyPairCount));
                }
            }

            System.out.println("All currencies have the same number of currency pairs.");

        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Test failed due to an exception: " + e.getMessage());
        }
    }
}

