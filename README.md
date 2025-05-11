# BCNC Test PriceApplier

## Descripción

Este proyecto implementa una API RESTful utilizando **Spring Boot**, **Gradle** y **H2** para aplicar la tarifa que corresponda a un producto segun su fecha de aplicacion y prioridad

Se realizaron 2 implementaciones de la logica de negocio, en primer lugar, se realiza una consulta mas general a la bdd, de manera de analizar, filtrar y mapear la respuesta correspondiente segun la prioridad, todo a travez del codigo de la aplicacion, iterando las respuestas que coincidan con el criterio de la busqueda y realizando las operaciones mencionadas, esto para mantener cierta flexibilidad interna
En segundo lugar, mas apto para delegar peso y responsabilidad en la base de datos, se realiza la consulta mediante una query jpa que realiza la validacion de fecha y de prioridad a nivel de la bdd, con lo cual el codigo solo se encarga de mapear la respuesta

El desarrollo se implemento en una arquitectura por capas, respetando los principios solid, DDD y codigo limpio

## Endpoints Implementados

1. **POST /bcnc/applyRate**: Aplicacion de tarifa a producto mediante operaciones en memoria, es decir, validando y decidiendo en codigo. 
   - **Request Body**:
     ```json
     {
      "productId": 35455,
      "brandId": 1,
      "applicationDate": "2020-06-14T10:00:00"
      }
     ```
   - **Response**:
     ```json
     {
      "id": 1,
      "brand": {
        "id": 1,
        "name": "ZARA"
      },
      "startDate": "2020-06-14T00:00:00",
      "endDate": "2020-12-31T23:59:59",
      "priceList": 1,
      "productId": 35455,
      "priority": 0,
      "price": 35.50,
      "curr": "EUR"
      }
     ```

2. **GET /bcnc/applyRate/v2**: Aplicacion de la tarifa delegando responsabilidad a la BDD.
   - **Request Body**:
   - ```json
     {
      "productId": 35455,
      "brandId": 1,
      "applicationDate": "2020-06-14T10:00:00"
      }
     ```
   - **Response**:
     ```json
     {
      "id": 1,
      "brand": {
        "id": 1,
        "name": "ZARA"
      },
      "startDate": "2020-06-14T00:00:00",
      "endDate": "2020-12-31T23:59:59",
      "priceList": 1,
      "productId": 35455,
      "priority": 0,
      "price": 35.50,
      "curr": "EUR"
      }
     ```

## Tecnologías

- **Backend**: Spring Boot
- **Base de Datos**: H2
- **Java**: 17
- **Build Tool**: Gradle

## Requerimientos

1. **Java 17+**
2. **Gradle** para la construcción del proyecto.

## Instalación

### 1. Clonar el repositorio y levantarlo
Descargar el repo
```bash
git clone https://github.com/kpinedaperez2/BCNC-Test
```

### 2. Ejecutar Tests
Para la ejecucion de las pruebas unitarias de mockito e integrales de mockmvc:
```bash
./gradlew test
```

### 3. Postman
Se incluyo una collection de postman para facilitar la importacion y ejecucion de los curls por cada caso de uso indicado en la consigna, importar el archivo **BCNC.postman_collection.json**

<!-- CONTRIBUTING -->
## 4. Contribuciones

Si bien en el proyecto se tuvo una consigna, ofrece muchas posibilidades de implementacion que quedan en modo "potencial", implementacion de colas de mensajeria para la emision y recepcion de pedidos de aplicacion de tarifas, logging con herramientas externas como datadog, o, si se quisiera implementar otro tipo de bdd, tambiense puede levantar en una imagen docker

Contribuciones y recomendaciones para ayudar a crecer son bienvenidas.

1. Realiza el fork del proyecto
2. Crea una rama feature (`git checkout -b feature/rama1`)
3. Realiza el Commit de los cambios (`git commit -m 'Cambios en la rama1'`)
4. Push a la rama (`git push origin feature/rama1`)
5. Realiza el pull request
