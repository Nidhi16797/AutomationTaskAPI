import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class CurrencyApiTest {
    private static final String CURRENCIES_URL = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies.json";
    @Test
    public void testCurrencyList() throws Exception {


        // Create an ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // Read JSON from the URL and convert it to a Map
        Map<String, Object> currencies = objectMapper.readValue(new URL(CURRENCIES_URL), Map.class);


        // Assert that the list has more than 20 items
//        Assert.assertTrue("Currency list should have more than 20 items", currencies.size() > 20);


        //Assert that "British Pound" and "United States Dollar" are in the list
        boolean hasBritishPound = currencies.containsKey("gbp");
        boolean hasUSDollar = currencies.containsKey("usd");

//        Assert.assertTrue("Currency list should contain British Pound (GBP)", hasBritishPound);
//        Assert.assertTrue("Currency list should contain United States Dollar (USD)", hasUSDollar);

        //Check how many elements does API returns, save all abbreviations to an ArrayList.
        ArrayList<String> currencyAbbreviations = new ArrayList<>(currencies.keySet());
//        System.out.println("Number of currencies: " + currencyAbbreviations.size());
//        System.out.println("Currency abbreviations: " + currencyAbbreviations);


        //Iterate over array list, while getting currency list for all currencies:
        //https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/${VariableFromArraylist}

        /*for (String abbreviation : currencyAbbreviations) {

            String APIURL = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/" + abbreviation + ".json";

            ObjectMapper objectMapper1 = new ObjectMapper();

            // Read JSON from the URL and convert it to a Map
            Map<String, Object> currenciesMap = objectMapper1.readValue(new URL(APIURL), Map.class);
            JsonNode currencyDetailNode = objectMapper1.readTree(currenciesMap.toString());
            System.out.println("Currency list for " + abbreviation + ":");
            System.out.println(currencyDetailNode.toString());
        }*/


    }
}

