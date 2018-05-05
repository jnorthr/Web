//@Grab('log4j:log4j:1.2.17')  use this when running outside gradle or groovyConsole
package io.jnorthr.toolkit;

// groovy code to choose one image file using our Chooser feature
// **************************************************************
import java.io.File;
import java.io.IOException;
//import org.apache.log4j.*
//import groovy.util.logging.*  
import io.jnorthr.toolkit.Response;

import org.slf4j.*
import groovy.util.logging.Slf4j

/**
* The Saver program implements a support application that
* allows user to choose an output folder for the project.
*
* Use annotation to inject log field into the class.
*
* @author  jnorthr
* @version 1.0
* @since   2016-08-17
*/
@Slf4j
public class Saver 
{       
    /**
     * Handle to component used to hold user selected values from the chooser dialog.
     */
    Response re = new Response();
    
    
    /**
     * Handle to component used by the chooser dialog.
     */
    Chooser ch = null;

    
   // =========================================================================
   /** 
    * Class constructor.
    *
    * defaults to let user pick either a file or a folder
    */
    public Saver()
    {
    	log.info("this is an .info msg from the Saver default constructor");
        setup();
    } // endof constructor    
    
    
   /**
    * Method to prepare class variables by reading a possibly non-existent cache file written in prior run.
    *
    * Then run logic to ask user to pick an output folder.
    */
    public void setup()
    {
        ch = new Chooser();
        ch.saveAs("fred.html");
        ch.setOpenOrSave(false);
        ch.setTitle("Save Filename in this Output Folder");
        say "trying to pick a project output folder-only";
    } // endof setup



	// =============================================================================
    /**
     * Returns a Respoonse object to indicate what the user did in the Chooser dialog. 
     * 
     * This method always returns Response.abort of false if user clicked the APPROVE button indicating 
     * an actual choice was not made else returns true if user makes a choice and it's values.
     *
     * @return Response false if user clicked the CANCEL button was not used
     *                true if user made a choice
     */
    public Response getChoice()
    {
		re = ch.getChoice();
		def x = re.artifact.trim().size();
		if (x < 1) { re.chosen = false; }
		return re;
	} // end of getChoice

   /** 
    * Produce log messages using .info method
    */
    public void say(String msg)
    {
    	log.info msg;
    } // end of say
    
    
	// =============================================================================    
    /**
     * The primary method to execute this class. Can be used to test and examine logic and performance issues. 
     * 
     * argument is a list of strings provided as command-line parameters. 
     * 
     * @param  args a list of possibly zero entries from the command line
     */
    public static void main(String[] args)
    {
		/*
		 * need to test the feature to allow user to choose a filename to saveAs
		 */    
        def obj = new Saver();
        Response re = obj.getChoice();
        def msg = (re.chosen) ? "response="+re.toString() : "no choice was made so output is "+re.fullname+" and path="+re.path;
        println msg;
        System.exit(0);
    } // end of main    
    
} // end of class