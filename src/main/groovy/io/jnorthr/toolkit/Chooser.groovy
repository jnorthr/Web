//@Grab('log4j:log4j:1.2.17')  use this when running outside gradle or groovyConsole

package io.jnorthr.toolkit;
// groovy sample to choose one file using java's  JFileChooser
// would only allow choice of a single directory by setting another JFileChooser feature
// http://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
// see more examples in above link to include a file filter
// fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
// fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
// **************************************************************
import io.jnorthr.toolkit.PathFinder;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.*;
import java.awt.Dimension;

import org.slf4j.*
import groovy.util.logging.Slf4j

/**
* The Chooser program implements a support application that
* allows user to pick a single file or single folder directory.
*
* Initially starts to choose artifacts from program working directory and saves user
* choice of path in a local text file 
*
* Use annotation to inject log field into the class.
*
* @author  jnorthr
* @version 1.0
* @since   2016-08-01 
*/
@Slf4j
public class Chooser 
{
    /**
     * A class reflecting values chosen by the user of JFileChooser.
     */
    Response re;

    /**
     * The kind of JFileChooser to show the user.
     */
    boolean openOrSave = true;

    /**
     * True if the user can select a single local file artifact.
     */
    boolean fileSelect = true;

    /**
     * True if the user can select a single local directory folder artifact.
     */
    boolean pathSelect = true;

    /**
     * A path value to influence the JFileChooser as where to allow the user to initially pick a local file artifact.
     * Can be over-written by a value chosen in the previous run of this module. See 'configPath' below
     */
     def initialPath = "";

    /**
     * A path value to influence the JFileChooser as where to allow the user to initially pick a local file artifact.
     * Can be over-written by a value chosen in the previous run of this module. See 'configPath' below
     */
	   def initialFile = "fileToSave.txt";

    /**
     * Handle to component used by the chooser dialog.
     */
     JFileChooser fc = new JFileChooser();
    
    /**
     * Integer value to influence the dialog of what's allowed in the user's inter-action with the chooser. 
     * For example: JFileChooser.FILES_AND_DIRECTORIES, JFileChooser.DIRECTORIES_ONLY, JFileChooser.FILES_ONLY 
     */
     java.lang.Integer mode = JFileChooser.FILES_AND_DIRECTORIES;

    /**
     * Temp work area holding a default file path and file name. This name points to a cache where the selected 
     * path from a prior run is stored.  
     */
     String configPath = initialPath +".path.txt";

    /**
     * Temp work area holding a default file path and file name. This name points to a cache where the selected 
     * full filename from a prior run is stored.  
     */
     String configFile = initialPath  +".file.txt";

    /**
     * This is the title to appear at the top of user's dialog. It confirms what we expect from the user.  
     */
     String menuTitle = "Make a Selection";
    
    /**
     * This is logic to get the name of the home folder used by this user.  
     */
     PathFinder pf = new PathFinder();
        
    /**
     * This is logic to only permit certain files with specific suffixes.  
     */
	   FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG, JPG, SVG & GIF Images", "png", "jpg", "gif", "svg");

   // =========================================================================
   /** 
    * Class constructor.
    * defaults to let user pick either a file or a folder
    */
    public Chooser()
    {
    	log.info("Chooser() - this is an .info msg from the Chooser default constructor");
      initialPath = pf.getHomePath();
		  re = new Response();

      log.info "re.fullname set to "+initialPath;
    	re.fullname = initialPath;
    	re.path = initialPath;
      log.info "re.path set to "+initialPath;
      selectBoth();
      setup();
    } // endof constructor
    

   /** 
    * Ask JFileChooser to only allow user to pick a local file but not folder.
    */
    public void setTitle(String newTitle)
    {
      log.info("setTitle(String ${newTitle})");
      menuTitle = newTitle;
      fc.setDialogTitle(menuTitle);
    } // end of method


   /** 
    * Influences JFileChooser to use either the 'Open' dialog if true or the 'Save' dialog if false.
    */
    public void setOpenOrSave(boolean oos)
    {
        log.info("setOpenOrSave(String ${oos})");
        openOrSave = oos;
    } // end of method

    
   /** 
    * Ask JFileChooser to only allow user to pick a local file but not folder.
    */
    public void selectFileOnly()
    {
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        pathSelect = false;
        fileSelect = true;
    } // endof method


   /** 
    * Ask JFileChooser to only allow user to pick a local directory folder but not a file.
    */
    public void selectFolderOnly()
    {
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileSelect = false;
        pathSelect = true;
    } // endof method


   /** 
    * Ask JFileChooser to only allow user to pick a local directory folder but not a file.
    */
    public void selectBoth()
    {
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileSelect = true;
        pathSelect = true;
    } // endof method
        
   /**
    * Method to prepare class variables by reading a possibly non-existent cache file written in prior run.
    */
    public void setup()
    {
        // look for .path.txt
        boolean present = new File(configPath).exists()
        if (present) 
        { 
        	initialPath = new File(configPath).getText(); 
          log.info "re.path set to "+initialPath;
        	re.path = initialPath; 
        	re.fullname = re.path;
        } // end of if
        
        // look for .file.txt
        present = new File(configFile).exists()
        if (present) 
        { 
        	initialFile = new File(configFile).getText(); 
        	re.artifact = initialFile; 
          log.info "re.artifact set to "+initialFile;

        	if (initialFile.size() > 0)
        	{
	        	re.fullname = re.path+File.separator+initialFile;
            log.info "re.fullname set to "+re.fullname;
	        } // end of if
        } // end of if 

        fc = new JFileChooser();
        //fc.addChoosableFileFilter(filter);

        mode = JFileChooser.FILES_AND_DIRECTORIES;
        fileSelect = true;
        pathSelect = true;

        log.info "mode set to FILES_AND_DIRECTORIES";

        if (pathSelect)
        {
            if (fileSelect)
            {
                mode = JFileChooser.FILES_AND_DIRECTORIES;
                log.info "mode set to FILES_AND_DIRECTORIES";
                fileSelect = true;
                pathSelect = true;
            }
            else
            {
                mode = JFileChooser.DIRECTORIES_ONLY;
                log.info "mode set to DIRECTORIES_ONLY";
                fileSelect = false;
                pathSelect = true;
            }
        }
        else
        {
            if (fileSelect) 
            { 
                mode = JFileChooser.FILES_ONLY; 
                log.info "mode set to FILES_ONLY";
                fileSelect = true;
                pathSelect = false;
            }
        }       

        fc.setFileSelectionMode(this.mode);
        fc.setDialogTitle(menuTitle);

        File workingDirectory = new File(pf.getPWD()); 
        fc.setCurrentDirectory(workingDirectory);
        log.info("setup changed fc.setCurrentDirectory to ${initialPath}");
    } // end of setup


   /** 
    * Influence JFileChooser to allow user selection to begin from a known local folder.
    */
    public void setPath(String newPath)
    {
        initialPath = newPath;
        log.info "setPath to "+newPath;

        File workingDirectory = new File(newPath);
        if ( !workingDirectory.exists() ) { throw new RuntimeException("Cannot setPath to non-existence path:"+newPath)} 
        log.info "setPath sets workingDirectory to "+workingDirectory.toString();

        fc.setCurrentDirectory(workingDirectory);
    } // endof setPath


   /** 
    * Influence JFileChooser to suggest to user the name of the output file to Save As.
    */
    public saveAs(String filename)
    {
        log.info "saveAs initialFile set to "+filename;
        initialFile = filename;
    } // endof setPath


   /** 
    * Influence JFileChooser to only allow image files to be selected by the user.
    */
    public allowImagesOnly()
    {
        log.info "allowImagesOnly()"
  	    fc.setFileFilter(filter);
    } // endof setPath


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
        log.info "... getChoice where openOrSave="+openOrSave;

        if (!openOrSave) 
        { 
            log.info "... setSelectedFile="+initialFile;
            fc.setFileSelectionMode(mode);
            fc.setSelectedFile(new File(initialFile)); 
            re.returncode = fc.showSaveDialog(null);
            log.info "... getChoice re.returncode is "+re.returncode;
        }
        else
        {
            log.info "... openOrSave="+openOrSave
            //fc = new JFileChooser();
            //fc.setFileSelectionMode(mode);
            JFrame frame = new JFrame("FrameDemo");
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            //frame.setPreferredSize(new Dimension(400, 300));
            frame.setVisible(true);
            re.returncode = fc.showOpenDialog(frame);
            log.info "... getChoice re.returncode is "+re.returncode;
            //frame.setVisible(false);
            //frame.dispose();
        }

        //re.returncode = (!openOrSave) ? fc.showSaveDialog(null) : fc.showOpenDialog(frame) ;
        re.chosen = false;
        log.info "... re.returncode="+re.returncode
        
        switch ( re.returncode )
        {
            case JFileChooser.APPROVE_OPTION:
                  File file = fc.getSelectedFile();
				          re.returncode = JFileChooser.APPROVE_OPTION;
				          re.found = file.exists();
				          log.info "JFileChooser.APPROVE_OPTION chose dir:"+fc.getCurrentDirectory().getAbsolutePath();
				          // was this a directory folder ?
                  re.isDir = new File(file.toString()).isDirectory();

                  re.fullname = file.toString();
                  re.path = fc.getCurrentDirectory().getAbsolutePath();
                  re.artifact = (re.isDir) ? "" : file.name; 

                  log.info "APPROVE path="+re.path+" artifact="+re.artifact+" fullname="+re.fullname+" configPath="+configPath+" isDir="+re.isDir;
                  
                  // keep path and filename for next time in our external ~/.path.txt file
                  def fo = new File(configPath)
                  fo.text = (re.isDir) ? re.fullname : re.path;
				          fo = new File(configFile)
                  fo.text = (re.isDir) ? "" : re.artifact;
                   
                  re.chosen = true;
                  break;

            case JFileChooser.CANCEL_OPTION:
				          re.returncode = JFileChooser.CANCEL_OPTION;
                  re.chosen = false;
                  re.found = false;
                  re.abort = true;
                  log.info "user cancelled action";
                  break;

            case JFileChooser.ERROR_OPTION:
				          re.returncode = JFileChooser.ERROR_OPTION;
                  re.found = false;
                  re.chosen = false;
                  log.info "user action caused error";
                  break;
        } // end of switch
        
        println "Chooser getChoice Response=\n"+re.toString();
        return re;
    } // end of pick


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

		  // ---------------------------------------------------------------------
		  /*
		   * need to test the feature to allow user to choose a filename to saveAs
	     */    
		    Response re;  
        def ch = new Chooser();


      	ch.say "------------------------"
        ch.say "... Try a SAVE feature"
        ch.saveAs("save.me");
        ch.setOpenOrSave(false);
        ch.setTitle("Pick output Folder then enter Save As Filename");

        re = ch.getChoice(); 
        if (re.abort)
        {
    		    println "user clicked 'cancel' button"
        } // end of if
    	
    		println re;

        if (re.chosen && !re.abort)
        {
            ch.say "... path="+re.path+"\nartifact name="+re.artifact;    
            ch.say "... the full name of the output file is "+re.fullname;
            ch.say "... isDir ? = "+re.isDir;    
        }
        else
        {
            ch.say "... no choice was made so output path is "+re.path+" and name="+re.fullname;
        }

		// ---------------------------------------------------------------------
		/*
		 * need to test the default to allow user to choose a folder OR a file
		 */
		    println "\n------------------------\n"
        def ch2 = new Chooser();
        ch2.say "... Try the default feature"
        re = ch2.getChoice(); 
        println re;

        if (re.abort)
        {
    	     println "... user clicked 'cancel' button"
    	  } // end of if
    	
      if (re.chosen && !re.abort)
      {
        	ch2.say "... re.path="+re.path+"\n... re.artifact name="+re.artifact;    
          ch2.say "... the full name of the selected choice is "+re.fullname;
          ch2.say "... isDir ? = "+re.isDir;    
      }
      else
      {
            ch2.say "... no choice was made so output re.path is "+re.path+" and re.fullname="+re.fullname;
		  }
		
      ch2.say "------------------------\n"
      //System.exit(0);

		// ---------------------------------------------------------------------
		/*
		 * need to test selecting folders only
		 */
		  ch.say "\n------------------------\n"
      ch = new Chooser();
      ch.say "Pick a folder-only test"
      ch.setTitle("Pick input Folder");
      ch.selectFolderOnly();
    	re = ch.getChoice(); 
    	if (re.abort)
    	{
    		println "user clicked 'cancel' button"
    	} // end of if
    	
		  println re;

      if (re.chosen && !re.abort)
      {
            ch.say "path="+re.path+"\nfile name="+re.artifact;    
            ch.say "the full name of the selected folder is "+re.fullname;    
            ch.say "isDir ? = "+re.isDir;    
      }
      else
      {
            ch.say "no choice was made so folder will be "+re.path+" and name="+re.fullname;
		  }
		  ch.say "------------------------\n"


		// ---------------------------------------------------------------------
		/*
		 * need to test get image only files like .jpg using Filter class
		 */
		  ch.say "\n------------------------\n"
      ch = new Chooser();
      ch.say "trying to pick a file-only image as png, jpg, gif"
      ch.setTitle("Pick input image");
      ch.selectFileOnly();
      ch.allowImagesOnly();
    	re = ch.getChoice();
    	
		  println re;

    	if (re.abort)
    	{
    	     println "user clicked 'cancel' button"
    	} // end of if
    	         
      if (re.chosen && !re.abort)
      {
            ch.say "path="+re.path+"\nfile name="+re.artifact;    
            ch.say "the full name of the selected image is "+re.fullname;    
            ch.say "isDir ? = "+re.isDir;   
      }
      else
      {
            ch.say "no choice was made so image file is "+re.path+" and name="+re.fullname;
		  }

		  ch.say "------------------------\n"

      System.exit(0);
    } // end of main

} // end of class