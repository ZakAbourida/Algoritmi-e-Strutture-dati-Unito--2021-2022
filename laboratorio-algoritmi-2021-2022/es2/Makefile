CFLAGS 	= -g -O1 -Wall -Wextra -Wpedantic -Wconversion
COMMON_DEPS 	= src/*.h lib/* Makefile

all: clean create main_ex2

test: clean create bin/test

create:
	mkdir -p build
	mkdir -p bin

clean:
	$(RM) build/*
	$(RM) bin/*

build/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@

lib/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@	


main_ex2: build/main_ex2.o build/SkipList.o $(COMMON_DEPS)
	$(CC) -o bin/main_ex2 build/main_ex2.o build/SkipList.o

bin/test: build/UnitTest.o build/SkipList.o lib/unity.o $(COMMON_DEPS)
	$(CC) -o bin/UnitTest build/UnitTest.o build/SkipList.o lib/unity.o
