# Online Banking App
## Introduction
This project use Spring-boot to build the apis to be used by banks.

Microservice supports following operations
 * Deposit Money
 * Transfer money between accounts in the bank
 * Reports for transactions made in an account

 
## Solution

### Security:-

Oauth2 Password Grant type is chosen for the authentication/authorisation of banking operations and implemented using spring security.

Authorisation and Resource servers are packaged in single spring boot application for this prototype.
1. User credential information are mocked in the h2 in memory database.  API not implemented for signup/create users.
2. Accounts are created/maintained against the loggedin user. Security in place to restrict account operation by other users

### API Design

The solution consists of below apis for basic online banking operations. Postman collection available 

* Accounts - responsible for Account management and transactions
  * POST /accounts/add    - Create Account
  * GET /accounts/{accountNumber}   - Get basic information on accounts
  * POST /accounts/deposit  - Deposit money to account
  
* Transfer
  * POST /transfers/local  - Api to perform intra account transfers.

* Reports
  * GET /reports/txns  - Api to return transactions for the provided account number

   The transaction list in the reports api is paginated as this can grow over period of time.

   ```
  Reporting api can be built on top of an eventual consistent datastore, considering the trade off that reports need not be real time (Near real time is fine).
  We can seperate data stores of account transactions for write and read operations. Data can be synched from master database to a analystics datastore.
   ```

### Observability
  Logs, metrics and trace are the pillars of observability.

  * Logs - Log4j + ELK stack  (ELK not part of prototype)
  * Metrics - Used micrometer library to expose internal state of  host, application (eg:- memory consumption,hikari connection pools, api success/failure rate etc).
    Also exposed custom metrics like number of accounts opened etc for better understanding of api usages. 
    Refer - http://localhost:8080/actuator/metrics/ <br/>
    Custom metrics for accounts - http://localhost:8080/actuator/metrics/Accounts . <br/>
    These metrics are exported in prometheus format as well and grafana can be used as monitoring tool.
    Prometheus metric can be viewed at http://localhost:8080/actuator/prometheus
  * Trace - When application scales to multiple microservices, distributed tracing is imported
    Jaeger open tracing framework is used in the POC to enable tracing, thereby assigning spanid, traceid for each requests

## Run the service on Docker
  Execute below steps from the root folder
  * ```./gradlew build```  - Build jar
  * ``` docker image build -t online-banking-service .```  - Build docker image
  * ``` docker run -p 8080:8080 online-banking-service ``` - Run image on docker

 

## Postman collecton
Postman collection -  ```Banking POC.postman_collection.json ```
