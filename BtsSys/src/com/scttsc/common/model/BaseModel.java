package com.scttsc.common.model;

import java.io.Serializable;

public class BaseModel implements Serializable {
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}