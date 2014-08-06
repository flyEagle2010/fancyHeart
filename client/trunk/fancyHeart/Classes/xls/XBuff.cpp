//XBuff
#include "XBuff.h"
static XBuff* instance;

XBuff* XBuff::record(Value v){ 
	if(instance==NULL) instance=new XBuff();
	if(instance->doc.IsNull()){
		std::string fullPath=FileUtils::getInstance()->fullPathForFilename("XBuff.json");
		std::string str=FileUtils::getInstance()->getStringFromFile(fullPath);
		instance->doc.Parse<0>(str.c_str());
		if(instance->doc.HasParseError()){
			log("GetParseError %s",instance->doc.GetParseError());
		}
	}
	instance->v=v;
	return instance;
}
int XBuff::getId(){
	return doc[v.asString().c_str()]["id"].GetInt();
}
int XBuff::getType(){
	return doc[v.asString().c_str()]["type"].GetInt();
}
int XBuff::getDuration(){
	return doc[v.asString().c_str()]["duration"].GetInt();
}
bool XBuff::getIsOffSave(){
	return doc[v.asString().c_str()]["isOffSave"].GetInt();
}
bool XBuff::getIsOffTimer(){
	return doc[v.asString().c_str()]["isOffTimer"].GetInt();
}
int XBuff::getDps(){
	return doc[v.asString().c_str()]["dps"].GetInt();
}
int XBuff::getHps(){
	return doc[v.asString().c_str()]["hps"].GetInt();
}
bool XBuff::getIsBreak(){
	return doc[v.asString().c_str()]["isBreak"].GetInt();
}
bool XBuff::getIsMove(){
	return doc[v.asString().c_str()]["isMove"].GetInt();
}
bool XBuff::getIsUseSkill(){
	return doc[v.asString().c_str()]["isUseSkill"].GetInt();
}
bool XBuff::getIsHeal(){
	return doc[v.asString().c_str()]["isHeal"].GetInt();
}
bool XBuff::getIsPhisicPass(){
	return doc[v.asString().c_str()]["isPhisicPass"].GetInt();
}
bool XBuff::getIsMagicPass(){
	return doc[v.asString().c_str()]["isMagicPass"].GetInt();
}
int XBuff::getAtc(){
	return doc[v.asString().c_str()]["atc"].GetInt();
}
int XBuff::getDef(){
	return doc[v.asString().c_str()]["def"].GetInt();
}
int XBuff::getMdef(){
	return doc[v.asString().c_str()]["mdef"].GetInt();
}
int XBuff::getHit(){
	return doc[v.asString().c_str()]["hit"].GetInt();
}
int XBuff::getMiss(){
	return doc[v.asString().c_str()]["miss"].GetInt();
}
int XBuff::getGroupID(){
	return doc[v.asString().c_str()]["groupID"].GetInt();
}
int XBuff::getLevel(){
	return doc[v.asString().c_str()]["level"].GetInt();
}
