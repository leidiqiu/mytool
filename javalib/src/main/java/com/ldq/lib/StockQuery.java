package com.ldq.lib;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文档：https://www.alphavantage.co/documentation/
 * <p>
 * Access Key：2ZYUUZANHGKU53N9
 */
public class StockQuery {

    //    private static final String QUERY_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&datatype=json&symbol='3690.HK'&apikey=2ZYUUZANHGKU53N9";
    private static final String QUERY_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=IBM&apikey=demo";

    public static void main(String[] args) {
        try {
            URL url = new URL(QUERY_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String response = sb.toString();
                System.out.println(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
