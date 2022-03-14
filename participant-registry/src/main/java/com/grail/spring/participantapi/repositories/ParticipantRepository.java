package com.grail.spring.participantapi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.grail.spring.participantapi.entity.Participant;

/**
 *   This Participant Interface contains all the find querys that implicitly implemented by the Spring data
 * 
 *   @author senthil
 *
 */
public interface ParticipantRepository extends CrudRepository<Participant, Integer> {

	
	public List<Participant> findByParticipantName(String participantName);
	public List<Participant> findByPhone(String phone);
	
	
	public List<Participant> findByParticipantNameAndPhone(String participantName, String phone);
}
