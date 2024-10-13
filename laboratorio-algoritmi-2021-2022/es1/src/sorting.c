#include <stdlib.h>

#include <stdio.h> //TODO remove after debugging

#include "common.h"
#include "my_array.h"
#include "sorting.h"

void Exchange(MyArray *my_array, long a, long b){
	void* tmp = my_array->array[a];
	my_array->array[a] = my_array->array[b];
	my_array->array[b] = tmp;
}

long Get_random_number(long lower, long upper){
	//get random number in range of positives values
	if(upper < lower || lower < 0){
		printf("Sorting - Get_random_number : upper < lower or lover < 0");
		return -1;
	}	
	return (rand() % (upper - lower + 1)) + lower;
}
//-----------------------------------------------------------//

/*
 * QuickSort function
 */
long Partition(MyArray *my_array, long p, long r, int (*precedes)(void*, void*)){
	void* x = my_array->array[r];
	long i = p-1;
	for(long j=p; j<r; j++ ){
		if( precedes( my_array->array[j], x) <= 0){
			i++;
			Exchange( my_array, i, j );
		}
	}
	Exchange(my_array, i+1, r);
	return i+1;
}

//---------------------------------------------------------//

long Random_partition(MyArray *my_array, long p, long r, int (*precedes)(void*, void*)){
	long q = Get_random_number(p, r);
	Exchange(my_array, r, q);
	return Partition(my_array, p, r, precedes);
}
long First_partition(MyArray *my_array, long p, long r, int (*precedes)(void*, void*)){
	Exchange(my_array, r, 0);
	return Partition(my_array, p, r, precedes);
}
long Mid_partition(MyArray *my_array, long p, long r, int (*precedes)(void*, void*)){
	long mid = my_array->size / 2;
	Exchange(my_array, r, mid);
	return Partition(my_array, p, r, precedes);
}

//---------------------------------------------------------//

void QuickSort(MyArray *my_array, long p, long r, int (*precedes)(void*, void*)){
	//printf("called QuickSort( p=%ld, r=%ld )\n", p, r);
	if(p < r){
		long q = Partition(my_array, p, r, precedes);
		//long q = Random_partition(my_array, p, r, precedes);
		//long q = First_partition(my_array, p, r, precedes);
		//long q = Mid_partition(my_array, p, r, precedes);
		
		
			/*printf("array after partition\n");
			for (long i=0; i < (long)my_array->size; i++) {
				printf("array[%ld] = %d\n", i, *(int*)my_array_get(my_array, (unsigned long)i));
			}*/
		QuickSort(my_array, p, q-1, precedes);
		QuickSort(my_array, q+1, r, precedes);
	}
}

//--------------------------------------------------------------------------------------------------------------------------------//
/*
 * BinaryInsertionSort function
 */
 
 /*
void InsertionSort(MyArray *array, int (*precedes)(void*, void*)){
	for(unsigned long j=1; j < array.size; j++){
		void* key = array[j];
		unsigned long i = j-1;
		while( i>=0 && array[i]>key){
			array[i+1] = array[i];
			i--;
			}
		array[i+1] = key;
	}
}*/

long BinarySearch(MyArray *my_array, void* key, long l, long r, int (*precedes)(void*, void*)){
	if(precedes(my_array->array[l], my_array->array[r]) == 0){
		return l;
	}
	long m = (l+r)/2;
	if(precedes(key, my_array->array[m]) <= 0){
		return BinarySearch(my_array, key, l, m, precedes);
	}
	else {
		return BinarySearch(my_array, key, m+1, r, precedes);	//TODO chech if the m+1 is correct
	}
}

void BinaryInsertionSort(MyArray *my_array, int (*precedes)(void*, void*)){
	for(long j=1; j < my_array->size; j++){
		void* key = my_array->array[j];
		long indice = BinarySearch(my_array, key, 0, j, precedes);
		for(long i=indice; i<j; i++){
			Exchange(my_array, i, j);
		}
	}
}

//------------------------------------------------------------------//

void sort_array_with_quicksort(MyArray *my_array, int (*precedes)(void*, void*)){
	//printf("size = %lu\n", my_array_size(my_array));
	//printf("empty = %d\n", my_array_is_empty(my_array));
	if(my_array_is_empty(my_array) || my_array_size(my_array) == 1){
		return;
	}
	//printf("calling quicksort\n");
	QuickSort(my_array, 0, my_array->size-1, precedes);
}

void sort_array_with_bin_insertion_sort(MyArray *my_array, int (*precedes)(void*, void*)){
	if(my_array_is_empty(my_array) || my_array_size(my_array) == 1){
		return;
	}
	BinaryInsertionSort(my_array, precedes);
}

//------------------------------------------------------------------//

