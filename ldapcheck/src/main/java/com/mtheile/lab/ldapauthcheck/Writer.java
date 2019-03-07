package com.mtheile.lab.ldapauthcheck;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;

public class Writer {

	private static final String RESULT_FILE = "./result.txt";

	private static PrintStream printStream;

	public static void writeToFile(String string) throws Exception {

		printStream.append(string + "\n");

	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSS");

	public static void writeHeader() throws Exception {

		writeToFile("" + "------------------------------------------------------------------------------\n"
				+ "------------------------------------------------------------------------------\n"
				+ "------------------------------------------------------------------------------\n"
				+ sdf.format(System.currentTimeMillis()) + "\n\n");

	}
	
	// In this static block the printStream is managed when class is loaded (initiated) or the runtime is shut down (closed).
	// Also the system streams are directed to the file.
	static {
		
		try {
			
			File file = new File(RESULT_FILE);

			if (!file.exists()) {
				file.createNewFile();
			}
			
			printStream = new PrintStream(new FileOutputStream(file, true));
			
			System.setOut(printStream); 
			System.setErr(printStream);

			Runtime.getRuntime().addShutdownHook(new Thread(){
				
				@Override
				public void run(){
					
					try {
						
						writeToFile("\n\nshutting down...");
						
						if(printStream != null) {

							printStream.close();

						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
				}
				
			});
			
		} catch (Exception e) {

			e.printStackTrace();
			
		}
		
	} 

}
