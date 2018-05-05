// groovy sample to choose one file or folder using java's  JFileChooser
// would only allow choice of a single directory by setting another JFileChooser feature
// http://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
package io.jnorthr.toolkit;

// see more examples in above link to include a file filter
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.*;

import org.slf4j.*
import groovy.util.logging.Slf4j

import io.jnorthr.toolkit.PathFinder;

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
public class FileChooser 
{
    /**
     * A class reflecting values chosen by the user of this Chooser.
     */
    Response re = new Response();

    // start to choose files from pwd
    def initialPath = System.getProperty("user.dir");

    // start to choose files from home path
    def homePath = System.getProperty("user.home") + File.separator;

    // choose files from this object 
    JFileChooser fc = null;

    /**
     * A class to gain system values about the local file structure paths.
     */
    PathFinder pf = new PathFinder();

   // =========================================================================
   /** 
    * Class constructor.
    *
    * defaults to let user pick either a file or a folder
    */
    public FileChooser()
    {
        homePath = pf.getHomePath();
        initialPath = pf.getPWD();

        fc = new JFileChooser(homePath);
        fc.setDialogTitle("Pick One Folder or One File");
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    } // endof constructor

  // =============================================================================
  /**
     * Returns a Response object to indicate what the user did in the JFileChooser dialog. 
     * 
     * This method always returns true if user clicked the APPROVE button indicating 
     * an actual choice was made else returns false if user aborted and failed to make a choice.
     *
     * @return Response object including a boolean true if user clicked the OPEN button
     *                false if user did not make a choice
     */
    public Response getChoice(boolean ok)
    {
        log.info "... getChoice starting";
        if (ok)
        {
            fc.setDialogTitle("Pick One Folder");
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        }  
        else
        {        
            fc.setDialogTitle("Pick One File");
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        }

        log.info "... getChoice ending";

        return getResponse();
    } // end of method


  /**
     * Returns a Response object to indicate what the user did in the JFileChooser dialog. 
     * 
     * This method always returns true if user clicked the APPROVE button indicating 
     * an actual choice was made else returns false if user aborted and failed to make a choice.
     *
     * @return Response object including a boolean true if user clicked the CHOOSE button
     *                false if user did not make a choice
     */
    public Response getResponse()
    {
        log.info "... getResponse starting";
JFrame frame = new JFrame("Frame Demo");
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setVisible(true);
        int result = fc.showOpenDialog( frame );
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
                    log.info "... folder path="+file.toString();
                    re.path = file.toString();
                    re.fullname = file.toString();
                    re.isDir = true;
                } // end of if

                if (isFile)
                {
                    log.info "... file path="+path+" file name="+file.toString();
                    re.path = path;
                    re.artifact = file.toString().substring(file.toString().lastIndexOf(File.separator) + 1)                                        
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

        log.info "... getResponse ending"

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
     * need to test the feature to allow user to choose a filename or folder name 
     */    
        Response re;  

        def ch3 = new FileChooser();
        re = ch3.getChoice(false);
        println "... FileChooser File-only Response="+re;

        def ch = new FileChooser();
        re = ch.getResponse();
        println "... FileChooser either Folder or File Response="+ch.re;

        def ch2 = new FileChooser();
        re = ch2.getChoice(true);
        println "... FileChooser Folder-only Response="+re;

        System.exit(0);
    } // end of main
    
} // end of class