//XSkill
#include "XSkill.h"
static XSkill* instance;

XSkill* XSkill::record(Value v){ 
	if(instance==NULL) instance=new XSkill();
	if(instance->doc.IsNull()){
		std::string fullPath=FileUtils::getInstance()->fullPathForFilename("XSkill.json");
		std::string str=FileUtils::getInstance()->getStringFromFile(fullPath);
		instance->doc.Parse<0>(str.c_str());
		if(instance->doc.HasParseError()){
			log("GetParseError %s",instance->doc.GetParseError());
		}
	}
	instance->v=v;
	return instance;
}
int XSkill::getId(){
	return doc[v.asString().c_str()]["id"].GetInt();
}
int XSkill::getName(){
	return doc[v.asString().c_str()]["name"].GetInt();
}
int XSkill::getDesc(){
	return doc[v.asString().c_str()]["desc"].GetInt();
}
int XSkill::getType(){
	return doc[v.asString().c_str()]["type"].GetInt();
}
int XSkill::getTriggerTyp(){
	return doc[v.asString().c_str()]["triggerTyp"].GetInt();
}
int XSkill::getTriggerRate(){
	return doc[v.asString().c_str()]["triggerRate"].GetInt();
}
int XSkill::getTriggerParam(){
	return doc[v.asString().c_str()]["triggerParam"].GetInt();
}
int XSkill::getHurtType(){
	return doc[v.asString().c_str()]["hurtType"].GetInt();
}
int XSkill::getLeadNum(){
	return doc[v.asString().c_str()]["leadNum"].GetInt();
}
int XSkill::getLeadGap(){
	return doc[v.asString().c_str()]["leadGap"].GetInt();
}
int XSkill::getRangeType(){
	return doc[v.asString().c_str()]["rangeType"].GetInt();
}
int XSkill::getRangeParam1(){
	return doc[v.asString().c_str()]["rangeParam1"].GetInt();
}
int XSkill::getRangeParam2(){
	return doc[v.asString().c_str()]["rangeParam2"].GetInt();
}
int XSkill::getRangeParam3(){
	return doc[v.asString().c_str()]["rangeParam3"].GetInt();
}
int XSkill::getRangeParam4(){
	return doc[v.asString().c_str()]["rangeParam4"].GetInt();
}
int XSkill::getRangeParam5(){
	return doc[v.asString().c_str()]["rangeParam5"].GetInt();
}
int XSkill::getSelectType(){
	return doc[v.asString().c_str()]["selectType"].GetInt();
}
int XSkill::getBuildTime(){
	return doc[v.asString().c_str()]["buildTime"].GetInt();
}
int XSkill::getBuildBaseRate(){
	return doc[v.asString().c_str()]["buildBaseRate"].GetInt();
}
int XSkill::getBuildBaseNum(){
	return doc[v.asString().c_str()]["buildBaseNum"].GetInt();
}
int XSkill::getBuildStepRate(){
	return doc[v.asString().c_str()]["buildStepRate"].GetInt();
}
int XSkill::getBuildStepNum(){
	return doc[v.asString().c_str()]["buildStepNum"].GetInt();
}
int XSkill::getEffectType(){
	return doc[v.asString().c_str()]["effectType"].GetInt();
}
int XSkill::getEffectParam1(){
	return doc[v.asString().c_str()]["effectParam1"].GetInt();
}
int XSkill::getEffectParam2(){
	return doc[v.asString().c_str()]["effectParam2"].GetInt();
}
int XSkill::getEffectParam3(){
	return doc[v.asString().c_str()]["effectParam3"].GetInt();
}
int XSkill::getEffectParam4(){
	return doc[v.asString().c_str()]["effectParam4"].GetInt();
}
int XSkill::getBuffID(){
	return doc[v.asString().c_str()]["buffID"].GetInt();
}
int XSkill::getBuffRate(){
	return doc[v.asString().c_str()]["buffRate"].GetInt();
}
int XSkill::getSpellTime(){
	return doc[v.asString().c_str()]["spellTime"].GetInt();
}
int XSkill::getEffectID(){
	return doc[v.asString().c_str()]["effectID"].GetInt();
}
bool XSkill::getIsAirFirst(){
	return doc[v.asString().c_str()]["isAirFirst"].GetInt();
}
bool XSkill::getIsHitOnAir(){
	return doc[v.asString().c_str()]["isHitOnAir"].GetInt();
}
