package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;

@Endpoint
public class CountryEndpoint {
	private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";
	private static Logger log = LoggerFactory.getLogger(CountryEndpoint.class);

	private CountryRepository countryRepository;

	@Autowired
	public CountryEndpoint(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
	@ResponsePayload
	public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {

		GetCountryResponse response = new GetCountryResponse();
		response.setCountry(countryRepository.findCountry(request.getName()));

		///////////////////////////////////////////////////////////////////////////////
		log.info("GetCountryResponse executed");
		ObjectMapper objectMapper = new ObjectMapper();
		XmlMapper xmlMapper = new XmlMapper();
		//pretty xml
		xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String json;
		String xmlrequest;
		String xmlresponse;
		try {
			json = objectMapper.writeValueAsString(request);
			xmlrequest = xmlMapper.writeValueAsString(request);
			xmlresponse = xmlMapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			xmlrequest = "UNKNOWN";
			xmlresponse = "UNKNOWN";
		}
		log.info("in:"+xmlrequest);
		log.info("out:"+xmlresponse);
		///////////////////////////////////////////////////////////////////////////////

		try {
			log.info("sleeping for 2 secs");
			Thread.sleep(2000);
			log.info("end");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}
}
