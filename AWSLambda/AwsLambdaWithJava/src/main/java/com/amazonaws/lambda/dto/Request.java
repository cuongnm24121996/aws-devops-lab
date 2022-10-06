package com.amazonaws.lambda.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Request {
	private UserAttributes userAttributes;
	private String codeParameter;
	private String usernameParameter;
	private String linkParameter;

	public UserAttributes getUserAttributes() {
		return userAttributes;
	}

	public void setUserAttributes(UserAttributes userAttributes) {
		this.userAttributes = userAttributes;
	}

	public String getCodeParameter() {
		return codeParameter;
	}

	public void setCodeParameter(String codeParameter) {
		this.codeParameter = codeParameter;
	}

	public String getUsernameParameter() {
		return usernameParameter;
	}

	public void setUsernameParameter(String usernameParameter) {
		this.usernameParameter = usernameParameter;
	}

	public String getLinkParameter() {
		return linkParameter;
	}

	public void setLinkParameter(String linkParameter) {
		this.linkParameter = linkParameter;
	}

}
