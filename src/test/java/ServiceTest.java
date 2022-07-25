import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

public class ServiceTest {

    @BeforeMethod
    public void setUp(){
        RestAssured.baseURI = "http://metadata-server-mock.herokuapp.com/";
    }

    @Test
    public void testPOSTQuery(){

        RequestSpecification request = RestAssured.given();

        request.contentType(ContentType.HTML);
        request.accept(ContentType.TEXT);

        Response response = request.queryParam
                ("subjects", "2048c7e09308f9138cef8f1a81733b72e601d016eea5eef759ff2933416d617a696e67436f696e")
                .post("/metadata/query");

        Assert.assertEquals(response.getStatusCode(), 500);

    }

    @Test
    public void testGETMetadata(){

        RequestSpecification request = RestAssured.given();

        JsonObject requestParams = new JsonObject();

        request.contentType(ContentType.JSON);
        request.accept(ContentType.ANY);
        request.body(requestParams);

        Response response = request.get("metadata/2048c7e09308f9138cef8f1a81733b72e601d016eea5eef759ff2933416d617a696e67436f696e");

        System.out.println(response.body().prettyPrint());

        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test
    public void testGETMetadataProperty(){

        RequestSpecification request = RestAssured.given();

        JsonObject requestParams = new JsonObject();

        request.contentType(ContentType.JSON);
        request.accept(ContentType.ANY);
        request.body(requestParams);

        Response response = request.get("/metadata/2048c7e09308f9138cef8f1a81733b72e601d016eea5eef759ff2933416d617a696e67436f696e/properties/name");

        System.out.println(response.body().prettyPrint());

        Assert.assertEquals(response.getStatusCode(), 200);

    }

}
