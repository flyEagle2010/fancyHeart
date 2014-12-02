//XItem
#include "XItem.h"
static XItem* instance;

XItem* XItem::record(Value v){ 
	if(instance==NULL) instance=new XItem();
	if(instance->doc.IsNull()){
		std::string fullPath=FileUtils::getInstance()->fullPathForFilename("XItem.json");
		std::string str=FileUtils::getInstance()->getStringFromFile(fullPath);
		instance->doc.Parse<0>(str.c_str());
		if(instance->doc.HasParseError()){
			log("GetParseError %s",instance->doc.GetParseError());
		}
	}
	instance->v=v;
	return instance;
}
int XItem::getId(){
	return doc[v.asString().c_str()]["id"].GetInt();
}
std::string XItem::getName(){
	return doc[v.asString().c_str()]["name"].GetString();
}
std::string XItem::getDes(){
	return doc[v.asString().c_str()]["des"].GetString();
}
int XItem::getRate(){
	return doc[v.asString().c_str()]["rate"].GetInt();
}
int XItem::getType(){
	return doc[v.asString().c_str()]["type"].GetInt();
}
int XItem::getMaxLv(){
	return doc[v.asString().c_str()]["maxLv"].GetInt();
}
int XItem::getEventID(){
	return doc[v.asString().c_str()]["eventID"].GetInt();
}
int XItem::getPrice(){
	return doc[v.asString().c_str()]["price"].GetInt();
}
int XItem::getCrh(){
	return doc[v.asString().c_str()]["crh"].GetInt();
}
int XItem::getMiss(){
	return doc[v.asString().c_str()]["miss"].GetInt();
}
int XItem::getDef(){
	return doc[v.asString().c_str()]["def"].GetInt();
}
int XItem::getMDef(){
	return doc[v.asString().c_str()]["mDef"].GetInt();
}
int XItem::getAtk(){
	return doc[v.asString().c_str()]["atk"].GetInt();
}
int XItem::getHp(){
	return doc[v.asString().c_str()]["hp"].GetInt();
}
int XItem::getHeal(){
	return doc[v.asString().c_str()]["heal"].GetInt();
}
int XItem::getIcon(){
	return doc[v.asString().c_str()]["icon"].GetInt();
}
