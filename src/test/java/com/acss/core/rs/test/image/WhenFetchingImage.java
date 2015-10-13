
package com.acss.core.rs.test.image;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import org.jooq.Condition;
import org.junit.rules.ExpectedException;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;

import com.acss.core.model.image.ApplicationImage;
import com.acss.core.orm.ReadOnlyRepository;
import com.acss.core.rs.test.BaseTestRunner;

import static com.acss.core.rs.db.tables.TImage.T_IMAGE;

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
		ApplicationImage resultImage = imageRepository.findById(new String("201507300275"));
		final String default_ImagePath= "C:\\osa\\app_images\\201507300106\\201507300275.jpg";
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
		//Example Test Implementation Guide.
		//arrange
		Condition whereDataCd = T_IMAGE.DATACD.eq("100133907-4");
		
		int actualSize = 1;
		//Act
		List<ApplicationImage> images = imageRepository
				.findUsingCondition(whereDataCd);
		//Arrange
		assertThat("Image should not be null! ",images,is(notNullValue()));
		assertThat("Query should return 1 record!", images.size(),is(equalTo(actualSize)));
		assertThat("Result image's username should be 'admin' !"
				,images.get(0).getDataCd(),is(equalTo("admin")));
		
		assert(true);
	}
	
}
