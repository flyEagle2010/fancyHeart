package com.doteyplay.game.persistence.rule;

import com.doteyplay.core.dbcs.dbspace.DBSpace;
import com.doteyplay.core.dbcs.dbspace.DBZone;

public class RoleIdRule extends AbstractSingleDBRule {
	public final static String RULE_NAME = "roleRule";

	@Override
	public String getName() {
		return RULE_NAME;
	}

	public DBZone[] getDBZone(String rulevalue) {
		DBZone[] dbzs = new DBZone[1];
		dbzs[0] = DBSpace.getDBZone(0);
		return dbzs;
	}
}
