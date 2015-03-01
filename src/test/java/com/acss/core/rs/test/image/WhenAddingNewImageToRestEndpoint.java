package com.acss.core.rs.test.image;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.jooq.DSLContext;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.acss.core.model.image.ApplicationImage;
import com.acss.core.orm.UpdateableRepository;
import com.acss.core.rs.db.routines.Numberingmachine;
import com.acss.core.rs.image.ImageBuilder;
import com.acss.core.rs.test.BaseTestRunner;

public class WhenAddingNewImageToRestEndpoint extends BaseTestRunner{
	@Autowired
	public UpdateableRepository<ApplicationImage,String> updateableImageRepository;
	@Autowired
	private DSLContext jooq;
	@Autowired
	private List<HttpMessageConverter<?>> converters;
	//the restful endpoint under test
	private final String RS_IMAGES_URL = "http://localhost:18081/images";
	private final String RS_SEQUENCE_URL = "http://localhost:18081/sequence";
	
	@Test
	public void shouldReturnNewImageOnSuccessfulImageCreation(){
		ApplicationImage createdImage = new ImageBuilder().withDefaultValues().build();

		RestTemplate rt = new RestTemplate();
		rt.setMessageConverters(converters);
		
		ResponseEntity<ApplicationImage> result = rt.postForEntity(RS_IMAGES_URL,populateTesData(createdImage),ApplicationImage.class);
		ApplicationImage body = result.getBody();
		
		assertThat("Created object shouldnt be null", body, is(notNullValue()));
		assertThat("Created Image Code should be equal to 201507220003 ", body.getImageCode(), is(equalTo("201507220003")));
		assertThat("Created Image Code should be equal to default OSA_PROC ", body.getCreProId(), is(equalTo("OSA_PROC")));
		
		//Default list is always 2 so it should be 3 in total after insert.
		assertThat("Default image list's size should be 3 !"
				,updateableImageRepository.findAll().size(),is(equalTo(3)));

	}
	
	@Test
	public void shouldReturn201CreatedStatusCodeOnSuccessfulPost(){
		ApplicationImage createdImage = new ImageBuilder().withDefaultValues().build();

		RestTemplate rt = new RestTemplate();
		rt.setMessageConverters(converters);
		
		ResponseEntity<ApplicationImage> result = rt.postForEntity(RS_IMAGES_URL,populateTesData(createdImage),ApplicationImage.class);
		HttpStatus statusCode = result.getStatusCode();
		
		assertThat("Status Code should be 201 Created", statusCode.toString(), is(equalTo(HttpStatus.CREATED.toString())));
	}
	
	@Test
	public void shouldIncreaseSizeOfInitiaListOnSuccessfulPost(){
		ApplicationImage createdImage = new ImageBuilder().withDefaultValues().build();
		
		RestTemplate rt = new RestTemplate();
		rt.setMessageConverters(converters);
		rt.postForEntity(RS_IMAGES_URL,populateTesData(createdImage),ApplicationImage.class);
		ResponseEntity<ApplicationImage[]> results = rt.getForEntity(RS_IMAGES_URL, ApplicationImage[].class);
		int resultSize = Arrays.asList(results.getBody()).size();
		
		assertThat("Result Size should increase to 3",resultSize, is(equalTo(3)));
	}
	
	/**
	 * Populate with some Test Data for creation test scripts.
	 * @param createdImage
	 * @return
	 */
	private ApplicationImage populateTesData(ApplicationImage createdImage){
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
		return createdImage;
	}
	
	@Test
	public void shouldReturnImageCodeOnSequenceInvokation(){
		
		Numberingmachine sequenceGenerator = new Numberingmachine();
		sequenceGenerator.setNow("20150301");
		sequenceGenerator.setNumtype("T_IMAGE_IMAGECODE");
		sequenceGenerator.execute(jooq.configuration());
		System.out.println(sequenceGenerator.getReturnValue());
		
	}
	
	@Test
	public void shouldReturnImageCodeOnPostInRSEndpoint(){
		RestTemplate rt = new RestTemplate();
		rt.setMessageConverters(converters);
		ResponseEntity<String> res =  rt.postForEntity(RS_SEQUENCE_URL,"T_IMAGE_IMAGECODE",String.class);
		String imageCode = res.getBody();
		System.out.println(imageCode);
		assertThat("Created object shouldnt be null", imageCode, is(notNullValue()));
	}
	
	@Test
	public void shouldReturnGroupIdOnPostInRSEndpoint(){
		RestTemplate rt = new RestTemplate();
		rt.setMessageConverters(converters);
		ResponseEntity<String> res =  rt.postForEntity(RS_SEQUENCE_URL,"T_IMAGE_GROUPID",String.class);
		String imageCode = res.getBody();
		System.out.println(imageCode);
	}	
}
