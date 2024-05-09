package dk.lyngby;
import io.javalin.Javalin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import dk.lyngby.Model.Route;
import dk.lyngby.config.ApplicationConfig;
import dk.lyngby.config.HibernateConfig;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestTest {


    private static Javalin app;

    private static final String BASE_URL = "http://localhost:7777/api/v1";

    private static EntityManagerFactory emfTest;

    @BeforeAll
    static void beforeAll(){
        HibernateConfig.setTest(true);
        emfTest = HibernateConfig.getEntityManagerFactory();
        app = Javalin.create();
        ApplicationConfig.startServer(app, 7777);

    }

    @BeforeEach
    void setup(){
        try (EntityManager em = emfTest.createEntityManager()) {
            // Insert test rows

            em.getTransaction().begin();
            em.createNativeQuery("TRUNCATE TABLE public.route RESTART IDENTITY").executeUpdate();


            em.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('1','Nørregade 10','1172','Rovsingsgade 31','2200');").executeUpdate();
            em.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('2','Nørregade 10','1172','Duevej 92','2000');").executeUpdate();
            em.createNativeQuery("INSERT INTO public.route (id,endlocation,endpostalcode, startlocation, startpostalcode) VALUES ('3','Nørregade 10','1172','Frederiksvej 10','2000');").executeUpdate();

            em.getTransaction().commit();
        }
    }
    @AfterEach
    void tearDown() {
        HibernateConfig.setTest(false);
        ApplicationConfig.stopServer(app);
    }

    /**
     * Integration testing of our endpoint: /route/available_routes
     * The output is a list of 2 available routes sorted by distance to the passengers start location
     * @author pelle112112
     */
    @Test
    void calculateRoutes(){
        List<Route> routeList =
                given()
                .contentType("application/json")
                .body("{\"startLocation\": \"Duevej,22,2000\", \"endLocation\": \"Nørregade,10,1172\"}")
                .when()
                .post(BASE_URL + "/route/available_routes")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().body().jsonPath().getList("", Route.class);
        assertEquals(2, routeList.size());
        assertEquals("Duevej 92", routeList.get(0).getStartLocation());
        assertEquals(2000, routeList.get(0).getStartPostalCode());
        assertEquals("Frederiksvej 10", routeList.get(1).getStartLocation());
        assertNotEquals("Frederiksvej 11", routeList.get(1).getStartLocation());
    }



}
