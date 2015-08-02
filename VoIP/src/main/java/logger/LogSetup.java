package logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Basic Log Setup for the Commons logging Logger Class.
 * 
 * Configures the main log file for all the classes involved in VOIP project.
 *
 * @author Ankit Bahuguna
 * @email: ankit.bahuguna@cs.tum.edu
 *
 */
public class LogSetup {
	/**
	 * Returns a log handle wrt to a specified classname passed as a string
	 * argument.
	 * 
	 * @param classname
	 *            : <String> : Name of the class, Recommended use :
	 *            ClassName.class.getname()
	 * @return log handle for the class
	 */
	public Log getLog(String classname) {
		System.setProperty("log4j.configuration", "log4j.properties");
		Log voiplog = LogFactory.getLog(classname);

		return voiplog;
	}

}
