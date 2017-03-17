package se.gafw.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * FileHandler provides a easy way to read files from disk to ram or write from ram to disk.
 * All the write functions returns the current instance of the filehandler so that you can write
 * x = handler.write(y).read(z);
 *
 */

public class FileHandler {
	
	private File file;
	
	/**
	 * NOTE: the file format does not really change anything, all files are written with default text headers, no compression
	 * is applyed when writing so just use whatever looks good. Althoe it is worth considering that the wrong file-format 
	 * can fool other applications that your file is something that it is not.
	 * 
	 * the parameters will be concatenated like this: path + fileName + "." + fileFormat
	 * @param path - the desired path to the file
	 * @param fileName - the file name
	 * @param fileFormat - the file format 
	 */
	
	public FileHandler(String path, String fileName, String fileFormat){
		file = new File(path);
		file.mkdirs();
		file = new File(path + fileName + "." + fileFormat);
		if(!file.exists()){
			try{
				file.createNewFile();
			}catch(IOException e) {System.err.println(":(  "+e.getMessage());}
		}
	}
	
	/**
	 * 
	 * Sets the file to a new path.
	 * 
	 * The parameters will be concatenated like this: path + fileName + "." + fileFormat
	 * @param path - the desired path to the file
	 * @param fileName - the file name
	 * @param fileFormat - the file format 
	 * @return this
	 */
	public FileHandler set(String path, String fileName, String fileFormat){
		file = new File(path);
		file.mkdirs();
		file = new File(path + fileName + "." + fileFormat);
		if(!file.exists()){
			try{
				file.createNewFile();
			}catch(IOException e) {System.err.println(":(  "+e.getMessage());}
		}
		return this;
	}
	/**
	 * Writes a file containing (all) the parameter(s) text.
	 * @param text The text to write
	 */
	public FileHandler write(String... text){
		try{
			StringBuilder toWrite = new StringBuilder();
			for(String s : text)toWrite.append(s + '\n');
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(toWrite.toString());
			writer.close();
		}catch(IOException e){System.err.println(":(  "+e.getMessage());}
		return this;
	}
	
	/**
	 * Does the same as write(String text) but takes an array of Strings representing different lines instead of a single string
	 * @param lines The text to write
	 */
	public FileHandler writeLines(String[] lines){
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for(int i = 0; i < lines.length; i++)
				writer.write(lines[i]+"\n");
			writer.close();
		}catch(IOException e){System.err.println(":(  "+e.getMessage());}
		return this;
	}
	
	/**
	 * Writes a serializable object to disk.
	 * @param o The object to be written.
	 * @return this
	 */
	public FileHandler writeObject(Object o){
		try{
			ObjectOutputStream stream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			stream.writeObject(o);
			stream.close();
		}catch(IOException e){System.err.println(":(  "+e.getMessage());}
		return this;
	}
	/**
	 * Writes an array of objects
	 * 	
	 * 	fileFolder
	 * 		subfolder
	 * 			fileName0.ser
	 * 			fileName1.ser
	 * 			fileName2.ser
	 * 			fileNameN.ser
	 *  
	 * @param subfolder if you want the files to be written in a subfolder relative to the original File folder, leave blank if not.
	 * @param fileNames the names of the multiple files to be written.
	 * @param toWrite   the objects to write.
	 * @return this
	 */
	public FileHandler writeObjects(String subfolder, String fileNames, Object[] toWrite){
		long timer = System.currentTimeMillis();
		File dir = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - file.getName().length())+subfolder);
		if(!dir.exists())dir.mkdirs();
		try{
			for(int i = 0; i < toWrite.length; i++){
				File f = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - file.getName().length())+subfolder+fileNames+i+".ser");
				f.createNewFile();
				ObjectOutputStream stream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
				stream.writeObject(toWrite[i]);
				stream.close();
			}
		}catch(Exception e){e.printStackTrace();}
		System.out.println("done writing "+toWrite.length+" objects from "+file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - file.getName().length())+subfolder+
				"\nin: " + (System.currentTimeMillis() - timer) / 1000.0 + "s");
		return this;
	}
	/**
	 * If the method writeObjects is used to write alot of objects to disk, this method can be used to read those.
	 * @param subfolder the subfolder containing all the files
	 * @param fileNames the root name of the files
	 * @return a list containing all the objects loaded from disk
	 */
	public List<Object> readObjects(String subfolder, String fileNames){
		long timer = System.currentTimeMillis();
		List<Object> result = new ArrayList<>();
		try{
			int ammountOfFiles = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - file.getName().length())+subfolder).listFiles().length;
			for(int i = 0; i < ammountOfFiles; i++){
				ObjectInputStream stream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - file.getName().length())+subfolder+fileNames+i+".ser"))));
				result.add(stream.readObject());
				stream.close();
			}
		}catch(Exception e){e.printStackTrace();}
		System.out.println("done reading "+result.size()+" objects from "+file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - file.getName().length())+subfolder+
				"\nin: " + (System.currentTimeMillis() - timer) / 1000.0 + "s");
		return result;
	}
	/**
	 * @return the object read from file path last specified
	 */
	public Object readObject(){
		Object result = null;
		try{
			ObjectInputStream stream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			result = stream.readObject();
			stream.close();
		}catch(ClassNotFoundException e){System.err.println(":(  "+e.getMessage());}catch(IOException e){System.err.println(":(  "+e.getMessage());}
		return result;
	}
	/**
	 * NOTE: use only when reading text files
	 * @return the text in the file read from the last specified path
	 */
	@Deprecated
	public String read(){
		String result = "";
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = reader.readLine()) != null)result += line+"\n";
			reader.close();
		}catch(IOException e){System.err.println(":(  "+e.getMessage());}
		return result;
	}
	
	/**
	 * reads text from a file on disk to ram
	 * @param path the filepath to the textfile
	 * @return the text read from the file
	 */
	public static String readText(String path){
		String result = "";
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			String line = null;
			while((line = reader.readLine()) != null)result += line+"\n";
			reader.close();
		}catch(IOException e){System.err.println(":(  "+e.getMessage());}
		return result;
	}
}
