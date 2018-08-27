package ads2_image_cic;

import java.awt.FileDialog;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class ImgFiles {
    final private JFrame frame;
    private FileDialog fd;
    private String path;
    private BufferedImage image;
    private JFileChooser  chooser;
  
        
    public ImgFiles(){
        this.frame = new JFrame("JFileChooser Popup");
    }
    
    /*Open Image dialog box and return the chosen image*/
    public BufferedImage ImgFromDialog(){
        this.fd = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
        this.fd.setDirectory(".");
        this.fd.setVisible(true);
       
        try {            
            this.path = fd.getDirectory()+fd.getFile();
            this.image = ImageIO.read(new File(path));
            return image;
        }
        catch (IOException e){
            System.out.println("Error: "+e);
            return null;
        }        
    }
    
    /*return image by using path from File class */
    public static BufferedImage ImgFromPath(File imgPath){
        try {            
            return ImageIO.read(imgPath);
        }
        catch (IOException e){
            System.out.println("Error: "+e);
            return null;
        }  
    }
    
    /*Open file dialog to choose a folder and return all the mathcing files based on ImageFileFilter*/
    public File[] ListFiles() {
        File[] listOfFiles=null;
        this.chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        
        this.chooser.setDialogTitle("Please select an image database");
        this.chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.chooser.setAcceptAllFileFilterUsed(false);

        if (this.chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File folder = new File(""+this.chooser.getSelectedFile());
            listOfFiles = folder.listFiles(new ImageFileFilter());                    
        } 
        return listOfFiles;
    }
    
    /*Print all the files name from File[] in the console*/
    public static void PrintAllFilesNames (File[] listOfFiles){
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
            }   else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
            
        }  
    }
    
    /*Define a filter for ListFiles() method*/
    public class ImageFileFilter implements FileFilter
    {
        private final String[] okFileExtensions = new String[] {"jpg", "png", "bmp"};
        public boolean accept(File file)
        {
            for (String extension : okFileExtensions)            
                if (file.getName().toLowerCase().endsWith(extension))
                    return true;           
            return false;
        }
    }
}    
    

