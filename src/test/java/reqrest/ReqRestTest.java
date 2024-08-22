package reqrest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ReqRestTest {

    Logger logger = Logger.getLogger(ReqRestTest.class.getName());

    @BeforeAll
    public static void setup() {

        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        //this remove log().all()
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        //this remove the call to contentType
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)

                .build();
    }

    @Test
    public void login() {

        given()
                .body("""
                        {
                            "email": "eve.holt@reqres.in",
                            "password": "cityslicka"
                        }
                        """)
                .post("login")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("token", notNullValue());

    }

    @Test
    public void getSingleUser() {

        given()
                .get("users/2")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data.id", equalTo(2))
                .body("data.email", containsStringIgnoringCase("Janet"))
                .body("data.id", greaterThanOrEqualTo(2));
    }

    @Test
    public void deleteUser() {

        given()
                .delete("users/2")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void patchUser() {

        String updatedName = given()
                .when()
                .body("""
                        {
                            "name": "morpheus",
                            "job": "zion resident"
                        }
                        """)
                .patch("users/2")
                .then()
                .statusCode(HttpStatus.SC_OK)
                //allow to extract info of the json generated in the respponse
                .extract()
                .jsonPath()
                .getString("name");

        assertThat(updatedName, equalTo("morpheus"));
    }

    @Test
    public void putUser() {

        String updatedName = given()
                .when()
                .body("""
                        {
                            "name": "morpheus",
                            "job": "zion resident"
                        }
                        """)
                .put("users/2")
                .then()
                .statusCode(HttpStatus.SC_OK)
                //allow to extract info of the json generated in the respponse
                .extract()
                .jsonPath()
                .getString("job");

        assertThat(updatedName, equalTo("zion resident"));
    }

    @Test
    public void getAllUsers() {
        Response response = given()
                .get("users?page=2");

        Headers headers = response.getHeaders();
        int statusCode = response.getStatusCode();
        String body = response.getBody().asString();
        String contentType = response.getContentType();

        assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        logger.info("Headers is: " + headers);
        logger.info("body is: " + body);
        System.out.println("Headers is: " + headers);
        System.out.println("body is: " + body);
        System.out.println("contentType is: " + contentType);

        System.out.println("Transfer encoding is " + headers.get("Transfer-Encoding").getValue());
    }

    @Test
    public void getAllUsersFrom() {

        String response = given()
                .when()
                .get("users?page=2")
                .then()
                .extract().body().asString();

        int page = from(response).get("page");
        int totalPages = from(response).get("total_pages");
        int id = from(response).get("data[0].id");

        List<Map> userWithIdGreaterThan10 = from(response).get("data.findAll {user-> user.id >=10}");
        String email = userWithIdGreaterThan10.get(0).get("email").toString();

        List<Map> userLastName = from(response).get("data.findAll {user-> user.id >=10 && user.last_name == 'Howell'}");
        int userId = Integer.parseInt(userLastName.get(0).get("id").toString());

        System.out.println("page: " + page);
        System.out.println("total pages: " + totalPages);
        System.out.println("id: " + id);
        System.out.println("user with id greater or equal to 10: " + userWithIdGreaterThan10);
        System.out.println("The email for user with id greater or equal to 10: " + email);
        System.out.println("The user for user with id greater or equal to 10 and lastName: " + userLastName);
        System.out.println("The id for user with id greater or equal to 10: " + userId);
    }

    @Test
    public void createUser() {

        String responseUser = given()
                .body("""
                        {
                             "name": "morpheus",
                             "job": "leader",
                             "id": "613"
                                }
                        """)
                .post("users")
                .then().extract()
                .body().asString();

        Users user = from(responseUser).getObject("", Users.class);
        System.out.println("User id is: " + user.getId());
        System.out.println("User job is: " + user.getJob());
    }

    @Test
    public void serialization() throws JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT);
        var user = new Users();
        user.setId("1");
        user.setCreatedAt("2024/06/03");
        user.setJob("QA");
        user.setName("Pepito");

        var serialiczed = mapper.writeValueAsString(user);
        logger.info(serialiczed);
    }

    @Test
    public void deserialization() throws IOException {

        final ObjectMapper mapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT);
        var jsonString = """
                {
                  "createdAt" : "2024/06/03",
                  "name" : "Pepito",
                  "id" : "1",
                  "job" : "QA"
                }
                """;

//        var deserialized = mapper.readValue("user.json", reqrest.Users.class);
        var deserialized = mapper.readValue(new File("src/test/java/user.json"), Users.class);
        logger.info(deserialized.getId());
        logger.info(deserialized.getName());
        logger.info(deserialized.getJob());
    }

    @Test
    public void registerUser() {

//        CreateUserRequest createUserRequest = new CreateUserRequest();
//        createUserRequest.setEmail("eve.holt@reqres.in");
//        createUserRequest.setPassword("pistol");
//
//        String responseUser = given()
//                .body("""
//                        {
//                            "email": "eve.holt@reqres.in",
//                            "password": "pistol"
//                                 }
//                        """)
//                .post("register")
//                .then().extract()
//                .body().asString();
//
//        Users user = from(responseUser).getObject("", Users.class);
//        System.out.println("User id is: " + user.getId());
//        System.out.println("User job is: " + user.getJob());

    }

}
