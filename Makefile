JAVA := $(shell which java)
JAVAC := $(shell which javac)

ifeq ($(JAVA),)
$(error No 'java' found! Please install the Java SDK)
endif
ifeq ($(JAVAC),)
$(error No 'javac' found! Please install the Java SDK)
endif

default: clean compile run

clean:
	rm -rf target

compile:
	mkdir -p target
	javac -Werror -d target HavaloCodingChallenge.java

run:
	java -cp target HavaloCodingChallenge || exit 0