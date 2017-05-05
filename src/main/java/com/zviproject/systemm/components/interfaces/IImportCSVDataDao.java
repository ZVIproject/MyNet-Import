package com.zviproject.systemm.components.interfaces;

import java.util.List;

import com.zviproject.systemm.components.entities.Client;
import com.zviproject.systemm.components.entities.Tariff;

/**
 * Save survey into db
 * 
 * @author zviproject
 */
public interface IImportCSVDataDao {

	/**
	 * Save tariff to db.
	 * 
	 * @param survey
	 * @return Integer status of saving
	 */
	public void saveTariff(List<Tariff> tariffs);

	/**
	 * Save client to db.
	 * 
	 * @param survey
	 * @return Integer status of saving
	 */
	public void saveClients(List<Client> clients);

	/**
	 * Delete clients from database
	 * 
	 * @param clients
	 */
	public void deleteClients(List<String> clients);

	/**
	 * Delete tariffs from database
	 * 
	 * @param tariffs
	 */
	public void deleteTariffs(List<String> tariffs);
}
