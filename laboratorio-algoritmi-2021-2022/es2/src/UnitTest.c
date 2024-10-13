#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../lib/unity.h"

#include "common.h"
#include "SkipList.h"

//---------------------------------------------------------------------------------------------------------------//

int precedes_int(void *a, void *b){
	if(a == NULL)
		return 1;

	int *A = (int*)a;
	int *B = (int*)b;
	return *A - *B;
}

//---------------------------------------------------------------------------------------------------------------//

static void add_3_check_3(){
	int a = 5;
	int b = -2;
	int c = 10;
	SkipList *list = create_skiplist(precedes_int);
	
	insertSkipList(list, (void*)&a);
	print_skiplist(list);
	insertSkipList(list, (void*)&b);
	print_skiplist(list);
	insertSkipList(list, (void*)&c);
	print_skiplist(list);
	
	int aa = *(int*)searchSkipList(list, (void*)&a);
	printf("U - 1\n");
	TEST_ASSERT_EQUAL_PTR(aa, a);
	int bb = *(int*)searchSkipList(list, (void*)&b);
	printf("U - 2\n");
	TEST_ASSERT_EQUAL_PTR(bb, b);
	int cc = *(int*)searchSkipList(list, (void*)&c);
	printf("U - 3\n");
	TEST_ASSERT_EQUAL_PTR(cc, c);
	
	free_skiplist(list);
}

//---------------------------------------------------------------------------------------------------------------//

static void search_skiplist_for_NULL(){
	SkipList *list = create_skiplist(precedes_int);
	TEST_ASSERT_EQUAL_PTR(NULL, searchSkipList(list, NULL));
	free_skiplist(list);

}

//---------------------------------------------------------------------------------------------------------------//

static void search_skiplist_for_not_present_element(){
	int a = 10;
	int b = -2;
	int c = 5;
	SkipList *list = create_skiplist(precedes_int);
	
	insertSkipList(list, (void*)&a);
	print_skiplist(list);
	insertSkipList(list, (void*)&b);
	print_skiplist(list);
	insertSkipList(list, (void*)&c);
	print_skiplist(list);
	
	int d = -5;
	
	TEST_ASSERT_EQUAL_PTR(NULL, searchSkipList(list, (void*)&d));
	free_skiplist(list);
}

//---------------------------------------------------------------------------------------------------------------//

static void test_A(){
	int a = 10;
	SkipList *list = create_skiplist(precedes_int);
	
	insertSkipList(list, (void*)&a);
	print_skiplist(list);
	a = 5;
	insertSkipList(list, (void*)&a);
	print_skiplist(list);
	a = -2;
	insertSkipList(list, (void*)&a);
	print_skiplist(list);
	
	int d = -5;
	
	TEST_ASSERT_EQUAL_PTR(NULL, searchSkipList(list, (void*)&d));
	free_skiplist(list);
}

int main() {
	UNITY_BEGIN();
	RUN_TEST(add_3_check_3);
	RUN_TEST(search_skiplist_for_NULL);
	RUN_TEST(search_skiplist_for_not_present_element);
	RUN_TEST(test_A);
	return UNITY_END();
}

