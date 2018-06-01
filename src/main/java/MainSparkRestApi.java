import com.google.gson.Gson;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.http.*;

import java.io.IOException;

import static spark.Spark.*;

public class MainSparkRestApi {
    public static void main(String[]args){
        //El puerto 8080 es el designado a ser escuchado
        port (8080);
        final ItemService itemService= new ItemsServiceMapImpl();


        post("/items/:id", (request, response)->{response.type("application/json");
        Item item= new Gson().fromJson(request.body(), Item.class);
        itemService.addItem(item);
        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        get("/items", (request, response)-> {response.type("application/json");
        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(itemService.getItems())));});

        get("/items/:id", (request, response)->{response.type("application/json");
            return new Gson().toJson(new Gson().toJsonTree(itemService.getItem(request.params(":id"))));});

        put("/items/:id", (request, response)->{
            response.type("application/json");
            Item toEdit = new Gson().fromJson(request.body(), Item.class);
            Item editedItem = itemService.editItem(toEdit);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(editedItem)));
        });

        delete("/items/:id", (request, response)->{response.type("application/json");
            itemService.deleteItem(request.params(":id"));
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "Item ha sido eliminado."));
        });
    }
}
