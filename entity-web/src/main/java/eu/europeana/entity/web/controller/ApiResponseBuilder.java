package eu.europeana.entity.web.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.web.model.EntityApiResponse;

public class ApiResponseBuilder {

	Logger logger = Logger.getLogger(getClass());
	private static ObjectMapper objectMapper = new ObjectMapper();
	

	public Logger getLogger() {
		return logger;
	}

	public EntityApiResponse buildErrorResponse(String errorMessage, String action, String apiKey) {
		EntityApiResponse response;
		response = new EntityApiResponse(apiKey, action);

		response.success = false;
		response.error = errorMessage;
		return response;
	}

	public EntityApiResponse buildResponse(Concept entity, String action, String apiKey) {
		EntityApiResponse response;
		response = new EntityApiResponse(apiKey, action);

		response.success = true;
		response.setEntity(entity);
		//response. setStatus(message);
		return response;
	}

	protected EntityApiResponse getValidationReport(String apiKey, String action, String errorMessage,
			Throwable th) {

		EntityApiResponse response = new EntityApiResponse(apiKey, action);

		String message = "";

		if (errorMessage != null)
			message += " " + errorMessage;
		if (th != null)
			message += th.toString();
		if (th != null && th.getCause() != null && th != th.getCause())
			message += " " + th.getCause().toString();

		response = buildErrorResponse(message, response.action, response.apikey);

		return response;
	}

	protected String serializeResponse(EntityApiResponse res) {
		String errorMessage;
		try {
			//correct serialization
			return objectMapper.writeValueAsString(res);
		} catch (JsonGenerationException e) {
			getLogger().error("Json Generation Exception: " + e.getMessage(),e);
			errorMessage = "Json Generation Exception: " + e.getMessage() + " See error logs!";
		} catch (JsonMappingException e) {
			getLogger().error("Json Mapping Exception: " + e.getMessage(),e);
			errorMessage = "Json Generation Exception: " + e.getMessage() + " See error logs!";
		} catch (IOException e) {
			getLogger().error("I/O Exception: " + e.getMessage(),e);
			errorMessage = "I/O Exception: " + e.getMessage() + " See error logs!";
		}
		return errorMessage;
	}
}