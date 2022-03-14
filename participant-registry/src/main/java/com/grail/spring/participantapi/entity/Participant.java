package com.grail.spring.participantapi.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

/**
 *   Participant Entity
 *   
 * @author senthil
 *
 */

@Entity
public class Participant {

	@Id
	@Column(name = "participantId")
	private Integer id;	
	
	@Column(name = "participantName")
	@NotEmpty(message = "participantName is required")
	private String participantName;		
	
	@Column(name = "dateofBirth")
	//@Temporal(TemporalType.DATE)
	//@NotEmpty(message = "Date of Birth is required")	
	private LocalDate dateofBirth;
	
	
	
	@Column(name = "phone")
	@NotEmpty(message = "phone is required")
	private String phone;
	
	@Embedded
	private Address address;

	public Participant() {
	
	}

	public Participant(Integer id, @NotEmpty(message = "participantName is required") String participantName,
			LocalDate dateofBirth, @NotEmpty(message = "phone is required") String phone, Address address) {
		super();
		this.id = id;
		this.participantName = participantName;
		this.dateofBirth = dateofBirth;
		this.phone = phone;
		this.address = address;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getParticipantName() {
		return participantName;
	}

	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}

	public LocalDate getDateofBirth() {
		return dateofBirth;
	}

	public void setDateofBirth(LocalDate dateofBirth) {
		this.dateofBirth = dateofBirth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, dateofBirth, id, participantName, phone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Participant other = (Participant) obj;
		return Objects.equals(address, other.address) && Objects.equals(dateofBirth, other.dateofBirth)
				&& Objects.equals(id, other.id) && Objects.equals(participantName, other.participantName)
				&& Objects.equals(phone, other.phone);
	}

	
	
	
}
