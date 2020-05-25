# Parking Management API
REST API of a parking management application built for test purposes and written with Java 1.8 and the Springboot framework. The API allows to:
- **Register** a new Parking
- **Delete** a Parking through its ID
- **Park a car** in a given parking, receiving a ticket
- **Remove a car** from a given parking, receving an invoice
- **Retrieve** the list of tickets, invoices and parkings.

### Dependencies
There two provided ways to run this application: it can either be deployed on localhost or it can be deployed on a kubernetes cluster.  

When deploying on localshost, the application assumues that a MongoDB instance is running on the same machine where the backend runs and listening to default port 27017. For more information about mongoDB setup follow [Mongo installation guide](https://docs.mongodb.com/manual/installation/). A possible improvement could be dockerization to avoid introducing dependencies. 

When deploying on kubernetes, it is necessary to make sure that kubctl is properly configured to manage the desired cluster. The kubernetes deployment files will open the port 30280 on all nodes of the cluster. The application will be reachable at NodeIp:30280.

### How to compile and run on localhost
To clone, compile and run, issue the following commands:
```
$ git clone https://github.com/valerioferretti92/parking-management-api.git
$ cd parking-management-api
$ mvn clean install
$ java -jar target/parking-management-api-1.0-SNAPSHOT.jar
```

### How to compile and run on a Kubernetes cluster
The application has been developed to be deployed on a Kubernetes cluster. To clone, compile and run, issue the following commands:
```
$ git clone https://github.com/valerioferretti92/parking-management-api.git
$ cd parking-management-api/k8sdeploy
$ ./deploy
```

### Authentication
All API endpoints are protected by JWT authentication BUT the following:
- Login: /api/v1/account/login
- Signup Admin: /api/v1/account/admin/signup
- Signup User: /api/v1/account/user/signup

In order to be able to interact with all other endpoints, it is necessary to login first, get the JWT token in the "Authorization" header of the response. Such token must then be included in any other request that needs authentication in the "Authorization" header.
There two different groups of accounts: users and admins. Admins are able to:
- create parkings
- delete parkings
- remove their own account
- modify their own account
- get all tickets / invoices. 

Users are abe to:
- remove their own account
- modify their own account
- park cars
- unpark cars. 

Login:
 ```
curl --location --request POST 'http://localhost:8080/api/v1/account/login' \
--header 'Content-Type: application/json' \
--data-raw '{
	"email":"admin@admin.com",
	"password":"admin"
}'
 ```

Signup as user:
 ```
curl --location --request POST 'http://localhost:8080/api/v1/account/user/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
	"email":"valerio@ferretti.com",
	"password":"valerioferretti"
}'
 ```

Signup as admin:
 ```
curl --location --request POST 'http://localhost:8080/api/v1/account/admin/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
	"email":"valerio@ferretti.com",
	"password":"valerioferretti"
}'
 ```

### How to test
The API can be tested by issuing HTTP request to the machine where the application runs at port 8080. The following shows how to test through CURL:

Register a parking:
```
curl --location --request POST 'http://localhost:8080/api/v1/parking' \
--header 'Content-Type: application/json' \
--header 'Authorization: jwt-token' \
--data-raw '{
	"parkingId": "parkingId",
	"parkingType": "GASOLINE_CAR_PARK",
	"pricingType": "HOURLY_PRICING",
	"capacity": 3,
	"fees": {
		"hourlyFee": 0,
		"fixedFee":5
	}
}'
```
Delete a parking:
```
curl --header 'Authorization: jwt-token' --location --request DELETE 'http://localhost:8080/api/v1/parking/{parkingId}'
```
Park a car:
```
curl --location --request PUT 'http://localhost:8080/api/v1/parking/checkin/{parkingId}' \
--header 'Content-Type: application/json' \
--header 'Authorization: jwt-token' \
--data-raw '{
	"carId": "carId",
	"carType": "GASOLINE"
}'
```
Remove a car:
```
curl --location --request PUT 'http://localhost:8080/api/v1/parking/checkout/{parkingId}' \
--header 'Content-Type: application/json' \
--header 'Authorization: jwt-token' \
--data-raw '{
	"carId": "carId"
}'
```
Get all parkings / tickets / invoices
```
curl --header 'Authorization: jwt-token' --location --request GET 'http://localhost:8080/api/v1/parking'
curl --header 'Authorization: jwt-token' --location --request GET 'http://localhost:8080/api/v1/invoice'
curl --header 'Authorization: jwt-token' --location --request GET 'http://localhost:8080/api/v1/ticket'
```
parkingType, pricingType and carType are enums which accept the following values:
- **carType**: GASOLINE, ELECTRIC_20KW, ELECTRIC_50KW
- **parkingType**: GASOLINE_CAR_PARK, E20KW_CAR_PARK, E50KW_CAR_PARK
- **pricingType**: HOURLY_PRICING, HOURLY_FIXED_PRICING

Cars can not be parked in parkings that do not match their type. E20WKW cars can only be parked in E20KW etc...
