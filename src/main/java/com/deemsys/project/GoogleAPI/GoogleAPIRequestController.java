package com.deemsys.project.GoogleAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deemsys.project.common.InjuryProperties;
import org.springframework.web.client.RestTemplate;

@Controller
public class GoogleAPIRequestController {

	@Autowired
	InjuryProperties injuryProperties;
	
	// get Printers List Based on user account
	@RequestMapping(value="/getPrinterList",method=RequestMethod.POST)
	public @ResponseBody String getPrinterList(@RequestParam("xsrf") String xsrf,@RequestParam("accessToken") String accessToken){
		
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Authorization", "Bearer " +accessToken);

		MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<String, String>();
		requestParam.add("xsrf", xsrf);
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String,String>>(requestParam,headers);
		
		String result=restTemplate.postForObject(injuryProperties.getProperty("googleCloudPrintDomain")+injuryProperties.getProperty("getPrinterList"), entity, String.class);
		
		return result;
	}
	
	// Submit Print Job
	@RequestMapping(value="/submitPrintJob",method=RequestMethod.POST)
	public @ResponseBody String submitPrintJob(@RequestBody GoogleSubmitPrintForm googleSubmitPrintForm){
		
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Authorization", "Bearer " +googleSubmitPrintForm.getAccessToken());

		MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<String, String>();
		requestParam.add("xsrf", googleSubmitPrintForm.getXsrf());
		requestParam.add("printerid", googleSubmitPrintForm.getPrinterId());
		requestParam.add("jobid", googleSubmitPrintForm.getJobId());
		requestParam.add("title", googleSubmitPrintForm.getTitle());
		requestParam.add("contentType", googleSubmitPrintForm.getContentType());
		requestParam.add("content", googleSubmitPrintForm.getContent());
		requestParam.add("ticket", googleSubmitPrintForm.getTicket());
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String,String>>(requestParam,headers);
		
		String result=restTemplate.postForObject(injuryProperties.getProperty("googleCloudPrintDomain")+injuryProperties.getProperty("submitPrintJob"), entity, String.class);
		
		return result;
	}
	
	// Get Print Job Status
	@RequestMapping(value="/getPrintJobStatus",method=RequestMethod.POST)
	public @ResponseBody String getPrintJobStatus(@RequestBody GoogleSubmitPrintForm googleSubmitPrintForm){
		
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Authorization", "Bearer " +googleSubmitPrintForm.getAccessToken());

		MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<String, String>();
		requestParam.add("xsrf", googleSubmitPrintForm.getXsrf());
		requestParam.add("jobid", googleSubmitPrintForm.getJobId());
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String,String>>(requestParam,headers);
		
		String result=restTemplate.postForObject(injuryProperties.getProperty("googleCloudPrintDomain")+injuryProperties.getProperty("getPrintJobStatus"), entity, String.class);
		
		return result;
	}
	
	
	@RequestMapping(value="/logoutFromGoogle",method=RequestMethod.GET)
	public String logoutFromGoogleAccount(ModelMap model){
		
		RestTemplate restTemplate = new RestTemplate();

		String result=restTemplate.getForObject(injuryProperties.getProperty("googleLogoutUrl"), String.class);
		model.addAttribute("requestSuccess", true);
		return result;
	}
}
