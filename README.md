# DevCamp Services

## Table of contents
- [Summary](#summary)
- [Repository](#repository)
- [Running the services](#running-the-services)
- [Interacting with the services](#interacting-with-the-services)
- [Postman](#postman)
- [SoapUI](#soapui)

## Summary

There are four mock services available for consumption as part of the DevCamp practical exercise.
- **Auth:** REST web service to obtain JWT tokens for auth with other services
- **KYC:** REST web service to ontain KYC (Know Your Customer) information for customers
- **DHA:** REST web service to obtain DHA (Dept. of Home Affairs) information for customers
- **Credit Check:** SOAP web service to perform a credit check for customers

## Repository

The services can be found as Docker images in the [internaltraining-devcamp](https://harbor.entelectprojects.co.za/harbor/projects/37/repositories) repository on Entelect's Harbor container registry.

For an introduction to Harbor, see the [Harbor Introduction](https://holocrons.entelect.co.za/build/container-registries/harbor-introduction/) holocron.

In order to access the Docker images on Harbor you will need to log in from the command line with the following command:

``` Bash
echo "YOUR_PASSWORD_HERE" | docker login harbor.entelectprojects.co.za --username "USER_NAME_HERE" --password-stdin
```

## Running the services

The services can be started in your local Docker host using the accompanying [Docker compose file](compose.yaml). Two of the services require configuration of a public key which is used internally for JWT auth. This public key is passed into the Docker composition as an environment variable, named `PUB_KEY`.

The public key is available as a [PEM-encoded public key file](app.pub).

You can set the environment variable and run docker compose all in one fell swoop with the following command:

Linux shell:
```bash
PUB_KEY=$(cat app.pub) && docker-compose up
```

Powershell:
```PowerShell
$env:PUB_KEY = Get-Content app.pub; docker-compose up
```

## Interacting with the services

### Auth Service

The Auth service can be accessed at http://localhost:8080 and exposes a single endpoint:

- [POST] `/token`.

The service requires Basic Auth, making use of a username and password.

The service responds to a successful request to the `/token` endpoint with a JWT in the response body. This JWT can be used for subsequent calls to the [KYC](#kyc-service) and [DHA](#dha-service) services.

The returned JWT is valid only for an hour, after which time a new JWT will have to be obtained from the Auth service.

### KYC Service

The KYC service can be accessed at http://localhost:8081 and exposes a single endpoint:

- [GET] `/kyc/{customerId}`

The service requires a Bearer token in the Authorization header. The bearer token must be a JWT obtained from the Auth service.

The service is documented in the accompanying [OpenAPI document](kyc.yaml) or at http://localhost:8081/swagger/index.html when the service is running.

### DHA Service

The DHA service can be accessed at http://localhost:8082 and exposes four endpoints:

- [GET] `/status/people`
- [GET] `/status/marital/{idNumber}`
- [GET] `/status/duplicateId/{idNumber}`
- [GET] `/status/living/{idNumber}`

The service requires a Bearer token in the Authorization header. The bearer token must be a JWT obtained from the Auth service.

The service is documented in the accompanying [OpenAPI document](dha.yaml) or at http://localhost:8082/swagger/index.html when the service is running.

### Credit Check Service ###

The Credit Check service can be accessed at http://localhost:8083 and exposes a single endpoint:

- `/CreditCheck`

The service requires Basic Auth, making use of a username and password.

The service is documented in the accompanying [WSDL document](creditcheck.wsdl)

## Postman

In the [/Postman](Postman/) folder of this repository, there are four collections that can be imported into your Postman workspace:

- [Auth Service.postman_collection.json](Postman/Auth%20Service.postman_collection.json)
- [KYC Service.postman_collection.json](Postman/KYC%20Service.postman_collection.json)
- [DHA Service.postman_collection.json](Postman/DHA%20Service.postman_collection.json)
- [Credit Check Service.postman_collection.json](Postman/Credit%20Check%20Service.postman_collection.json)

Additionally there is also an environment that can be imported into your workspace:

- [Docker.postman_environment.json](Docker.postman_environment.json)

These collections and environment will enable you to test all four of the services if they are running through Docker Compose.

It is worth pointing out that the `POST Token` request in the `Auth Service` collection includes a Test Script which automatically writes the returned JWT into the `{{jwt}}` variable of the active environment. This means that you do not have to manually copy and paste the JWT in order to test the KYC Service and DHA Service.

## SoapUI

In the [/SoapUI](SoapUI/) folder of this repository, there is a [SoapUI project file](SoapUI/creditcheck-soapui-project.xml) which includes some test SOAP Api calls to the Credit Check Service.