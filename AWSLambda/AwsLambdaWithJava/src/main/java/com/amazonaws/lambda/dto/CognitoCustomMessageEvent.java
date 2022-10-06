package com.amazonaws.lambda.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CognitoCustomMessageEvent {
	private String version;
	private String region;
	private String userPoolId;
	private String userName;
	private String triggerSource;
	private CallerContext callerContext;
	private Request request;
	private Response response;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getUserPoolId() {
		return userPoolId;
	}

	public void setUserPoolId(String userPoolId) {
		this.userPoolId = userPoolId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTriggerSource() {
		return triggerSource;
	}

	public void setTriggerSource(String triggerSource) {
		this.triggerSource = triggerSource;
	}

	public CallerContext getCallerContext() {
		return callerContext;
	}

	public void setCallerContext(CallerContext callerContext) {
		this.callerContext = callerContext;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

}
