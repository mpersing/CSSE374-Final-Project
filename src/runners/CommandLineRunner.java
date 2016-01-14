package runners;

import java.io.BufferedInputStream;

public abstract class CommandLineRunner {
	
	public static void runApplication(String command, String arg) {

		// Run the application if support is available
		try {
			System.out.format("Launching %s ...%n", command);
			Runtime.getRuntime().exec("cmd /c start " + command + " " + arg);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
