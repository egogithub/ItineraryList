package com.worldline.ego.itinerarylist.helpers;

import android.util.Log;
import android.util.Xml;

import com.worldline.ego.itinerarylist.pojo.Itinerary;

import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.List;
import java.net.Proxy;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by a143210 on 28/09/2016.
 */

public class ItineraryHelper {
    // Proxy stuff
    private static final boolean USE_PROXY = false;
    private static final String PROXY_HOST = "bcproxynew.extsec.banksys.be";
    private static final int PROXY_PORT = 8080;
    private static final boolean USE_PROXY_AUTHENTICATION = true;
    private static final String PROXY_USERNAME = "training10";
    private static final String PROXY_PASSWORD = "Student10/";

    private static final String URL_STREAM_GET_ITINERARY = "http://m.stib.be/api/getitinerary.php?1=1";
    /* retrieve itinerary from the host */
    public static List<ItineraryStop> getItinerary(String lineNumber, String direction) {
        String mode="B";
        String lang="fr";
        String rnd="399527036";
        String URL_STREAM=URL_STREAM_GET_ITINERARY+"&line="+lineNumber+"&mode="+mode+"&iti="+direction+"&lang="+lang+"&rnd="+rnd;
        try {
            final HttpURLConnection connection = getHTTPUrlConnection(URL_STREAM);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Host", "m.stib.be");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Accept-Encoding", "gzip");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("User-Agent", "okhttp/3.4.1");
            connection.setRequestProperty("Content-Type", "application/json");

            final int responseCode = connection.getResponseCode();

            if(responseCode == 200) { //OK
                String type = connection.getContentType();
/*
                BufferedReader inputReader = new BufferedReader(new InputStreamReader( new GZIPInputStream(connection.getInputStream()), "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String inline;
                while ((inline = inputReader.readLine()) != null) {
                    sb.append(inline);
                }
                Log.d("Itinerary", "Type= "+ type);
                Log.d("Itinerary", "Content-Encoding: "+connection.getContentEncoding());
                Log.d("Itinerary", sb.toString());
*/
                return ItineraryXmlParser.parse(new GZIPInputStream(connection.getInputStream()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static HttpURLConnection getHTTPUrlConnection(String url) throws Exception {
        if (USE_PROXY) {
            final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_HOST, PROXY_PORT));

            if (USE_PROXY_AUTHENTICATION){
                Authenticator authenticator = new Authenticator() {
                    public PasswordAuthentication getPasswordAuthentication() {
                        return (new PasswordAuthentication(PROXY_USERNAME, PROXY_PASSWORD.toCharArray()));
                    }
                };
                Authenticator.setDefault(authenticator);
            }
            return (HttpURLConnection) new URL(url).openConnection(proxy);
        }
        else {
            return(HttpURLConnection) new URL(url).openConnection();
        }
    }
}
