package com.zviproject.systemm.importt.csv;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.Part;
import javax.xml.bind.TypeConstraintException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csvreader.CsvReader;
import com.zviproject.systemm.components.entities.Client;
import com.zviproject.systemm.components.entities.Tariff;
import com.zviproject.systemm.components.interfaces.IImportCSVDataDao;
import com.zviproject.systemm.interfaces.IImportData;

/**
 * Importing survey from file
 * 
 * @author olegnovatskiy
 */
@Service
public class CSVImport implements IImportData {

	@Autowired
	private IImportCSVDataDao iImportCSVDataDAO;

	private static final Logger LOGGER = Logger.getLogger(CSVImport.class);

	private static final String IMPORT_TARIFF_NAME = "name";
	private static final String IMPORT_TARIFF_COST = "cost";
	private static final String IMPORT_TARIFF_DESCRIPTION = "description";
	private static final String IMPORT_TARIFF_CONDITION = "condition";

	private static final String IMPORT_CLIENT_NAME = "name";
	private static final String IMPORT_CLIENT_SURNAME = "surname";
	private static final String IMPORT_CLIENT_CONTRACT_NUMBER = "contaract_number";
	private static final String IMPORT_CLIENT_PASSWORD = "password";
	private static final String IMPORT_CLIENT_BALANCE = "balance";
	private static final String IMPORT_CLIENT_CONNECTION_STATUS = "connection_status";
	private static final String IMPORT_CLIENT_TARRIFF_NAME = "tariff_name";

	/**
	 * Import survey from file of CSV format.
	 * 
	 * @param fileName
	 * @return
	 */
	@Override
	public void fromFileTariff(Part filePart) {
		try {

			iImportCSVDataDAO.saveTariff(prepareTariff(filePart));

		} catch (TypeConstraintException | IOException e) {
			LOGGER.error(e.getMessage());
		} finally {
			try {
				filePart.delete();
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		}
	}

	/**
	 * Import data from file to object of survey.
	 * 
	 * @param fileName
	 * @return
	 */
	private List<Tariff> prepareTariff(Part filePart) throws IOException {
		CsvReader csvReader = null;
		List<Tariff> tariffs = new LinkedList<>();

		try {

			csvReader = new CsvReader(filePart.getInputStream(), Charset.forName("UTF-8"));

			csvReader.readHeaders();
			csvReader.readRecord();

			while (csvReader.readRecord()) {
				tariffs.add(parsingTariffsCsvFile(csvReader, tariffs));
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			csvReader.close();
		}
		return tariffs;
	}

	private Tariff parsingTariffsCsvFile(CsvReader csvReader, List<Tariff> tariffs) {
		Tariff tariff = new Tariff();
		try {

			tariff.setName(csvReader.get(IMPORT_TARIFF_NAME));
			tariff.setCost(Double.parseDouble(csvReader.get(IMPORT_TARIFF_COST)));
			tariff.setDescription(csvReader.get(IMPORT_TARIFF_DESCRIPTION));
			tariff.setCondition(csvReader.get(IMPORT_TARIFF_CONDITION));

		} catch (NumberFormatException | IOException e) {
			LOGGER.error(e.getMessage());
		}
		return tariff;
	}

	/**
	 * Import clients to the database
	 */
	@Override
	public void fromFileClients(Part filePart) {
		try {

			iImportCSVDataDAO.saveClients(prepareClients(filePart));

		} catch (TypeConstraintException e) {
			LOGGER.error(e.getMessage());
		} finally {
			try {
				filePart.delete();
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		}
	}

	private List<Client> prepareClients(Part filePart) {
		CsvReader csvReader = null;
		List<Client> clients = new LinkedList<>();

		try {

			csvReader = new CsvReader(filePart.getInputStream(), Charset.forName("UTF-8"));

			csvReader.readHeaders();
			csvReader.readRecord();

			while (csvReader.readRecord()) {
				clients.add(parsingClientsCsvFile(csvReader, clients));
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			csvReader.close();
		}
		return clients;
	}

	private Client parsingClientsCsvFile(CsvReader csvReader, List<Client> clList) {
		Client client = new Client();

		try {
			client.setName(csvReader.get(IMPORT_CLIENT_NAME));
			client.setSurname(csvReader.get(IMPORT_CLIENT_SURNAME));
			client.setContractNumber(Integer.parseInt(csvReader.get(IMPORT_CLIENT_CONTRACT_NUMBER)));
			client.setPassword(csvReader.get(IMPORT_CLIENT_PASSWORD));
			client.setBalance(Double.parseDouble(csvReader.get(IMPORT_CLIENT_BALANCE)));
			client.setConnectionStatus(csvReader.get(IMPORT_CLIENT_CONNECTION_STATUS));
			client.setTariffName(csvReader.get(IMPORT_CLIENT_TARRIFF_NAME));

		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return client;
	}

}
