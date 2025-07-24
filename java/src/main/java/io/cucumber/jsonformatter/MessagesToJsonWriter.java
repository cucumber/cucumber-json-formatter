package io.cucumber.jsonformatter;

import io.cucumber.messages.types.Envelope;
import io.cucumber.query.Query;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * Writes the message output of a test run as single json report.
 * <p>
 * Note: Messages are first collected and only written once the stream is
 * closed.
 */
public final class MessagesToJsonWriter implements AutoCloseable {

    private final OutputStreamWriter out;
    private final Query query = new Query();
    private final Serializer serializer;
    private final Function<URI, String> uriFormatter;
    private boolean streamClosed = false;

    private MessagesToJsonWriter(OutputStream out, Serializer serializer, Function<URI, String> uriFormatter) {
        this.out = new OutputStreamWriter(
            requireNonNull(out),
            StandardCharsets.UTF_8);
        this.serializer = requireNonNull(serializer);
        this.uriFormatter = requireNonNull(uriFormatter);
    }

    public static Builder builder(Serializer serializer) {
        return new Builder(serializer);
    }

    public static final class Builder {
        private final Serializer serializer;
        private Function<URI, String> uriFormatter = URI::toString;

        private Builder(Serializer serializer) {
            this.serializer = requireNonNull(serializer);
        }

        public Builder relativizeAgainst(URI uri) {
            this.uriFormatter = new RelativeUriFormatter(uri).andThen(URI::toString);
            return this;
        }

        public MessagesToJsonWriter build(OutputStream out) {
            requireNonNull(out);
            return new MessagesToJsonWriter(out, serializer, uriFormatter);
        }
    }

    /**
     * Writes a cucumber message to the xml output.
     *
     * @param  envelope    the message
     * @throws IOException if an IO error occurs
     */
    public void write(Envelope envelope) throws IOException {
        if (streamClosed) {
            throw new IOException("Stream closed");
        }
        query.update(envelope);
    }

    /**
     * Closes the stream, flushing it first. Once closed further write()
     * invocations will cause an IOException to be thrown. Closing a closed
     * stream has no effect.
     *
     * @throws IOException if an IO error occurs
     */
    @Override
    public void close() throws IOException {
        if (streamClosed) {
            return;
        }
        try {
            List<Object> report = new JsonReportWriter(query, uriFormatter).createJsonReport();
            serializer.writeValue(out, report);
        } finally {
            try {
                out.close();
            } finally {
                streamClosed = true;
            }
        }
    }

    @FunctionalInterface
    public interface Serializer {

        void writeValue(Writer writer, Object value) throws IOException;

    }

}
