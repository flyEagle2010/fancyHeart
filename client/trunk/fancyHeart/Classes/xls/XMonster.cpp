//XMonster
#include "XMonster.h"
static XMonster* instance;

XMonster* XMonster::record(Value v){ 
	if(instance==NULL) instance=new XMonster();
	if(instance->doc.IsNull()){
		std::string fullPath=FileUtils::getInstance()->fullPathForFilename("XMonster.json");
		std::string str=FileUtils::getInstance()->getStringFromFile(fullPath);
		instance->doc.Parse<0>(str.c_str());
		if(instance->doc.HasParseError()){
			log("GetParseError %s",instance->doc.GetParseError());
		}
	}
	instance->v=v;
	return instance;
}
int XMonster::getId(){
	return doc[v.asString().c_str()]["id"].GetInt();
}
int XMonster::getPreID(){
	return doc[v.asString().c_str()]["preID"].GetInt();
}
int XMonster::getAfterID(){
	return doc[v.asString().c_str()]["afterID"].GetInt();
}
bool XMonster::getIsBoss(){
	return doc[v.asString().c_str()]["isBoss"].GetInt();
}
int XMonster::getMID1(){
	return doc[v.asString().c_str()]["mID1"].GetInt();
}
int XMonster::getMLv1(){
	return doc[v.asString().c_str()]["mLv1"].GetInt();
}
int XMonster::getMStar1(){
	return doc[v.asString().c_str()]["mStar1"].GetInt();
}
int XMonster::getMID2(){
	return doc[v.asString().c_str()]["mID2"].GetInt();
}
int XMonster::getMLv2(){
	return doc[v.asString().c_str()]["mLv2"].GetInt();
}
int XMonster::getMStar2(){
	return doc[v.asString().c_str()]["mStar2"].GetInt();
}
int XMonster::getMID3(){
	return doc[v.asString().c_str()]["mID3"].GetInt();
}
int XMonster::getMLv3(){
	return doc[v.asString().c_str()]["mLv3"].GetInt();
}
int XMonster::getMStar3(){
	return doc[v.asString().c_str()]["mStar3"].GetInt();
}
int XMonster::getMID4(){
	return doc[v.asString().c_str()]["mID4"].GetInt();
}
int XMonster::getMLv4(){
	return doc[v.asString().c_str()]["mLv4"].GetInt();
}
int XMonster::getMStar4(){
	return doc[v.asString().c_str()]["mStar4"].GetInt();
}
int XMonster::getMID5(){
	return doc[v.asString().c_str()]["mID5"].GetInt();
}
int XMonster::getMLv5(){
	return doc[v.asString().c_str()]["mLv5"].GetInt();
}
int XMonster::getMStar5(){
	return doc[v.asString().c_str()]["mStar5"].GetInt();
}
