//XWord
#include "XWord.h"
static XWord* instance;

XWord* XWord::record(Value v){ 
	if(instance==NULL) instance=new XWord();
	if(instance->doc.IsNull()){
		std::string fullPath=FileUtils::getInstance()->fullPathForFilename("XWord.json");
		std::string str=FileUtils::getInstance()->getStringFromFile(fullPath);
		instance->doc.Parse<0>(str.c_str());
		if(instance->doc.HasParseError()){
			log("GetParseError %s",instance->doc.GetParseError());
		}
	}
	instance->v=v;
	return instance;
}
int XWord::getId(){
	return doc[v.asString().c_str()]["id"].GetInt();
}
