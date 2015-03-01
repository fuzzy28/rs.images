
package com.acss.core.rs.configuration;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Contains the customized converter used by Model Mapper
 * to conveniently convert types into another type.
 * 
 * Developer need to use in case he needs 
 * to convert objects into record type field.
 * or vice-versa.
 * 
 * @author gvargas
 *
 */
@Component
public class ImageModelMapperConfig {
	
	/**
	 * Default Constructor which depends on modelMapper.
	 * get the modelMapper bean and customized it further like
	 * adding converter and adding more Configuration.
	 * @param modelMapper
	 */
	@Autowired
	public ImageModelMapperConfig(ModelMapper modelMapper) {
		addCustomConvertersToModelMapper(modelMapper);
	}
	
	/**
	 * adds the customized converters.
	 * @param modelMapper
	 */
	private void addCustomConvertersToModelMapper(ModelMapper modelMapper) {
		
		/**
		 * Example implementation which converts a AccountStatus Type into Integer.
			//A Custom Mapper for AccountStatus into Integer
			final Converter<AccountStatus, BigDecimal> accountStatusToInteger = new AbstractConverter<AccountStatus, BigDecimal>() {
					  
			 	protected BigDecimal convert(AccountStatus source) {
			 		    return source == null ? null : new BigDecimal(source.getCode());
			 		  }
				};
			 	//A Custom Mapper for Integer to AccountStatus
			final Converter<BigDecimal, AccountStatus> integerToAccountStatus = new AbstractConverter<BigDecimal, AccountStatus>() {
				  
				  protected AccountStatus convert(BigDecimal source) {
				    return source == null ? null : AccountStatus.getByCode(source.intValue());
				  }
			};
			
			//add the converted for AccountStatus into Integer
			modelMapper.addConverter(accountStatusToInteger);
			modelMapper.addConverter(integerToAccountStatus);
		
		**/
		
		//TODO Add your custom converters here.
	}

}
