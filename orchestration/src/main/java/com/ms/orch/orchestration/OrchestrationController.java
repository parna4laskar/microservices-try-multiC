package com.ms.orch.orchestration;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ms.orch.bean.UserInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

@RestController
public class OrchestrationController {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private EurekaClient eurekaClient;

	private UserInfo user;
	
	

	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/orchestrate-hello-world")
	public String orchestrate() {

		Application application = eurekaClient.getApplication("ms-try");
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/" + "hello-world";
		System.out.println("URL" + url);
		String resp = restTemplate.getForObject(url, String.class);
		System.out.println("RESPONSE " + resp);
		return resp;

	}

	@RequestMapping(method = RequestMethod.POST, path = "/orchestrate-login", consumes= {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> login(@RequestBody(required=true) UserInfo userInfo) throws UnknownHostException {

		Application application = eurekaClient.getApplication("ms-try");
		
		/*user = new UserInfo();
		user.setUserId("Parna");
		user.setPwd("Parna");*/
		
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/" + "loginAction";
		//URI targetUrl = UriComponentsBuilder.fromUriString(url).build(user);
		
		InetAddress inetAddress = InetAddress. getLocalHost();
		System.out.println("URL" + url);
		UserInfo resp = restTemplate.postForObject(url, userInfo, UserInfo.class);
		resp.setIpAddress(inetAddress. getHostAddress());
		System.out.println("RESPONSE " + resp);
		
		return new ResponseEntity<UserInfo>(resp,HttpStatus.OK);

	}

}
