package com.zviproject.systemm.components.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.zviproject.systemm.components.entities.Client;
import com.zviproject.systemm.components.entities.Tariff;
import com.zviproject.systemm.components.interfaces.IImportCSVDataDao;

/**
 * Save survey into db
 * 
 * @author olegnovatskiy
 */
@Repository
public class ImportDataDao implements IImportCSVDataDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SQL_INSERT_TARIFF_DATA = "INSERT INTO tariff_plan (`cost`, `name`, `condition`, `description`) VALUES (?, ?, ?, ?)";

	private static final String SQL_INSERT_CLIENT_DATA = "INSERT INTO user (`password`, `name`, `surname`, `contract_number`, `balance`, `connection_sytatus`) VALUES (?, ?, ?, ?, ?, ?)";

	private static final String SQL_INSERT_CONNECTING_TARIFF_TO_CLIENT = "INSERT INTO connect_user_tariff_plan (`user_id`, `tariff_plan_id`) VALUES (?, ?)";

	private static final String SQL_GET_TARIFF_DATA = "SELECT id, name FROM tariff_plan";

	private static final Logger LOGGER = Logger.getLogger(ImportDataDao.class);

	@Override
	public void saveTariff(List<Tariff> tariffs) {
		jdbcTemplate.batchUpdate(SQL_INSERT_TARIFF_DATA, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Tariff tariff = tariffs.get(i);

				ps.setDouble(1, tariff.getCost());
				ps.setString(2, tariff.getName());
				ps.setString(3, tariff.getCondition());
				ps.setString(4, tariff.getDescription());

			}

			@Override
			public int getBatchSize() {
				return tariffs.size();
			}
		});

	}

	@Override
	public void saveClients(List<Client> clients) {
		Connection connection = null;

		Map<String, Integer> tariffsMap = new HashMap<>();

		try {
			connection = jdbcTemplate.getDataSource().getConnection();

			prepareTariffs(tariffsMap, connection);

			jdbcTemplate.batchUpdate(SQL_INSERT_CLIENT_DATA, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
					Client client = clients.get(i);

					preparedStatement.setString(1, client.getPassword());
					preparedStatement.setString(2, client.getName());
					preparedStatement.setString(3, client.getSurname());
					preparedStatement.setInt(4, client.getContractNumber());
					preparedStatement.setDouble(5, client.getBalance());
					preparedStatement.setString(6, client.getConnectionStatus());

					ResultSet generatedId = preparedStatement.getGeneratedKeys();

					jdbcTemplate.queryForList(SQL_INSERT_CONNECTING_TARIFF_TO_CLIENT, generatedId.getInt(1),
							tariffsMap.get(client.getTariffName()));
				}

				@Override
				public int getBatchSize() {
					return clients.size();
				}
			});

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}

	}

	private Map<String, Integer> prepareTariffs(Map<String, Integer> tariffsMap, Connection connection) {

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_TARIFF_DATA);
			ResultSet keys = preparedStatement.executeQuery();

			tariffsMap.put(keys.getString(2), keys.getInt(1));
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}

		return tariffsMap;
	}
}