#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>

#include "common.h"
#include "SkipList.h"

void Usage(){
	fprintf(stderr, "USAGE:\n");
	fprintf(stderr, "\tdictionary_file\n");
	fprintf(stderr, "\tcorrectme_file\n");
	exit(1);
}

//------------------------------------------------------------------------------------------//
//remove ounctuation and make everything to lowercase
void rem_punt_and_lowercase(char *s){
	char *src = s;
	char *dst = s;
	
	while(*src){
		if(ispunct((unsigned char)*src))
			src++;
		else if(isupper((unsigned char)*src)){
			*dst++ = tolower((unsigned char)*src);
			src++;
		}
		else if(src == dst){
			src++;
			dst++;
		}
		else
			*dst++ = *src++;
	}
	
	*dst = 0;
}

//---------------------------------------------------------------------------//

FILE *open_file(const char *filename){
	FILE *file = fopen(filename, "r");
	if (file == NULL) {
		printf("File not found: %s\n", filename);
		exit(1);
	}
	return file;
}

//---------------------------------------------------------------------------//
/* 
 * return
 * x<0 if r1_p precedes r2_p (r1_p < r2_p)
 * x=0 if r1_p == r2_p
 * x>0 if r1_p succedes r2_p (r1_p > r2_p)
 */

static int precedes_string(void *r1_p, void *r2_p) {
	if(r1_p == NULL)
		return 0;

	char *rec1_p = (char*)r1_p;
	char *rec2_p = (char*)r2_p;
	return strcmp(rec1_p, rec2_p);
}

//---------------------------------------------------------------------------//

void load_dictionary(SkipList *list, const char *filename){
	
	FILE *fp = open_file(filename);
	char *line = NULL;
	size_t len = 0;

	//for each lone
	while (getline(&line, &len, fp) != -1) {
		char *tmp;
		tmp = (char*)malloc(sizeof(char)*30);
		if(tmp == NULL){
			fprintf(stderr, "load_dictionary : tmp = malloc() failed\n");
			exit(EXIT_FAILURE);
		}
		memcpy((void*)tmp, (void*)line, sizeof(char)*30);
		//remove the \n
		tmp[strcspn(tmp, "\n")] = 0;
		//insert word in the dictionary
		insertSkipList(list, (void*)tmp);
	}

	if (line) free(line);
	fclose(fp);
	printf("\nData loaded\n");
	
}

//---------------------------------------------------------------------------//

void check_correctme(SkipList *dictionary, const char *filename){
	
	FILE *fp = open_file(filename);
	char *line = NULL;
	size_t len = 0;

	//for each line
	while (getline(&line, &len, fp) != -1) {
		//remove punctuation and make lowercase
		rem_punt_and_lowercase(line);
		//for each token
		char *word = strtok(line, " ");
		while(word){
			//remove \n
			word[strcspn(word, "\n")] = 0;
			//ask dictionary if the word is correct
			void *exist = searchSkipList(dictionary, (void*)word);
			if( exist == NULL )
				printf("found missmatch: \t\"%s\"\n", word);
			word = strtok(NULL, " ");
		}
		
	}

	fclose(fp);
	
}


//---------------------------------------------------------------------------//
int precedes_int(void *a, void *b){
	if(a == NULL)
		return 0;

	int *A = (int*)a;
	int *B = (int*)b;
	return *A - *B;
}
//---------------------------------------------------------------------------//

int main(int argc, char const *argv[]) {
	if( argc < 3 ){
		Usage();
		exit(EXIT_FAILURE);
	}
	
	SkipList *dictionary = create_skiplist(precedes_string);
	printf("dictionary created\n");
	
	load_dictionary(dictionary, argv[1]);
	printf("dictionary loaded\n");

	check_correctme(dictionary, argv[2]);
	printf("correctme checked\n");
	
}
