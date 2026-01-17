package stepDefinations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;


public class StepDefination extends Utils{
	RequestSpecification req;
	ResponseSpecification resspec;
	Response response;
	static String placeid;
	//APIResources resourcesAPI;
	
	TestDataBuild data=new TestDataBuild();
	@Given("Add Place Payload with {string}{string}{string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
	
	    //RequestSpecification reqspec=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).addQueryParam("key", "qaclick123").build();
	     req=given().spec(requestSpecification()).body(data.addPlacePayload(name,language,address));
	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String httpmethod) {
	    // Write code here that turns the phrase above into concrete actions
		resspec=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		APIResources resourcesAPI=APIResources.valueOf(resource);
		System.out.println("resource api:  "+resourcesAPI);
		if(httpmethod.equalsIgnoreCase("POST")) {
	   response=req.when().post(resourcesAPI.getResource());
		}
		else if(httpmethod.equalsIgnoreCase("GET")) {
			response=req.when().get(resourcesAPI.getResource());	
		}
		else if(httpmethod.equalsIgnoreCase("DELETE")) {
			response=req.when().delete(resourcesAPI.getResource());	
		}
			   //.then().spec(resspec).extract().response();
	}

	@Then("the API got success with status code {int}")
	public void the_api_got_success_with_status_code(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	   assertEquals(response.getStatusCode(),200);
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String value) {
		 //String res=response.asString();
		  //  JsonPath js=new JsonPath(res);
		  //  assertEquals(js.get(keyValue).toString(),value);
		    assertEquals(getJsonPath(response,keyValue), value);
	}

	@Then("verify placeid created maps to {string} using {string}")
	public void verify_placeid_created_maps_to_using(String Expectedname, String resource) throws IOException {
	  placeid=getJsonPath(response, "place_id");
		req=given().spec(requestSpecification()).queryParam("place_id", placeid);
		user_calls_with_http_request(resource,"GET");
		//System.out.println(response.asString());
		String actualname=getJsonPath(response,"name");
		assertEquals(Expectedname,actualname);
	   
	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	    req=given().spec(requestSpecification()).body(data.deletePlacePayload(placeid));
	}


}
