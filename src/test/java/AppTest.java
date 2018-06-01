import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.jayway.restassured.specification.RequestSpecification;

import org.junit.Assert;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

public class AppTest {


    //Se realizaron algunos test cada uno tiene detallado en la funcion el objetivo de cada uno
    // Se crea un objeto, se lo transforma a formato Json y se lo envia en el request para
    //generar un post del mismo, se corrobora que la respuesta sea 200 "Success"  para confirmar que tuvo éxito
    @Test
    public void validar_status_200_en_Post() {

        RestAssured.baseURI= "http://localhost:8080";
        List<Pictures> list = new ArrayList<>();
        Pictures picture = new Pictures("http://lalala.com");
        list.add(picture);
        Item item = new Item("121212", "Teclado", "Insumo Informatico", 50.00,"Pesos"
                ,10,"Comprar ahora","Oro","Nuevo",
                "Teclado a Pilas","https://www.youtube.com","1 año", list);
        String itemJson= new Gson().toJson(item);
        RequestSpecification httpRequest = RestAssured.given();
        //httpRequest.header("Content-Type", "application/json");
        httpRequest.body(itemJson);
        Response response = httpRequest.post("/items/121212");
        int statusCode = (response).getStatusCode();
        Assert.assertEquals(String.valueOf(statusCode), "200");
    }

    @Test
    public void validar_status_code_200(){
        RestAssured.baseURI= "http://localhost:8080";
        RequestSpecification httpRequest= RestAssured.given();
        Response response= httpRequest.get("/items/121212");
        int statusCode = (response).getStatusCode();
        Assert.assertEquals(String.valueOf(statusCode), "200");
    }

    @Test
    public void validar_status_line(){
        RestAssured.baseURI= "http://localhost:8080";
        RequestSpecification httpRequest= RestAssured.given();
        Response response= httpRequest.get("/items/121212");
        String statusLine = (response).getStatusLine();
        Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
    }

    @Test
    public void validar_header_ContentTypeq(){
        RestAssured.baseURI= "http://localhost:8080";
        RequestSpecification httpRequest= RestAssured.given();
        Response response= httpRequest.get("/items/121212");
        String header_value = response.header("Content-Type");
        Assert.assertEquals(header_value, "application/json");
    }


    @Test
    public void validar_elementos(){
        RestAssured.baseURI= "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("items");
        ResponseBody body = response.getBody();
        String stringBody = body.asString();
        System.out.println(stringBody);
        Assert.assertEquals(stringBody.contains(""), true);
    }

    @Test
    public void validar_elemento_nulo(){
        //Se verifica que si se apunta a un elemento que no haya sido creado se obtenga null
        RestAssured.baseURI= "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("items/800000");
        ResponseBody body = response.getBody();
        String stringBody = body.asString();
        System.out.println(stringBody);
        Assert.assertEquals(stringBody.equals("null"), true);
    }
    @Test
    public void validar_elemento(){
        RestAssured.baseURI= "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("items/121212");
        ResponseBody body = response.getBody();
        String stringBody = body.asString();
        Assert.assertEquals(stringBody.contains("121212"), true);
    }

    @Test
    //Se genera un objeto para actualizar el contenido del mismo objeto que ya ha sido grabado
    //el nuevo objeto se lo transforma a Json, se lo adjunta al cuerpo del request
    //y se realiza POST para actualizar el objeto, la respuesta indica por medio del estado el exito de
    //la operación realizada.
    public void validar_status_200_en_Put() {

        RestAssured.baseURI= "http://localhost:8080";
        List<Pictures> list = new ArrayList<>();
        Pictures picture = new Pictures("http://lalala.com");
        list.add(picture);
        Item item = new Item("121212", "Mouse", "Insumo Informatico", 50.00,"Pesos"
                ,10,"Comprar ahora","Oro","Nuevo",
                "Mouse inalambrico a Pilas","https://www.youtube.com","1 año", list);
        String itemJson= new Gson().toJson(item);
        RequestSpecification httpRequest = RestAssured.given();
        //httpRequest.header("Content-Type", "application/json");
        httpRequest.body(itemJson);
        Response response = httpRequest.post("/items/121212");
        int statusCode = (response).getStatusCode();
        Assert.assertEquals(String.valueOf(statusCode), "200");
    }

    @Test
    public void validar_status_200_en_Delete() {
        RestAssured.baseURI= "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.delete("/items/121212");
        int statusCode = (response).getStatusCode();
        Assert.assertEquals(String.valueOf(statusCode), "200");
    }



}