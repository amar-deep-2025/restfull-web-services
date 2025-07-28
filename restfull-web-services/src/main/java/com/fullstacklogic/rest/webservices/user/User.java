package com.fullstacklogic.rest.webservices.user;

import java.time.LocalDate;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity(name="user_details")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	@Size(min = 3, message = "Name should be at least 3 characters")
	private String name;

	@NotBlank(message = "Phone number is required")
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number must be 10 digits and start with 6-9")
	@Column(name="phone_no")
	private String phoneNo;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	@Column(name="mail_id")
	private String mailId;


    @NotNull(message = "Birthdate is required")
	@Past(message = "Birthdate must be in the past")
    @Column(name="birth_date")
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
