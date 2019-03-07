package com.mtheile.lab.ldapauthcheck;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

public class KonfigExImport {
	
	private static final String KONFIG_FILE = "./konfig.json.txt";

	public static Konfig importOrCreateKonfig() throws Exception {
		
		return new ObjectMapper().readValue(getOrCreateKonfigFile(), Konfig.class);
		
	}
	
	public static void exportKonfig(Konfig konfig) throws Exception {
		
		exportKonfig(konfig, getOrCreateKonfigFile());
		
	}
	
	private static void exportKonfig(Konfig konfig, File file) throws Exception {

		new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(file, konfig);
	
	}

	private static File getOrCreateKonfigFile() throws Exception {
		
		File file = new File(KONFIG_FILE);
		
		if(!file.exists()) {
			
			file.createNewFile();
			
			initWithDefaultKonfig(file);
			
		}
		
		return file;
		
	}

	private static void initWithDefaultKonfig(File file) throws Exception {

		Konfig konfig = new Konfig();

		konfig.setSecurityPricipal("uid=riemann,dc=example,dc=com");
		konfig.setSecurityCredentials("password");
		konfig.setProviderUrl("ldap://ldap.forumsys.com:389");
		konfig.setLdapSearchscope("dc=example,dc=com");
		konfig.setLdapSearchfilter("uid=riemann");
		konfig.setLdapSearchresult("uid");
		
		exportKonfig(konfig, file);
		
	}
}
