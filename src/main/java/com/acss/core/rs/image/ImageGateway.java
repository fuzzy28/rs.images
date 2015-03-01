
package com.acss.core.rs.image;

import static com.acss.core.rs.db.tables.TImage.T_IMAGE;

import java.util.List;

import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Component;

import com.acss.core.orm.BasicGatewayImpl;
import com.acss.core.rs.db.tables.records.TImageRecord;

/**
 * The persistent layer used by the Image Repository.
 * @author gvargas
 *
 */
@Component
public class ImageGateway extends BasicGatewayImpl<String,TImageRecord> {
	
	/**
	 * Queries for retrieving image.
	 * the common statement.
	 * @return SelectJoinStep<Record>
	 */
	private SelectJoinStep<Record> selectImage(){
		return jooq.select(T_IMAGE.fields())
		.from(T_IMAGE);
	}
	
	/**
	 * Retrieves a single record using id as parameter
	 */
	public Record retrieve(String id) {
		return selectImage()
				.where(T_IMAGE.IMAGECODE.equal(id))
				.fetchOne();
	}
	
	/**
	 * Retrieves all record
	 */
	public List<Record> retrieve() {
		return selectImage().limit(1000)
				.fetch();
	}
	
	/**
	 * Retrieves with a limit provided
	 */
	public List<Record> retrieve(int max) {
		return selectImage()
				.limit(max)
				.fetch();
	}
	
	/**
	 * Retrieve image/s based on the condition given.
	 */
	public List<Record> retrieve(Condition condition) {
		return selectImage().where(condition).orderBy(T_IMAGE.IMAGETYPE).fetch();
	}

}
