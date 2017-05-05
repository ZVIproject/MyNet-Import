package com.zviproject.systemm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zviproject.systemm.services.ImportSurveyService;

/**
 * Controller for import survey
 * 
 * @author olegnovatskiy
 */
@RestController
@RequestMapping("/rest/mynet/v1/import")
public class ImportDataController {

	private static final Logger LOGGER = Logger.getLogger(ImportDataController.class);

	@Autowired
	private ImportSurveyService importSurveyService;

	/**
	 * Importing tariffs from selected file of csv type to datadase.
	 * 
	 * @param request
	 * @param clientId
	 * @return Response
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Object> importTariff(HttpServletRequest request) {
		Map<String, String> resultMap = new HashMap<>();

		try {

			importSurveyService.importFile(request);
			resultMap.put("Response status", "Done");
			return new ResponseEntity<>(resultMap, HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			resultMap.put("Response status", "Error " + e.getMessage());
			return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/tariffs", method = RequestMethod.DELETE)
	public void deleteTariffs(@RequestBody List<String> tariffs) {
		importSurveyService.deleteTariffs(tariffs);
	}

	@RequestMapping(value = "/clients", method = RequestMethod.DELETE)
	public void deleteClients(@RequestBody List<String> clients) {
		importSurveyService.deleteClients(clients);
	}

}
