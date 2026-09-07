package AgriPrice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import AgriPrice.model.PriceRecord;

@Service
public class PriceService {

    @Value("${data.gov.api.key}")
    private String apiKey;

    @Value("${data.gov.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<PriceRecord> getPrices(String selectedState, String selectedCommodity) {

        // State validation
        if (selectedState != null && !selectedState.isBlank()) {

            boolean validState =
                    selectedState.equalsIgnoreCase("Maharashtra")
                    || selectedState.equalsIgnoreCase("Gujarat")
                    || selectedState.equalsIgnoreCase("Karnataka");

            if (!validState) {
                throw new IllegalArgumentException(
                        "State not supported. Use Maharashtra, Gujarat or Karnataka."
                );
            }
        }

        String[] states = {
            "Maharashtra",
            "Gujarat",
            "Karnataka"
        };

        List<PriceRecord> allPrices = new ArrayList<>();

        for (String state : states) {

            // State filter
            if (selectedState != null
                    && !selectedState.isBlank()
                    && !selectedState.equalsIgnoreCase(state)) {
                continue;
            }

            String url = apiUrl
                    + "?api-key=" + apiKey
                    + "&format=json"
                    + "&limit=1000"
                    + "&filters[state]=" + state;

            System.out.println("Calling API for: " + state);

            ApiResponse response =
                    restTemplate.getForObject(url, ApiResponse.class);
            System.out.println(
            	    state + " API records: " +
            	    (response != null && response.getRecords() != null
            	        ? response.getRecords().size()
            	        : 0)
            	);

            if (response != null && response.getRecords() != null) {

                List<PriceRecord> records = response.getRecords();
                
                
                System.out.println(state + " commodities:");

                for (PriceRecord record : records) {
                    System.out.println(record.getCommodity());
                }
                // Commodity filter
                if (selectedCommodity != null
                        && !selectedCommodity.isBlank()) {

                    records = records.stream()
                            .filter(record ->
                                record.getCommodity() != null
                                && record.getCommodity()
                                    .equalsIgnoreCase(selectedCommodity)
                            )
                            .toList();
                }

                System.out.println(
                    state + " records after filtering: "
                    + records.size()
                );

                allPrices.addAll(records);

            } else {

                System.out.println(
                    state + " records: 0"
                );
            }
        }

        System.out.println(
            "Total records: "
            + allPrices.size()
        );

        return allPrices;
    }

    public static class ApiResponse {

        private List<PriceRecord> records;

        public List<PriceRecord> getRecords() {
            return records;
        }

        public void setRecords(List<PriceRecord> records) {
            this.records = records;
        }
    }
}