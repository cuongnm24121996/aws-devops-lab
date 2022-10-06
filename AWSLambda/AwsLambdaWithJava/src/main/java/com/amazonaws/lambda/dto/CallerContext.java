package com.amazonaws.lambda.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CallerContext {
	private String awsSdkVersion;
	private String clientId;

	public String getAwsSdkVersion() {
		return awsSdkVersion;
	}

	public void setAwsSdkVersion(String awsSdkVersion) {
		this.awsSdkVersion = awsSdkVersion;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
