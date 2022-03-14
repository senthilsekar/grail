package com.grail.spring.participantapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grail.spring.participantapi.controllers.ParticipantApiController;
import com.grail.spring.participantapi.entity.Address;
import com.grail.spring.participantapi.entity.Participant;
import com.grail.spring.participantapi.repositories.ParticipantRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ParticipantApiControllerUnitTestWithMock {

	@Autowired
	private ParticipantApiController participantController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ParticipantRepository participantRepository;
	
	@Test
	void contextLoads() {
		assertThat(participantController).isNotNull();
	}
	
	
	private static List<Participant> getParticipantList() {
		List<Participant> participants = new ArrayList<Participant>();
		participants.add(new Participant(1001,"JOHN",LocalDate.parse("1956-11-23"),"2356425",new Address("1011 test street","","citytest","teststate","testzip")));
		participants.add(new Participant(1002,"DAVID",LocalDate.parse("1985-05-23"),"732-458-9686",new Address("1011 test street1","","citytest1","teststate1","testzip2")));
		participants.add(new Participant(1003,"ROY",LocalDate.parse("1960-03-27"),"635-698-7863",new Address("1011 test street","","citytest","teststate","testzip")));
		return participants;
	}
	
	private static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	@Test
	public void getAllParticipantsTest() throws Exception{
		List<Participant> sampleInput = getParticipantList();
		
		Mockito.when(participantRepository.findAll()).thenReturn(sampleInput);
		
		mockMvc.perform(get("/participants"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(sampleInput.size())))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1001));
		
		Mockito.verify(participantRepository, Mockito.times(1)).findAll();
		
	}
	
	@Test
	public void getParticipantWithParticipantIdTest() throws Exception{
		
		Participant participant = new Participant(1003,"ROY",LocalDate.parse("1960-03-27"),"635-698-7863",new Address("1011 test street","","citytest","teststate","testzip"));
		
		Mockito.when(participantRepository.findById(1003)).thenReturn(Optional.ofNullable(participant));
		
		mockMvc.perform(get("/participants/{id}",1003))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))		
		.andExpect(MockMvcResultMatchers.jsonPath("$.participantName").value("ROY"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("635-698-7863"));
		
		Mockito.verify(participantRepository, Mockito.times(1)).findById(1003);
		
	}
	
	@Test
	public void getParticipantWithParticipantIdErrorTest() throws Exception{
		
		Participant participant = null;
		
		Mockito.when(participantRepository.findById(1003)).thenReturn(Optional.ofNullable(participant));
		
		mockMvc.perform(get("/participants/{id}",1003))
		.andExpect(status().isNotFound())
		.andExpect(content().contentType("text/plain;charset=UTF-8"))		
		.andExpect(content().string("Participant with id 1003 not found"));
		
		Mockito.verify(participantRepository, Mockito.times(1)).findById(1003);
		
	}	

	
	@Test
	public void deleteParticipantTest() throws Exception 
	{
		Integer participantIdtoDelete = 1005;
		Mockito.doNothing().when(participantRepository).deleteById(participantIdtoDelete); 
		Mockito.when(participantRepository.existsById(participantIdtoDelete)).thenReturn(true);
		
		mockMvc.perform( MockMvcRequestBuilders.delete("/participants/{id}", participantIdtoDelete) )
		 .andExpect(status().isNoContent())
		 .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
		
		Mockito.verify(participantRepository, Mockito.times(1)).deleteById(participantIdtoDelete);
		Mockito.verify(participantRepository, Mockito.times(1)).existsById(participantIdtoDelete);
	}
	
	@Test
	public void deleteParticipantErrorTest() throws Exception 
	{
		Integer participantIdtoDelete = 1005;
		Mockito.doNothing().when(participantRepository).deleteById(participantIdtoDelete); 
		Mockito.when(participantRepository.existsById(participantIdtoDelete)).thenReturn(false);
		
		mockMvc.perform( MockMvcRequestBuilders
		  .delete("/participants/{id}", participantIdtoDelete) 
		  .accept(MediaType.TEXT_PLAIN))
	      .andExpect(status().isNotFound())
	      .andExpect(content().string("Participant with id 1005 not found"));
		
		Mockito.verify(participantRepository, Mockito.times(0)).deleteById(participantIdtoDelete);
		Mockito.verify(participantRepository, Mockito.times(1)).existsById(participantIdtoDelete);
	}
}
