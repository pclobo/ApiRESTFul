# ApiRESTFul

El presente software es una API Restful que utiliza una base de datos no relacional : Elasticsearch para almacenar información
de items, esta desarollado en JAVA, se utilizo la estructura de los items visualizada en la API de Mercado Libre.
para ejecutarlo se indica tener corriendo previamente elasticsearch, y para utilizar este software hay que descargar el 
archivo "ApiRESTFul.jar.zip" y descomprimirlo. Ingresar por consola a la carpeta en la que fue descargado el archivo .jar 
y ejecutar el siguiente comando "java -jar ./ApiRESTFul.jar".
una vez corriendo se pueden probar mediante diferentes comandos que fueron previamente desarrollados para interpretar
las consultad a elasticsearch mediante la API Restful (POST, GET, PUT, DELETE) 
Se usa el siguiente formato de consulta mediandte la consola de comandos:
POST:
curl -X POST http://localhost:8080/items/889 -d '{"id":"889","title":"Teclado","category_id":"Insumo Informatico",
"price":50.0,"currency_id":"Pesos","available_quantity":10,"buying_mode":"Comprar ahora","listing_type_id":"Oro",
"condition":"Nuevo","description":"Teclado a Pilas","video_id":"https://www.youtube.com","warranty":"1 año",
"pictures":[{"source":"https://d243u7pon29hni.cloudfront.net/images/products/1148608_l.png"}]}'

GET: 
curl -X GET http://localhost:8080/items/889

curl -X GET http://localhost:8080/items

PUT:
curl -X PUST http://localhost:8080/items/889 -d '{"id":"889","title":"Mouse","category_id":"Insumo Informatico",
"price":50.0,"currency_id":"Pesos","available_quantity":10,"buying_mode":"Comprar ahora","listing_type_id":"Oro",
"condition":"Nuevo","description":"Teclado a Pilas","video_id":"https://www.youtube.com","warranty":"1 año",
"pictures":[{"source":"https://cdn.tecnogaming.com/wp-content/uploads/2017/05/Rzr_Lancehead_V4.jpg"}]}'

DELETE:
curl -X DELETE http://localhost:8080/items/889

