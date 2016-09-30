package com.worldline.ego.itinerarylist.helpers;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by a143210 on 28/09/2016.
 */

public class ItineraryXmlParser {
    private static final String ns = null;
    private final static String msg = "ItineraryXmlParser";

    public static List<ItineraryStop> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            while(parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                parser.nextTag();
                String name = parser.getName();
                if (name.equals("stops")) {
                    Log.d(msg, "stops tag found");
                    return readStops(parser);
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List readStops(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "stops");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("stop")) {
                entries.add(readStop(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private static ItineraryStop readStop(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "stop");
        int id = 0;
        String stopName = null;
        Boolean present = false;
        float latitude = 0;
        float longitude = 0;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("id")) {
                id = readIntVal(parser, "id");
            } else if (name.equals("name")) {
                stopName = readTextVal(parser, "name");
            } else if (name.equals("latitude")) {
                latitude = readFloatVal(parser, "latitude");
            } else if (name.equals("longitude")) {
                longitude = readFloatVal(parser, "longitude");
            } else if (name.equals("present")) {
                present = readBooleanVal(parser, "present");
            } else {
                skip(parser);
            }
        }
        return new ItineraryStop(id,stopName,latitude, longitude, present);
    }

    private static int readIntVal(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
        int result = 0;
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String strResult = readText(parser);
        result = Integer.parseInt(strResult);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return result;
    }

    private static String readTextVal(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String result = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return result;
    }

    private static Float readFloatVal(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
        float result = 0;
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String strResult = readText(parser);
        result = Float.parseFloat(strResult);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return result;
    }

    private static Boolean readBooleanVal(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
        Boolean result = false;
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String strResult = readText(parser);
        if (strResult.equals("TRUE")) {
            result = true;
        }
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return result;
    }

    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}

