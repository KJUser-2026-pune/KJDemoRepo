Feature: Validating Place API's

@AddPlace @Regeression
Scenario Outline: Verify place is being successfully added using AddPlaceAPI
Given Add Place Payload with "<name>""<language>""<address>"
When user calls "AddPlaceAPI" with "Post" http request
Then the API got success with status code 200
And "status" in response body is "OK"
And "scope" in response body is "APP"
And verify placeid created maps to "<name>" using "getPlaceAPI"

Examples:
|name   |language|address|
|AAhouse|Marathi |Pune   |
|BBhouse|Spanish |Mumbai |

@DeletePlace @Regression
Scenario: verify delete place functionality is working or not
Given DeletePlace Payload
When user calls "deletePlaceAPI" with "DELETE" http request 
Then the API got success with status code 200
And "status" in response body is "OK"

