package com.grail.spring.participantapi.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

@Embeddable
public class Address {
		
	@Transient
	String address_updated;
	
	@Column(name = "address_1")
	@NotEmpty(message = "address_1 is required")
	public String address_1;
	
	@Column(name = "address_2")	
	public String address_2;
	
	@Column(name = "city")
	@NotEmpty(message = "city is required")
	public String city;
	
	@Column(name = "state")
	@NotEmpty(message = "state is required")
	public String state;
	
	@Column(name = "zip_code")
	@NotEmpty(message = "zip_code is required")
	public String zip_code;
	
	public Address() {
	
	}
	
	

	public Address(@NotEmpty(message = "address_1 is required") String address_1, String address_2,
			@NotEmpty(message = "city is required") String city, @NotEmpty(message = "state is required") String state,
			@NotEmpty(message = "zip_code is required") String zip_code) {
		super();
		this.address_1 = address_1;
		this.address_2 = address_2;
		this.city = city;
		this.state = state;
		this.zip_code = zip_code;
	}



	public String getAddress_1() {
		return address_1;
	}

	public void setAddress_1(String address_1) {
		this.address_1 = address_1;
	}

	public String getAddress_2() {
		return address_2;
	}

	public void setAddress_2(String address_2) {
		this.address_2 = address_2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostal_code() {
		return zip_code;
	}

	public void setPostal_code(String zip_code) {
		this.zip_code = zip_code;
	}



	@Override
	public int hashCode() {
		return Objects.hash(address_1, address_2, city, zip_code, state);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		return Objects.equals(address_1, other.address_1) && Objects.equals(address_2, other.address_2)
				&& Objects.equals(city, other.city) && Objects.equals(zip_code, other.zip_code)
				&& Objects.equals(state, other.state);
	}
	
	
}
