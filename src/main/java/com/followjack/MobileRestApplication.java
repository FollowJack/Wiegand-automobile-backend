package com.followjack;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MobileRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobileRestApplication.class, args);
		System.out.println("IT works");
	}

	@RestController
	@CrossOrigin(origins = "http://localhost:3000")
	public class GreetingController {
		
		private static final String URL_MOBILE_DE_SEARCH_API = "https://services.mobile.de/search-api/search?customerId=473777";

		@RequestMapping("/vehicles")
		public String getVehicles() {
			
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
			// set basic authentication
			BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			credentialsProvider.setCredentials(AuthScope.ANY,
					new UsernamePasswordCredentials("dlr_wiegandau", "NudBkVYI31HK"));
			httpClient.setCredentialsProvider(credentialsProvider);
			// set language to german
			ClientHttpRequestFactory rf = new HttpComponentsClientHttpRequestFactory(httpClient);

			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept-Language", "de");

			HttpEntity<String> entity = new HttpEntity<String>("",headers);
				
			RestTemplate restConsumer = new RestTemplate(rf);
			HttpEntity<String> response = restConsumer.exchange(
					URL_MOBILE_DE_SEARCH_API, HttpMethod.GET, entity, String.class, "");
//			String response = restConsumer
//					.getForObject(URL_MOBILE_DE_SEARCH_API,entity, String.class);

			return response.getBody();
		}
	}
}
