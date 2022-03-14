package com.grail.spring.participantapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grail.spring.participantapi.entity.Participant;
import com.grail.spring.participantapi.exception.PariticpantAlreadyExistException;
import com.grail.spring.participantapi.exception.ParticipantNotFoundException;
import com.grail.spring.participantapi.repositories.ParticipantRepository;

/**
 *  Service class to implement the Add/view/update/delete features for Participant Entity
 *  
 *  @author senthil
 *
 */
@Service
public class ParticipantApiService {

	@Autowired
	private ParticipantRepository participantRepository;

	/**
	 *   This Method fetches all the participant from the repository
	 *   
	 *   @return list of participant object
	 */
	public List<Participant> getAllParticipants(){
		List<Participant> participants = new ArrayList<Participant>();
		participantRepository.findAll().forEach(participant -> participants.add(participant));		
		return participants;
	}
	
	/** 
	 *  This method fetches participants list based on matching filter attributes that are given
	 *  
	 * @param make
	 * @param model
	 * @param participantYear
	 * @return
	 */
	public List<Participant> getParticipantsFiltered(String participantName, String phone){
		List<Participant> participants = new ArrayList<Participant>();
		
		if(participantName!=null && phone !=null) {
			participants = participantRepository.findByParticipantNameAndPhone(participantName, phone);
		}else if(participantName!=null) {
			participants = participantRepository.findByParticipantName(participantName);
		}else if(phone!=null) {
			participants = participantRepository.findByPhone(phone);
		}
		
		return participants;
	}
	
	/**
	 *  This method Fetches single Participant Entity for the given participantId,
	 *  it throws ParticipantNotFound Runtime Exception if there is no matching participantID
	 * 
	 *  @param id
	 *  @return
	 */
	public Participant getParticipant(Integer id) {
		//Optional<Participant> participant= participants.stream().filter(t -> t.getId().equals(id)).findFirst();
		Optional<Participant> participant= participantRepository.findById(id);
		if(participant.isPresent()){
			return participant.get();
		}else{
			throw new ParticipantNotFoundException("Participant with id " + id + " not found");
		}
		
	}
	
	/**
	 *  This method add the given Participant Entity to the repository,
	 *  it throws ParticipantAlreadyExist Runtime Exception if the Participant data already present in repository
	 *  
	 *  @param participant
	 */
	public void addParticipant(Participant participant) {
		if(participantRepository.existsById(participant.getId())) {
			throw new PariticpantAlreadyExistException("Participant with id " + participant.getId() + " Already Exist");
		}else {
			participantRepository.save(participant);
		}
	}	
	
	/**
	 * This method update the Participant Entity in the repository,
	 *  it throws ParticipantNotFound Runtime Exception if there is no matching Participant to update
	 * @param participant
	 */
	public void updateParticipant(Participant participant) {
		if(participantRepository.existsById(participant.getId())) {
			participantRepository.save(participant);
		}else{
			throw new ParticipantNotFoundException("Participant with id " + participant.getId() + " not found");
		}
	}
	
	
	/**
	 *  This method delete the Participant Entity in the repository 
	 *   it throws ParticipantNotFound Runtime Exception if there is no matching participant to Delete
	 *  @param id
	 */
	public void deleteParticipant(Integer id) {
		if(participantRepository.existsById(id)) {
			participantRepository.deleteById(id);
		}else{
			throw new ParticipantNotFoundException("Participant with id " + id + " not found");
		}
	}
	
}
