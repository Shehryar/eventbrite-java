package org.aif.eventbrite;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Attendees {

	private String id;
	private Boolean checked_in;
	private String status;
	private Profile profile;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getChecked_in() {
		return checked_in;
	}

	public void setChecked_in(Boolean checked_in) {
		this.checked_in = checked_in;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
