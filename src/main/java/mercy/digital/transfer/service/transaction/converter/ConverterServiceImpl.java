package mercy.digital.transfer.service.transaction.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.service.transaction.CurrencyCode;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class ConverterServiceImpl implements ConverterService {

    private final String API_KEY = "fe62525f8c87ad20de58256179c42db4";

    public Double doExchange (CurrencyCode from, CurrencyCode to, Double amount) {
        double exchangeTargetAmount = 0;
        ObjectMapper mapper = new ObjectMapper();
        String url = "http://data.fixer.io/api/convert\n" +
                "    ? access_key = "+API_KEY+"\n" +
                "    & from = "+from.name()+"\n" +
                "    & to = "+to.name()+"\n" +
                "    & amount = "+amount;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            JsonNode jsonNode = mapper.readTree(con.getInputStream());
            exchangeTargetAmount = jsonNode.get("result").asDouble();
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            return 0.0;
        }
        return exchangeTargetAmount;
    }
}
