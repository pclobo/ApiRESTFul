import com.google.gson.Gson;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ItemsServiceMapImpl implements ItemService{


    private RestHighLevelClient client;
    //RestHighLevelClient es un wraper de la instancia de bajo nivel de RestClient
    // que permite crear requests y leer los responses.
    //Se establece la conexion a los puertos que usa elasticsearch
    public ItemsServiceMapImpl() {
        client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http"),
                new HttpHost("localhost", 9201, "http")));
    }
    @Override
    public void addItem(Item item) {
        //Se crea el index dentro de elastic search con el id del elemento que se trata de guardar
        IndexRequest request = new IndexRequest("tabla", "items", item.getId());
        //el elemento se lo transforma a formato JSON y se lo asigna al source del request
        request.source(new Gson().toJson(item), XContentType.JSON);
        try {
            //se indexa el elemento al cliente de elasticsearch y se obtiene la respuesta.
            IndexResponse indexResponse = client.index(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    //para devolver todos los elementos dentro del path /tabla/items dentro de elasticsearch
    //se realizo por medio de una busqueda en la que luego de indicar el path
    //se le peticionó que devolviera tod lo que encontrara
    //una vez obtenida la respuesta que contiene la cantidad de hits que tuvo durante la busqueda
    //se pudo recorrer cada hit para obtener el source que contenia cada elemento en formato Json
    //para luego transformalo en un objeto y agregarlo a una lista que luego será retornada.
    public Collection<Item> getItems() {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("tabla");
        searchRequest.types("items");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        List<Item> list = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            // do something with the SearchHit
            String sourceAsString = hit.getSourceAsString();
            list.add(new Gson().fromJson(sourceAsString, Item.class));
        }
        return list;
    }

    @Override
    //GetRequest permite obtener lo que se encuentre en el path que se le indique
    //Lo obtiene de elastic search en formato JSON por eso luego se separa el contenido y se lo
    //convierte a objeto el cual será retornado.
    public Item getItem(String id) {
        GetRequest getRequest= new GetRequest("tabla","items", id);
        GetResponse getResponse = null;
        try {
            getResponse = client.get(getRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
            String sourceAsString = getResponse.getSourceAsString();
        return new Gson().fromJson(sourceAsString, Item.class);

    }

    @Override
    //La edición del item se realizó por medio de UpdateRequest en el cual se le indica cual
    //es el objeto de la BD que será modificado, como argumento viene el objeto que tiene las actualizaciones
    //correspondientes el cual es transformado a Json y se lo agrega al request
    //se manda el request al cliente (la BD) para que sea modificado.
    //y finalmente se retorna el elemento modificado.
    public Item editItem(Item forEdit) throws ItemException {
        UpdateRequest request = new UpdateRequest("tabla", "items", forEdit.getId());
        String sourceAsString= new Gson().toJson(forEdit);
        request.doc(sourceAsString, XContentType.JSON);
        UpdateResponse updateResponse=null;
        try {
            updateResponse = client.update(request);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(sourceAsString, Item.class);
    }

    @Override
    //se le indica mediante el id cual es el objeto que sera eliminado
    //se lo agrega en el request y se envia al cliente para que sea eliminado
    public void deleteItem(String id) {
        DeleteRequest request = new DeleteRequest("tabla","items", id);
        try {
            DeleteResponse deleteResponse = client.delete(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
