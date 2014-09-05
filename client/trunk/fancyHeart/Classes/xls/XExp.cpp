//XExp
#include "XExp.h"
static XExp* instance;

XExp* XExp::record(Value v){ 
	if(instance==NULL) instance=new XExp();
	if(instance->doc.IsNull()){
		std::string fullPath=FileUtils::getInstance()->fullPathForFilename("XExp.json");
		std::string str=FileUtils::getInstance()->getStringFromFile(fullPath);
		instance->doc.Parse<0>(str.c_str());
		if(instance->doc.HasParseError()){
			log("GetParseError %s",instance->doc.GetParseError());
		}
	}
	instance->v=v;
	return instance;
}
int XExp::getId(){
	return doc[v.asString().c_str()]["id"].GetInt();
}
int XExp::getExp(){
	return doc[v.asString().c_str()]["exp"].GetInt();
}
