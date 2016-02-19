package runners;


public abstract class CommandLineRunner {
	
	public static void runApplication(String command, String arg) {
		Process p;
		// Run the application if support is available
		try {
			System.out.format("Launching %s ...%n", command);
			p = Runtime.getRuntime().exec("cmd /c start \"\" " + command + " " + arg);
			while(p.isAlive()) {
				System.out.println("Waiting for termination..");
				Thread.sleep(100);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		
		
		
	}
}
