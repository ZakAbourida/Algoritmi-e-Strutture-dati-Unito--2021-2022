#ifndef COMMON_H
#define COMMON_H

#define STRING 1
#define INT 2
#define DOUBLE 3

typedef struct{
	int id_field;
	char *string_field;
	int int_field;
	double double_field;
}Record;

typedef struct{
	void **array;
	long size;
	long array_capacity;
}MyArray;

#endif
