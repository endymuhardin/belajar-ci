package com.muhardin.endy.belajar.ci.controller;

import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.*;
import com.jayway.restassured.http.ContentType;
import com.muhardin.endy.belajar.BelajarCiApplication;
import com.muhardin.endy.belajar.ci.entity.Product;
import java.math.BigDecimal;
import static org.hamcrest.Matchers.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BelajarCiApplication.class)
@Sql(scripts = {"/mysql/delete-data.sql", "/mysql/sample-product.sql"})
@WebIntegrationTest(randomPort = true)
public class ProductControllerTests {

    private static final String BASE_URL = "/api/product";
    
    @Value("${local.server.port}")
    int serverPort;

    @Before
    public void setup() {
        RestAssured.port = serverPort;
    }

    @Test
    public void testFindAll() {
        get(BASE_URL+"/")
                .then()
                .body("totalElements", equalTo(1))
                .body("content.id", hasItems("abc123"));
    }

    @Test
    public void testSave() throws Exception {

        Product p = new Product();
        p.setCode("PT-001");
        p.setName("Product Test 001");
        p.setPrice(BigDecimal.valueOf(102000.02));

        given()
                .body(p)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL+"/")
                .then()
                .statusCode(201)
                .header("Location", containsString(BASE_URL+"/"))
                .log().headers();

        // nama tidak diisi
        Product px = new Product();
        px.setCode("PT-001");
        given()
                .body(px)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL+"/")
                .then()
                .statusCode(400);

        // kode kurang dari 3 huruf
        Product px1 = new Product();
        px1.setCode("PT");
        px1.setName("Product Test");
        p.setPrice(BigDecimal.valueOf(100));

        given()
                .body(px1)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL+"/")
                .then()
                .statusCode(400);

        // Harga negatif
        Product px2 = new Product();
        px2.setCode("PT-009");
        px2.setName("Product Test");
        p.setPrice(BigDecimal.valueOf(-100));
        given()
                .body(px1)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL+"/")
                .then()
                .statusCode(400);
    }

    @Test
    public void testFindById() {
        get(BASE_URL+"/abc123")
                .then()
                .statusCode(200)
                .body("id", equalTo("abc123"))
                .body("code", equalTo("P-001"));

        get(BASE_URL+"/990")
                .then()
                .statusCode(404);
    }

    @Test
    public void testUpdate() {
        Product p = new Product();
        p.setCode("PX-009");
        p.setName("Product 909");
        p.setPrice(BigDecimal.valueOf(2000));

        given()
                .body(p)
                .contentType(ContentType.JSON)
                .when()
                .put(BASE_URL+"/abc123")
                .then()
                .statusCode(200);

        get(BASE_URL+"/abc123")
                .then()
                .statusCode(200)
                .body("id", equalTo("abc123"))
                .body("code", equalTo("PX-009"))
                .body("name", equalTo("Product 909"));
        
        // test id salah
        given()
                .body(p)
                .contentType(ContentType.JSON)
                .when()
                .put(BASE_URL+"/xyz456")
                .then()
                .statusCode(404);
    }

    @Test
    public void testDelete() {
        delete(BASE_URL+"/abc123")
                .then()
                .statusCode(200);

        get(BASE_URL+"/abc123")
                .then()
                .statusCode(404);
        
        // test id salah
        delete(BASE_URL+"/xyz123")
                .then()
                .statusCode(404);
    }
}
