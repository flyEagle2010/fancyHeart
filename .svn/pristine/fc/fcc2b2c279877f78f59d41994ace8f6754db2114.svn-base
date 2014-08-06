//XCraft
#include "XCraft.h"
static XCraft* instance;

XCraft* XCraft::record(Value v){ 
	if(instance==NULL) instance=new XCraft();
	if(instance->doc.IsNull()){
		std::string fullPath=FileUtils::getInstance()->fullPathForFilename("XCraft.json");
		std::string str=FileUtils::getInstance()->getStringFromFile(fullPath);
		instance->doc.Parse<0>(str.c_str());
		if(instance->doc.HasParseError()){
			log("GetParseError %s",instance->doc.GetParseError());
		}
	}
	instance->v=v;
	return instance;
}
int XCraft::getType(){
	return doc[v.asString().c_str()]["type"].GetInt();
}
int XCraft::getCost(){
	return doc[v.asString().c_str()]["cost"].GetInt();
}
int XCraft::getOutItemID(){
	return doc[v.asString().c_str()]["outItemID"].GetInt();
}
int XCraft::getItem1(){
	return doc[v.asString().c_str()]["item1"].GetInt();
}
int XCraft::getNum1(){
	return doc[v.asString().c_str()]["num1"].GetInt();
}
int XCraft::getItem2(){
	return doc[v.asString().c_str()]["item2"].GetInt();
}
int XCraft::getNum2(){
	return doc[v.asString().c_str()]["num2"].GetInt();
}
int XCraft::getItem3(){
	return doc[v.asString().c_str()]["item3"].GetInt();
}
int XCraft::getNum3(){
	return doc[v.asString().c_str()]["num3"].GetInt();
}
int XCraft::getItem4(){
	return doc[v.asString().c_str()]["item4"].GetInt();
}
int XCraft::getNum4(){
	return doc[v.asString().c_str()]["num4"].GetInt();
}
int XCraft::getItem5(){
	return doc[v.asString().c_str()]["item5"].GetInt();
}
int XCraft::getNum5(){
	return doc[v.asString().c_str()]["num5"].GetInt();
}
