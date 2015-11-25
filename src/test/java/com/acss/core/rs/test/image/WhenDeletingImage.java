
package com.acss.core.rs.test.image;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;

import com.acss.core.model.image.ApplicationImage;
import com.acss.core.orm.UpdateableRepository;
import com.acss.core.rs.test.BaseTestRunner;

public class WhenDeletingImage extends BaseTestRunner{
	@Autowired
	public UpdateableRepository<ApplicationImage,Long> updateableImageRepository;	
		
	@Rule
	public ExpectedException thrown= ExpectedException.none(); 
	
	@Test
	public void shouldReturnDeletedObjectOnSuccessfulDelete(){
		//Arrange
		//the original record for checking the update result.
		int expectedSize = 1;
		updateableImageRepository.delete(1L);		
		//assertThat("Deleted user should be admin.",deleted.getImagename(), is("admin"));
		assertThat(updateableImageRepository.findAll().size(),is(equalTo(expectedSize)));
	}
	
	@Test
	public void shouldThrowExceptionOnImageNotExists(){
		//Arrange and Assert
		thrown.expect(DataRetrievalFailureException.class);  
		thrown.expectMessage("Image not Found");
		//Act - use non-existing ID
		updateableImageRepository.delete(new Long(4L));
	}
	
	@Test
	public void shouldDeleteChildEntityOnSuccessfulDelete(){
		//TODO Please add some test implementation about Returning a record when conditon is met.
		/**
			Put your own test implementation here
		**/
		assert(true);
	}
	
}
