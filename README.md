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

# Implementing RetriveAllUser method to get all user resource
1) add deleteById method  
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