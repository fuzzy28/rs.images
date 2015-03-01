
package com.acss.core.rs.image;


import java.util.ArrayList;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.acss.core.model.image.ApplicationImage;
import com.acss.core.orm.DomainFactory;
import com.acss.core.orm.DomainGateway;
import com.acss.core.orm.UpdateableRepository;
import com.acss.core.rs.db.tables.records.TImageRecord;



/**
 * The repository for Image.
 * @author gvargas
 *
 */
@Repository
public class ImageRepository implements UpdateableRepository<ApplicationImage,String>{
	
	private final DomainGateway<String,TImageRecord> imageGateway;
	private final DomainFactory<ApplicationImage, TImageRecord> imageFactory;	
	/**
	 * The repository for Image
	 * 
	 * List of dependencies as follows
	 * @param imageGateway
	 * @param imageFactory
	 */
	@Autowired
	public ImageRepository(DomainGateway<String,TImageRecord> imageGateway,
			DomainFactory<ApplicationImage, TImageRecord> imageFactory){
		
		this.imageFactory = imageFactory;
		this.imageGateway = imageGateway;
	}
	
	/**
	 * Adds an Image
	 */
	@Transactional
	public ApplicationImage add(ApplicationImage image) {
		TImageRecord newImage = imageGateway.persist(imageFactory.createRecord(image));
		return findById(newImage.getImagecode());
	}
	
	/**
	 * Deletes by Id and return the deleted entity for post delete operation.
	 */
	@Transactional
	public ApplicationImage delete(String imageCode) {
		ApplicationImage deletedImage = findById(imageCode);
		
		imageGateway.delete(imageFactory.createRecord(deletedImage));
		
		return deletedImage;
	}
	
	/**
	 * Finds all record without any condition or limit.
	 */
	@Transactional(readOnly=true)
	public List<ApplicationImage> findAll() {
		List<Record> queryResults = imageGateway.retrieve();
		
		List<ApplicationImage> images = new ArrayList<ApplicationImage>();
		for(Record queryResult:queryResults){
			images.add(imageFactory.make(queryResult, ApplicationImage.class));
		}
		if(images.size() > 0){
			return images;
		}else
			throw new DataRetrievalFailureException("Images not Found");
	}
	
	/**
	 * Finds by specified ID.
	 */
	@Transactional(readOnly=true)
	public ApplicationImage findById(String imageCode) {
		Record queryResult = imageGateway.retrieve(imageCode);
		
		if(queryResult!=null){
			return imageFactory.make(queryResult,ApplicationImage.class);
		}else
			throw new DataRetrievalFailureException("Image not Found");
	}
	
	/**
	 * Updates by specified Image imageCode.
	 */
	@Transactional
	public ApplicationImage update(ApplicationImage image) {
		//TODO customize your update method here
		imageGateway.update(imageFactory.createRecord(image));
		return findById(image.getImageCode());
	}
	
	/**
	 * Fetch by Condition
	 * will return results based on the Condition.
	 */
	@Transactional(readOnly=true)
	public List<ApplicationImage> findUsingCondition(Condition condition) {
		List<Record> queryResults = imageGateway.retrieve(condition);
		
		List<ApplicationImage> images = new ArrayList<ApplicationImage>();
		for(Record queryResult:queryResults){
			images.add(imageFactory.make(queryResult, ApplicationImage.class));
		}
		if(images.size() > 0){
			return images;
		}else
			throw new DataRetrievalFailureException("image not Found"); 
	}
}
