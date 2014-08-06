package com.doteyplay.game.persistence.rule;

import com.doteyplay.core.dbcs.dbspace.DBSpace;
import com.doteyplay.core.dbcs.dbspace.DBZone;
import com.doteyplay.core.dbcs.dbspace.IDBZoneRule;

public abstract class AbstractSingleDBRule implements IDBZoneRule
{
	public DBZone[] getDBZone(String rulevalue)
	{
		DBZone[] dbzs = new DBZone[1];
		dbzs[0] = DBSpace.getDBZone(0);
		return dbzs;
	}
}
