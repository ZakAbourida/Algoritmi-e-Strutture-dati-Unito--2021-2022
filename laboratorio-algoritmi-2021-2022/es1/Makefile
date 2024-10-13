CFLAGS 	= -g -O1 -Wall -Wextra -Wpedantic -Wconversion
COMMON_DEPS 	= src/*.h lib/* Makefile

all: clean create bin/my_array_main

create:
	mkdir -p build
	mkdir -p bin

clean:
	$(RM) build/*
	$(RM) bin/*

test_quicksort: clean create bin/my_array_tests_quicksort

test_binary: clean create bin/my_array_tests_binary




build/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@

lib/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@	
	

bin/common: build/common.o $(COMMON_DEPS)
	$(CC) -o bin/common
	
bin/sorting: build/sorting.o build/common.o build/my_array.o $(COMMON_DEPS)
	$(CC) -o bin/sorting build/sorting.o build/common.o build/my_array.o
	
bin/my_array: build/my_array.o build/common.o $(COMMON_DEPS)
	$(CC) -o bin/my_array build/my_array.o build/common.o
	
bin/my_array_main: build/my_array_main.o build/common.o build/my_array.o build/sorting.o $(COMMON_DEPS)
	$(CC) -o bin/my_array_main build/my_array_main.o build/common.o build/my_array.o build/sorting.o


bin/my_array_testing: build/my_array_testing.o build/common.o $(COMMON_DEPS)
	$(CC) -o bin/my_array_testing build/my_array_testing.o build/common.o 

bin/my_array_tests_quicksort: build/my_array_tests_quicksort.o build/common.o build/my_array_testing.o build/sorting.o lib/unity.o $(COMMON_DEPS)
	$(CC) -o bin/my_array_tests_quicksort build/my_array_tests_quicksort.o build/common.o build/my_array_testing.o build/sorting.o lib/unity.o
	
bin/my_array_tests_binary: build/my_array_tests_binary.o build/common.o build/my_array_testing.o build/sorting.o lib/unity.o $(COMMON_DEPS)
	$(CC) -o bin/my_array_tests_binary build/my_array_tests_binary.o build/common.o build/my_array_testing.o build/sorting.o lib/unity.o
