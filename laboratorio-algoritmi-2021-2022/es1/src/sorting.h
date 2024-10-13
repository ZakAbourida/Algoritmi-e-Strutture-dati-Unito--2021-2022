#ifndef SORTING_H
#define SORTING_H

	void sort_array_with_quicksort(MyArray*, int (*precedes)(void*, void*));
	void sort_array_with_bin_insertion_sort(MyArray*, int (*precedes)(void*, void*));

	void Exchange(MyArray*, long, long);
	void QuickSort(MyArray*, long, long, int (*precedes)(void*, void*));
	long Partition(MyArray*, long, long, int (*precedes)(void*, void*));
	long BinarySearch(MyArray*, void*, long, long, int (*precedes)(void*, void*));

#endif
