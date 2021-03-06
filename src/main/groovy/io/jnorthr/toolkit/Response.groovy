package io.jnorthr.toolkit;

import org.slf4j.*
import groovy.util.logging.Slf4j

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* A class that contains results of most recent user dialog
*
* Use annotation to inject log field into the class.
*
* @author  jnorthr
* @version 1.0
* @since   2016-08-27
*/
 
// Use annotation to inject log field into the class.
@Slf4j
public class Response
{
    /**
     * t/f indicator of the user's inter-action with the dialog. True when user hits JFileSaver.APPROVE_OPTION
     */
    boolean chosen = false;


    /**
     * Integer indicator of the user's inter-action with the chooser. For example: JFileChooser.APPROVE_OPTION
     */
    int returncode = -1;


    /**
     * Temp work area holding the absolute path to the user's artifact selected with the chooser. 
     * For example: fc.getCurrentDirectory().getAbsolutePath() 
	 *
	 * example results after choosing a file artifact:
     *
	 * APPROVE path=/Users/jimnorthrop/Dropbox/Projects/Web/config artifact=logback.xml 
	 * fullname=/Users/jimnorthrop/Dropbox/Projects/Web/config/logback.xml 
	 * path=/Users/jimnorthrop/.path.txt 
	 * isDir=false
     */
    def path = null;


    /**
     * Temp work area holding only the name of the file the user's artifact selected with the chooser, 
     * but not it's path. Holds a value when choice is a file and parent path is in 'path' variable above.
     */
    def artifact = null;
    
    /**
     * Temp work area holding the full and complete absolute path plus file name of the user's artifact 
     * selected with the chooser. 
     */
    def fullname = null;


    /**
     * Flag set when name of the user's artifact 
     * selected with the chooser is a folder directory 
     */
    boolean isDir = false;

    /**
     * Flag set when name of the user's artifact 
     * points to an actual file or folder that really does exist 
     */
    boolean found = false;


    /**
     * Flag set when user hits cancel button on dialog 
     */
    boolean abort = false;


    /**
      * default class constructor
      */
    public Response()
    {
		//log.level = Level.INFO
    }

    /** 
     * to get user selection of path of a known local folder minus trailing artifact.
     */
    public String getPath()
    {
        return path;
    } // end of getPath


    /** 
     * To get user selection of  full name of a known local folder.
     */
    public String getName()
    {
        return fullname;
    } // end of getName


    /** 
     * To get user selection of file name in a known local folder when/if a file was chosen to be saved.
     */
    public String getArtifact()
    {
        return artifact
    } // end of getArtifact


    /** 
     * To get user selection: true if user APPROVE or CHOOSE.
     */
    public boolean getChosen()
    {
        return chosen;
    } // end of getChosen

    /** 
     * To see if user selection is a known local folder.
     */
    public getDir()
    {   
        return isDir;
    } // end of getDir


    /**
      * class method to pump out log entries as 'info'
      */
    def say()
    {
    	log.info this.toString();
    }

    /**
      * class toString() method
      */
        String toString() {
"""
chosen=${chosen}
returncode=${returncode}
path=${path}
artifact=${artifact} 
fullname=${fullname}
found=${found}
isDir=${isDir}
cancelled=${abort}""".toString()
         } // end of toString()    
         

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
        def obj = new Response();
        println "Response object="+obj.toString();
        
        println "... getDir()="+obj.getDir();
        println "... getChosen()="+obj.getChosen()
        println "... getArtifact()="+obj.getArtifact();
        println "... getPath()="+obj.getPath()
        println "... getName()="+obj.getName()

        println "--- Response end ---\n";
	} // end of main
	
} // end of class 
