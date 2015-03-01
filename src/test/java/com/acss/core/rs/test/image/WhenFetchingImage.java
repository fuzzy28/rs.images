
package com.acss.core.rs.test.image;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import org.junit.rules.ExpectedException;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;

import com.acss.core.model.image.ApplicationImage;
import com.acss.core.orm.ReadOnlyRepository;
import com.acss.core.rs.test.BaseTestRunner;

public class WhenFetchingImage extends BaseTestRunner{
	@Autowired
	public ReadOnlyRepository<ApplicationImage,String>  imageRepository;
	
	@Rule
	public ExpectedException thrown= ExpectedException.none(); 
	
	@Test
	public void shouldReturnTwoRecordsOnInitialLoading(){
		List<ApplicationImage> images = imageRepository.findAll();
		assertThat(images,is(notNullValue()));
		assertThat("Default image list's size should be 2 !"
				,images.size(),is(equalTo(2)));
	}
	
	@Test
	public void shouldFetchOneImageOnMatchedId(){
		/*
		 * 201507220001 is the init data please see: src/main/resources/oraclePopulateDB.sql
		 * IMAGEPATH = /storwyzmnt/batch/imageconverter/fax_images/2870827/201507220001_00002.jpg
		 * 
		*/
		ApplicationImage resultImage = imageRepository.findById(new String("201507220001"));
		final String default_ImagePath= "/storwyzmnt/batch/imageconverter/fax_images/000000/201507220001_00001.jpg";
		assertThat("Image should not be null! ",resultImage,is(notNullValue()));
		assertThat("Imagepath should match the default entry", 
				resultImage.getImagePath(), is(equalTo(default_ImagePath)));
	}
	
	@Test
	public void shouldThrowExceptionOnImageNotExists(){
		//Arrange and Assert
		thrown.expect(DataRetrievalFailureException.class);  
		thrown.expectMessage("Image not Found");
		//Act
		//TODO Please add some test implementation about fetching a non-existent entity Id access might be different.
		imageRepository.findById(new String(""));
	}
	
	@Test
	public void shouldReturnRecordOnConditionMet(){
		//TODO Please add some test implementation about Returning a record when conditon is met.
		/**
		Example Test Implementation Guide.
		//arrange
		Condition whereImageEqualToGail = M_ACCOUNT.USERNAME.equal("admin");
		Condition whereEmailLikeVargas = M_ACCOUNT.EMAIL.like("vargas%");
		
		int actualSize = 1;
		//Act
		List<Image> images = imageRepository
				.findUsingCondition(whereImageEqualToGail
						.and(whereEmailLikeVargas));
		//Arrange
		assertThat("Image should not be null! ",images,is(notNullValue()));
		assertThat("Query should return 1 record!", images.size(),is(equalTo(actualSize)));
		assertThat("Result image's username should be 'admin' !"
				,images.get(0).getImagename(),is(equalTo("admin")));
		**/
		assert(true);
	}
	
}
