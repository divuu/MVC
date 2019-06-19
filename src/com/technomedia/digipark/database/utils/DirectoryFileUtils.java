package com.technomedia.digipark.database.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.Vector;

import org.codehaus.jettison.json.JSONArray;

import com.technomedia.logger.MLogger;

public class DirectoryFileUtils {
	// Vector

	/**
	 * Get list of all files in a directory
	 * 
	 * @param folder
	 *            : Directory path
	 * @return Vector list of all file in the given directory and its
	 *         subdirectory.
	 */
	public Vector<File> getFilesFromFolder(final File folder) {
		File[] fileList = folder.listFiles();
		Vector<File> resultFiles = new Vector<File>();
		Vector<File> subFiles = new Vector<File>();

		for (final File fileEntry : fileList) {
			if (fileEntry.isDirectory()) {
				subFiles = getFilesFromFolder(fileEntry);
				MLogger.i(MLogger.MOD_DB,
						"Sub Folder--> " + fileEntry.getName());
			} else {
				resultFiles.add(fileEntry);
				MLogger.i(MLogger.MOD_DB, "File--> " + fileEntry.getName());
			}
			if (!subFiles.isEmpty()) {
				for (File f : subFiles) {
					resultFiles.add(f);
				}
				subFiles.removeAll(subFiles);
			}
		}
		return resultFiles;
	}

	/**
	 * Save Input Stream to a specified file
	 * 
	 * @param is
	 *            : InputStream
	 * @param saveTo
	 *            : file to save data
	 * @return true if success else false
	 */
	public boolean saveInputStreamToFile(InputStream is, String saveTo) {

		boolean status = false;
		OutputStream out = null;
		int read = 0;

		try {
			MLogger.i(MLogger.MOD_DB, "Save Input Stream to File " + saveTo);

			byte[] bytes = new byte[1024];

			File opFile = new File(saveTo);
			out = new FileOutputStream(opFile);

			// if file doesn't exists, then create it
			if (!opFile.exists()) {
				opFile.createNewFile();
			}

			while ((read = is.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();

			status = true;

			MLogger.i(MLogger.MOD_DB, "Data Stream successfully saved to File "
					+ saveTo);

			// return status;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return status;
	}

	/**
	 * Read from a file starting with line No and get json array of lines
	 * 
	 * @param fileName
	 *            : file from which data to be read
	 * @param lineNo
	 *            : start reading from line no.
	 * @return jsonArray of Lines as string
	 */
	public JSONArray readFileAsJson(String fileName, int lineNo) {
		File readFile = new File(fileName);
		BufferedReader br = null;
		JSONArray fileData = new JSONArray();

		try {
			// if file doesn't exists,
			if (!readFile.exists()) {
				MLogger.i(MLogger.MOD_DB, "File does not exists");
				return null;
			}

			String sCurrentLine;
			br = new BufferedReader(new FileReader(readFile));
			int curLineNo = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				curLineNo += 1;
				if (curLineNo >= lineNo) {
					fileData.put(sCurrentLine);
				}
				MLogger.i(MLogger.MOD_DB, sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return fileData;
	}

	/**
	 * Copy File using file stream
	 * 
	 * @param fromFileName
	 * @param toFileName
	 * @return
	 */
	public boolean copyDataFromFileToFile(String fromFileName, String toFileName) {
		boolean status = false;
		InputStream in = null;
		OutputStream out = null;

		try {
			File fFile = new File(fromFileName);
			File tFile = new File(toFileName);

			// if file doesn't exists,
			if (!fFile.exists()) {
				MLogger.i(MLogger.MOD_DB, "File does not exists");
				return false;
			}

			// if file doesn't exists,
			if (!tFile.exists()) {
				tFile.createNewFile();
			}

			in = new FileInputStream(fFile);
			out = new FileOutputStream(tFile);

			byte[] buf = new byte[1024];
			int len;

			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}

			status = true;
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			try {
				if (out != null)
					out.close();
			} catch (IOException ioe2) {
				ioe2.printStackTrace();
			}
		}
		return status;
	}

	/**
	 * Copy file using file channel
	 * 
	 * @param from
	 * @param to
	 * @throws IOException
	 */
	public static void copyFile(File from, File to) throws IOException {

		if (!to.exists()) {
			to.createNewFile();
		}

		try (FileChannel in = new FileInputStream(from).getChannel();
				FileChannel out = new FileOutputStream(to).getChannel()) {

			out.transferFrom(in, 0, in.size());
		}
	}

	// java 7 Files.copy(source, target, REPLACE_EXISTING);

	// eg:
	/*
	 * Path FROM = Paths.get("C:\\Temp\\from.txt"); Path TO =
	 * Paths.get("C:\\Temp\\to.txt"); //overwrite existing file, if exists
	 * CopyOption[] options = new CopyOption[]{
	 * StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES
	 * }; Files.copy(FROM, TO, options);
	 */

	// Test functions
	/*
	 * public static void main(String [] args){ final File folder = new
	 * File("\\apache-tomcat-7.0.29\\webapps"); DirectoryFileScanner dfs = new
	 * DirectoryFileScanner();
	 * 
	 * Vector<File> files = dfs.getFilesFromFolder(folder);
	 * //saveToFile(folder);
	 * 
	 * }
	 */
}
