package services.utility;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dom.content.User;

public class VotersMarshaller extends JsonSerializer<Set<User>> {

	@Override
	public void serialize(Set<User> set, JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		generator.writeObject(set.stream().map(u -> u.getId()).collect(Collectors.toSet()));
	}

}
