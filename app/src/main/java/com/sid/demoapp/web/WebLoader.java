package com.sid.demoapp.web;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Okis on 2016.08.21 @ 09:34.
 */

public class WebLoader {
    public static final String WEB_URL = "http://www.wiki.com/";

    public String loadDataFromWeb() {
        String data = "";
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) new URL(WEB_URL).openConnection();
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(10000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            final InputStream in = new BufferedInputStream(connection.getInputStream());
            data = readStream(in);

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return data;
    }

    private String readStream(InputStream in) {
        StringBuffer data = new StringBuffer("");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return data.toString();
    }
}
