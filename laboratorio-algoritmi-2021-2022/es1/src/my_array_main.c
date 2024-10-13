#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "common.h"
#include "my_array.h"
#include "sorting.h"

#define BUFFER_SIZE 1024

#define PROGRAM "my_array_main"

#define QUICK_SORT 0
#define BIN_INSERTION_SORT 1

//----------------------------------------------------------------------------//

void Usage(){
	fprintf(stderr, "\nUSAGE\t: \t%s\n", PROGRAM);
	fprintf(stderr, "input_file\n");
	fprintf(stderr, "output_file\n");
	fprintf(stderr, "field_number\n");
	fprintf(stderr, "algorithm_type\n");
	fprintf(stderr, "          field_number	{%d = string, %d = int, %d = double}.\n",
		STRING, INT, DOUBLE);
	fprintf(stderr, "          algorithm_type	{%d = quick sort, %d = binary insertion sort}.\n",
		QUICK_SORT, BIN_INSERTION_SORT);
	exit(1);
}

//----------------------------------------------------------------------------//

FILE *open_file(const char *filename){
	FILE *file = fopen(filename, "r");
	if (file == NULL) {
		printf("File not found: %s\n", filename);
		exit(1);
	}
	return file;
}

//----------------------------------------------------------------------------//
/* 
 * return
 * x<0 if r1_p precedes r2_p (r1_p < r2_p)
 * x=0 if r1_p == r2_p
 * x>0 if r1_p succedes r2_p (r1_p > r2_p)
 */

static int precedes_string(void *r1_p, void *r2_p) {
	if (r1_p == NULL) {
		fprintf(stderr, "precedes_string: the first parameter is a null pointer");
		exit(EXIT_FAILURE);
	}
	if (r2_p == NULL) {
		fprintf(stderr, "precedes_string: the second parameter is a null pointer");
		exit(EXIT_FAILURE);
	}
	Record *rec1_p = (Record*)r1_p;
	Record *rec2_p = (Record*)r2_p;
	return strcmp(rec1_p->string_field, rec2_p->string_field);
}

static int precedes_int(void *r1_p, void *r2_p) {
	if (r1_p == NULL) {
		fprintf(stderr, "precedes_int: the first parameter is a null pointer");
		exit(EXIT_FAILURE);
	}
	if (r2_p == NULL) {
		fprintf(stderr, "precedes_int: the second parameter is a null pointer");
		exit(EXIT_FAILURE);
	}
	Record *rec1_p = (Record*)r1_p;
	Record *rec2_p = (Record*)r2_p;
	return rec1_p->int_field - rec2_p->int_field;
}

static int precedes_double(void *r1_p, void *r2_p) {
	if (r1_p == NULL) {
		fprintf(stderr, "precedes_double: the first parameter is a null pointer");
		exit(EXIT_FAILURE);
	}
	if (r2_p == NULL) {
		fprintf(stderr, "precedes_double: the second parameter is a null pointer");
		exit(EXIT_FAILURE);
	}
	Record *rec1_p = (Record*)r1_p;
	Record *rec2_p = (Record*)r2_p;
	
	double rec1_d = rec1_p->double_field;
	double rec2_d = rec2_p->double_field;
	if(rec1_d < rec2_d)
		return -1;
	else if(rec1_d > rec2_d)
		return 1;
	return 0;
}

//----------------------------------------------------------------------------//

static void free_array(MyArray *array) {
	long size = my_array_size(array);
	for (long i = 0; i < size; ++i) {
		Record *array_element = (Record*)my_array_get(array, i);
		free(array_element->string_field);
		free(array_element);
	}
	my_array_free_memory(array);
}

//----------------------------------------------------------------------------//

static void print_array(MyArray *array) {
	long size = my_array_size(array);

	Record *array_element;
	printf("\nORDERED ARRAY OF RECORDS\n");

	for (long i = 0; i < size; i++) {
		array_element = (Record*)my_array_get(array, i);
		printf("%d, %s, %d, %lf\n",	//TODO check if %lf is correct or need to be changed
			array_element->id_field,
			array_element->string_field,
			array_element->int_field,
			array_element->double_field);
	}
}

//----------------------------------------------------------------------------//

static void print_array_to_file(const char *file_name, MyArray *my_array) {
	remove(file_name);
	FILE *file = fopen(file_name, "w+");
	//fprintf(fpt,"ID, Name, Email, Phone Number\n");
	for(long i=0; i<my_array->size; i++)
	{
		Record *record = (Record*)my_array->array[i];
		fprintf(file,"%d, %s, %d, %lf\n",
			record->id_field,
			record->string_field,
			record->int_field,
			record->double_field);
	}
	fclose(file);		
}

//----------------------------------------------------------------------------//

static void load_array(const char *file_name, MyArray *array) {
	FILE *fp;
	char *line = NULL;
	size_t len = 0;

	fp = open_file(file_name);

	while (getline(&line, &len, fp) != -1) {
		Record *record_p = malloc(sizeof(Record));
		if (record_p == NULL) {
			fprintf(stderr,"main: unable to allocate memory for the read record");
			exit(EXIT_FAILURE);
		}

		//read line and tokenize
		char *id_field_in_read_line_p = strtok(line, ",");
		char *string_field_in_read_line_p = strtok(NULL, ",");	//TODO check the functionalitity of the NULL parameter
		char *int_field_in_read_line_p = strtok(NULL, ",");
		char *double_field_in_read_line_p = strtok(NULL, ",");

		record_p->string_field = malloc((strlen(string_field_in_read_line_p)+1) * sizeof(char));
		if (record_p->string_field == NULL) {
			fprintf(stderr,"main: unable to allocate memory for the string field of the read record");
			exit(EXIT_FAILURE);
		}
		//load record data
		record_p->id_field = atoi(id_field_in_read_line_p);
		strcpy(record_p->string_field, string_field_in_read_line_p);
		record_p->int_field = atoi(int_field_in_read_line_p);
		record_p->double_field = strtod(double_field_in_read_line_p, NULL);	//TODO check strtod()
    
    		//load record into array
		my_array_add(array, (void*)record_p);
	}

	if (line) free(line);	//TODO check meaning
	fclose(fp);
	printf("\nData loaded\n");
}

//----------------------------------------------------------------------------//

static void test_quicksort(const char *input_file_name, const char *output_file_name, int (*precedes)(void*, void*)) {
	MyArray *array = my_array_create();
	load_array(input_file_name, array);
	printf("sorting..\n");
	sort_array_with_quicksort(array, precedes);
	//print_array(array);
	print_array_to_file(output_file_name, array);
	free_array(array);
}

static void test_bin_insertion_sort(const char *input_file_name, const char *output_file_name, int (*precedes)(void*, void*)) {
	MyArray *array = my_array_create();
	load_array(input_file_name, array);
	printf("sorting..\n");
	sort_array_with_bin_insertion_sort(array, precedes);
	//print_array(array);
	print_array_to_file(output_file_name, array);
	free_array(array);
}

//----------------------------------------------------------------------------//

int main(int argc, char const *argv[]) {
	if (argc < 5) {	//TODO update enter statement
		Usage();
		exit(EXIT_FAILURE);
	}

	printf("input file = %s\noutput file = %s\nfield number =%s\nalgorythm type = %s", argv[1], argv[2], argv[3], argv[4]);

	if(atoi(argv[4]) == QUICK_SORT){
		//quicksort
		switch(atoi(argv[3])){
			case STRING:
				test_quicksort(argv[1], argv[2], precedes_string);
				break;
			case INT:
				test_quicksort(argv[1], argv[2], precedes_int);
				break;
			case DOUBLE:
				test_quicksort(argv[1], argv[2], precedes_double);
				break;
			default:
				exit(EXIT_FAILURE);
		}	
	}
	else if(atoi(argv[4]) == BIN_INSERTION_SORT){
		//binary insertion sort
		switch(atoi(argv[3])){
			case STRING:
				test_bin_insertion_sort(argv[1], argv[2], precedes_string);
				break;
			case INT:
				test_bin_insertion_sort(argv[1], argv[2], precedes_int);
				break;
			case DOUBLE:
				test_bin_insertion_sort(argv[1], argv[2], precedes_double);
				break;
			default:
				exit(EXIT_FAILURE);
		}
	}
	else{
		exit(EXIT_FAILURE);
	}

  return EXIT_SUCCESS;
}
