//XBattle
#include "XBattle.h"
static XBattle* instance;

XBattle* XBattle::record(Value v){ 
	if(instance==NULL) instance=new XBattle();
	if(instance->doc.IsNull()){
		std::string fullPath=FileUtils::getInstance()->fullPathForFilename("XBattle.json");
		std::string str=FileUtils::getInstance()->getStringFromFile(fullPath);
		instance->doc.Parse<0>(str.c_str());
		if(instance->doc.HasParseError()){
			log("GetParseError %s",instance->doc.GetParseError());
		}
	}
	instance->v=v;
	return instance;
}
int XBattle::getId(){
	return doc[v.asString().c_str()]["id"].GetInt();
}
int XBattle::getNameID(){
	return doc[v.asString().c_str()]["nameID"].GetInt();
}
int XBattle::getDesID(){
	return doc[v.asString().c_str()]["desID"].GetInt();
}
int XBattle::getMGroup1(){
	return doc[v.asString().c_str()]["mGroup1"].GetInt();
}
int XBattle::getMGroup2(){
	return doc[v.asString().c_str()]["mGroup2"].GetInt();
}
int XBattle::getMGroup3(){
	return doc[v.asString().c_str()]["mGroup3"].GetInt();
}
int XBattle::getMGroup4(){
	return doc[v.asString().c_str()]["mGroup4"].GetInt();
}
int XBattle::getMGroup5(){
	return doc[v.asString().c_str()]["mGroup5"].GetInt();
}
int XBattle::getCoin(){
	return doc[v.asString().c_str()]["coin"].GetInt();
}
int XBattle::getExp(){
	return doc[v.asString().c_str()]["exp"].GetInt();
}
int XBattle::getDropID(){
	return doc[v.asString().c_str()]["dropID"].GetInt();
}
int XBattle::getMapID(){
	return doc[v.asString().c_str()]["mapID"].GetInt();
}
