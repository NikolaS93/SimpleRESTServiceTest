import models.Coin;
import models.Signature;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestServerMock {

    public final String baseURI = "http://metadata-server-mock.herokuapp.com/";
    public final String signature = "2bf86524f8cb8dff2d06ea291cccb6bd69cd31499d64eac5d661245d6c68ebe047515f0e119ddf08848d20e7b898dda59b1369bcb1dc59210049c76d00db9f04";
    public final String publicKey = "9b3c4095df24e08599115c750988b0a105043cd15b6521a123f21d7b92369a73";
    public final String metadataValue = "2048c7e09308f9138cef8f1a81733b72e601d016eea5eef759ff2933416d617a696e67436f696e";


    @Test
    public void postQuery() {

        String endpoint = "metadata/query";

        Signature[] signatures = new Signature[1];

        signatures[0] = new Signature(this.signature, this.publicKey);

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

//        String endpoint = "metadata/2048c7e09308f9138cef8f1a81733b72e601d016eea5eef759ff2933416d617a696e67436f696e";
        String endpoint = String.format("metadata/%s", metadataValue);

        RestAssured.
                given().
                when().
                get(baseURI + endpoint).
                then().
                assertThat().
                statusCode(200).
                body("subject", equalTo(metadataValue));

    }

    @Test
    public void getMetadataProperty() {


        String endpoint = String.format("metadata/%s/properties/name", metadataValue);

        Signature[] signatures = new Signature[1];

        signatures[0] = new Signature(signature, publicKey);

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
