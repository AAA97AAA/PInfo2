package services.utility;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

/**
 * Mapper for localdatetime objects for jackson
 * 
 * @author petrbinko
 *
 */

@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

    final ObjectMapper mapper;

    public ObjectMapperContextResolver() {
    	mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }  
}