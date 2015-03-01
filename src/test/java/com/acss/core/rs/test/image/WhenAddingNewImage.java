
package com.acss.core.rs.test.image;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.acss.core.model.image.ApplicationImage;
import com.acss.core.orm.UpdateableRepository;
import com.acss.core.rs.image.ImageBuilder;
import com.acss.core.rs.test.BaseTestRunner;

public class WhenAddingNewImage extends BaseTestRunner{
	@Autowired
	public UpdateableRepository<ApplicationImage,String> updateableImageRepository;
	
	@Rule 
	public ExpectedException thrown= ExpectedException.none(); 
	
	@Test
	public void shouldReturnNewImageOnSuccessfulImageCreation(){
		ApplicationImage createdImage = new ImageBuilder().withDefaultValues().build();
		
		//generated
		createdImage.setImageCode("201507220003");
		createdImage.setGroupId("201507220003");
		createdImage.setDataCd("000000000-3");
		
		//from screen
		createdImage.setImageFilename("201507220003_00001.jpg");
		createdImage.setImagePath("C:\\osa\\app_images\\201507220003\\201507220001_00003.jpg");
		createdImage.setImageType(new BigDecimal(0));
		
		//from principal
		final String sampleMerchantCd = "9999999";
		createdImage.setInputerId(sampleMerchantCd);
		createdImage.setAdjustperson(sampleMerchantCd);
		createdImage.setCrePerson(sampleMerchantCd);
		createdImage.setUpdPerson(sampleMerchantCd);
		
		updateableImageRepository.add(createdImage);
		assertThat(createdImage,is(notNullValue()));
		
		//Default list is always 2 so it should be 3 in total after insert.
		assertThat("Default image list's size should be 3 !"
				,updateableImageRepository.findAll().size(),is(equalTo(3)));
		
	}
	
	@Test
	public void shouldIncreaseRecordsByOneOnOneRecordCreated(){
		ApplicationImage createdImage = new ApplicationImage();
		createdImage.setImageCode("0000001");
		updateableImageRepository.add(createdImage);
		
		//should become 3 in total
		assertThat("List of Image's size should be 3",updateableImageRepository.findAll().size(),is(equalTo(3)));
	}
	
	@Test
	public void shouldThrowDataIntegrityViolationOnSameIdAdded(){
		thrown.expect(DataIntegrityViolationException.class);
		ApplicationImage createdImage = new ApplicationImage();
		
		ApplicationImage sameImage = updateableImageRepository.add(createdImage);
		updateableImageRepository.add(sameImage);
		
	}
	@Test
	public void shouldRollbackTransactionOnExceptionThrown(){
		int expectedSize = 3;
		ApplicationImage createdImage = new ApplicationImage();
		
		ApplicationImage sameImage = updateableImageRepository.add(createdImage);
		
		try{
			updateableImageRepository.add(sameImage);
		}catch(DataIntegrityViolationException e){
			System.out.println("Caught data integrity exception, do nothing and see the fetch size.");
		}
		
		//fetch size should be still 3 (2 initial and 1 new) and not 4 since sameAccount is attempted to be added.
		int actualSize = updateableImageRepository.findAll().size();
		
		assertThat("Image should be rolled back and should not be added! "
				,actualSize,is(equalTo(expectedSize)));
	}
	
}
