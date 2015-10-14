package org.aif.eventbrite;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pagination {

	private Long object_count;
	private Long page_number;
	private Long page_size;
	private Long page_count;

	public Long getObject_count() {
		return object_count;
	}

	public void setObject_count(Long object_count) {
		this.object_count = object_count;
	}

	public Long getPage_number() {
		return page_number;
	}

	public void setPage_number(Long page_number) {
		this.page_number = page_number;
	}

	public Long getPage_size() {
		return page_size;
	}

	public void setPage_size(Long page_size) {
		this.page_size = page_size;
	}

	public Long getPage_count() {
		return page_count;
	}

	public void setPage_count(Long page_count) {
		this.page_count = page_count;
	}

}
