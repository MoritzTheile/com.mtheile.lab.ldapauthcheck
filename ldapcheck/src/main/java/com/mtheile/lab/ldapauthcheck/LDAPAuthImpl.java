package com.mtheile.lab.ldapauthcheck;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LDAPAuthImpl {

	/**
	 * First step: Authenticate to LDAP 
	 * 
	 * Second step: search for own username as proof of having access. 
	 * 
	 * Authentication successful if username can be found and is returned.
	 * 
	 * Authentication failed if null is returned or exception is thrown.
	 * 
	 * (Attention: Authentication relies on LDAP-Authentication. 
	 * If LDAP doesn't require authentication this method will 
	 * always authenticate.) 
	 * 
	 */
	public String loginAndReturnUsername(
			String SECURITY_PRINCIPAL,
			String SECURITY_CREDENTIALS,
			String PROVIDER_URL,
			String LDAP_SEARCHSCOPE,
			String LDAP_SEARCHFILTER,
			String LDAP_SEARCHRESULT
			) throws Exception {

		// see https://stackoverflow.com/questions/12359831/java-ldap-make-it-not-ignore-blank-passwords
        if(SECURITY_CREDENTIALS == null || SECURITY_CREDENTIALS.trim().isEmpty()) {
            throw new Exception("no password provided");
        }

		InitialDirContext initialDirContext = login(
				SECURITY_PRINCIPAL, 
				SECURITY_CREDENTIALS, 
				PROVIDER_URL
				);
		
		if (initialDirContext == null) {

			throw new Exception("InitialDirContext could not be initialized.");

		}

		// if dircontext can be searched we know the user has access to LDAP.
		return searchKontext(
				initialDirContext, 
				LDAP_SEARCHSCOPE, 
				LDAP_SEARCHFILTER, 
				LDAP_SEARCHRESULT
				);
		
	}

	private InitialDirContext login(String SECURITY_PRINCIPAL, String SECURITY_CREDENTIALS, String PROVIDER_URL) throws Exception {

		try {

			Properties props = new Properties();

			props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			props.put(Context.PROVIDER_URL, PROVIDER_URL);
			props.put(Context.SECURITY_PRINCIPAL, SECURITY_PRINCIPAL);
			props.put(Context.SECURITY_CREDENTIALS, SECURITY_CREDENTIALS);

			// this one is important! throws Exception if no login possible
			 return new InitialDirContext(props);

		} catch (Exception e) {

			return null;

		}

	}

	private String searchKontext(InitialDirContext initialDirContext, String LDAP_SEARCHSCOPE, String LDAP_SEARCHFILTER, String LDAP_SEARCHRESULT) throws NamingException {

		SearchControls ctrls = new SearchControls();

		ctrls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		NamingEnumeration<SearchResult> answers = initialDirContext.search(LDAP_SEARCHSCOPE, LDAP_SEARCHFILTER, ctrls);

		if (answers.hasMore()) {

			SearchResult answer = answers.next();

			NamingEnumeration<? extends Attribute> attributes = answer.getAttributes().getAll();

			String result = null;

			while (attributes.hasMoreElements()) {
				Attribute attribute = attributes.next();
				if (LDAP_SEARCHRESULT.equals(attribute.getID())) {
					result = attribute.get() + "";
				}
			}

			return result;

		}

		return null;
		
	}

	
	public static void main(String[] args) throws Exception{
		
		System.out.println(new LDAPAuthImpl().loginAndReturnUsername(
				"uid=riemann,dc=example,dc=com",// SECURITY_PRINCIPAL,
				"password",// SECURITY_CREDENTIALS,
				"ldap://ldap.forumsys.com:389",// PROVIDER_URL,
				"dc=example,dc=com",// LDAP_SEARCHSCOPE,
				"uid=riemann",// LDAP_SEARCHFILTER,
				"uid"// LDAP_SEARCHRESULT

				));
		
	}
	}
