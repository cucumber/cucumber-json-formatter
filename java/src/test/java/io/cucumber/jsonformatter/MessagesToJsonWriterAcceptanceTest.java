package io.cucumber.jsonformatter;

import io.cucumber.messages.NdjsonToMessageIterable;
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
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.cucumber.jsonformatter.Jackson.OBJECT_MAPPER;
import static io.cucumber.jsonformatter.Jackson.PRETTY_PRINTER;
import static java.nio.charset.StandardCharsets.UTF_8;


class MessagesToJsonWriterAcceptanceTest {
    private static final NdjsonToMessageIterable.Deserializer deserializer = (json) -> OBJECT_MAPPER.readValue(json, Envelope.class);
    private static final MessagesToJsonWriter.Serializer serializer = OBJECT_MAPPER.writer(PRETTY_PRINTER)::writeValue;

    static List<TestCase> compatibilityKit() throws IOException {
        return TestCase.fromDirectory("../testdata/cucumber-jvm/compatibility-kit");
    }

    static List<TestCase> dialectsCucumberJvm726Java() throws IOException {
        return TestCase.fromDirectory("../testdata/cucumber-jvm/7.26.0-java/testdata");
    }


    static List<TestCase> dialectsCucumberJvm726Java8() throws IOException {
        return TestCase.fromDirectory("../testdata/cucumber-jvm/7.26.0-java8/testdata");
    }

    private static <T extends OutputStream> T writeJsonReport(TestCase testCase, T out) throws IOException {
        try (InputStream in = Files.newInputStream(testCase.source)) {
            try (NdjsonToMessageIterable envelopes = new NdjsonToMessageIterable(in, deserializer)) {
                MessagesToJsonWriter.Builder builder = MessagesToJsonWriter.builder(serializer);
                try (MessagesToJsonWriter writer = builder.build(out)) {
                    for (Envelope envelope : envelopes) {
                        writer.write(envelope);
                    }
                }
            }
        }
        return out;
    }

    private static void assertJsonEquals(String expected, String actual) throws JSONException {
        JSONAssert.assertEquals(expected, actual, true);
    }

    @ParameterizedTest
    @MethodSource("compatibilityKit")
    @MethodSource("dialectsCucumberJvm726Java")
    @MethodSource("dialectsCucumberJvm726Java8")
    void testCompatibilityKit(TestCase testCase) throws IOException, JSONException {
        ByteArrayOutputStream actual = writeJsonReport(testCase, new ByteArrayOutputStream());
        byte[] expected = Files.readAllBytes(testCase.expected);
        assertJsonEquals(new String(expected, UTF_8), new String(actual.toByteArray(), UTF_8));
    }

    @ParameterizedTest
    @MethodSource("compatibilityKit")
    void validateAgainstJsonSchema(TestCase testCase) throws IOException {
        // TODO:
    }

    @ParameterizedTest
    @MethodSource("compatibilityKit")
    @Disabled
    void updateExpectedJsonFiles(TestCase testCase) throws IOException {
        try (OutputStream out = Files.newOutputStream(testCase.expected)) {
            writeJsonReport(testCase, out);
        }
    }

    static class TestCase {
        private final Path source;
        private final Path expected;
        private final String name;

        TestCase(Path source) {
            this.source = source;
            String fileName = source.getFileName().toString();
            this.name = fileName.substring(0, fileName.lastIndexOf(".ndjson"));
            // Each cucumber has a different dialect
            this.expected = source.getParent().resolve(name + ".ndjson.jvm.json");
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
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestCase testCase = (TestCase) o;
            return source.equals(testCase.source);
        }

        @Override
        public int hashCode() {
            return Objects.hash(source);
        }
    }
}

