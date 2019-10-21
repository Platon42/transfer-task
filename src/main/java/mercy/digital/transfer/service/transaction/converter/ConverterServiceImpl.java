package mercy.digital.transfer.service.transaction.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class ConverterServiceImpl implements ConverterService {

    private final String API_KEY = "ba274ae507e40659ed68dd2186ecd410";

    public Double doExchange (CurrencyCode from, CurrencyCode to, Double amount) {
        double exchangeTargetAmount = 0;
        ObjectMapper mapper = new ObjectMapper();
        String url = "http://data.fixer.io/api/convert" +
                "?access_key=" + API_KEY +
                "&from=" + from.name() +
                "&to=" + to.name() +
                "&amount=" + amount;

        System.out.println(url);

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            JsonNode jsonNode = mapper.readTree(con.getInputStream());
            System.out.println(jsonNode);
            exchangeTargetAmount = jsonNode.get("result").asDouble();
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            return 0.0;
        }
        return exchangeTargetAmount;
    }
}
