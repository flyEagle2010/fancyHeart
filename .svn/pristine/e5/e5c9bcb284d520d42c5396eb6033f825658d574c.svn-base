//XGate
#include "XGate.h"
static XGate* instance;

XGate* XGate::record(Value v){ 
	if(instance==NULL) instance=new XGate();
	if(instance->doc.IsNull()){
		std::string fullPath=FileUtils::getInstance()->fullPathForFilename("XGate.json");
		std::string str=FileUtils::getInstance()->getStringFromFile(fullPath);
		instance->doc.Parse<0>(str.c_str());
		if(instance->doc.HasParseError()){
			log("GetParseError %s",instance->doc.GetParseError());
		}
	}
	instance->v=v;
	return instance;
}
int XGate::getId(){
	return doc[v.asString().c_str()]["id"].GetInt();
}
std::string XGate::getName(){
	return doc[v.asString().c_str()]["name"].GetString();
}
std::string XGate::getDesc(){
	return doc[v.asString().c_str()]["desc"].GetString();
}
std::string XGate::getDesc2(){
	return doc[v.asString().c_str()]["desc2"].GetString();
}
int XGate::getType(){
	return doc[v.asString().c_str()]["type"].GetInt();
}
int XGate::getAvatorId(){
	return doc[v.asString().c_str()]["avatorId"].GetInt();
}
int XGate::getMapId(){
	return doc[v.asString().c_str()]["mapId"].GetInt();
}
int XGate::getParentId(){
	return doc[v.asString().c_str()]["parentId"].GetInt();
}
