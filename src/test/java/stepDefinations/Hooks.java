package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		
		//execute this code only when place id is null
		//write a code that will give place id bcz our DeletePlace scenario depends on on AddPlace scenario but if we are using tagging now.
		if(StepDefination.placeid==null) {
		StepDefination m=new StepDefination();
		m.add_place_payload_with("Heena", "French", "London");
		m.user_calls_with_http_request("AddPlaceAPI","POST");
		m.verify_placeid_created_maps_to_using("Heena", "getPlaceAPI");
		}
	}

}
