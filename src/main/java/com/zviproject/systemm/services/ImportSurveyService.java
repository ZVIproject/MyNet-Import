package com.zviproject.systemm.services;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zviproject.systemm.components.interfaces.IImportCSVDataDao;
import com.zviproject.systemm.importt.csv.CSVImport;
import com.zviproject.systemm.interfaces.IImportData;

/**
 * Major distributor import file<br>
 * coordinate files to services, that can work with this file format
 * 
 * @author zviproject
 *
 */
@Service
public class ImportSurveyService {

	@Autowired
	private CSVImport csvImport;

	@Autowired
	private IImportCSVDataDao iImportCSVDataDao;

	private static final Logger LOGGER = Logger.getLogger(ImportSurveyService.class);

	/**
	 * Select service for working with file by format
	 * 
	 * @param request
	 * @param clientId
	 */
	public void importFile(HttpServletRequest request) {

		try {

			Part filePart = request.getPart("file");

			String fullFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			String format = FilenameUtils.getExtension(fullFileName);

			IImportData iImportData = selectImportServiceRealization(format);

			switch (FilenameUtils.getBaseName(fullFileName).toUpperCase()) {
			case "CLIENTS": {
				iImportData.fromFileClients(filePart);
			}
				break;

			case "TARIFFS": {
				iImportData.fromFileTariff(filePart);
			}
				break;
			}

		} catch (IOException | ServletException e) {
			LOGGER.error(e.getMessage());
		}

	}

	private IImportData selectImportServiceRealization(String format) {

		switch (format.toUpperCase()) {
		case "CSV":
			return csvImport;

		default:
			return null;
		}
	}

	public void deleteTariffs(List<String> tariffs) {
		iImportCSVDataDao.deleteTariffs(tariffs);
	}

	public void deleteClients(List<String> clients) {
		iImportCSVDataDao.deleteClients(clients);
	}

}
