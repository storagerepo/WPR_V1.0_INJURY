package com.deemsys.project.Map;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

@Service
public class GeoLocation {

	
	// Get Lat long of Particular Address
		public String getLocation(String address){
			String outputJSON = null;
			String latLang = null;
			System.out.println(address);
			//Google Map
			String gURL = "https://maps.googleapis.com/maps/api/geocode/json";
			try {
			
				URL url = new URL(gURL + "?address=" + URLEncoder.encode(address, "UTF-8")+ "&sensor=false&key=AIzaSyCSrffdwIzj8p4RZgvglbhQEEjgEx7ZUKQ");

				// Open the Connection 
				URLConnection conn = url.openConnection();

				//This is Simple a byte array output stream that we will use to keep the output data from google. 
				ByteArrayOutputStream output = new ByteArrayOutputStream(1024);

				// copying the output data from Google which will be either in JSON or XML depending on your request URL that in which format you have requested.
				IOUtils.copy(conn.getInputStream(), output);

				//close the byte array output stream now.
				output.close();
				outputJSON=output.toString();
				// Parse the Output to Json
				JSONParser jsonparser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonparser.parse(outputJSON);
				// Convert to JSON Object
				
				JSONArray results = (JSONArray) jsonObject.get("results");
				JSONObject mainJson = (JSONObject)results.get(0);
		        JSONObject geoMetry = (JSONObject)mainJson.get("geometry");
		        JSONObject location = (JSONObject) geoMetry.get("location");
				
		        latLang=location.get("lat")+","+location.get("lng");
		        
				return latLang;
			}
			 catch (Exception ex) {
				 outputJSON = "Err";
			}
			return outputJSON;
			}
		
		
		/*
		 * Calculate the distance between two latitude/longs
		 *
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
		@SuppressWarnings("static-access")
		public static Double convertMilesToKiloMeter(Integer distance)
		{
			
			Double convertedDistance;
			
				convertedDistance=distance*1.609344;
				DecimalFormat df1 = new DecimalFormat("#.###");      
				convertedDistance = Double.valueOf(df1.format(convertedDistance));
				
				return convertedDistance;
		}
		
		// Convert Miles to KiloMeter
		@SuppressWarnings("static-access")
		public static Double convertMilesToMeter(Integer distance)
		{
					
				Double convertedMeter;
					
				convertedMeter=distance*1609.344;
					DecimalFormat df1 = new DecimalFormat("#.##");      
					convertedMeter = Double.valueOf(df1.format(convertedMeter));
						
					return convertedMeter;
		}
		
		// Convert Miles to KiloMeter
		@SuppressWarnings("static-access")
		public static Double convertKiloMeterToMiles(Double distance)
		{
					
			Double convertedMilesDistance;
					
			convertedMilesDistance=distance/1.609344;
			DecimalFormat df1 = new DecimalFormat("#.###");      
			convertedMilesDistance = Double.valueOf(df1.format(convertedMilesDistance));
						
			return convertedMilesDistance;
		}
}
