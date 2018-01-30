package com.deemsys.project.Logging;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.IPGeoLocation.IPGeoLocationDAO;
import com.deemsys.project.IPGeoLocation.IPGeoLocationForm;
import com.deemsys.project.IPGeoLocation.IPGeoLocationService;
import com.deemsys.project.common.InjuryProperties;

@Service
@Transactional
public class IPGeoLocationServiceImpl {

	@Autowired
	IPGeoLocationService ipGeoLocationService;
	
	@Autowired
	InjuryProperties injuryProperties;

	// Geo Location From IP and Update in DB
	public IPGeoLocationForm getGeoLocationFromIP(String ipAddress) throws ParseException {
		// Create a new Client for Request
		Client client = ClientBuilder.newClient();
		// Access API and Result as JSON
		Response response = client.target(injuryProperties.getProperty("APIUrl")+ipAddress)
				.request(MediaType.TEXT_PLAIN_TYPE)
				.header("Accept", "application/json").get();
		String outputJSON = response.readEntity(String.class);
		int statusCode = response.getStatus();
		return this.getIPGeoLocationFormFromJSON(ipAddress,outputJSON, statusCode);
	}

	// IP Geo Location Form From API Response
	public IPGeoLocationForm getIPGeoLocationFormFromJSON(String ipAddress,
			String outputJSON, int statusCode) throws ParseException {
		IPGeoLocationForm ipGeoLocationForm = null;
		if (statusCode == 200) {
			// Object JSON Parser
			JSONParser jsonParser = new JSONParser();
			// Parse JSON Output
			JSONObject jsonObject = (JSONObject) jsonParser.parse(outputJSON);
			ipGeoLocationForm = new IPGeoLocationForm(
					(String) jsonObject.get("ip"),
					(String) jsonObject.get("city"),
					(String) jsonObject.get("region"),
					(String) jsonObject.get("country_name"),
					(String) jsonObject.get("country_code"),
					(String) jsonObject.get("continent_name"),
					(String) jsonObject.get("continent_code"),
					(String) jsonObject.get("postal"),
					(String) jsonObject.get("latitude"),
					(String) jsonObject.get("longitude"),
					(String) jsonObject.get("time_zone"),
					(String) jsonObject.get("flag"), statusCode);
		} else {
			// Status Code 400 - Private or Invalid IP Address, 429 - Too Many
			// Requests, Exceed 1500 Requests
			System.out.println("Private or Invalid IP Address");
			ipGeoLocationForm = new IPGeoLocationForm(ipAddress, "", "", "",
					"", "", "", "", "", "", "", "", statusCode);
		}

		return ipGeoLocationForm;
	}

	public IPGeoLocationServiceImpl() {
		// TODO Auto-generated constructor stub
	}

}
