/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

/**
 *
 * @author Ahmed
 */
public final class Log
{
    public static Log l =new Log();
    public static String OTPHEADER,OTPBODY;
    public static int PWDEXPIREDDAYS;
    public static String ADMINUSERMESSAGE;
    public static String FILEPATH;
    public static int NOOFFAILEDLOGINS;
    public static String EMAILADDRESS;
    private String propsFile="C:/Temp/exitstrategy.properties";
//    private String propsFile="/etc/exitstrategy.properties";
    private String INFO_LOG_FILE,FATAL_LOG_FILE, ERROR_LOG_FILE, MAX_LOGFILE_SIZE = "100MB",INFO_LOG_LEVEL, ERROR_LOG_LEVEL,FATAL_LOG_LEVEL;
    public int MAX_NUM_LOGFILES = 10;
    public Logger infoLog, errorLog, fatalLog;
    private Properties props;

    public Log()
    {
     loadProperties();
     initializeLoggers();
    }
    /**
     * @param args the command line arguments
     */

   public void loadProperties() {
		try 
                {
			props = new Properties();            
                        props.load(new FileInputStream(propsFile));

			/* Extract the values for the values in the configuration file */
			INFO_LOG_LEVEL = props.getProperty("INFO_LOG_LEVEL");
                        FATAL_LOG_LEVEL = props.getProperty("FATAL_LOG_LEVEL");
			ERROR_LOG_LEVEL = props.getProperty("ERROR_LOG_LEVEL");
                        MAX_NUM_LOGFILES=Integer.parseInt(props.getProperty("MAX_NUM_LOGFILES"));
                        MAX_LOGFILE_SIZE=props.getProperty("MAX_LOGFILE_SIZE");
                        INFO_LOG_FILE=props.getProperty("INFO_LOG_FILE");
                        FATAL_LOG_FILE=props.getProperty("FATAL_LOG_FILE");
                        ERROR_LOG_FILE=props.getProperty("ERROR_LOG_FILE");                        
                        OTPBODY=props.getProperty("OTPBODY");
                        OTPHEADER=props.getProperty("OTPHEADER");
                        ADMINUSERMESSAGE =props.getProperty("ADMINUSERMESSAGE");
                        NOOFFAILEDLOGINS =Integer.parseInt(props.getProperty("NOOFFAILEDLOGINS"));
                        PWDEXPIREDDAYS=Integer.parseInt(props.getProperty("PWDEXPIREDDAYS"));
                        EMAILADDRESS=props.getProperty("EMAILADDRESS");
		} catch (IOException ioe)
                {
			System.err.println("caught IOException attempting to loadProperties "+ ioe);
		}
	}

    public void initializeLoggers() {
        infoLog = Logger.getLogger("infoLog");
        errorLog = Logger.getLogger("errorLog");
        fatalLog = Logger.getLogger("fatalLog");

        PatternLayout layout = new PatternLayout();
        layout.setConversionPattern("%d{yyyy MMM dd HH:mm:ss,SSS}: %p : %m%n");

        try
        {
            RollingFileAppender rfaINFO_LOG = new RollingFileAppender(layout,  INFO_LOG_FILE, true);
            rfaINFO_LOG.setMaxFileSize(MAX_LOGFILE_SIZE);
            rfaINFO_LOG.setMaxBackupIndex(MAX_NUM_LOGFILES);

            RollingFileAppender rfaERROR_LOG = new RollingFileAppender(layout, ERROR_LOG_FILE, true);
            rfaERROR_LOG.setMaxFileSize(MAX_LOGFILE_SIZE);
            rfaERROR_LOG.setMaxBackupIndex(MAX_NUM_LOGFILES);
            
            RollingFileAppender rfaFATAL_LOG = new RollingFileAppender(layout, FATAL_LOG_FILE, true);
            rfaFATAL_LOG.setMaxFileSize(MAX_LOGFILE_SIZE);
            rfaFATAL_LOG.setMaxBackupIndex(MAX_NUM_LOGFILES);

            infoLog.addAppender(rfaINFO_LOG);
            fatalLog.addAppender(rfaFATAL_LOG);
            errorLog.addAppender(rfaERROR_LOG);
        } catch (Exception e) {
            System.err.println("initiliazeLog --- EXCEPTION THROWN WHILE CREATING A RollingFileAppender"
                    + e);
            e.printStackTrace();
        }

        infoLog.setLevel(Level.toLevel(INFO_LOG_LEVEL));
        fatalLog.setLevel(Level.toLevel(FATAL_LOG_LEVEL));
        errorLog.setLevel(Level.toLevel(ERROR_LOG_LEVEL));
    }

}
