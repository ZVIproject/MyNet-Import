package com.zviproject.systemm.components.interfaces;

import java.util.List;

import com.zviproject.systemm.components.entities.Client;
import com.zviproject.systemm.components.entities.Tariff;

/**
 * Save survey into db
 * 
 * @author olegnovatskiy
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

}
