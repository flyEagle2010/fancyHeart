//XRole
#include "XRole.h"
static XRole* instance;

XRole* XRole::record(Value v){ 
	if(instance==NULL) instance=new XRole();
	if(instance->doc.IsNull()){
		std::string fullPath=FileUtils::getInstance()->fullPathForFilename("XRole.json");
		std::string str=FileUtils::getInstance()->getStringFromFile(fullPath);
		instance->doc.Parse<0>(str.c_str());
		if(instance->doc.HasParseError()){
			log("GetParseError %s",instance->doc.GetParseError());
		}
	}
	instance->v=v;
	return instance;
}
int XRole::getId(){
	return doc[v.asString().c_str()]["id"].GetInt();
}
std::string XRole::getName(){
	return doc[v.asString().c_str()]["name"].GetString();
}
std::string XRole::getDes(){
	return doc[v.asString().c_str()]["des"].GetString();
}
std::string XRole::getSlogan(){
	return doc[v.asString().c_str()]["slogan"].GetString();
}
int XRole::getPos(){
	return doc[v.asString().c_str()]["pos"].GetInt();
}
std::string XRole::getAvatar(){
	return doc[v.asString().c_str()]["avatar"].GetString();
}
std::string XRole::getCard(){
	return doc[v.asString().c_str()]["card"].GetString();
}
std::string XRole::getIcon(){
	return doc[v.asString().c_str()]["icon"].GetString();
}
std::string XRole::getPotrait(){
	return doc[v.asString().c_str()]["potrait"].GetString();
}
int XRole::getCommonSkill(){
	return doc[v.asString().c_str()]["commonSkill"].GetInt();
}
int XRole::getSkill0(){
	return doc[v.asString().c_str()]["skill0"].GetInt();
}
int XRole::getSkill1(){
	return doc[v.asString().c_str()]["skill1"].GetInt();
}
int XRole::getSkill2(){
	return doc[v.asString().c_str()]["skill2"].GetInt();
}
int XRole::getSkill3(){
	return doc[v.asString().c_str()]["skill3"].GetInt();
}
int XRole::getSkill4(){
	return doc[v.asString().c_str()]["skill4"].GetInt();
}
int XRole::getSkill5(){
	return doc[v.asString().c_str()]["skill5"].GetInt();
}
int XRole::getSkill6(){
	return doc[v.asString().c_str()]["skill6"].GetInt();
}
int XRole::getSkill7(){
	return doc[v.asString().c_str()]["skill7"].GetInt();
}
int XRole::getCalled(){
	return doc[v.asString().c_str()]["called"].GetInt();
}
int XRole::getCalledNum(){
	return doc[v.asString().c_str()]["calledNum"].GetInt();
}
int XRole::getStar(){
	return doc[v.asString().c_str()]["star"].GetInt();
}
double XRole::getLockGrid(){
	return doc[v.asString().c_str()]["lockGrid"].GetDouble();
}
int XRole::getPropId(){
	return doc[v.asString().c_str()]["propId"].GetInt();
}
bool XRole::getIsRole(){
	return doc[v.asString().c_str()]["isRole"].GetInt();
}
int XRole::getMutationNum(){
	return doc[v.asString().c_str()]["mutationNum"].GetInt();
}
int XRole::getProfessionSign(){
	return doc[v.asString().c_str()]["professionSign"].GetInt();
}
bool XRole::getIsOnAir(){
	return doc[v.asString().c_str()]["isOnAir"].GetInt();
}
