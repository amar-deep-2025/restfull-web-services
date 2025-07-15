# restfull-web-services
A RESTful web service project built using Java and Spring Boot, integrated with Talend APIs for seamless data integration and processing. This project exposes clean, scalable, and secure REST endpoints for data operations, with MySQL as the backend database. Tested using Postman and designed for real-time API consumption.


# Add Response Status 
->Return the correct reposnse status
-->Resource not found =>404
-->Server exception =>500
-->Validation error =>400

Important Response Status 
-->200 => Success
-->201 => Created
-->204 => No Content
-->401 => Unauthorized (when authorization fails)

-->400 => Bad Request (such as validation error)

-->404 =>Resource Not Found
-->500 =>Server Error

--> ResponseEntity is used to return the HTTP response status along with the response body in a Spring Boot application.

Q-->Location?
->It tells the client where to find the newly created resource.

Q-->Location Header?
->A uri pointing to the newly created resource