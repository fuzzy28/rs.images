
package com.acss.core.rs.test.image;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import com.acss.core.model.image.ApplicationImage;
import com.acss.core.orm.UpdateableRepository;
import com.acss.core.rs.test.BaseTestRunner;

public class WhenUpdatingImage extends BaseTestRunner{
	
	@Autowired
	public UpdateableRepository<ApplicationImage,String> updateableImageRepository;
	
	@Rule 
	public ExpectedException thrown= ExpectedException.none(); 
	
	@Test
	public void shouldReturnUpdatedObjectOnSuccessfulUpdate(){
		//Arrange
		//TODO Please add some test implementation about Returning the object on update successful.
		//Act
		//Assert
		assert(true);
	
	}
	
	@Test
	public void shouldThrowExceptionOnImageNotExists(){
		//Arrange and Assert
		//thrown.expect(DataRetrievalFailureException.class);  
		//thrown.expectMessage("Image not Found");
		//updated object
		//Arrange
		//TODO Please add some test implementation about Returning the object on update successful.
		//Act
		//Assert
		assert(true);
	}
	
}
