package pl.wit.bikerental;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class App 
{
	protected static final Logger log = LogManager.getLogger(App.class.getName());
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        log.error("error");
    }
}


