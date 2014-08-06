//XDrop
#include "XDrop.h"
static XDrop* instance;

XDrop* XDrop::record(Value v){ 
	if(instance==NULL) instance=new XDrop();
	if(instance->doc.IsNull()){
		std::string fullPath=FileUtils::getInstance()->fullPathForFilename("XDrop.json");
		std::string str=FileUtils::getInstance()->getStringFromFile(fullPath);
		instance->doc.Parse<0>(str.c_str());
		if(instance->doc.HasParseError()){
			log("GetParseError %s",instance->doc.GetParseError());
		}
	}
	instance->v=v;
	return instance;
}
int XDrop::getItemID(){
	return doc[v.asString().c_str()]["itemID"].GetInt();
}
int XDrop::getNum(){
	return doc[v.asString().c_str()]["num"].GetInt();
}
int XDrop::getRate(){
	return doc[v.asString().c_str()]["rate"].GetInt();
}
