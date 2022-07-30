import io.restassured.http.ContentType;
import models.Coin;
import models.Signature;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestServerMock {

    public final String baseURI = "http://metadata-server-mock.herokuapp.com/";
    public final String signature = "2bf86524f8cb8dff2d06ea291cccb6bd69cd31499d64eac5d661245d6c68ebe047515f0e119ddf08848d20e7b898dda59b1369bcb1dc59210049c76d00db9f04";
    public final String publicKey = "9b3c4095df24e08599115c750988b0a105043cd15b6521a123f21d7b92369a73";
    public final String subject = "2048c7e09308f9138cef8f1a81733b72e601d016eea5eef759ff2933416d617a696e67436f696e";
    public final String subjectTwo = "919e8a1922aaa764b1d66407c6f62244e77081215f385b60a62091494861707079436f696e";


    @Test
    public void postQuerySubjects() {

        String endpoint = "metadata/query";

        ArrayList<String> subjects = new ArrayList<>();

        subjects.add(subjectTwo);
        subjects.add(subject);

        Map<String, ArrayList<String>> params = new HashMap<>();
        params.put("subjects", subjects);

        System.out.println(params);

        RestAssured.
                given().
                body(params).
                when().
                post(baseURI + endpoint).
                then().log().body().
                    assertThat().statusCode(200).
                body("subjects.subject", equalTo(subjects));

    }

    @Test
    public void postQuerySubjectsProperties(){

        String endpoint = "metadata/query";

        ArrayList<String> subjects = new ArrayList<>();
        ArrayList<String> properties = new ArrayList<>();

        subjects.add(subjectTwo);
        subjects.add(subject);

        properties.add("name");
        properties.add("description");
        properties.add("url");

        Map<String, ArrayList<String>> params = new HashMap<>();
        params.put("subjects", subjects);
        params.put("properties", properties);

        System.out.println(params);

        RestAssured.
                given().
                body(params).
                when().
                post(baseURI + endpoint).
                then().log().body().
                assertThat().statusCode(200).
                body("subjects.subject", equalTo(subjects));

    }

    @Test
    public void getMetadataVerifyProperties(){
        // Verify GET Metadata request returns right object and existence of metadata properties

        String endpoint = String.format("metadata/%s", subject);

        RestAssured.
                given().
                when().
                get(baseURI + endpoint).
                then().
                assertThat().
                statusCode(200).
                body(
                        "subject", equalTo(subject),
                        ("any { it.key == 'ticker' }"), is(true),
                        ("any { it.key == 'decimals' }"), is(true),
                        ("any { it.key == 'policy' }"), is(true),
                        ("any { it.key == 'logo' }"), is(true),
                        ("name.any { it.key == 'anSignatures' }"), is(true),
                        ("any { it.key == 'preImage' }"), is(true)
                        );

    }

    @Test
    public void getMetadataPropertyName() {
        // Verify values of property 'Name'

        String endpoint = String.format("metadata/%s/properties/name", subject);

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
