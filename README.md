# restfull-web-services
A RESTful web service project built using Java and Spring Boot, integrated with Talend APIs for seamless data integration and processing. This project exposes clean, scalable, and secure REST endpoints for data operations, with MySQL as the backend database. Tested using Postman and designed for real-time API consumption.


# Add Response Status 
->Return the correct reposnse status
-->Resource not found =>404
-->Server exception =>500
-->Validation error =>400

# Important Response Status 
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

-->Add Exception when Resource Not Found

# Implementing Generic Exception Handling for all the Resource
1) Extends ResponseEntityExceptionHandler-> For Handle all the Exception
2) create custom handleAllException method to handle
3) create handleUserNotFoundException method to handle NOT_FOUND Exception 

# Implementing Delete method to delete a user resource
1) add deleteById method

# Implementing The validations
1) @valid arguments  from jakarta.validations
2) Pass  @Valid as arguments in  Post method with RequestBody
3) create method handleMethodArgumentNotValid with @Nullable and @Override 
4)  Found Total Errors ex.getErrorCount() and First error provide by  ex.getFieldError().getDefaultMessage()
5) return responseEntity with (errorDetails, StatusCode and statusBody)

# Important Annotation
1) @NotBlank -> check empty String.
2) @size -> check min and max length of the String.
3) @Pattern  -> provide regular expression for the validation.
4) @Email -> check email format.
5) @Past ->  check birthdate must be in past
6) @Max -> check must be less than or equal to value
7) @Min  -> Must be greater than or equaklt to value
# Swagger UI
1) Swagger Ui allows you to make to check, test, and document your rest APIs interactively - directly from your browser. It's powerful tool especially when working with Spring Boot APIs.

2) See all your REST endpoints
3) send Test requests
4)  see real-time response 
5) check request models 
6) authorize APIs 
7) Generate API docs

# Content Negotiation
1) Content Negotitation is the mechanism used by the springboot (and rest in genral) to serve different data formats (like JSON, XML, etc) from the same API endpoint, based on the what the client asks for . 

In other word content negotiation lets the server return data in the format the client wants (e.g; JOSN or XML) using the Accept header or URL extension.

# Enable Content Negotiation
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
</dependency>

# Internatiolization
## 
Internationalization is the process of designing  your application so it can be support multiple languages and regional language setting (like data/time/currency formats) without changing your code.

## Steps yto Use Internationalization in Spring boot

1) Create message Properties files
Ex : 
-->messages.properties
-->messages_hi.properties
-->messages_fr.properties

2) Configure MessageSource bean in SpringBoot

@Bean
public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("messages"); // name of your properties files
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
}

3) Use in Controller
@RestController
public class HelloWorldController {

    private final MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/hello-world-internationalized")
    public String helloWorldInternationalized() {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
    }
}



