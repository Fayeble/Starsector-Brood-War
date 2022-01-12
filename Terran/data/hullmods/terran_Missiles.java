package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class terran_Missiles extends BaseHullMod {

	public static final float DAMAGE_REDUCTION = 25f;
	public static final float MISSILE_MAX_SPEED = 100f;
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getMissileWeaponDamageMult().modifyMult("terran_Missiles", 1 - DAMAGE_REDUCTION * 0.01f);
		stats.getMissileMaxSpeedBonus().modifyMult("terran_Missiles", 1 + MISSILE_MAX_SPEED * 0.01f);
	}
		
	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int) DAMAGE_REDUCTION + "%";
		if (index == 1) return "" + (int) MISSILE_MAX_SPEED + "%";
		return null;
	}
}











