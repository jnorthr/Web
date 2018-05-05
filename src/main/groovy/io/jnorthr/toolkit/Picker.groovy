//@Grab('log4j:log4j:1.2.17')  use this when running outside gradle or groovyConsole
package io.jnorthr.toolkit;

/* select multi files ? see: http://stackoverflow.com/questions/11922152/jfilechooser-to-open-multiple-txt-files
JFileChooser chooser = new JFileChooser();
chooser.setMultiSelectionEnabled(true);
chooser.showOpenDialog(frame);
File[] files = chooser.getSelectedFiles();
if(files.length >= 2) 
*/

// **************************************************************
// Groovy code to choose one image file using our Chooser feature
// **************************************************************
import java.io.File;
import java.io.IOException;

import org.slf4j.*
import groovy.util.logging.Slf4j

//import org.apache.log4j.*
//import groovy.util.logging.*  

/**
* The Picker program implements a support application that
* allows user to pick a single image file or series of images from a folder directory.
*
* Initially starts to choose artifacts from program working directory and saves user
* choice of path in a local text file 
*
* Use annotation to inject log field into the class.
*
* @author  jnorthr
* @version 1.0
* @since   2018-03-17
*/
@Slf4j
public class Picker 
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
    public Picker()
    {
    	log.info("this is an .info msg from the Image Picker default constructor");
        setup();
    } // endof constructor    
    
    
   /**
    * Method to fill class variables by using the Chooser object
    */
    public void setup()
    {
        ch = new Chooser();
        ch.selectFileOnly();
        ch.setTitle("Pick a Folder");
    	log.info("setup changed currentDirectory to ${ch.initialPath}");
    } // end of setup


   /**
    * Method to prepare choose to only allow images to be selected.
    */
    public void setImage()
    {
        ch.allowImagesOnly();
        ch.setTitle("Pick an image");
    } // end of setup


    // =============================================================================
    /**
     * Returns a Response object to indicate what the user did in the JFileChooser dialog. 
     * 
     * This method always returns true if user clicked the APPROVE button indicating 
     * an actual choice was made else returns false if user aborted and failed to make a choice.
     *
     * @return response object with values from user choice
     */
    public Response getChoice()
    {
		re = ch.getChoice();
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
	 * need to test get image only files like .jpg using Filter class
	 */
        println "--- Starting Picker.groovy ---"
        def ch = new Picker();
        ch.say "... trying to find a folder";
        Response re = ch.getChoice();

        if (re.chosen)
        {
            ch.say "... path="+re.path;
            ch.say "... file name="+re.artifact;    
            ch.say "... the full name of the selected folder is "+re.fullname;    
        }
        else
        {
            ch.say "... no choice was made so output folder is "+re.fullname+" and path="+re.path;
            ch.say "... artifact name="+re.artifact;    
        }
        
		ch.say "------------------------"

        //ch.say "... try to pick a file-only image as png, jpg, gif, .svg";



        println "--- Ending Picker.groovy ---"

       System.exit(0);
    } // end of main    
    
} // end of class