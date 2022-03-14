package com.grail.spring.participantapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.grail.spring.participantapi.ParticipantRegistryApiApplication;
import com.grail.spring.participantapi.entity.Address;
import com.grail.spring.participantapi.entity.Participant;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(classes = ParticipantRegistryApiApplication.class, 
                 webEnvironment = WebEnvironment.RANDOM_PORT)

class ParticipantApiControllerIntegrationTest {

	@LocalServerPort
    private int port;
 
    @Autowired
    private TestRestTemplate restTemplate;
	
    @Test
    public void testAddViewUpdateDeleteParticipant() {
    	Participant participantToAdd = new Participant(1001,"JOHN",LocalDate.parse("1956-11-23"),"2356425",new Address("1011 test street","","citytest","teststate","testzip"));
    	
    	
    	{//---------------POST participants
		    	ResponseEntity<String> responseEntity = this.restTemplate
		            .postForEntity("http://localhost:" + port + "/participants", participantToAdd, String.class);
		        assertEquals(201, responseEntity.getStatusCodeValue());
    	}
        {//---------------GET participants
		       Participant[] participantArray=  this.restTemplate
		                .getForObject("http://localhost:" + port + "/participants", Participant[].class);
		        
		       assertTrue(participantArray.length == 1);
		       assertTrue(participantArray[0].equals(participantToAdd));   //participant obj that is added via POST is compared after GET ALL
        }
        
        {//--------------GET participants/{id}
	        Participant singleParticipant=  this.restTemplate
	               .getForObject("http://localhost:" + port + "/participants/"+participantToAdd.getId(), Participant.class);     
	        assertTrue(singleParticipant.equals(participantToAdd));   //participant obj that is added via POST is compared after GET
        }
        
        {//---------------PUT participants
        	participantToAdd.setParticipantName("participant-updated");
	    	this.restTemplate.put("http://localhost:" + port + "/participants", participantToAdd);	        
	   }
        
        {//--------------GET participants/{id}  --- to read the PUT value
	        Participant singleParticipant=  this.restTemplate
	               .getForObject("http://localhost:" + port + "/participants/"+participantToAdd.getId(), Participant.class);     
	        assertTrue(singleParticipant.getParticipantName().equals("participant-updated"));   //participant obj that is added via POST is compared after GET
        }
        
        {//--------------DELETE participants/{id}
        	this.restTemplate.delete("http://localhost:" + port + "/participants/"+participantToAdd.getId());        	
        }
        
        {//--------------GET participants/{id}  --- to read the DELETE value
	        String errorMessage=  this.restTemplate
	               .getForObject("http://localhost:" + port + "/participants/"+participantToAdd.getId(), String.class);     
	       assertTrue(errorMessage.equalsIgnoreCase("Participant with id 1001 not found"));
        }
        
    }
	
}
