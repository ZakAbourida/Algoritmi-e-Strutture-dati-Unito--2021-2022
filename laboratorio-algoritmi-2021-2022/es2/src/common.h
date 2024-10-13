#ifndef COMMON_H

	#define COMMON_H
	
	#define MAX_HEIGHT 80

	typedef struct _Node Node;
	struct _Node{
		Node **next;
		int size;
		void *item;
	};

	typedef struct {
		Node *head;
		int max_level; // max_level < MAX_HEIGHT	max_level go from 0 to N
		int (*precedes)(void*, void*);
	}SkipList;

#endif
