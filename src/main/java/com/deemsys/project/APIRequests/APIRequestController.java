package com.deemsys.project.APIRequests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.deemsys.project.common.InjuryProperties;
import com.google.gson.Gson;

@Controller
public class APIRequestController {
	
	@Autowired
	InjuryProperties injuryProperties;
	
	// Get Bills List
	@RequestMapping(value="/getSubscriptionsList",method=RequestMethod.POST)
	public @ResponseBody String getSubscriptionsList(@RequestBody APIBillSearchForm apiBillSearchForm){
		
		RestTemplate restTemplate = new RestTemplate();
		Gson gson=new Gson();
		String requestJson = gson.toJson(apiBillSearchForm);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
		String result=restTemplate.postForObject(injuryProperties.getProperty("marketingAppDomain")+injuryProperties.getProperty("getMySubscriptionsAPI"), entity, String.class);
		return result;
	}
	// Get Bill Details
	@RequestMapping(value="/getBillDetails",method=RequestMethod.GET)
	public @ResponseBody String getBillDetails(@RequestParam("billId") Long billId){
		
		RestTemplate restTemplate = new RestTemplate();
		String result=restTemplate.getForObject(injuryProperties.getProperty("marketingAppDomain")+injuryProperties.getProperty("getBillDetailsAPI")+billId, String.class);
		return result;
	}
	// Get Card Details
	@RequestMapping(value="/getCardDetails",method=RequestMethod.GET)
	public @ResponseBody String getCardDetails(@RequestParam("productToken") String productToken){
		
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("customerProductToken", productToken);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		
		RestTemplate restTemplate = new RestTemplate();
		String result=restTemplate.postForObject(injuryProperties.getProperty("marketingAppDomain")+injuryProperties.getProperty("getCreditCardDetailsAPI"), request, String.class);
		return result;
	}
	// Update Card Details
	@RequestMapping(value="/updateCardDetails",method=RequestMethod.POST)
	public @ResponseBody String updateCardDetails(@RequestBody APICreditCardForm apiCreditCardForm){
		
		RestTemplate restTemplate = new RestTemplate();
		Gson gson=new Gson();
		String requestJson = gson.toJson(apiCreditCardForm);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
		String result=restTemplate.postForObject(injuryProperties.getProperty("marketingAppDomain")+injuryProperties.getProperty("updateCreditCardDetailsAPI"), entity, String.class);
		return result;
	}
	// Get Transaction Details
	@RequestMapping(value="/getTransactionDetails",method=RequestMethod.GET)
	public @ResponseBody String getTransactionDetails(@RequestParam("billId") Long billId){
		
		RestTemplate restTemplate = new RestTemplate();
		String result=restTemplate.getForObject(injuryProperties.getProperty("marketingAppDomain")+injuryProperties.getProperty("getTransactionDetailsAPI")+billId, String.class);
		return result;
	}
}
