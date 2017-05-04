package com.zviproject.systemm.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

		try {
			importSurveyService.importFile(request);
			return new ResponseEntity<>("Done", HttpStatus.OK);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
