import models.Coin;
import models.Signature;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestServerMock {

    @Test
    public void postQuery() {

        String baseURI = "http://metadata-server-mock.herokuapp.com/";
        String endpoint = "metadata/query";

        Signature[] signatures = new Signature[1];

        signatures[0] = new Signature("789ef8ae89617f34c07f7f6a12e4d65146f958c0bc15a97b4ff169f16861707079636f696e",
                "789ef8ae89617f34c07f7f6a12e4d65146f958c0bc15a97b4ff169f1");

        RestAssured.
                given().
                queryParam("subjects", signatures[0]).
                when().
                post(baseURI + endpoint).
                then().
                    assertThat().statusCode(500).
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

        Signature[] signatures = new Signature[1];

        signatures[0] = new Signature("2bf86524f8cb8dff2d06ea291cccb6bd69cd31499d64eac5d661245d6c68ebe047515f0e119ddf08848d20e7b898dda59b1369bcb1dc59210049c76d00db9f04",
                "9b3c4095df24e08599115c750988b0a105043cd15b6521a123f21d7b92369a73");

        Coin expectedCoin = new Coin(0, "Amazing Coin", signatures);

        Coin actualCoin = RestAssured.
                given().
                when().
                    get(baseURI + endpoint).as(Coin.class);

        assertThat(actualCoin.getSignatures()[0], samePropertyValuesAs(expectedCoin.getSignatures()[0]));
        assertThat(actualCoin.getSequenceNumber(), equalTo(expectedCoin.getSequenceNumber()));
        assertThat(actualCoin.getValue(), equalTo(expectedCoin.getValue()));
    }
}
