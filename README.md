# position-book-app
Exposes Rest API to use position book related operation

Author: Sandeep Das

E-mail: sandeepdas25@gmail.com

Main-Class: PositionBookApp.java

Rest-client used: Postman

Maven command for execution:
mvn clean install
mvn exec:java  -Dexec.mainClass=edu.book.position.PositionBookApp

The following rest-api has been exposed: 

Create Buy Order:
---------------
@POST
http://localhost:8080/events/order

 Sample Request:

 {
        "securityId": "SEC2",
        "accountId": "ACC2",
        "quantity": 50,
        "tradeType":"buy"
 }
 
 Sample Response:
 
 {
            "tradeId":2,
            "securityId": "SEC2",
            "accountId": "ACC2",
            "quantity": 50,
            "tradeType":"buy"
     }
 
 Create Sell Order:
 ---------------
 @POST
 http://localhost:8080/events/order
 
 Sample Request:
  {
         "securityId": "SEC3",
         "accountId": "ACC3",
         "quantity": 50,
         "tradeType":"sell"
  }
  
 Sample Response:
  {
      "tradeId": 3,
      "securityId": "SEC3",
      "accountId": "ACC3",
      "quantity": 50,
      "tradeType": "sell"
  }
  
Cancel Order:
---------------
 @PUT
 http://localhost:8080/events/{tradeId}/cancel
 
 Sample Response:
 {
          "tradeId":2,
          "securityId": "SEC2",
          "accountId": "ACC2",
          "quantity": 50,
          "tradeType":"cancel"
   }

Get Trade Order filter by Account Id and Security Id:
-----------------
@GET
http://localhost:8080/events/order/{accountId}/{securityId}

Sample Response:

{
    "quantity": 0,
    "tradeOrder": [
        {
            "tradeId": 2,
            "securityId": "SEC2",
            "accountId": "ACC2",
            "quantity": 50,
            "tradeType": "cancel"
        }
    ]
}