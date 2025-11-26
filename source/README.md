# Proyecto Final · E-commerce con Spring Boot y Kafka

**Alumno:** Daniel Zerain Medinaceli
**Fecha:** 26/11/2025
**Curso:** Spring Boot & Apache Kafka: Desarrollo de Microservicios y Mensajería en Tiempo Real- i-Quattro

## Arquitectura y estructura

Se crearon 3 Microservicios para la gestion de las ordenes generadas en el Ecommerce, cada una conectada a su propia base de datos, se gestiona el bus de eventos con  Apache Kafka

![product](screenshots/arquitectura.png)

**product-service**

En este servicio se gestionan los productos y sus categorias, expone dos api

![category](screenshots/categoryController.png)

![product](screenshots/productController.png)

**inventory-service**

En este servicio se gestionan el inventario de los productos

![inventory](screenshots/inventoryController.png)

**order-service**

En este servicio se gestionan las ordenes de compra de los productos

![order](screenshots/orderController.png)

## Funcionalidad REST

Todos los servicios tienen los endpoint basicos de un CRUD

**product-service**

Este microservicio tiene dos servicios el de categorias y de producto 

***Servicio de Categoria***

![alt text](screenshots/categoria_rest.png)

Cuenta con 3 endpoint para la consulta y el registro de las categorias

***Servicio de Producto***

Cuenta con 6 endpoint que permiten el registro, modificacion, eliminacion y consulta de los productos registrados

![alt text](screenshots/product_rest.png)

**inventory-service**

El servicio cuenta con 5 endpoint quie permiten el registro, eleiminacion y consulta de los items del inventario

![alt text](screenshots/inventory_api.png)

Se muestra la secuencia que sigue el proceso principal de este servicio que es la creacion el item en el inventario 

![alt text](screenshots/seq1(2).png)

**order-service**

![alt text](screenshots/orders_api.png)

Se muestra la secuencia que sigue el proceso principal de este servicio que es la creacion de la orden 

![alt text](screenshots/seq1(1).png)

### Flujo completo del Ecommerce

- Creacion de categorias

![alt text](screenshots/cp08.png)

- Creacion de productos

![alt text](screenshots/cp10.png)

- Creacion de inventario

![alt text](screenshots/cp11.png)

- Creacion de Orden

![alt text](screenshots/cp12.png)

- Verificacion de orden Orden

![alt text](screenshots/cp13.png)

- Verificacion del estado del inventario

![alt text](screenshots/cp14.png)

- Verificacion del estado de la orden

![alt text](screenshots/cp15.png)

- Listado de las ordenes y su detalle

![alt text](screenshots/cp16.png)

### Validacion y manejo de excepciones

Para todos los request se valida la informacion requerida, mostrando los mensajes con el datos incorrecto, del mismo modo se definieron excepciones en un formato estandar para todas las respuestas

![alt text](screenshots/cp09.png)

![alt text](screenshots/cperr2.png)


## Integracion Kafka

Todos los microservicios se conectan a Kafka para consumir y producir enventos, una vez levantada la instancia de kafka en docker, se deben crear los topicos, para esto se utilizo el cliente de kakfa y se ejecutaron los siguientes comandos:

```bash
kafka-topics --bootstrap-server localhost:9092 --create --topic ecommerce.inventory.updated --partitions 5  --replication-factor 1

kafka-topics --bootstrap-server localhost:9092 --create --topic ecommerce.orders.cancelled --partitions 5  --replication-factor 1

kafka-topics --bootstrap-server localhost:9092 --create --topic ecommerce.orders.confirmed --partitions 5  --replication-factor 1

kafka-topics --bootstrap-server localhost:9092 --create --topic ecommerce.orders.placed --partitions 5  --replication-factor 1

kafka-topics --bootstrap-server localhost:9092 --create --topic ecommerce.products.created --partitions 5  --replication-factor 1
```

Podemos consultar los topicos creados con el comando:

```bash
kafka-topics --bootstrap-server localhost:9092 --list
```

![alt text](screenshots/cp05.png)

Podemos consultar el detalle de cada topico creado con el comando:

```bash
kafka-topics --bootstrap-server localhost:9092 --describe --topic ecommerce.products.created
```

![alt text](screenshots/kafka_estado_tp.png)

cuando se consumen los servicios se generar los eventos en kafka, como se muestra a continuacion el flujo de consumo:

**Eventos en Product_service**

![alt text](screenshots/cp17.png)

**Eventos en inventory_service**

![alt text](screenshots/cp18.png)

**Eventos en inventory_service**

![alt text](screenshots/cp19.png)

## Base de datos

Se utiliza 1 base de datos para cada servicio, las mismas fueron creadas en la careacion del contenedor de docker

![alt text](screenshots/docker_pg.png)

Se crean con el script:

```bash
#!/bin/bash

set -e
set -u

function create_user_and_database() {
	local database=$1
	echo "  Creando base de datos '$database'"
	psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
	    SELECT 'CREATE DATABASE $database'
	    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = '$database')\gexec
EOSQL
}

if [ -n "$POSTGRES_MULTIPLE_DATABASES" ]; then
	echo "Solicitud de creación de múltiples bases de datos: $POSTGRES_MULTIPLE_DATABASES"
	for db in $(echo $POSTGRES_MULTIPLE_DATABASES | tr ',' ' '); do
		create_user_and_database $db
	done
	echo "Bases de datos creadas exitosamente"
fi

echo "host all all 0.0.0.0/0 scram-sha-256" >> "$PGDATA/pg_hba.conf"

echo "Reglas de pg_hba.conf actualizadas"
```

![alt text](screenshots/cp03.png)

El mismo es registrado en la carpeta docker-entrypoint-initdb.d

Como se puede observar se crearon las bases de datos de los servicios

![alt text](screenshots/cp04.png)

Se tienen las siguientes bases de datos

**ecommerce_inventory**

![alt text](screenshots/ent1.png)

**ecommerce_orders**

![alt text](screenshots/ent2.png)

**ecommerce_products**

![alt text](screenshots/ent3.png)


## Configuracion

## Documentacion API