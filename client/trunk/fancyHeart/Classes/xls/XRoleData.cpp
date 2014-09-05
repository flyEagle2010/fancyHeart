//XRoleData
#include "XRoleData.h"
static XRoleData* instance;

XRoleData* XRoleData::record(Value v){ 
	if(instance==NULL) instance=new XRoleData();
	if(instance->doc.IsNull()){
		std::string fullPath=FileUtils::getInstance()->fullPathForFilename("XRoleData.json");
		std::string str=FileUtils::getInstance()->getStringFromFile(fullPath);
		instance->doc.Parse<0>(str.c_str());
		if(instance->doc.HasParseError()){
			log("GetParseError %s",instance->doc.GetParseError());
		}
	}
	instance->v=v;
	return instance;
}
std::string XRoleData::getId(){
	return doc[v.asString().c_str()]["id"].GetString();
}
int XRoleData::getHp(){
	return doc[v.asString().c_str()]["hp"].GetInt();
}
int XRoleData::getHpRate(){
	return doc[v.asString().c_str()]["hpRate"].GetInt();
}
int XRoleData::getAtk(){
	return doc[v.asString().c_str()]["atk"].GetInt();
}
int XRoleData::getAtkRate(){
	return doc[v.asString().c_str()]["atkRate"].GetInt();
}
int XRoleData::getDf(){
	return doc[v.asString().c_str()]["df"].GetInt();
}
int XRoleData::getDfRate(){
	return doc[v.asString().c_str()]["dfRate"].GetInt();
}
int XRoleData::getMDf(){
	return doc[v.asString().c_str()]["mDf"].GetInt();
}
int XRoleData::getMDfRate(){
	return doc[v.asString().c_str()]["mDfRate"].GetInt();
}
int XRoleData::getMiss(){
	return doc[v.asString().c_str()]["miss"].GetInt();
}
int XRoleData::getCrh(){
	return doc[v.asString().c_str()]["crh"].GetInt();
}
int XRoleData::getHeal(){
	return doc[v.asString().c_str()]["heal"].GetInt();
}
int XRoleData::getEquipPos1(){
	return doc[v.asString().c_str()]["equipPos1"].GetInt();
}
int XRoleData::getEquipPos2(){
	return doc[v.asString().c_str()]["equipPos2"].GetInt();
}
int XRoleData::getEquipPos3(){
	return doc[v.asString().c_str()]["equipPos3"].GetInt();
}
int XRoleData::getEquipPos4(){
	return doc[v.asString().c_str()]["equipPos4"].GetInt();
}
int XRoleData::getEquipPos5(){
	return doc[v.asString().c_str()]["equipPos5"].GetInt();
}
int XRoleData::getEquipPos6(){
	return doc[v.asString().c_str()]["equipPos6"].GetInt();
}
