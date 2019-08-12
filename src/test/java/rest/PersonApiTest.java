package rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.PersonDto;
import entity.EntityManagerFactoryCreator;
import entity.Person;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.AfterClass;
import org.junit.Before;

public class PersonApiTest {

    private static final int SERVER_PORT = 8084;
    private static final String SERVER_URL = "http://127.25.200.200/api";
    private static final String TEST_DB = "jdbc:mysql://127.25.200.200:3306/mydb_test";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeClass
    public static void setUpClass() {
        //First Drop and Rebuild the test database 
        emf = EntityManagerFactoryCreator.getEntityManagerFactory(
                "pu",
                TEST_DB,
                "dev",
                "ax2",
                EntityManagerFactoryCreator.Strategy.DROP_AND_CREATE);

        //Set property so server is using the same database
        System.setProperty("IS_TEST", TEST_DB);
        //We are using the database on the virtual Vagrant image, so username password are the same for all dev-databases
        httpServer = startServer();

        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        //RestAssured.basePath = APP_CONTEXT;
        RestAssured.defaultParser = Parser.JSON;

    }

    @AfterClass
    public static void tearDownClass() {
        httpServer.shutdownNow();
    }

    //Used to store autogenerated id's to use in the test
    int id1;
    int id2;
    @Before
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        Person p1 = new Person("a1", "b1", "c1", "d1", "a1@b.dk");
        Person p2 = new Person("a2", "b2", "c2", "d2", "a2@b.dk");
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from Person").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        id1 = p1.getId();
        id2 = p2.getId();
    }

    @Test
    public void serverIsRunning() {
        System.out.println("Testing is server UP");
        given().when().get("/person/all").then().statusCode(200);
    }

    @Test
    public void testGetPerson() throws Exception {
        given()
        .contentType("application/json")
        .get("/person/"+id1).then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("id", equalTo(id1))
        .body("firstName",equalTo("a1"));       
    }

    @Test
    public void testGetPersons() throws Exception {
        String responseString = given()
                .contentType("application/json")
                .get("/person/all").then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .asString();
        Gson gson = new Gson();
        List<PersonDto> persons = gson.fromJson(responseString, new TypeToken<List<PersonDto>>() {
        }.getType());
        assertEquals("Expected two persons", 2, persons.size());
        String fn1 = persons.get(0).getFirstName();
        String fn2 = persons.get(1).getFirstName();
        boolean namesAreDifferent = !fn1.equals(fn2);
        assertTrue("Expected two Person instances with different names", namesAreDifferent);
    }

    @Test
    public void testGetMsg() {
    }

    public static void main(String[] args) throws IOException {
//        System.setProperty("IS_TEST", "jdbc:mysql://localhost:3307/mydb_test");
//        System.out.println("Starting grizzly... (Press any key to stop the server)");
//        HttpServer httpServer = startServer();
//        System.in.read();
//        httpServer.shutdownNow();
    }
}
