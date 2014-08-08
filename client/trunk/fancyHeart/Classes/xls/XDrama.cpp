//XDrama
#include "XDrama.h"
static XDrama* instance;

XDrama* XDrama::record(Value v){ 
	if(instance==NULL) instance=new XDrama();
	if(instance->doc.IsNull()){
		std::string fullPath=FileUtils::getInstance()->fullPathForFilename("XDrama.json");
		std::string str=FileUtils::getInstance()->getStringFromFile(fullPath);
		instance->doc.Parse<0>(str.c_str());
		if(instance->doc.HasParseError()){
			log("GetParseError %s",instance->doc.GetParseError());
		}
	}
	instance->v=v;
	return instance;
}
int XDrama::getId(){
	return doc[v.asString().c_str()]["id"].GetInt();
}
int XDrama::getType(){
	return doc[v.asString().c_str()]["type"].GetInt();
}
int XDrama::getStartTime(){
	return doc[v.asString().c_str()]["startTime"].GetInt();
}
int XDrama::getRoleId(){
	return doc[v.asString().c_str()]["roleId"].GetInt();
}
int XDrama::getDirection(){
	return doc[v.asString().c_str()]["direction"].GetInt();
}
int XDrama::getFaceTo(){
	return doc[v.asString().c_str()]["faceTo"].GetInt();
}
std::string XDrama::getPos(){
	return doc[v.asString().c_str()]["pos"].GetString();
}
int XDrama::getIsStop(){
	return doc[v.asString().c_str()]["isStop"].GetInt();
}
int XDrama::getMusicId(){
	return doc[v.asString().c_str()]["musicId"].GetInt();
}
std::string XDrama::getTargetPos(){
	return doc[v.asString().c_str()]["targetPos"].GetString();
}
int XDrama::getMoveTime(){
	return doc[v.asString().c_str()]["moveTime"].GetInt();
}
std::string XDrama::getAction(){
	return doc[v.asString().c_str()]["action"].GetString();
}
std::string XDrama::getTalk(){
	return doc[v.asString().c_str()]["talk"].GetString();
}
std::string XDrama::getEffectName(){
	return doc[v.asString().c_str()]["effectName"].GetString();
}
int XDrama::getTimes(){
	return doc[v.asString().c_str()]["times"].GetInt();
}
int XDrama::getIsSkill(){
	return doc[v.asString().c_str()]["isSkill"].GetInt();
}
std::string XDrama::getSkillPos(){
	return doc[v.asString().c_str()]["skillPos"].GetString();
}
int XDrama::getPart(){
	return doc[v.asString().c_str()]["part"].GetInt();
}
std::string XDrama::getSoundName(){
	return doc[v.asString().c_str()]["soundName"].GetString();
}
int XDrama::getIsFadeIn(){
	return doc[v.asString().c_str()]["isFadeIn"].GetInt();
}
std::string XDrama::getTargetName(){
	return doc[v.asString().c_str()]["targetName"].GetString();
}
