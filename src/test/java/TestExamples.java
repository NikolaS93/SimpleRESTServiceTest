import models.Coin;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestExamples {

    @Test
    public void getQuery() {

        String baseURI = "http://metadata-server-mock.herokuapp.com/";
        String endpoint = "metadata/query";

        String subjectParam = "2048c7e09308f9138cef8f1a81733b72e601d016eea5eef759ff2933416d617a696e67436f696e";

        RestAssured.
                given().
                queryParam("subjects", subjectParam).
                when().
                get(baseURI + endpoint).
                then().
                //               assertThat().statusCode(200);
                        log().body();


    }

    @Test
    public void getMetadata() {

        String baseURI = "http://metadata-server-mock.herokuapp.com/";
        String endpoint = "metadata/2048c7e09308f9138cef8f1a81733b72e601d016eea5eef759ff2933416d617a696e67436f696e";

        RestAssured.
                given().
                when().
                get(baseURI + endpoint).
                then().
                assertThat().
                statusCode(200).
                body("subject", equalTo("2048c7e09308f9138cef8f1a81733b72e601d016eea5eef759ff2933416d617a696e67436f696e"));

    }

    @Test
    public void getMetadataProperty() {

        String baseURI = "http://metadata-server-mock.herokuapp.com/";
        String endpoint = "metadata/2048c7e09308f9138cef8f1a81733b72e601d016eea5eef759ff2933416d617a696e67436f696e/properties/name";

        RestAssured.
                given().
                when().
                    get(baseURI + endpoint).
                then().
                    log().body().
                    assertThat().
                        statusCode(200).
                        header("Content-Type", equalTo("application/json")).
                        body("sequenceNumber.", notNullValue()).
                        body("value", equalTo("Amazing Coin")).
                        body("signatures.size()", greaterThan(0));
    }

    @Test
    public void getMetadataPropertyDeserialized() {

        String baseURI = "http://metadata-server-mock.herokuapp.com/";
        String endpoint = "metadata/2048c7e09308f9138cef8f1a81733b72e601d016eea5eef759ff2933416d617a696e67436f696e/properties/name";

        Object signatures = "<[{signature=2bf86524f8cb8dff2d06ea291cccb6bd69cd31499d64eac5d661245d6c68ebe047515f0e119ddf08848d20e7b898dda59b1369bcb1dc59210049c76d00db9f04, publicKey=9b3c4095df24e08599115c750988b0a105043cd15b6521a123f21d7b92369a73}]>";

        Coin expectedCoin = new Coin(0, "Amazing Coin", signatures);

        Coin actualCoin = RestAssured.
                given().
                when().
                    get(baseURI + endpoint).as(Coin.class);

        assertThat(actualCoin, samePropertyValuesAs(expectedCoin));
    }
}
