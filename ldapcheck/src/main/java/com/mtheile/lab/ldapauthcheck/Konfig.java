package com.mtheile.lab.ldapauthcheck;

public class Konfig {

	private String securityPricipal;
	private String securityCredentials;
	private String providerUrl;
	private String ldapSearchscope;
	private String ldapSearchfilter;
	private String ldapSearchresult;
	
	public String getSecurityPricipal() {
		return securityPricipal;
	}
	public void setSecurityPricipal(String securityPricipal) {
		this.securityPricipal = securityPricipal;
	}
	public String getSecurityCredentials() {
		return securityCredentials;
	}
	public void setSecurityCredentials(String securityCredentials) {
		this.securityCredentials = securityCredentials;
	}
	public String getProviderUrl() {
		return providerUrl;
	}
	public void setProviderUrl(String providerUrl) {
		this.providerUrl = providerUrl;
	}
	public String getLdapSearchscope() {
		return ldapSearchscope;
	}
	public void setLdapSearchscope(String ldapSearchscope) {
		this.ldapSearchscope = ldapSearchscope;
	}
	public String getLdapSearchfilter() {
		return ldapSearchfilter;
	}
	public void setLdapSearchfilter(String ldapSearchfilter) {
		this.ldapSearchfilter = ldapSearchfilter;
	}
	public String getLdapSearchresult() {
		return ldapSearchresult;
	}
	public void setLdapSearchresult(String ldapSearchresult) {
		this.ldapSearchresult = ldapSearchresult;
	}

}
