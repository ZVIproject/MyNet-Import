package com.zviproject.systemm.components.service;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.zviproject.standalone.MyNetImportApplication;
import com.zviproject.systemm.components.entities.Client;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = MyNetImportApplication.class)
@Rollback
@Transactional
public class ImportDataDaoIntegrationTest {

	@Test
	public void saveClientsTest() {
		ImportDataDao importDataDao = new ImportDataDao();

		List<Client> clients = new LinkedList<>();

		Client firstClient = new Client();
		firstClient.setName("IntegrationTestFirstName");
		firstClient.setSurname("IntegrationTestFirstSurname");
		firstClient.setBalance(2000.0);
		firstClient.setConnectionStatus("CONNECTED");
		firstClient.setContractNumber(1111111);
		firstClient.setPassword("IntegrationTestFirstPassword");
		firstClient.setTariffName("Test2");

		Client secondClient = new Client();
		secondClient.setName("IntegrationTestSecondName");
		secondClient.setSurname("IntegrationTestSecondSurname");
		secondClient.setBalance(2000.0);
		secondClient.setConnectionStatus("CONNECTED");
		secondClient.setContractNumber(2222222);
		secondClient.setPassword("IntegrationTestSecondPassword");
		secondClient.setTariffName("Test3");

		clients.add(firstClient);
		clients.add(secondClient);

		importDataDao.saveClients(clients);

	}

}
