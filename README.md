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
## Versioning REST api  
Api versioning is the process of managing changes in your REST APIs without breaking existing clients.  
## Why Versioning  
* You want to improve or add feature without affecting users using the current API.  
* You need to maintain multiple versions of the same endpoint.  
## Variety Versioning options  

### URL : url(/v1/person/)
```
@GetMapping("/v1/person")
public PersonV1 getFirstVersionOfPerson() {
    return new PersonV1("Bob Charlie");
}

@GetMapping("/v2/person")
public PersonV2 getSecondVersionOfPerson() {
    return new PersonV2(new Name("Bob", "Charlie"));
}
```  
### Request Parameter :- url  (http://localhost:9090/person?version=1)
### Request Parameter :- url  (http://localhost:9090/person?version=2)
```
@GetMapping(path = "/person", params = "version=1")
public PersonV1 getFirstVersionOfPersonRequestParameter() {
    return new PersonV1("Bob Charlie");
}```

@GetMapping(path = "/person", params = "version=2")
public PersonV2 getSecondVersionOfPersonRequestParameter() {
    return new PersonV2(new Name("Bob", "Charlie"));
}
```  
### Request Header
```
@GetMapping(path = "/person/header", headers = "X-API-VERSION=1")
public PersonV1 getFirstVersionOfPersonRequestHeader() {
    return new PersonV1("Bob Charlie");
}

@GetMapping(path = "/person/header", headers = "X-API-VERSION=2")
public PersonV2 getSecondVersionOfPersonRequestHeader() {
    return new PersonV2(new Name("Bob", "Charlie"));
}
```
### Media Type(Content Negotiation Accept header)
```
@GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v1+json")
public PersonV1 getFirstVersionOfPersonAcceptHeader() {
    return new PersonV1("Bob Charlie");
}
@GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v2+json")
public PersonV2 getSecondVersionOfPersonAcceptHeader() {
    return new PersonV2(new Name("Bob", "Charlie"));
}
```  
## HATEOAS  
Hypermedia as the Engine of Application State (HATEOAS).  
Websites allow you to:  
See Data AND Perform Actions (Using links)  
* How about enhancing your REST API to tell consumers how to perform subsequent actions ?  

## Implementation Options:  
1. Custom Format and Implementation  
    > Difficult to maintain
2. Use Standard Implementations  
    > HAL(JSON Hypertext Application Language): Simple format that gives a consistent and easy way to hyperlink between resources in your API.  
    > Spring HATEOAS: Generate HAL responses with hyperlink to resources  
## add Maven dependecy  
```
	<!-- HATEOAS Support -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-hateoas</artifactId>
	</dependency>
```  

# Filtering
In java (especially in Spring Bot and REST APIs) , static filtering and dynamic filtering are techniques used to control which fields of a model (java class) should be included or excluded in JSON response. These techniques are particularly useful in REST APIs to protect sensitive data, reduce payload size, and customize responses for different clients.

## Static Filtering 
Static filtering is hard-coded and applied at compile time using annotations. It cannot be changed at runtime.  
Common annotations: 
```
@JsonIgnore
@JsonIgnoreProperties  
EX :
@JsonIgnore
private String value2;
```  
## Dynamic Filtering 
Dynamic filtering is applied at runtime, and you can control which fields to include/exclude dynamically per request controller.
### Requirements 
```
@JsonFilter
@MappingJacksonValue to apply filters.
```  
# Model Class :
```
@JsonFilter("SomeBeanFilter")
public class SomeBean {

	private String value1;
	//@JsonIgnore
	private String value2;
	//@JsonIgnore
	private String value3;
	public SomeBean(String value1, String value2, String value3) {
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
	}
	public String getValue1() {
		return value1;
	}
	public String getValue2() {
		return value2;
	}
	public String getValue3() {
		return value3;
	}
}
```
# Controller :
```
@RestController
public class FilteringController {

	@GetMapping("/filtering")
	public MappingJacksonValue filtering() {
		SomeBean someBean=new SomeBean("value1", "value2","value3");

		MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(someBean);
		SimpleBeanPropertyFilter filter=SimpleBeanPropertyFilter.filterOutAllExcept("value1","value3");
		FilterProvider filters=new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}
	@GetMapping("filtering-list")
	public List<SomeBean>  filtertingList(){
		return Arrays.asList(new SomeBean("value1", "value2", "value3"),
				new SomeBean("value4", "value5", "value6"));
	}
}
```  
# Spring Boot Actuator?  
Spring Boot Actuator is a powerful tool that helps you to monitoring and manages your Spring Boot application in production.  

## Spring Boot Actuator Endpoints Table
| **Endpoint**            | **URL Path**               | **Purpose**                                                             |
| ----------------------- | -------------------------- | ----------------------------------------------------------------------- |
| `Health`                | `/actuator/health`         | Shows application health (e.g. UP/DOWN status).                         |
| `Info`                  | `/actuator/info`           | Displays custom app information (name, version, etc.).                  |
| `Metrics`               | `/actuator/metrics`        | Provides application metrics (e.g. JVM, CPU, HTTP requests).            |
| `Env`                   | `/actuator/env`            | Shows all environment properties and profiles.                          |
| `Beans`                 | `/actuator/beans`          | Lists all Spring beans and their dependencies.                          |
| `Loggers`               | `/actuator/loggers`        | View/change logging levels of classes at runtime.                       |
| `Thread Dump`           | `/actuator/threaddump`     | Dumps all thread info for debugging.                                    |
| `HTTP Trace`            | `/actuator/httptrace`      | Displays last few HTTP request-response traces. *(disabled by default)* |
| `Mappings`              | `/actuator/mappings`       | Shows all URL paths and controller mappings.                            |
| `Scheduled Tasks`       | `/actuator/scheduledtasks` | Lists all scheduled tasks.                                              |
| `Startup` (from 2.3+)   | `/actuator/startup`        | Shows Spring Boot app startup steps (time taken by each phase).         |
| `Shutdown` *(optional)* | `/actuator/shutdown`       | Allows app shutdown via POST (requires enabling explicitly).            |


## Add Maven dependency  
```
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-actuator</artifactId>
	</dependency>
```
# DataBase Connectivity && JPA-Implementation  

## H2 DataBase  
H2 is a lightweight , fast and open-source in memory-database written in Java. It is commonly used for development , testing, and demos because it doesn't require setup and can run entirely in memory. H2 also includes a built-in-web console for querying and manage data via /h2-console  


## JPA(Java Persistence API) :  
JPA is a java specification that enables developers to manage relational data in a java applications. It provides an abstraction layer over ORM (Object-Relational-Mapping) tools like Hibernate. With JPA, you can map java classes to database tables using annotations like @Entity, @Id and @GeneratedValue, making database interactions simpler and more maintainable.  

| Annotation                                                  | Usage Example                                               | Description                                                                |
| ----------------------------------------------------------- | ----------------------------------------------------------- | -------------------------------------------------------------------------- |
| `@Entity`                                                   | `@Entity`                                                   | Marks the class as a JPA entity (maps to a DB table).                      |
| `@Table(name = "users")`                                    | `@Table(name = "users")`                                    | Specifies the name of the database table.                                  |
| `@Id`                                                       | `@Id`                                                       | Specifies the primary key of the entity.                                   |
| `@GeneratedValue(strategy = GenerationType.IDENTITY)`       | `@GeneratedValue(strategy = GenerationType.IDENTITY)`       | Auto-generates primary key values (e.g., auto-increment).                  |
| `@Column(name = "username", nullable = false, length = 50)` | `@Column(name = "username", nullable = false, length = 50)` | Defines column properties like name, length, nullability.                  |
| `@Transient`                                                | `@Transient`                                                | Excludes the field from persistence (not stored in the DB).                |
| `@Temporal(TemporalType.DATE)`                              | `@Temporal(TemporalType.DATE)`                              | Specifies date type for `java.util.Date`.                                  |
| `@OneToMany` / `@ManyToOne` / `@OneToOne` / `@ManyToMany`   | `@OneToMany(mappedBy = "user")`                             | Defines relationships between entities.                                    |
| `@JoinColumn(name = "user_id")`                             | `@JoinColumn(name = "user_id")`                             | Specifies the foreign key column.                                          |
| `@Lob`                                                      | `@Lob`                                                      | Used to store large objects like images, files, or long texts (CLOB/BLOB). |

# add Dependencies  
### JPA dependency  
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```  
### Add H2 Database dependency  
```
<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
	<scope>runtime</scope>
</dependency>
```
# Application.properties 
### Enable H2 Console  
```
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console  
```
### Configure in-memory H2 DB  
```
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=amar.deep
spring.datasource.password=amar.deep@123
```  
### JPA / Hibernate settings
```
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```  
### Enable SQL file Loading
```
spring.jpa.defer-datasource-initialization=true
```
# Add UserRepository and PostRepository  
### UserRepository  
```
package com.fullstacklogic.rest.webservices.jpa;  
import java.util.Optional;  
import org.springframework.data.jpa.repository.JpaRepository;  
import com.fullstacklogic.rest.webservices.user.User;  
public interface UserRepository extends JpaRepository<User, Integer> {  
	void deleteById(Optional<User> user_id);  
}  
```
### PostRepository  
```  
package com.fullstacklogic.rest.webservices.jpa;  
import java.util.Optional;  
import org.springframework.data.jpa.repository.JpaRepository;  

import com.fullstacklogic.rest.webservices.user.Post;  
import com.fullstacklogic.rest.webservices.user.User;  

public interface PostRepository extends JpaRepository<Post, Integer> {  
}  
```


