#ifndef SKIPLIST_H
	#define SKIPLIST_H
	
	#include "common.h"
	SkipList *create_skiplist(int (*precedes)(void*, void*));
	void free_skiplist(SkipList *list);
	
	void* searchSkipList(SkipList *list, void *I);
	int randomLevel();
	Node *createNode(void *I, int size);
	void insertSkipList(SkipList *list, void *I);
	
	
	void print_skiplist(SkipList *list);
#endif
