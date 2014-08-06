//XRoleStar
#include "XRoleStar.h"
static XRoleStar* instance;

XRoleStar* XRoleStar::record(Value v){ 
	if(instance==NULL) instance=new XRoleStar();
	if(instance->doc.IsNull()){
		std::string fullPath=FileUtils::getInstance()->fullPathForFilename("XRoleStar.json");
		std::string str=FileUtils::getInstance()->getStringFromFile(fullPath);
		instance->doc.Parse<0>(str.c_str());
		if(instance->doc.HasParseError()){
			log("GetParseError %s",instance->doc.GetParseError());
		}
	}
	instance->v=v;
	return instance;
}
std::string XRoleStar::getId(){
	return doc[v.asString().c_str()]["id"].GetString();
}
int XRoleStar::getItemNum(){
	return doc[v.asString().c_str()]["itemNum"].GetInt();
}
int XRoleStar::getCost(){
	return doc[v.asString().c_str()]["cost"].GetInt();
}
int XRoleStar::getHpRate(){
	return doc[v.asString().c_str()]["hpRate"].GetInt();
}
int XRoleStar::getAttackRate(){
	return doc[v.asString().c_str()]["attackRate"].GetInt();
}
int XRoleStar::getDfRate(){
	return doc[v.asString().c_str()]["dfRate"].GetInt();
}
int XRoleStar::getMDfRate(){
	return doc[v.asString().c_str()]["mDfRate"].GetInt();
}
