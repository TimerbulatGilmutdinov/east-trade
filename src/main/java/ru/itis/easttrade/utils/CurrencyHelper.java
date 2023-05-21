package ru.itis.easttrade.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import ru.itis.easttrade.exceptions.CurrencyApiException;

import java.io.IOException;

@Component
public class CurrencyHelper {
    private final String CURRENCY_API_URL = "https://www.cbr-xml-daily.ru/daily_json.js";
    private final String VALUTE_KEY = "Valute";
    private final String VALUE_KEY = "Value";

    public Double getCurrency(String currencyShortName) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(CURRENCY_API_URL)
                .header("Content-type","application/json;charset=UTF-8")
                .build();

        double value = 0;

        try {
            Response response = client.newCall(request).execute();

            String jsonString = response.body().string();
            Object obj = new JSONParser().parse(jsonString);
            JSONObject jo = (JSONObject) obj;

            JSONObject currenciesJson = (JSONObject) jo.get(VALUTE_KEY);
            JSONObject requestedCurrencyJson = (JSONObject) currenciesJson.get(currencyShortName);
            value = (Double) requestedCurrencyJson.get(VALUE_KEY);
        } catch (NullPointerException ex) {
            throw new CurrencyApiException("Invalid currency short name : " + currencyShortName);
        } catch (ParseException | IOException ex) {
            throw new CurrencyApiException(ex.getMessage());
        }
        return value;
    }
}
