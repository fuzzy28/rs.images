package com.acss.core.rs.image;

import static com.acss.core.rs.db.tables.TImage.T_IMAGE;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.acss.core.model.ACSSDateUtil;
import com.acss.core.model.image.ApplicationImage;
import com.acss.core.orm.UpdateableRepository;
import com.acss.core.rs.db.routines.Numberingmachine;
/**
 * Restful Endpoint for the Image Resource.
 * @author gvargas
 */
@RestController
public class ImagesRestController {
	
	
	@Autowired
	private UpdateableRepository<ApplicationImage,String> imageRepo;
	@Autowired
	private DSLContext jooq;
	private static final BigDecimal REGSTATUS_COMPLETE = new BigDecimal(0);
	
	/**
	 * Gets all the Image Resource
	 * @return ResponseEntity
	 */
	@RequestMapping(value="/images",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ApplicationImage>> getImages(){
		List<ApplicationImage> images = imageRepo.findAll();
		return new ResponseEntity<List<ApplicationImage>>(images,HttpStatus.FOUND);
	}
	
	/**
	 * Adds a new Image Resource
	 * @param inputImage
	 * @return ResponseEntity
	 */
	@RequestMapping(value="/images",method = RequestMethod.POST)
	public ResponseEntity<ApplicationImage> addImage(@RequestBody ApplicationImage inputImage){
		ApplicationImage addedImage = null;
		try {
			addedImage = imageRepo.add(inputImage);
		//TODO Do not catch generic exception, please make it more specific Gail.
		} catch (Exception e) {
			//return non-created status code
			return new ResponseEntity<ApplicationImage>(addedImage,HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<ApplicationImage>(addedImage,HttpStatus.CREATED);
	}
	
	/**
	 * Gets the images based on the dataCd
	 * @param dataCode
	 * @return
	 */
	@RequestMapping(value="/images/{dataCode}",method = RequestMethod.GET)
	public ResponseEntity<List<ApplicationImage>> getImage(@PathVariable String dataCode){
		Condition whereDataCd = T_IMAGE.DATACD.equal(dataCode);
				whereDataCd.and(T_IMAGE.DELFLAG.equal((byte) 0));
		return new ResponseEntity<List<ApplicationImage>>(imageRepo.findUsingCondition(whereDataCd),HttpStatus.FOUND);	
	}
	
	/**
	 * Streams the file using the image code.
	 * image code will then be used to find the image path
	 * image path will then be returned as fileSystemResource.
	 * @param imageCode
	 * @return
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "/images/{imageCode}/view",method = RequestMethod.GET)
	public ResponseEntity<FileSystemResource> view(@PathVariable String imageCode) throws FileNotFoundException{
		ApplicationImage image = imageRepo.findById(imageCode);
		String imagePath = image.getImagePath();
		File imageFile = new File(imagePath);
		
		return ResponseEntity.ok()
	            .contentType(MediaType.IMAGE_JPEG)
	            .body(new FileSystemResource(imageFile));
	}
	
	/**
	 * Generates a new image code used for renaming the image file and to uniquely identify
	 * each images.
	 * 
	 * @return imagecode
	 */
	@RequestMapping(value="/sequence",method = RequestMethod.POST)
	public ResponseEntity<String> getImageCode(@RequestBody String numType){
		Numberingmachine sequenceGenerator = new Numberingmachine();
		String dateNowAsYYYYMMDD = ACSSDateUtil.getDateAsYYYYMMDDFromDateTime().toString();
		sequenceGenerator.setNow(dateNowAsYYYYMMDD);
		//this is the key stored at M_SEQUENCE.
		sequenceGenerator.setNumtype(numType);
		sequenceGenerator.execute(jooq.configuration());
		String imageCode = sequenceGenerator.getReturnValue();
		
		return new ResponseEntity<String>(imageCode,HttpStatus.CREATED);
	}
	
	/**
	 * Updates the submission status of an image into 'complete' and becomes available for data entry.
	 * @param dataCode
	 * @return dataCode
	 */
	@RequestMapping(value="/images/{dataCode}",method = RequestMethod.PUT)
	public ResponseEntity<String> updateStatus(@PathVariable String dataCode){
		List<ApplicationImage> imagesForUpdate = imageRepo.findUsingCondition(T_IMAGE.DATACD.eq(dataCode));
		//tag everything as complete.
		for(ApplicationImage appImage:imagesForUpdate){
			appImage.setRegStatus(REGSTATUS_COMPLETE);
			imageRepo.update(appImage);
		}
		
		return new ResponseEntity<String>(dataCode,HttpStatus.OK);
	}
	
	
	
	
	/*
	 * Exception Mappings below.
	 */
	@ExceptionHandler(DataRetrievalFailureException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleNotFoundException(Exception e) {
		
	    return "Record not found";
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String handleConstraintViolation(Exception e) {
	    return "Contraint violation!";
	}
}
