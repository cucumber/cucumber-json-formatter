MAKEFILES = $(shell find . -maxdepth 3 -type f -name Makefile)
SUBDIRS   = $(filter-out ./,$(dir $(MAKEFILES)))
SUBDIRSCLEAN=$(addprefix clean-,$(SUBDIRS))

.PHONY: all $(SUBDIRS)
all: $(SUBDIRS)

.PHONY: clean-all $(SUBDIRSCLEAN)
clean-all: $(SUBDIRSCLEAN)

$(SUBDIRS):
	$(MAKE) --directory=$@

clean:
	rm -rf ./**/**/testdata
