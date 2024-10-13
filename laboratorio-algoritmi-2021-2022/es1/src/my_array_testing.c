#include <stdlib.h>
#include <stdio.h>
#include "common.h"
#include "my_array.h"

//Initial capacity for the array
#define INITIAL_CAPACITY 2

//----------------------------------------------------------------------------//

MyArray *my_array_create() {
	srand((unsigned int)time(0));	//set the key for the random sequence based on the time of excition
	MyArray *my_array = (MyArray*)malloc(sizeof(MyArray));
	if (my_array == NULL) {
		fprintf(stderr, "my_array_create: unable to allocate memory for the array");
		return NULL;
	}
	my_array->array = (void**)malloc(INITIAL_CAPACITY * sizeof(int*));
	if (my_array->array == NULL) {
		fprintf(stderr, "my_array_create: unable to allocate memory for the internal array");
		return NULL;
	}
	my_array->size = 0;
	my_array->array_capacity = INITIAL_CAPACITY;
	return my_array;
}

//----------------------------------------------------------------------------//

int my_array_is_empty(MyArray *my_array) {
	if (my_array == NULL) {
		return 1;
	}
	return my_array->size == 0;
}

//----------------------------------------------------------------------------//

long my_array_size(MyArray *my_array) {
	if (my_array == NULL) {
		return 0;	//TODO ret 0??
	}
	return my_array->size;
}

//----------------------------------------------------------------------------//

MyArray *my_array_add(MyArray *my_array, void *element) {
	if (my_array == NULL) {
		fprintf(stderr, "my_array_add: my_array parameter cannot be NULL");
		return NULL;
	}
	if (element == NULL) {
		fprintf(stderr, "my_array_add: element parameter cannot be NULL");
		return NULL;
	}

	//check for available space or increment the memory allocation
	if (my_array->size >= my_array->array_capacity) {
		my_array->array_capacity = 2 * my_array->array_capacity; //TODO Why multiply by 2?
		my_array->array = (void**)realloc(my_array->array, (unsigned long)my_array->array_capacity * sizeof(int*));
		if (my_array->array == NULL) {
			fprintf(stderr,"my_array_add: unable to reallocate memory to host the new element");
			return NULL;
		}
	}

	//add the element in the current 1st available position
	my_array->array[my_array->size] = element;
	my_array->size++;
	return my_array;
}

//----------------------------------------------------------------------------//

void *my_array_get(MyArray *my_array, long i) {
	if (my_array == NULL) {
		fprintf(stderr, "my_array_get: my_array parameter cannot be NULL");
		return NULL;
	}
	if (i >= my_array->size) {
		fprintf(stderr, "my_array_get: Index %lu is out of the array bounds", i);
		return NULL;
	}
  
	return my_array->array[i];
}

//----------------------------------------------------------------------------//

void my_array_free_memory(MyArray *my_array) {
	if (my_array == NULL) {
		fprintf(stderr, "my_array_free_memory: my_array parameter cannot be NULL");
		return;
	}
	free(my_array->array);
	free(my_array);
}

//----------------------------------------------------------------------------//
