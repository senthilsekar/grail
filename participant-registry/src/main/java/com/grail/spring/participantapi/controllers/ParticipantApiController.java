package com.grail.spring.participantapi.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grail.spring.participantapi.entity.Participant;
import com.grail.spring.participantapi.services.ParticipantApiService;

/**
 *  This class handles all the REST request for Participant API
 *  
 * @author senthil
 *
 */
@RestController
@Validated
@CrossOrigin
public class ParticipantApiController {

	@Autowired
	private ParticipantApiService participantService;

	/**
	 *    This method handles the GET request for the Participant matching the provided filter, 
	 *    if no filter specified returns all participant
	 *     
	 * @param make (optional request param)
	 * @param model (optional request param)
	 * @param year (optional request param)
	 * 
	 *    @return list of all Participant matching given filter(optional),  default: all Participant data is returned 
	 */
	@RequestMapping("/participants")
	public List<Participant> getAllParticipants(@RequestParam(value = "name", required = false) String participantName,
										@RequestParam(value = "phone", required = false) String phone){
		if(participantName != null || phone != null) {
			return participantService.getParticipantsFiltered(participantName, phone);
		}else {
			return participantService.getAllParticipants();
		}
		
	}
	
	/** This method handles the GET request for a given participantId
	 * 
	 * @param participantId
	 * @return
	 */
	@RequestMapping("/participants/{id}")
	public Participant getParticipant(@PathVariable("id") @Min(1) Integer participantId){
		return participantService.getParticipant(participantId);
		
	}
	
	/** This method handles the POST request (Add) for adding Participant
	 * 
	 * @param participant Object to add
	 * @return
	 */
	@RequestMapping(path = "/participants" , method = RequestMethod.POST)
	public ResponseEntity<Object> addParticipant(@RequestBody @Valid Participant participant) {
		participantService.addParticipant(participant);
		return ResponseEntity.status(HttpStatus.CREATED).build();  //201 - Created
	}
	
	/** This method handles PUT request (update) for the Participant
	 * 
	 * @param participant Object to update
	 * @return
	 */
	@RequestMapping(path = "/participants" , method = RequestMethod.PUT)
	public ResponseEntity<Object> updateParticipant(@RequestBody @Valid Participant participant){
		participantService.updateParticipant(participant);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //204 - success with no body content
		
	}
	
	/** This method handles the DELETE request for Participant 
	 * @param participantId
	 * @return
	 */
	@RequestMapping(path = "/participants/{id}" , method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteParticipant(@PathVariable("id") @Valid @Min(1) Integer participantId){
		 participantService.deleteParticipant(participantId);
		 return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //204 - success with no body content
		
	}
	
}
