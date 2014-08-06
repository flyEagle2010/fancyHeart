//XNode
#include "XNode.h"
static XNode* instance;

XNode* XNode::record(Value v){ 
	if(instance==NULL) instance=new XNode();
	if(instance->doc.IsNull()){
		std::string fullPath=FileUtils::getInstance()->fullPathForFilename("XNode.json");
		std::string str=FileUtils::getInstance()->getStringFromFile(fullPath);
		instance->doc.Parse<0>(str.c_str());
		if(instance->doc.HasParseError()){
			log("GetParseError %s",instance->doc.GetParseError());
		}
	}
	instance->v=v;
	return instance;
}
std::string XNode::getId(){
	return doc[v.asString().c_str()]["id"].GetString();
}
int XNode::getGateID(){
	return doc[v.asString().c_str()]["gateID"].GetInt();
}
std::string XNode::getPos(){
	return doc[v.asString().c_str()]["pos"].GetString();
}
std::string XNode::getIcon(){
	return doc[v.asString().c_str()]["icon"].GetString();
}
int XNode::getType(){
	return doc[v.asString().c_str()]["type"].GetInt();
}
int XNode::getBattleID(){
	return doc[v.asString().c_str()]["battleID"].GetInt();
}
