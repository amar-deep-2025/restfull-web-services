# 📡 RESTful Web Services with Spring Boot

A RESTful web service project built using **Java** and **Spring Boot**, integrated with **Talend APIs** for seamless data integration and processing. This project exposes clean, scalable, and secure REST endpoints for data operations, backed by **MySQL**. It is tested using **Postman** and designed for **real-time API consumption**.

---

## ✅ Features

- Clean RESTful endpoints using Spring Boot
- Talend API integration
- Real-time data processing
- MySQL database backend
- Swagger UI for interactive API testing
- Validation and Exception Handling
- Content Negotiation (JSON/XML)
- Internationalization (i18n)
- API Versioning
- HATEOAS support
- Static & Dynamic Response Filtering

---

## 📡 Response Status Codes

| Code | Meaning                 | Usage                                    |
|------|-------------------------|------------------------------------------|
| 200  | OK                      | Successful response                      |
| 201  | Created                 | Resource created successfully            |
| 204  | No Content              | Deleted successfully, no content to show|
| 400  | Bad Request             | Validation or client-side error          |
| 401  | Unauthorized            | Authentication failed                    |
| 404  | Not Found               | Resource not found                       |
| 500  | Internal Server Error   | Server-side issue                        |

**Use `ResponseEntity<T>`** to return status code + response body.

---

## 🔁 Resource Location Header

When creating a new resource (e.g., via POST), return the **URI of the new resource** using the `Location` header.

```java
URI location = ServletUriComponentsBuilder.fromCurrentRequest()
    .path("/{id}")
    .buildAndExpand(savedUser.getId())
    .toUri();

return ResponseEntity.created(location).build();
```
**Q:Location?**  
ANS: It tells the client where to find the newly created resource.

**Q:Location Header?**  
ANS: A uri pointing to the newly created resource

Add Exception when Resource Not Found

# Implementing Generic Exception Handling for all the Resource
1) Extends ResponseEntityExceptionHandler-> For Handle all the Exception
2) create custom handleAllException method to handle
3) create handleUserNotFoundException method to handle NOT_FOUND Exception  

# Implementing RetrieveAllUser method to get all users in resource
1) add  RetrieveAllUser  method  
Class :UserDaoService  

```
@Component
public class UserDaoService {

	private static List<User> users=new ArrayList<>();
	private static int userCount=0;
	static {
		users.add(new User(++userCount,"Varun","8974844894","varun@2018gmail.com",LocalDate.now().minusYears(5)));
		users.add(new User(++userCount,"Amar","8974847839","amar@2018gmail.com",LocalDate.now().minusYears(8)));
		users.add(new User(++userCount,"Dheeredra","8974849849","dheerendra@2018gmail.com",LocalDate.now().minusYears(10)));
		users.add(new User(++userCount,"Kajal","8974768738","kajal@2018gmail.com",LocalDate.now().minusYears(3)));
	}
	
	public List<User> getAllUser(){
		return users;
	}
}
```
**class :UserResources  
```
public class UserResource {

	private final UserDaoService service;
	@Autowired
	public UserResource(UserDaoService service) {
		this.service = service;
	}

	// Get all users
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return service.getAllUser();
	}
```

# Implementing The validations
1) @valid arguments  from jakarta.validations  
2) Pass  @Valid as arguments in  Post method with RequestBody  
3) create method handleMethodArgumentNotValid with @Nullable and @Override  
4)  Found Total Errors ex.getErrorCount() and First error provide by  ex.getFieldError().getDefaultMessage()  
5) return responseEntity with (errorDetails, StatusCode and statusBody)  


# Add Maven dependency in pom.xml for the validations  
```
		<dependency>
			<groupId>jakarta.validation</groupId>
			<artifactId>jakarta.validation-api</artifactId>
			<version>3.0.2</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
```

# ✅ Bean Validation Annotations in Spring Boot  
| Annotation   | Description                                                                          |
|--------------|--------------------------------------------------------------------------------------|
| `@NotBlank`  | Validates that the string is **not null and not empty**, after trimming whitespace. |
| `@Size`      | Checks that the string length is within a specified **min and max** range.          |
| `@Pattern`   | Validates the string using a **regular expression**.                                |
| `@Email`     | Validates that the string is in a **valid email format**.                           |
| `@Past`      | Ensures the date is a **past date**.                                                |
| `@Max`       | Checks the number is **less than or equal** to a given maximum.                     |
| `@Min`       | Checks the number is **greater than or equal** to a given minimum.                  |

# Add Validations  
```
package com.fullstacklogic.rest.webservices.user;
import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class User {

	private Integer id;
	
	@NotBlank
	@Size(min = 3, message = "Name should be at least 3 characters")
	private String name;

	@NotBlank(message = "Phone number is required")
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number must be 10 digits and start with 6-9")
	private String phoneNo;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String mailId;


    @NotNull(message = "Birthdate is required")
	@Past(message = "Birthdate must be in the past")
	private LocalDate birthDate;

	public User() {
	}

	public User(Integer id, String name, String phoneNo, String mailId, LocalDate birthDate) {
		this.id = id;
		this.name = name;
		this.phoneNo = phoneNo;
		this.mailId = mailId;
		this.birthDate = birthDate;
	}

	// Getters and Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", phoneNo=" + phoneNo + ", mailId=" + mailId + ", birthDate="
				+ birthDate + "]";
	}
}
```  
# Swagger UI
1) Swagger Ui allows you to make to check, test, and document your rest APIs interactively - directly from your browser. It's powerful tool especially when working with Spring Boot APIs.  
2) See all your REST endpoints  
3) send Test requests  
4)  see real-time response  
5) check request models  
6) authorize APIs  
7) Generate API docs  

## Add Maven Dependency 
```<!-- OpenAPI Swagger UI -->
	<dependency>
	<groupId>org.springdoc</groupId>
		<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
		<version>2.5.0</version>
	</dependency>
```  
# Content Negotiation
1) Content Negotitation is the mechanism used by the springboot (and rest in genral) to serve different data formats (like JSON, XML, etc) from the same API endpoint, based on the what the client asks for .  
In other word content negotiation lets the server return data in the format the client wants (e.g; JOSN or XML) using the Accept header or URL extension.

# Add Maven dependency
```
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
</dependency>
```

# Internationalization  
Internationalization is the process of designing  your application so it can be support multiple languages and regional language setting (like data/time/currency formats) without changing your code.

## Steps to Use Internationalization in Spring boot

1) Create message Properties files  
*messages.properties
```
good.morning.message=Good Morning
```
*messages_nl.properties  
```
good.morning.message=Goedemogen
```
*messages_it.properties  
```
good.morning.message=Buongiorno
```  
2) Configure MessageSource bean in SpringBoot
```
@Bean
public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("messages"); // name of your properties files
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
}
```  
3) Use in Controller :
```
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
```
