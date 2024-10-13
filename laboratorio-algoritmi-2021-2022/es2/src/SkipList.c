#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <string.h>

#include "common.h"

//------------------------------------------------------------------------------------------//

void print_skiplist(SkipList *list){
	Node *x = list->head;
	
	if(x->next[0] == NULL)
		return;
	
	x = x->next[0];
	
	while(x != NULL){
		printf("size: %d - elem : \"%s\"\n", x->size, ((char*)(x->item)));
		x = x->next[0];
	}
}

//------------------------------------------------------------------------------------------//

SkipList *create_skiplist(int (*precedes)(void*, void*)){
	srand((unsigned int)time(0));	//set the key for the random sequence based on the time of excition

	SkipList *skiplist = (SkipList*)malloc(sizeof(SkipList));
	if(skiplist == NULL){
		fprintf(stderr, "create_skiplist: skiplist malloc failure\n");
		exit(EXIT_FAILURE);
	}
	Node *head = (Node*)malloc(sizeof(Node));
	if(head == NULL){
		fprintf(stderr, "create_skiplist: head malloc failure\n");
		exit(EXIT_FAILURE);
	}
	head->next = (Node**)malloc(sizeof(Node*)*MAX_HEIGHT);
	if(head->next == NULL){
		fprintf(stderr, "create_skiplist: head->next malloc failure\n");
		exit(EXIT_FAILURE);
	}
	
	//initialize skiplist
	head->next[0] = NULL;
	head->size = 1;
	head->item = NULL;
	
	skiplist->head = head;
	skiplist->max_level = 0;
	skiplist->precedes = precedes;
	
	return skiplist;
}

//------------------------------------------------------------------------------------------//

void free_skiplist(SkipList *list){
	Node *x = list->head;
	Node *tmp;
	while( x->next[0] != NULL ){
		tmp = x->next[0];
		free(x->next);
		x = tmp;
	}
}

//------------------------------------------------------------------------------------------//

void* searchSkipList(SkipList *list, void *I){
	if( I == NULL || list == NULL)
		return NULL;

	Node *x = list->head;
	
	//loop invariant: x->item < I
	for( int i = list->max_level; i >= 0; i-- ){
		while( x->next[i] != NULL && list->precedes( x->next[i]->item, I) <= 0 ){
			x = x->next[i];
		}
	}
	// x->item < I <= n->next[1]->item
	
	//x = x->next[0];
	if(x->item == NULL){
		return NULL;
	}
	
	if( list->precedes(x->item, I) == 0 )
		return x->item;
	else
		return NULL;
}

//------------------------------------------------------------------------------------------//

int randomLevel(){
	return ((rand() % (MAX_HEIGHT - 1 + 1)) + 1);	//(rand() % (max - min + 1)) + min
}

//------------------------------------------------------------------------------------------//

Node *createNode(void *I, int size){
	Node *node = (Node*)malloc(sizeof(Node));
	//load data into node
	node->next = (Node**)malloc((unsigned int)size * sizeof(Node*));
	if(node->next == NULL){
		fprintf(stderr, "malloc for Node failure\n");
		exit(EXIT_FAILURE);
	}
	for(int i=0; i<size; i++){
		node->next[i] = NULL;
	}
	
	node->size = size;
	
	node->item = I;
	
	return node;
}

//------------------------------------------------------------------------------------------//

void insertSkipList(SkipList *list, void *I){
	Node *node = createNode(I, randomLevel());
	
	if( node->size-1 > list->max_level )
		list->max_level = node->size-1;
	
	Node *x = list->head;
	//move the node to the correct position
	for( int i = list->max_level; i >= 0; i-- ){
		if( x->next[i] == NULL || list->precedes(I, x->next[i]->item) < 0 ){
			if( i < node->size ){
				node->next[i] = x->next[i];
				x->next[i] = node;
			}
		}
		else{
			x = x->next[i];
			i++;
		}
	}
}

//------------------------------------------------------------------------------------------//




