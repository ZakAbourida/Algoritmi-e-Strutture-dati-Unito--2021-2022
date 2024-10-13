#ifndef MY_ARRAY_H
#define MY_ARRAY_H


	// Alternatively, we can use
	// #pragma once
	
	MyArray *my_array_create();
	int my_array_is_empty(MyArray*);
	long my_array_size(MyArray*);
	MyArray *my_array_add(MyArray*, void*);

	void *my_array_get(MyArray*, long);
	void my_array_free_memory(MyArray*);

#endif // ORDERED_ARRAY_H

