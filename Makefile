JAVAC=javac
sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all: $(classes)

run: all
	java Ui

clean :
	rm -f *.class

%.class : %.java
	$(JAVAC) $<
