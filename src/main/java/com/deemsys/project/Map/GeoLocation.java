package com.deemsys.project.Map;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deemsys.project.common.InjuryProperties;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Service
public class GeoLocation {

	@Autowired
	InjuryProperties injuryProperties;
	
	// Get Lat long of Particular Address
	public String getLocation(String address) {
		String outputJSON = null;
		String latLang = null;
		System.out.println(address);
		// Google Map
		String gURL = "https://maps.googleapis.com/maps/api/geocode/json";
		try {

			URL url = new URL(
					gURL
							+ "?address="
							+ URLEncoder.encode(address, "UTF-8")
							+ "&sensor=false&key="+injuryProperties.getProperty("reportDownloadAPIKey"));

			// Open the Connection
			URLConnection conn = url.openConnection();

			// This is Simple a byte array output stream that we will use to
			// keep the output data from google.
			ByteArrayOutputStream output = new ByteArrayOutputStream(1024);

			// copying the output data from Google which will be either in JSON
			// or XML depending on your request URL that in which format you
			// have requested.
			IOUtils.copy(conn.getInputStream(), output);

			// close the byte array output stream now.
			output.close();
			outputJSON = output.toString();
			// Parse the Output to Json
			JSONParser jsonparser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonparser.parse(outputJSON);
			// Google Map Status As of handled only one, In future we will handle the further staus
			// OVER_QUERY_LIMIT
			// REQUEST_DENIED
			// INVALID_REQUEST
			// UNKNOWN_ERROR
			String status = (String) jsonObject.get("status");
			if (!status.equals("ZERO_RESULTS")) {
				// Convert to JSON Object
				JSONArray results = (JSONArray) jsonObject.get("results");
				JSONObject mainJson = (JSONObject) results.get(0);
				JSONObject geoMetry = (JSONObject) mainJson.get("geometry");
				JSONObject location = (JSONObject) geoMetry.get("location");
				latLang = location.get("lat") + "," + location.get("lng");
			} else {
				latLang = "0.1,0.1";
			}

			return latLang;
		} catch (Exception ex) {
			outputJSON = "0.0,0.0";
		}
		return outputJSON;
	}

	public String getLocationFromAlternateAPIKey(String address) {
		String outputJSON = null;
		String latLang = null;
		System.out.println(address);
		// Google Map
		String gURL = "https://maps.googleapis.com/maps/api/geocode/json";
		try {

			URL url = new URL(
					gURL
							+ "?address="
							+ URLEncoder.encode(address, "UTF-8")
							+ "&sensor=false&key="+injuryProperties.getProperty("updateLatLongAPIKey"));

			// Open the Connection
			URLConnection conn = url.openConnection();

			// This is Simple a byte array output stream that we will use to
			// keep the output data from google.
			ByteArrayOutputStream output = new ByteArrayOutputStream(1024);

			// copying the output data from Google which will be either in JSON
			// or XML depending on your request URL that in which format you
			// have requested.
			IOUtils.copy(conn.getInputStream(), output);

			// close the byte array output stream now.
			output.close();
			outputJSON = output.toString();
			// Parse the Output to Json
			JSONParser jsonparser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonparser.parse(outputJSON);
			// Google Map Status As of handled only one, In future we will handle the further staus
			// OVER_QUERY_LIMIT
			// REQUEST_DENIED
			// INVALID_REQUEST
			// UNKNOWN_ERROR
			String status = (String) jsonObject.get("status");
			if (!status.equals("ZERO_RESULTS")) {
				// Convert to JSON Object
				JSONArray results = (JSONArray) jsonObject.get("results");
				JSONObject mainJson = (JSONObject) results.get(0);
				JSONObject geoMetry = (JSONObject) mainJson.get("geometry");
				JSONObject location = (JSONObject) geoMetry.get("location");
				latLang = location.get("lat") + "," + location.get("lng");
			} else {
				latLang = "0.1,0.1";
			}

			return latLang;
		} catch (Exception ex) {
			outputJSON = "0.0,0.0";
		}
		return outputJSON;
	}
	
	public List<String> getDrivingDistance(String origin,String destination){
		List<String> result = new ArrayList<String>();
		String outputJSON = null;
		System.out.println(origin+"---->"+destination);
		String distanceMatrixURL="https://maps.googleapis.com/maps/api/distancematrix/json";
		try {
			URL distanceAPIUrl = new URL(distanceMatrixURL+"?origins="+origin+"&destinations="+destination+"&key="+injuryProperties.getProperty("updateLatLongAPIKey")+"&units=imperial");
			
			// Open Connection
			URLConnection conn = distanceAPIUrl.openConnection();
			
			// Declare output stream to store google output
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
			
			// Copy Output to Stram
			IOUtils.copy(conn.getInputStream(), outputStream);
			// close the byte array output stream now.
			outputStream.close();
			outputJSON=outputStream.toString();
			
			// Use JSONPARSER
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(outputJSON);
			// get Response Status
			// INVALID_REQUEST
			// MAX_ELEMENTS_EXCEEDED
			// OVER_QUERY_LIMIT
			// REQUEST_DENIED
			// UNKNOWN_ERROR
			String status = (String) jsonObject.get("status");
			if(status.equals("OK")){
				JSONArray rowsElement = (JSONArray) jsonObject.get("rows");
				JSONObject subElementJson = (JSONObject) rowsElement.get(0);
				JSONArray subElements = (JSONArray) subElementJson.get("elements");
				JSONObject element= (JSONObject) subElements.get(0);
				// OK
				// NOT_FOUND
				// ZERO_RESULTS
				// MAX_ROUTE_LENGTH_EXCEEDED
				String elementStatus = (String) element.get("status");
				if(elementStatus.equals("OK")){
					JSONObject distanceObject = (JSONObject) element.get("distance");
					JSONObject durationObject = (JSONObject) element.get("duration");
					result.add(distanceObject.get("text").toString());
					result.add(durationObject.get("text").toString());
				}else{
					result.add("0 mi");
					result.add("0 mins");
				}
			}else{
				result.add("0 mi");
				result.add("0 mins");
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	/*
	 * Calculate the distance between two latitude/longs
	 */
	public double distance(double lat1, double lat2, double lon1, double lon2) {
		final int R = 6371; // Radius of the earth
		Double latDistance = deg2rad(lat2 - lat1);
		Double lonDistance = deg2rad(lon2 - lon1);
		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c; // convert the meters
		return distance;

	}

	/*
	 * Helper function for distance calculation
	 */
	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	// Convert Miles to KiloMeter
	public static Double convertMilesToKiloMeter(Integer distance) {

		Double convertedDistance;

		convertedDistance = distance * 1.609344;
		DecimalFormat df1 = new DecimalFormat("#.###");
		convertedDistance = Double.valueOf(df1.format(convertedDistance));

		return convertedDistance;
	}

	// Convert Miles to Meter
	public static Double convertMilesToMeter(Integer distance) {

		Double convertedMeter;

		convertedMeter = distance * 1609.344;
		DecimalFormat df1 = new DecimalFormat("#.##");
		convertedMeter = Double.valueOf(df1.format(convertedMeter));

		return convertedMeter;
	}

	// Convert KiloMeter to Miles
	public static Double convertKiloMeterToMiles(Double distance) {

		Double convertedMilesDistance;

		convertedMilesDistance = distance / 1.609344;
		DecimalFormat df1 = new DecimalFormat("#.###");
		convertedMilesDistance = Double.valueOf(df1
				.format(convertedMilesDistance));

		return convertedMilesDistance;
	}
}
