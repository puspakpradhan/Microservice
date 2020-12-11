package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Audit;
import com.example.demo.entity.AuditNew;
import com.example.demo.repository.AuditRepository;

@RestController
public class AuditController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AuditRepository auditRepository;
	
	
	@GetMapping("/audits")
	public List<Audit> getAuditById() {
	  List<Audit> audit = auditRepository.findAllAudit();
    	return audit;
	}
	
	
	
	@GetMapping("/auditsNew")
	public List<AuditNew> findAllAuditsNew() {
	  List<AuditNew> audit = auditRepository.findAllAuditsNew();
    	return audit;
	}
	
	@GetMapping("/audit/{userID}")
	public List<Audit> getAuditById(@PathVariable String userID) {
	  List<Audit> userAudits = auditRepository.getAuditsByID(userID);
    	return userAudits;
	}
	
//	@GetMapping("/audit/{userID}")
//	public Audit getAuditById1(@PathVariable String userID) {
//	  Audit audit = auditRepository.getAuditByID(userID);
//    	return audit;
//	}
	
	
	@PostMapping("/addAudit")
	 public ResponseEntity<Object> addToAudit(@RequestBody Audit audit) throws Exception {
		
		logger.info("[Audit Controller]-----[addToAudit() ----from Rabbit MQ calling Audit-MS--]-");
		
		try {
			logger.info("[Audit Controller]-----[addToAudit() ---Insert in AUDIT Table-from Rabbit MQ calling Audit-MS--]-");
			auditRepository.insertAudit(audit);
		}catch(Exception ex) {
			logger.info("[Audit Controller]-----[addToAudit() ---Exception while Insert in AUDIT Table-from Rabbit MQ calling Audit-MS--]-"+ex.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		
		logger.info("[Audit Controller]-----[addToAudit() ----from Rabbit MQ calling Audit-MS--]----Success!!!!");
     return ResponseEntity.status(HttpStatus.CREATED).build();
}
	
	
	//3. Delete/Remove  order from cart 
	
	  @DeleteMapping("/audit/{userID}")
	  public String deleteCart(@PathVariable String  userID) {
		  
		  List<Audit> existingOrders = null;
		  //String deleteByCountryCode = newConversionFactor.getCountry();
		  String message = "";
		  
		  if(userID.isEmpty()) {
			  message = HttpStatus.BAD_REQUEST.toString();
			  return message;
		  }
		  
		  try {
			  existingOrders = auditRepository.getAuditsByID(userID);
			   
			   if(null == existingOrders) {
			    	message = "No record found to Delete"; 
			   } 
			  
		  }catch(EmptyResultDataAccessException emptEx) {
			  message = "No record found to Delete";
			  
		  }catch(DataAccessException dae) {
			  message = dae.getMessage();
		  }catch(Exception ex) {
			  message = ex.getMessage();
		  }
		  
		  try {
			  
			  if(null != existingOrders) {
				  
					 auditRepository.deleteAuditByUserID(userID);
					 message ="Success";
				  
			  }
		    }catch(Exception ex) {
		    	message =ex.getMessage();
		    	
		    }
		  
			return message;
		  
	  }
	
	

}
