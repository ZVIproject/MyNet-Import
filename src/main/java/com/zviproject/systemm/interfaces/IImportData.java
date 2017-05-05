package com.zviproject.systemm.interfaces;

import javax.servlet.http.Part;

/**
 * Importing survey from file
 * 
 * @author zviproject
 */
public interface IImportData {

	/**
	 * Import survey from file diferent formats.
	 * 
	 * @param importFileName
	 * @return
	 */
	public void fromFileClients(Part filePart);

	public void fromFileTariff(Part filePart);

}
