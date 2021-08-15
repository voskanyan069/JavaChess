SRC=$(wildcard src/main/java/am/chess/**/*)
BIN=target/JavaChess-1.0-SNAPSHOT.jar

all: run

$(BIN): $(SRC)
	mvn package

run: $(BIN)
	clear
	java -cp target/JavaChess-1.0-SNAPSHOT.jar am.chess.App

.PHONY: clean
clean:
	mvn clean
