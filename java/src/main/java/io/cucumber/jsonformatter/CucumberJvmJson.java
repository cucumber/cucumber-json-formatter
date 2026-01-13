package io.cucumber.jsonformatter;

import org.jspecify.annotations.Nullable;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Object representation of <a href=
 * "https://github.com/cucumber/cucumber-json-schema/blob/main/schemas/cucumber-jvm.json">cucumber-jvm.json</a>
 * schema.
 */
final class CucumberJvmJson {
    
    @Nullable
    private static <T> List<T> nullIfEmpty(List<T> list) {
        return list.isEmpty() ? null : list;
    }

    enum JvmElementType {
        background, scenario
    }
    
    enum JvmStatus {
        passed,
        skipped,
        pending,
        undefined,
        ambiguous,
        failed
    }

    static final class JvmFeature {
        private final Integer line;
        private final String uri;
        private final String id;
        private final String keyword;
        private final String name;
        private final String description;
        private final List<JvmElement> elements;
        private final @Nullable List<JvmLocationTag> tags;

        JvmFeature(
                String uri, String id, Integer line, String keyword, String name, String description,
                List<JvmElement> elements, @Nullable List<JvmLocationTag> tags
        ) {
            this.uri = requireNonNull(uri);
            this.id = requireNonNull(id);
            this.line = requireNonNull(line);
            this.keyword = requireNonNull(keyword);
            this.name = requireNonNull(name);
            this.description = requireNonNull(description);
            this.elements = requireNonNull(elements);
            this.tags = tags;
        }

        public String getUri() {
            return uri;
        }

        public String getId() {
            return id;
        }

        public Integer getLine() {
            return line;
        }

        public String getKeyword() {
            return keyword;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public List<JvmElement> getElements() {
            return elements;
        }

        public @Nullable List<JvmLocationTag> getTags() {
            return tags;
        }
    }

    static final class JvmElement {
        private final @Nullable String start_timestamp;
        private final Integer line;
        private final @Nullable String id;
        private final JvmElementType type;
        private final String keyword;
        private final String name;
        private final String description;
        private final List<JvmStep> steps;
        private final @Nullable List<JvmHook> before;
        private final @Nullable List<JvmHook> after;
        private final @Nullable List<JvmTag> tags;

        JvmElement(
                @Nullable String start_timestamp, Integer line, @Nullable String id, JvmElementType type, String keyword, String name,
                String description, List<JvmStep> steps, List<JvmHook> before, List<JvmHook> after, List<JvmTag> tags
        ) {
            this.start_timestamp = start_timestamp;
            this.line = requireNonNull(line);
            this.id = id;
            this.type = requireNonNull(type);
            this.keyword = requireNonNull(keyword);
            this.name = requireNonNull(name);
            this.description = requireNonNull(description);
            this.steps = requireNonNull(steps);
            this.before = nullIfEmpty(before);
            this.after = nullIfEmpty(after);
            this.tags = nullIfEmpty(tags);
        }

        public @Nullable String getStart_timestamp() {
            return start_timestamp;
        }

        public Integer getLine() {
            return line;
        }

        public @Nullable String getId() {
            return id;
        }

        public JvmElementType getType() {
            return type;
        }

        public String getKeyword() {
            return keyword;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public List<JvmStep> getSteps() {
            return steps;
        }

        public @Nullable List<JvmHook> getBefore() {
            return before;
        }

        public @Nullable List<JvmHook> getAfter() {
            return after;
        }

        public @Nullable List<JvmTag> getTags() {
            return tags;
        }
    }

    static final class JvmStep {
        private final String keyword;
        private final Integer line;
        private final @Nullable JvmMatch match;
        private final String name;
        private final JvmResult result;
        private final @Nullable JvmDocString doc_string;
        private final @Nullable List<JvmDataTableRow> rows;
        private final @Nullable List<JvmHook> before;
        private final @Nullable List<JvmHook> after;
        private final @Nullable List<JvmEmbedding> embeddings;
        private final @Nullable List<String> output;

        JvmStep(
                String keyword, Integer line, @Nullable JvmMatch match, String name, JvmResult result, @Nullable JvmDocString doc_string,
                @Nullable List<JvmDataTableRow> rows, List<JvmHook> before, List<JvmHook> after, List<JvmEmbedding> embeddings, List<String> output
        ) {
            this.keyword = requireNonNull(keyword);
            this.line = requireNonNull(line);
            this.match = match;
            this.name = requireNonNull(name);
            this.result = requireNonNull(result);
            this.doc_string = doc_string;
            this.rows = rows;
            this.before = nullIfEmpty(before);
            this.after = nullIfEmpty(after);
            this.embeddings = nullIfEmpty(embeddings);
            this.output = nullIfEmpty(output);
        }

        public String getKeyword() {
            return keyword;
        }

        public Integer getLine() {
            return line;
        }

        public @Nullable JvmMatch getMatch() {
            return match;
        }

        public String getName() {
            return name;
        }

        public JvmResult getResult() {
            return result;
        }

        public @Nullable JvmDocString getDoc_string() {
            return doc_string;
        }

        public @Nullable List<JvmDataTableRow> getRows() {
            return rows;
        }

        public @Nullable List<JvmHook> getBefore() {
            return before;
        }

        public @Nullable List<JvmHook> getAfter() {
            return after;
        }

        public @Nullable List<JvmEmbedding> getEmbeddings() {
            return embeddings;
        }

        public @Nullable List<String> getOutput() {
            return output;
        }
    }

    static final class JvmMatch {
        private final @Nullable String location;
        private final @Nullable List<JvmArgument> arguments;

        JvmMatch(@Nullable String location, @Nullable List<JvmArgument> arguments) {
            this.location = location;
            this.arguments = arguments;
        }

        public @Nullable String getLocation() {
            return location;
        }

        public @Nullable List<JvmArgument> getArguments() {
            return arguments;
        }
    }

    static final class JvmArgument {
        private final @Nullable String val;
        private final @Nullable Number offset;

        JvmArgument(@Nullable String val, @Nullable Number offset) {
            this.val = val;
            this.offset = offset;
        }

        public @Nullable String getVal() {
            return val;
        }

        public @Nullable Number getOffset() {
            return offset;
        }
    }

    static final class JvmResult {
        private final @Nullable Long duration;
        private final JvmStatus status;
        private final @Nullable String error_message;

        JvmResult(@Nullable Long duration, JvmStatus status, @Nullable String error_message) {
            this.duration = duration;
            this.status = requireNonNull(status);
            this.error_message = error_message;
        }

        public @Nullable Long getDuration() {
            return duration;
        }

        public JvmStatus getStatus() {
            return status;
        }

        public @Nullable String getError_message() {
            return error_message;
        }
    }

    static final class JvmDocString {
        private final Integer line;
        private final String value;
        private final @Nullable String content_type;

        JvmDocString(Integer line, String value, @Nullable String content_type) {
            this.line = requireNonNull(line);
            this.value = requireNonNull(value);
            this.content_type = content_type;
        }

        public Integer getLine() {
            return line;
        }

        public String getValue() {
            return value;
        }

        public @Nullable String getContent_type() {
            return content_type;
        }
    }

    static final class JvmDataTableRow {
        private final List<String> cells;

        JvmDataTableRow(List<String> cells) {
            this.cells = requireNonNull(cells);
        }

        public List<String> getCells() {
            return cells;
        }
    }

    static final class JvmHook {
        private final JvmMatch match;
        private final JvmResult result;
        private final @Nullable List<JvmEmbedding> embeddings;
        private final @Nullable List<String> output;

        JvmHook(JvmMatch match, JvmResult result, List<JvmEmbedding> embeddings, List<String> output) {
            this.match = requireNonNull(match);
            this.result = requireNonNull(result);
            this.embeddings = nullIfEmpty(embeddings);
            this.output = nullIfEmpty(output);
        }

        public JvmMatch getMatch() {
            return match;
        }

        public JvmResult getResult() {
            return result;
        }

        public @Nullable List<JvmEmbedding> getEmbeddings() {
            return embeddings;
        }

        public @Nullable List<String> getOutput() {
            return output;
        }
    }

    static final class JvmEmbedding {
        private final String mime_type;
        private final String data;
        private final @Nullable String name;

        JvmEmbedding(String mime_type, String data, @Nullable String name) {
            this.mime_type = requireNonNull(mime_type);
            this.data = requireNonNull(data);
            this.name = name;
        }

        public String getData() {
            return data;
        }

        public String getMime_type() {
            return mime_type;
        }

        public @Nullable String getName() {
            return name;
        }
    }

    static final class JvmTag {
        private final String name;

        JvmTag(String name) {
            this.name = requireNonNull(name);
        }

        public String getName() {
            return name;
        }
    }

    static final class JvmLocationTag {
        private final String name;
        private final String type;
        private final JvmLocation location;

        JvmLocationTag(String name, String type, JvmLocation location) {
            this.name = requireNonNull(name);
            this.type = requireNonNull(type);
            this.location = requireNonNull(location);
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public JvmLocation getLocation() {
            return location;
        }
    }

    static final class JvmLocation {
        private final Integer line;
        private final Integer column;

        JvmLocation(Integer line, Integer column) {
            this.line = requireNonNull(line);
            this.column = requireNonNull(column);
        }

        public Integer getLine() {
            return line;
        }

        public Integer getColumn() {
            return column;
        }
    }
}
