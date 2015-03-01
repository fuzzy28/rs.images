
package com.acss.core.rs.image;

import java.math.BigDecimal;

import com.acss.core.model.image.ApplicationImage;

/**
 * Builds an Image object.
 * @author gvargas
 *
 */
public class ImageBuilder {
	
	private ApplicationImage image = new ApplicationImage();
	public ImageBuilder(){}
	public ImageBuilder(ApplicationImage image){
		this.image = image;
	}
	
	/*
	 * This are the default values needed by the HPS system so that these images will be available in data entry.
	 */
	private static final BigDecimal DEFAULT_ADJUSTSTATUS = new BigDecimal(1);
	private static final BigDecimal DEFAULT_REGSTATUS = new BigDecimal(0);
	private static final BigDecimal DEFAULT_DEFLAG = new BigDecimal(0);
	private static final BigDecimal DEFAULT_NOTYPE = new BigDecimal(2);
	private static final String DEFAULT_FAXNO = "1000";
	private static final BigDecimal DEFAULT_IMAGESOURCE_OSA = new BigDecimal(3);
	private static final BigDecimal DEFAULT_APPLICATIONTYPE = new BigDecimal(1);
	private static final String DEFAULT_CREPROID = "OSA_PROC";
	
	
	/**
	 * This are the default values needed by the HPS system so that these images will be available in data entry. 
	 * @return ImageBuilder
	 */
	public ImageBuilder withDefaultValues(){
		image.setAdjustStatus(DEFAULT_ADJUSTSTATUS);
		image.setRegStatus(DEFAULT_REGSTATUS);
		image.setDelflag(DEFAULT_DEFLAG);
		image.setNoType(DEFAULT_NOTYPE);
		image.setFaxNo(DEFAULT_FAXNO);
		image.setImageSource(DEFAULT_IMAGESOURCE_OSA);
		image.setApplicationType(DEFAULT_APPLICATIONTYPE);
		image.setCreProId(DEFAULT_CREPROID);
		image.setUpdProId(DEFAULT_CREPROID);
		image.setAdjustDate(image.getCreDate());
		image.setAdjustTime(image.getCreTime());
		return this;
	}
	
	/**
	 * Builds the entity.
	 * @return ApplicationImage
	 */
	public ApplicationImage build(){
		return image;
	}
}
