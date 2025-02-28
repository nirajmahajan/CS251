/** @file stud_rec.cpp 
* This Code is taken from http://www.worldbestlearningcenter.com for learning/teaching purpose
* This program implements a working, editable student database in the form of array
* with the help of structs
**/

#include <cstdlib>
#include <iostream>
#include <iomanip>
#include <string.h>
using namespace std;

/** 
  * This struct is the basic student object which holds all the details of
  * a student
*/
struct student
{
	string stnumber;
	string stname;
	char sex;
	float quizz1;
	float quizz2;
	float assigment;
	float midterm;
	float final;
	float total;
	int numberOfitem;
};

int search(struct student st[],string id, int itemcount);
void clean(struct student st[],int deleteitem);

/** 
  * This function is used to display all of the actions which 
  * can be performed on the given database of student objects
*/
void displaymenu(){
	cout<<"=========================================="<<"\n";
	cout<<" MENU "<<"\n";
	cout<<"=========================================="<<"\n";
	cout<<" 1.Add student records"<<"\n";
	cout<<" 2.Delete student records"<<"\n";
	cout<<" 3.Update student records"<<"\n";
	cout<<" 4.View all student records"<<"\n";
	cout<<" 5.Calculate average score of a student"<<"\n";
	cout<<" 6.Show student who gets the max total score"<<"\n";
	cout<<" 7.Show student who gets the max total score"<<"\n"; 
	cout<<" 8.Find a student by ID"<<"\n"; 
	cout<<" 9.Sort records by TOTAL"<<"\n"; 
}

/** \param st the array of Student objects containing each student's information
  * \param itemcount total number of records so far
  * 
  * This function takes in input a student's Id and confirm if it doesn't exist already,
  * if not, it asks for the details of the student to be added into the database
*/
void add_rec(struct student st[],int& itemcount){
	again:
	cout<<"\nEnter student's ID:";
	cin>>st[itemcount].stnumber;
	if(search(st,st[itemcount].stnumber,itemcount)!=-1){
		cout<<"This ID already exists\n";goto again;
	}
	cout<<"Enter student's Name:"; 
	cin>>st[itemcount].stname;
	cout<<"Enter student's Sex(F or M):";cin>>st[itemcount].sex;
	cout<<"Enter student's quizz1 score:";cin>>st[itemcount].quizz1;
	cout<<"Enter student's quizz2 score:";cin>>st[itemcount].quizz2;
	cout<<"Enter student's assigment score:";cin>>st[itemcount].assigment;
	cout<<"Enter student's mid term score:";cin>>st[itemcount].midterm;
	cout<<"Enter student's final score:";cin>>st[itemcount].final;
	st[itemcount].total=st[itemcount].quizz1+st[itemcount].quizz2+st[itemcount].assigment+st[itemcount].midterm+st[itemcount].final;

	++itemcount;
}

/** \param st the array of Student objects containing each student's information
  * \param id the ID of the student to be searched in the record array
  * \param itemcount total number of records so far
  * \return Always returns index of the search id in the record array st
  * 
  * This function searches a given id of a student in the records array and returns -1 
  * the given id is not present in the records array st
*/
int search(struct student st[], string id,int itemcount){
	int found =-1;
	for (int i = 0; i < itemcount && found==-1; i++)
	{
		if (st[i].stnumber == id) found=i;
		else found=-1 ;
	}

	return found;
}

/** \param st the array of Student objects containing each student's information
  * \param itemcount total number of records so far
  * 
  * This function prints all the records inserted so far in st array in a tabular format woth proper headings  
*/
void viewall(struct student st[], int itemcount){
	int i=0;
	cout<<left<<setw(5)<<"ID"<<setw(20)<<"NAME"<<setw(5)<<"SEX"

	<<setw(5)<<"Q1"
	<<setw(5)<<"Q2"<<setw(5)<<"As"<<setw(5)<<"Mi"<<setw(5)<<"Fi"
	<<setw(5)<<"TOTAL"<<"\n";
	cout<<"==============================================\n";
	while(i<=itemcount){
		if(st[i].stnumber!=""){
			cout<<left<<setw(5)<<st[i].stnumber<<setw(20)<<st[i].stname<<setw(5)

			<<st[i].sex;
			cout<<setw(5)<<st[i].quizz1<<setw(5)<<st[i].quizz2<<setw(5)<<st[i].assigment
			<<setw(5)<<st[i].midterm<<setw(5)<<st[i]. final<<setw(5)
			<<st[i].total;

			cout<<"\n";
		}
		i=i+1;
	}
}

/** \param st the array of Student objects containing each student's information
  * \param itemcount total number of records so far
  * 
  * This function deletes a given student's details from the st array, if it originally existed 
  * in the array, otherwise does nothing except printing the error message
*/
void delete_rec(struct student st[], int& itemcount){
	string id;
	int index;
	if (itemcount > 0)
	{
		cout<<"Enter student's ID:";
		cin>>id;
		index = search(st, id,itemcount); 

		if ((index!=-1) && (itemcount != 0)){
			if (index == (itemcount-1)){ 

				clean(st, index);
				--itemcount;

				cout<<"The record was deleted.\n";
			}
			else{ 
				for (int i = index; i < itemcount-1; i++){
					st[i] = st[i + 1];
					clean(st, itemcount);
					--itemcount ;
				}
			}
		}
		else cout<<"The record doesn't exist.Check the ID and try again.\n";
	}
	else cout<<"No record to delete\n";
}

/** \param st the array of Student objects containing each student's information
  * \param index the index of the student in the record array
  * 
  * This function clears all the details of the student present at a given index in the original
  * record array st, that resets all the details of a given student to a default array  
*/
void clean(struct student st[],int index){
	st[index].stnumber = "";
	st[index].stname = "";
	st[index].sex = 'X';
	st[index].quizz1 = 0;
	st[index].quizz2 = 0;
	st[index].assigment = 0;
	st[index].midterm = 0;
	st[index].final = 0;
	st[index].total = 0;
}

/** \param st the array of Student objects containing each student's information
  * \param itemcount total number of records so far
  * 
  * This function takes input ID and the detail of the student of that ID which needs to be changed 
  * in the database (st array), in the form of column index as input
*/
void update_rec(struct student st[],int itemcount){
	string id;
	int column_index;
	cout<<"Enter student's ID:";
	cin>>id;
	cout<<"Which field you want to update(1-7)?:";
	cin>>column_index;

	int index = search(st, id,itemcount);

	if (index != -1)
	{
		if (column_index == 1){
			cout<<"Enter student's Name:";
			cin>>st[index].stname;
		}

		else if (column_index == 2){
			cout<<"Enter student's Sex(F or M):";
			cin>>st[index].sex;
		}
		else if (column_index == 3){
			cout<<"Enter student's quizz1 score:";
			cin>>st[index].quizz1;
		}
		else if (column_index == 4){
			cout<<"Enter student's quizz2 score:";
			cin>>st[index].quizz2;
		}
		else if (column_index == 5){
			cout<<"Enter student's assigment score:";
			cin>>st[index].assigment;
		}
		else if (column_index == 6){
			cout<<"Enter student's mid term score:";
			cin>>st[index].midterm;
		}
		else if (column_index == 7)	{
			cout<<"Enter student's final score:";
			cin>>st[index].final;
		}
		else cout<<"Invalid column index";

		st[index].total = st[index].quizz1 + st[index].quizz2 + st[index].assigment

		+ st[index].midterm + st[index].final;
	}
	else cout<<"The record deosn't exits.Check the ID and try again.";
}

/** \param st the array of Student objects containing each student's information
  * \param itemcount total number of records so far
  * 
  * This function prints the ID of the student with the maximum total marks
*/
void showmax(struct student st[], int itemcount){
	float max = st[0].total;
	int index=0;

	if (itemcount >= 2){
		for (int j = 0; j < itemcount-1; ++j)
			if (max < st[j+1].total) {
				max = st[j+1].total;
				index = j+1;
			}
	}
	else if (itemcount == 1){
		index = 0;
		max = st[0].total;
	}
	else 
		cout<<"Not record found!\n";

	if (index != -1) 
		cout<<"The student with ID "<<st[index].stnumber<<" gets the highest score "<<max<<endl;
}

/** \param st the array of Student objects containing each student's information
  * \param itemcount total number of records so far
  * 
  * This function prints the ID of the student with the minimum total marks
*/
void showmin(struct student st[], int itemcount){

	float min = st[0].total;
	int index = 0;

	if (itemcount >= 2){
		for (int j = 0; j < itemcount-1; ++j)
			if (min > st[j+1].total){
				min = st[j+1].total;
				index = j+1;
			}
	}
	else if (itemcount == 1){
		index = 0;
		min = st[0].total;
	}
	else 
		cout<<"No record found!\n";

	if (index != -1) cout<<"The student with ID "<<st[index].stnumber<<" gets the highest score "<<min<<endl;

}

/** \param st the array of Student objects containing each student's information
  * \param itemcount total number of records so far
  * 
  * This function takes in input ID of a student and prints all his/her details in a tabular format 
*/
void find(struct student st[], int itemcount){
	string id;
	cout<<"Enter student's ID:";
	cin>>id;

	int index=search(st,id,itemcount);
	if (index != -1) { 								
		cout<<left<<setw(5)<<st[index].stnumber<<setw(20)<<st[index].stname<<setw(5)<<st[index].sex;
		cout<<setw(5)<<st[index].quizz1<<setw(5)<<st[index].quizz2<<setw(5)

		<<st[index].assigment
		<<setw(5)<<st[index].midterm<<setw(5)<<st[index].final<<setw(5)
		<<st[index].total;
		cout<<"\n"; 

	}
	else cout<<"The record doesn't exits.";

}

/** \param dataset the array of Student objects containing each student's information
  * \param n The length of the dataset array
  * 
  * This function sorts the dataset array consisting of Student objects using the
  * BubbleSort Algorithm which is an O(n^2) time algorithm (repeated swapping of elements) 
*/
void bubblesort(struct student dataset[], int n){
	int i, j;
	for (i = 0; i < n; i++)
		for (j = n - 1; j > i; j--)
			if (dataset[j].total < dataset[j - 1].total ){
				student temp = dataset[j];
				dataset[j] = dataset[j - 1];
				dataset[j - 1] = temp;
			}

}

/** \param st the array of Student objects containing each student's information
  * \param itemcount total number of records so far
  * 
  * This function takes in input as ID of a student and prints the 
  * average marks of that student (considering equal weightage)
*/
void average(struct student st[], int itemcount){
	string id;
	float avg=0;
	cout<<"Enter students'ID:";
	cin>>id;
	int index = search(st, id,itemcount);
	if (index != -1 && itemcount>0)
	{
		st[index].total = st[index].quizz1 + st[index].quizz2 + st[index].assigment
			+ st[index].midterm + st[index].final;
		
		avg = st[index].total /5;
	}

	cout<<"The average score is "<<avg;
}

/**  
  * This function is the main driver code for this program which 
  * uses all the functions defined in this file
*/
int main(int argc, char *argv[]){

	student st[80];
	int itemcount=0;

	int yourchoice;
	char confirm;
	do{
	
		displaymenu();
		cout<<"Enter your choice(1-9):";
		cin>>yourchoice;

		switch(yourchoice){
			case 1:add_rec(st, itemcount);break;
			case 2:delete_rec(st, itemcount);break;
			case 3:update_rec(st, itemcount);break;
			case 4:viewall(st, itemcount);break;
			case 5:average(st, itemcount);break;
			case 6:showmax(st, itemcount);break;
			case 7:showmin(st, itemcount);break;
			case 8:find(st, itemcount);break;

			case 9:bubblesort(st,itemcount);break;
			default:cout<<"invalid";
		}

		cout<<"Press y or Y to continue:";
		cin>>confirm;
	}while(confirm=='y'||confirm=='Y');

	system("PAUSE");
	
	return EXIT_SUCCESS;
}