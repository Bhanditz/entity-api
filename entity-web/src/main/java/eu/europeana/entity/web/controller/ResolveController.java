package eu.europeana.entity.web.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eu.europeana.api.common.config.swagger.SwaggerSelect;
import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.utils.jsonld.EuropeanaEntityLd;
import eu.europeana.entity.web.exception.HttpException;
import eu.europeana.entity.web.exception.InternalServerException;
import eu.europeana.entity.web.http.HttpHeaders;
import eu.europeana.entity.web.service.EntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@SwaggerSelect
@Api(tags = "Entity retrieval", description=" ")
public class ResolveController {
	
	@Resource 
	EntityService entityService;

	@ApiOperation(value = "Retrieve a known entity", nickname = "getEntity", response = java.lang.Void.class)
	@RequestMapping(value = {"/entity/{type}/{namespace}/{identifier}", "/entity/{type}/{namespace}/{identifier}.jsonld"}, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, "application/ld+json"})
	public ResponseEntity<String> getEntity(
			@RequestParam(value = WebEntityFields.PARAM_WSKEY) String wskey,
			@PathVariable(value = WebEntityFields.PATH_PARAM_TYPE) String type,
			@PathVariable(value = WebEntityFields.PATH_PARAM_NAMESPACE) String namespace,
			@PathVariable(value = WebEntityFields.PATH_PARAM_IDENTIFIER) String identifier
			) throws HttpException  {

		try {
			String action = "get:/entity/{type}/{namespace}/{identifier}";
			//return getAnnotationById(wskey, provider, identifier, action);
			Concept entity = entityService.retrieveByUrl(type, namespace, identifier);
			
			EuropeanaEntityLd entityLd = new EuropeanaEntityLd(entity);
			
			String jsonLd = new String(entityLd.toString(4));

			Date etagDate = (entity.getTimestamp() == null)? entity.getTimestamp() : new Date();
			int etag = etagDate.hashCode(); 
			
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(5);
			headers.add(HttpHeaders.VARY, HttpHeaders.ACCEPT);
			headers.add(HttpHeaders.ETAG, "" + etag);
			headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_RESOURCE);
			headers.add(HttpHeaders.ALLOW, HttpHeaders.ALLOW_GET);

			ResponseEntity<String> response = new ResponseEntity<String>(jsonLd, headers, HttpStatus.OK);
			
			return response;
		}catch (RuntimeException e) {
			//not found .. 
			throw new InternalServerException(e);
//		} catch (HttpException e) {
//			//avoid wrapping http exception
//			throw e;
		} catch (Exception e) {
			throw new InternalServerException(e);
		}
			
	}

}