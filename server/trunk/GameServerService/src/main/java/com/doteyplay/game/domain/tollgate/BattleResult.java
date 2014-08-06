package com.doteyplay.game.domain.tollgate;

import java.util.HashMap;
import java.util.Map;

/**
 * @className:BattleResult.java
 * @classDescription:
 * @author:Tom.Zheng
 * @createTime:2014年7月8日 下午6:41:39
 */
public class BattleResult {
	
	public int gameCoin;
	
	
	public int star;
	
	public Map<Long,BattlePetResult> petMap = new HashMap<Long,BattlePetResult>();
	
	public BattleRoleResult battleRoleResult = new BattleRoleResult();
	
	public class BattleRoleResult{
		public int oldRoleLevel;
		
		public int newRoleLevel;
		
		public int oldRoleExp;
		
		public int newRoleExp;
		
		public void recordOldRole(int oldRoleLevel,int oldRoleExp){
			this.oldRoleLevel = oldRoleLevel;
			this.oldRoleExp = oldRoleExp;
		}
		
		public void recordNewRole(int newRoleLevel,int newRoleExp){
			this.newRoleLevel = newRoleLevel;
			this.newRoleExp = newRoleExp;
		}
		
		public int getLevelUp(){
			return newRoleLevel - oldRoleLevel;
		}
		
		public int getExpUp(){
			return newRoleExp - oldRoleExp;
		}
	}
	
	public class BattlePetResult{
		
		public long petId;
		
		public int oldPetLevel;
		
		public int newPetLevel;
		
		public int oldPetExp;
		
		public int newPetExp;
		
		public int getLevelUp(){
			return newPetLevel - oldPetLevel;
		}
		
		public int getExpUp(){
			return newPetExp - oldPetExp;
		}
		
	}
	
	public void recordOldPet(long petId,int oldPetLevel,int oldPetExp){
		BattlePetResult petResult = new BattlePetResult();
		petResult.petId = petId;
		petResult.oldPetLevel = oldPetLevel;
		petResult.oldPetExp = oldPetExp;
		petMap.put(petId, petResult);
	}
	
	public void recordNewPet(long petId,int newPetLevel,int newPetExp){
		BattlePetResult petResult = petMap.get(petId);
		petResult.newPetLevel = newPetLevel;
		petResult.newPetExp = newPetExp;
	}
	
	
	
}
