// groovy sample to choose one file using java's  JFileChooser
// would only allow choice of a single directory by setting another JFileChooser feature
// http://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
package io.jnorthr.toolkit;

// see more examples in above link to include a file filter
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;

import org.slf4j.*
import groovy.util.logging.Slf4j

/**
* The Chooser program implements a support application that
* allows user to pick a single file or single folder directory.
*
* Initially starts to choose artifacts from program working directory 
*
* Use annotation to inject log field into the class.
*
* @author  jnorthr
* @version 1.0
* @since   2018-05-01 
*/
@Slf4j
public class Chooser 
{
    /**
     * A class reflecting values chosen by the user of this Chooser.
     */
    Response re = new Response();

    // start to choose files from pwd
    def initialPath = System.getProperty("user.dir");

    def homePath = System.getProperty("user.home") + File.separator;

    JFileChooser fc 

   // =========================================================================
   /** 
    * Class constructor.
    * defaults to let user pick either a file or a folder
    */
    public getFileChooser()
    {
        homePath = homePath.collectReplacements(replacement);
        fc = new JFileChooser(homePath);
    } // endof constructor


    // replace windows \ values in homePath with /
    def replacement = 
    {
  	    // Change \\ to /
        if (it == '\\') 
        {
            '/'
        }
        // Do not transform
        else 
        {
          null
      }
    } // end of replacement


  // =============================================================================
    /**
     * Returns a Response object to indicate what the user did in the JFileChooser dialog. 
     * 
     * This method always returns true if user clicked the APPROVE button indicating 
     * an actual choice was made else returns false if user aborted and failed to make a choice.
     *
     * @return Response object including a boolean true if user clicked the APPROVE button
     *                false if user did not make a choice
     */
    public Response getChoice()
    {
        log.info "... getChoice starting";

        // fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        int result = fc.showOpenDialog( null );
        re.returncode = result;

        switch ( result )
        {
            case JFileChooser.APPROVE_OPTION:
                File file = fc.getSelectedFile();

                re.found = file.exists();
                re.chosen = true;      
                boolean isDirectory = file.isDirectory(); // Check if it's a directory
                boolean isFile =      file.isFile();   

                def path =  fc.getCurrentDirectory().getAbsolutePath();
                re.fullname = path;

                if (isDirectory)
                {
                    println "... folder path="+file.toString();
                    re.path = file.toString();
                    re.fullname = file.toString();
                    re.isDir = true;
                } // end of if

                if (isFile)
                {
                    println "... file path="+path+" file name="+file.toString();
                    re.path = path;
                    re.artifact = file.toString();
                    re.fullname = file.toString();
                } // end of if

                break;

            case JFileChooser.CANCEL_OPTION:
                log.info "... user cancelled gui";                
                re.abort = true;
                break;

            case JFileChooser.ERROR_OPTION:
                log.info "... user cancelled gui";
                re.abort = true;
                break;
        } // end of switch

        log.info "... getChoice ending with Response="+re;;

        return re;
    } // end of method


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
    // ---------------------------------------------------------------------
    /*
     * need to test the feature to allow user to choose a filename to saveAs
     */    
        Response re;  
        def ch = new getFileChooser();
        re = ch.getChoice();
        println "... getFileChooser Response="+ch.re;
        System.exit(0);
    } // end of main
    
} // end of class