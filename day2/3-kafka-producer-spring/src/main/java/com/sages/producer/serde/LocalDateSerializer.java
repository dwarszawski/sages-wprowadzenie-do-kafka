package com.sages.producer.serde;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class LocalDateSerializer extends StdSerializer<LocalDate> {

	private static final long serialVersionUID = 1L;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");

	public LocalDateSerializer() {
		this(null);
	}

	public LocalDateSerializer(Class<LocalDate> t) {
		super(t);
	}

	@Override
	public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider arg2)
			throws IOException, JsonProcessingException {

		gen.writeString(formatter.format(value));
	}
}
