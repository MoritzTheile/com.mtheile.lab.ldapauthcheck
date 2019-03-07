package com.mtheile.lab.ldapauthcheck;

public class Main {

	public static void main(String[] args) throws Exception {

		try {

			Writer.writeHeader();

			Konfig konfig = KonfigExImport.importOrCreateKonfig();


			String username = new LDAPAuth().loginAndReturnUsername(
					konfig.getSecurityPricipal(), 
					konfig.getSecurityCredentials(), 
					konfig.getProviderUrl(), 
					konfig.getLdapSearchscope(), 
					konfig.getLdapSearchfilter(), 
					konfig.getLdapSearchresult()
					);

			if(username != null) {

				Writer.writeToFile("Login with username '"+username+"' SUCCESSFUL");

			}else {

				Writer.writeToFile("Login FAILED because of username == null");

			}

		}catch(Exception e) {

			Writer.writeToFile("Login FAILED because of Exception: " + e.getMessage());

			e.printStackTrace();

		}

	}

}
