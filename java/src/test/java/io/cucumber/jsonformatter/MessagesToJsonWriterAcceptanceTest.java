package io.cucumber.jsonformatter;

import com.networknt.schema.Error;
import com.networknt.schema.InputFormat;
import com.networknt.schema.Schema;
import com.networknt.schema.SchemaRegistry;
import com.networknt.schema.SpecificationVersion;
import io.cucumber.compatibilitykit.MessageOrderer;
import io.cucumber.messages.NdjsonToMessageIterable;
import io.cucumber.messages.ndjson.Deserializer;
import io.cucumber.messages.types.Envelope;
import org.json.JSONException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.cucumber.jsonformatter.Jackson.OBJECT_MAPPER;
import static io.cucumber.jsonformatter.Jackson.PRETTY_PRINTER;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;


class MessagesToJsonWriterAcceptanceTest {
    private static final MessagesToJsonWriter.Serializer serializer = OBJECT_MAPPER.writer(PRETTY_PRINTER)::writeValue;
    private static final Schema jsonSchema = readJsonSchema();
    private static final Random random = new Random(202509171757L);
    private static final MessageOrderer messageOrderer = new MessageOrderer(random);


    static List<TestCase> all() throws IOException {
        List<TestCase> cases = new ArrayList<>();
        cases.addAll(compatibilityKit());
        cases.addAll(TestCase.fromDirectory("../testdata/cucumber-jvm/7.26.0-java/testdata"));
        cases.addAll(TestCase.fromDirectory("../testdata/cucumber-jvm/7.26.0-java8/testdata"));
        cases.addAll(TestCase.fromDirectory("../testdata/cucumber-jvm/7.26.0-scala/testdata"));
        return cases;
    }

    private static List<TestCase> compatibilityKit() throws IOException {
        return TestCase.fromDirectory("../testdata/compatibility-kit/src");
    }

    private static ByteArrayOutputStream writeJsonReport(TestCase testCase, Consumer<List<Envelope>> orderer) throws IOException {
        return writeJsonReport(testCase, new ByteArrayOutputStream(), orderer);
    }

    private static <T extends OutputStream> T writeJsonReport(TestCase testCase, T out, Consumer<List<Envelope>> orderer) throws IOException {
        List<Envelope> messages = new ArrayList<>();
        try (InputStream in = Files.newInputStream(testCase.source)) {
            try (NdjsonToMessageIterable envelopes = new NdjsonToMessageIterable(in, new Deserializer())) {
                for (Envelope envelope : envelopes) {
                    messages.add(envelope);
                }
            }
        }
        orderer.accept(messages);

        MessagesToJsonWriter.Builder builder = MessagesToJsonWriter.builder(serializer);
        try (MessagesToJsonWriter writer = builder.build(out)) {
            for (Envelope envelope : messages) {
                writer.write(envelope);
            }
        }
        return out;
    }

    private static void assertJsonEquals(String expected, String actual) throws JSONException {
        JSONAssert.assertEquals(expected, actual, true);
    }

    private static Schema readJsonSchema() {
        Path path = Paths.get("../testdata/json-schema/src/cucumber-jvm.json");
        try (InputStream resource = Files.newInputStream(path)) {
            SchemaRegistry registry = SchemaRegistry.withDefaultDialect(SpecificationVersion.DRAFT_2020_12);
            return registry.getSchema(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @MethodSource("all")
    void test(TestCase testCase) throws IOException, JSONException {
        ByteArrayOutputStream actual = writeJsonReport(testCase, messageOrderer.originalOrder());
        byte[] expected = Files.readAllBytes(testCase.expected);
        assertJsonEquals(new String(expected, UTF_8), new String(actual.toByteArray(), UTF_8));
    }

    @ParameterizedTest
    @MethodSource("all")
    void testWithSimulatedParallelExecution(TestCase testCase) throws IOException, JSONException {
        ByteArrayOutputStream actual = writeJsonReport(testCase, messageOrderer.simulateParallelExecution());
        byte[] expected = Files.readAllBytes(testCase.expected);
        assertJsonEquals(new String(expected, UTF_8), new String(actual.toByteArray(), UTF_8));
    }

    @ParameterizedTest
    @MethodSource("all")
    void validateAgainstJsonSchema(TestCase testCase) throws IOException {
        // The actual should be identical to the expected.
        // So for schema validation there is no need to run the formatter
        // Makes the test faster 
        byte[] expected = Files.readAllBytes(testCase.expected);
        List<Error> assertions = jsonSchema.validate(
                new String(expected, UTF_8),
                InputFormat.JSON,
                // By default, since Draft 2019-09 the format keyword only generates annotations and not assertions
                executionContext -> executionContext.executionConfig(config -> config.formatAssertionsEnabled(true)));
        assertThat(assertions).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("compatibilityKit")
    @Disabled
    void updateExpectedFiles(TestCase testCase) throws IOException {
        try (OutputStream out = Files.newOutputStream(testCase.expected)) {
            writeJsonReport(testCase, out, messageOrderer.originalOrder());
        }
    }

    static class TestCase {
        private final Path source;
        private final Path expected;
        private final String name;
        private final String group;

        TestCase(Path source) {
            this.source = source;
            this.group = getGroupName(source);
            this.name = getFileNameWithNdjsonExtension(source);
            // Each cucumber has a different dialect
            this.expected = requireNonNull(source.getParent()).resolve(name + ".ndjson.jvm.json");
        }

        private static String getFileNameWithNdjsonExtension(Path source) {
            String fileName = source.getFileName().toString();
            return fileName.substring(0, fileName.lastIndexOf(".ndjson"));
        }

        private static String getGroupName(Path source) {
            Path parent = requireNonNull(source.getParent());
            String parentName = parent.getFileName().toString();
            if (parentName.equals("testdata")) {
                parentName = requireNonNull(parent.getParent()).getFileName().toString();
            }
            return parentName;
        }

        static List<TestCase> fromDirectory(String files) throws IOException {
            try (Stream<Path> paths = Files.list(Paths.get(files))) {
                return paths
                        .filter(path -> path.getFileName().toString().endsWith(".ndjson"))
                        .map(TestCase::new)
                        .sorted(Comparator.comparing(testCase -> testCase.source))
                        .collect(Collectors.toList());
            }
        }

        @Override
        public String toString() {
            return group + "/" + name;
        }

    }
}

