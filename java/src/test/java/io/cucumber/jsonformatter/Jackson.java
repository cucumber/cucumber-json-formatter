package io.cucumber.jsonformatter;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.Separators;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.ConstructorDetector;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import static com.fasterxml.jackson.core.util.DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;
import static com.fasterxml.jackson.core.util.Separators.Spacing.AFTER;

final class Jackson {
    public static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
            .addModule(new Jdk8Module())
            .addModule(new ParameterNamesModule(Mode.PROPERTIES))
            .serializationInclusion(Include.NON_ABSENT)
            .constructorDetector(ConstructorDetector.USE_PROPERTIES_BASED)
            .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
            .enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
            .enable(DeserializationFeature.USE_LONG_FOR_INTS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET)
            .build();
    static final DefaultPrettyPrinter PRETTY_PRINTER = new DefaultPrettyPrinter(
                    Separators.createDefaultInstance()
                            .withObjectFieldValueSpacing(AFTER)
            )
                    .withArrayIndenter(SYSTEM_LINEFEED_INSTANCE);

    private Jackson() {
    }
}

