#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../lib/unity.h"

#include "common.h"
#include "my_array_testing.h"
#include "sorting.h"

//=====================================================================================//
// precedence relation used in tests

/*static int precedes_string(void *r1_p, void *r2_p) {
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
}*/

static int precedes_int(void *r1_p, void *r2_p) {
	if (r1_p == NULL) {
		fprintf(stderr, "precedes_int: the first parameter is a null pointer");
		exit(EXIT_FAILURE);
	}
	if (r2_p == NULL) {
		fprintf(stderr, "precedes_int: the second parameter is a null pointer");
		exit(EXIT_FAILURE);
	}
	int *i1_p = (int*)r1_p;
	int *i2_p = (int*)r2_p;
	return *i1_p - *i2_p;
}

/*static int precedes_double(void *r1_p, void *r2_p) {
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
}*/

//=====================================================================================//



//=====================================================================================//

static void binary_test_array_is_empty_zero_el() {
	MyArray *my_array = my_array_create();
	sort_array_with_bin_insertion_sort(my_array, precedes_int);

	TEST_ASSERT_TRUE(my_array_is_empty(my_array));
	my_array_free_memory(my_array);
}

static void binary_test_array_is_empty_one_el() {
	MyArray *my_array = my_array_create();
	int i1 = -12;
	my_array_add(my_array, (void*)&i1);
	sort_array_with_bin_insertion_sort(my_array, precedes_int);
	
	TEST_ASSERT_FALSE(my_array_is_empty(my_array));
	my_array_free_memory(my_array);
}

static void binary_test_array_size_zero_el() {
	MyArray *my_array = my_array_create();
	sort_array_with_bin_insertion_sort(my_array, precedes_int);

	TEST_ASSERT_EQUAL_INT(0, my_array_size(my_array));
	my_array_free_memory(my_array);
}

static void binary_test_array_size_one_el() {
	MyArray *my_array = my_array_create();
	int i1 = -12;
	my_array_add(my_array, (void*)&i1);
	sort_array_with_bin_insertion_sort(my_array, precedes_int);

	TEST_ASSERT_EQUAL_INT(1, my_array_size(my_array));
	my_array_free_memory(my_array);
}

static void binary_test_array_size_two_el() {
	MyArray *my_array = my_array_create();
	int i1 = -12;
	my_array_add(my_array, (void*)&i1);
	int i2 = 2;
	my_array_add(my_array, (void*)&i2);
	sort_array_with_bin_insertion_sort(my_array, precedes_int);
	TEST_ASSERT_EQUAL_INT(2, my_array_size(my_array));
	my_array_free_memory(my_array);
}

static void binary_test_array_add_get_one_el() {
	MyArray *my_array = my_array_create();
	int i1 = -12;
	my_array_add(my_array, (void*)&i1);
	
	sort_array_with_bin_insertion_sort(my_array, precedes_int);
	
	TEST_ASSERT_EQUAL_PTR(i1, *(int*)my_array_get(my_array, 0));
	my_array_free_memory(my_array);
}

static void binary_test_array_add_get_three_el() {
	int i1 = -12;
	int i2 = 0;
	int i3 = 4;
	int *exp_arr[] = {&i1, &i2, &i3};

	MyArray *my_array = my_array_create();
	my_array_add(my_array, (void*)&i2);
	my_array_add(my_array, (void*)&i3);
	my_array_add(my_array, (void*)&i1);
	/*for (unsigned long i=0; i < 3; i++) {
		printf("array[%lu] = %d\n", i, *(int*)my_array_get(my_array, i));
	}*/
	sort_array_with_bin_insertion_sort(my_array, precedes_int);
	int **act_arr = malloc(3*sizeof(int*));
	for (unsigned long i=0; i < 3; i++) {
		act_arr[i] = (int*)my_array_get(my_array, i);
	}
	TEST_ASSERT_EQUAL_PTR_ARRAY(exp_arr, act_arr, 3);
	free(act_arr);
	my_array_free_memory(my_array);
}

static void binary_test_array_all_same(){

	int i = 4;
	int *exp_arr[] = {&i, &i, &i};
	
	MyArray *my_array = my_array_create();
	my_array_add(my_array, (void*)&i);
	my_array_add(my_array, (void*)&i);
	my_array_add(my_array, (void*)&i);
	
	sort_array_with_bin_insertion_sort(my_array, precedes_int);
	
	int **act_arr = malloc(3*sizeof(int*));
	for (unsigned long i=0; i < 3; i++) {
		act_arr[i] = (int*)my_array_get(my_array, i);
	}
	TEST_ASSERT_EQUAL_PTR_ARRAY(exp_arr, act_arr, 3);
	free(act_arr);
	my_array_free_memory(my_array);
}

static void binary_test_array_already_sorted(){

	int i1 = -12;
	int i2 = 0;
	int i3 = 4;
	int *exp_arr[] = {&i1, &i2, &i3};

	MyArray *my_array = my_array_create();
	my_array_add(my_array, (void*)&i1);
	my_array_add(my_array, (void*)&i2);
	my_array_add(my_array, (void*)&i3);
	/*for (unsigned long i=0; i < 3; i++) {
		printf("array[%lu] = %d\n", i, *(int*)my_array_get(my_array, i));
	}*/
	
	sort_array_with_bin_insertion_sort(my_array, precedes_int);
	
	int **act_arr = malloc(3*sizeof(int*));

	for (unsigned long i=0; i < 3; i++) {
		act_arr[i] = (int*)my_array_get(my_array, i);
	}
	TEST_ASSERT_EQUAL_PTR_ARRAY(exp_arr, act_arr, 3);
	free(act_arr);
	my_array_free_memory(my_array);

}

static void binary_test_array_inverted_order(){
	
	int i1 = -12;
	int i2 = 0;
	int i3 = 4;
	int *exp_arr[] = {&i1, &i2, &i3};

	MyArray *my_array = my_array_create();
	my_array_add(my_array, (void*)&i3);
	my_array_add(my_array, (void*)&i2);
	my_array_add(my_array, (void*)&i1);
	/*for (unsigned long i=0; i < 3; i++) {
		printf("array[%lu] = %d\n", i, *(int*)my_array_get(my_array, i));
	}*/
	
	sort_array_with_bin_insertion_sort(my_array, precedes_int);
	
	int **act_arr = malloc(3*sizeof(int*));
	for (unsigned long i=0; i < 3; i++) {
		act_arr[i] = (int*)my_array_get(my_array, i);
	}
	TEST_ASSERT_EQUAL_PTR_ARRAY(exp_arr, act_arr, 3);
	free(act_arr);
	my_array_free_memory(my_array);
}

int main() {
	UNITY_BEGIN();
	RUN_TEST(binary_test_array_is_empty_zero_el);
	RUN_TEST(binary_test_array_is_empty_one_el);
	RUN_TEST(binary_test_array_size_zero_el);
	RUN_TEST(binary_test_array_size_one_el);
	RUN_TEST(binary_test_array_size_two_el);
	RUN_TEST(binary_test_array_add_get_one_el);
	RUN_TEST(binary_test_array_add_get_three_el);
	RUN_TEST(binary_test_array_all_same);
	RUN_TEST(binary_test_array_already_sorted);
	RUN_TEST(binary_test_array_inverted_order);
	//TODO stessi elementi, già ordinati, ordinati al contrario
	//TODO binary insertion sort
	return UNITY_END();
}
