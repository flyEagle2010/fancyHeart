/**
 * 
 */
package com.doteplay.editor.data.buff;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.dom4j.Element;

import com.doteplay.editor.common.BaseData;
import com.doteplay.editor.common.DataManager;
import com.doteplay.editor.util.Util;
import com.doteyplay.game.CommonConstants;
import com.doteyplay.game.config.GameConfig;
import com.doteyplay.game.constants.ActivateEvent;
import com.doteyplay.game.constants.DamageType;
import com.doteyplay.game.constants.skill.SkillAmmoType;
import com.doteyplay.game.constants.skill.SkillEffectRange;
import com.doteyplay.game.constants.skill.SkillTargetSelectType;
import com.doteyplay.game.gamedata.data.buff.BuffGameData;
import com.doteyplay.game.gamedata.data.skill.SkillGameData;

public class BuffData extends BaseData<BuffGameData>
{
	public BuffData()
	{
		super(new BuffGameData());
		type = "buff";
		dataGroup = DataManager.getDataGroup(type);
	}

}
