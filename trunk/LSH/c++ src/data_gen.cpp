#include <iostream>
#include <fstream>
#include <sstream>
#include <cstdio>
#include <cstdlib>
#include <cmath>
#include <algorithm>
#include <set>
#include <vector>
#include <map>
#include <time.h>

using namespace std;

const int MIN_DEGREE = 2, MAX_DEGREE = 4;
const int MAX_COMMON_ADD = 5;
const int MIN_DATA = 10, MAX_DATA = 100;
const int MAX_FEATURE_BOUND = 20;

typedef set<int> FEATURE_SET;
typedef vector<int> ID_SET;

struct LabelStructure{
  double label;
  int nson;
  LabelStructure* father;
  ID_SET data_id;
  FEATURE_SET common_feature;
};

int max_level;
char* result_file;

int max_feature = 0;
double cur_label = 0;
vector<LabelStructure*> label_info;

void Gen_Data_Info(int level, LabelStructure* father){

  if (false){
    cout << level << endl;
  }

  if (level >= max_level){
    return;
  }

  if (level == 0){
    LabelStructure* ls = new LabelStructure();
    ls->label = cur_label;
    cur_label += 1;
    ls->nson = 0;
    ls->father = NULL;
    label_info.push_back(ls);
    Gen_Data_Info(level + 1, ls);
  } else {
    int nson = MIN_DEGREE + (rand() % (MAX_DEGREE - MIN_DEGREE));
    for (int i = 0; i < nson; ++i){
      LabelStructure* ls = new LabelStructure();
      ls->label = cur_label;
      cur_label += 1;
      ls->nson = 0;
      ls->father = father;
      ++(father->nson);
      for (FEATURE_SET::iterator it = father->common_feature.begin();
	   father->common_feature.end() != it;
	   ++it){
	ls->common_feature.insert((*it));
      }
      int nadd = (rand() % MAX_COMMON_ADD) + 1;
      for (int j = 0; j < nadd; ++j){
	ls->common_feature.insert(max_feature++);
      }     

      if (false){
	cout << "label_info.size() : " << label_info.size() << endl;
      }
 
      label_info.push_back(ls);

      Gen_Data_Info(level + 1, ls);
    }
  }
}

void Gen_Data(){

  Gen_Data_Info(0, NULL);

  ofstream fout(result_file);
  stringstream sbuf;
  sbuf << result_file << ".label";
  ofstream lout(sbuf.str().c_str());
  stringstream ssbuf;
  ssbuf << result_file << ".info";
  ofstream infoout(ssbuf.str().c_str());


  int data_id = 0;

  for (int i = 0; i < label_info.size(); ++i){
    LabelStructure* ls = label_info[i];

    infoout << "LABEL : " << ls->label << endl;
    infoout << "FATHER : " << ((NULL == ls->father)? -1 : ls->father->label) << endl;
    infoout << "COMMEN_FEATURE(" << ls->common_feature.size() << ") : ";
    for (FEATURE_SET::iterator ite = ls->common_feature.begin();
	 ls->common_feature.end() != ite;
	 ++ite){
      infoout << (*ite) << " ";
    }
    infoout << endl << endl << endl;

    if (ls->nson != 0){
      continue;
    }

    int ndata = MIN_DATA + (rand() % (MAX_DATA - MIN_DATA));
    for (int j = 0; j < ndata; ++j){

      if (0 != data_id){
	lout << endl;
	fout << endl;
      }

      lout << ls->label;
      fout << (data_id++);
      
      int nf = rand() % MAX_FEATURE_BOUND;
      FEATURE_SET data;
      for (FEATURE_SET::iterator it = ls->common_feature.begin();
	   ls->common_feature.end() != it;
	   ++it){
	data.insert((*it));
      }
      for (int k = 0; k < nf; ++k){
	int f = rand() % (MAX_FEATURE_BOUND + max_feature);
	data.insert(f);
      }
      for (FEATURE_SET::iterator it = data.begin();
	   data.end() != it;
	   ++it){
	fout << " " << (*it) << " 1.0";
      }
    }

  }

  fout.close();
  lout.close();
  infoout.close();
}

int main(int argc, char* argv[]){

  srand((unsigned int)time(0));

  if (argc != 3){
    //    Print_Help();
    return -1;
  }

  max_level = atoi(argv[1]);
  result_file = argv[2];

  Gen_Data();

  return 0;
}
