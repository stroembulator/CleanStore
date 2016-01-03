package de.exaxway.cleanstore;

import java.io.Serializable;
import java.lang.Integer;
import java.sql.Blob;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: DatPersist
 *
 */
@Entity

public class DatPersist implements Serializable {

	
	private Blob image;
	@Id
	private Integer id;
	private static final long serialVersionUID = 1L;

	public DatPersist() {
		super();
	}   
	public Blob getImage() {
		return this.image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}   
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
   
}
