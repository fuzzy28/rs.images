
package com.acss.core.rs.image;


import org.jooq.DSLContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acss.core.model.image.ApplicationImage;
import com.acss.core.orm.EntityProviderFactory;
import com.acss.core.rs.db.tables.records.TImageRecord;
import com.acss.core.rs.db.tables.TImage;

/**
 * A Factory class which provides various ways of creating and returning
 * Image and ImageRecord Type instance.
 * No Need to modify this code.
 * @author gvargas
 *
 */
@Component
public class ImageProvider extends EntityProviderFactory<ApplicationImage, TImageRecord,TImage>{

	@Autowired
	public ImageProvider(ModelMapper modelMapper, DSLContext jooq) {
		super(modelMapper, jooq,TImage.class);
	}
}
