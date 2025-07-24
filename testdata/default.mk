FEATURE_FILES := $(wildcard ../../features/*.feature)
NDJSON_FILES = $(patsubst ../../features/%.feature,testdata/%.ndjson,$(FEATURE_FILES))

ndjson-files: $(NDJSON_FILES)

clean:
	rm -rf ./testdata
