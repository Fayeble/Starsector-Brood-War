package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.WeaponAPI.WeaponType;
import com.fs.starfarer.api.util.IntervalUtil;

public class terran_RegenMissiles extends BaseHullMod {

	public static String RM_DATA_KEY = "terran_reload_data_key";
	public static final float DAMAGE_REDUCTION = 25f;
	public static final float MISSILE_MAX_SPEED = 100f;
	
	public static class RegenMissileData {
		IntervalUtil interval = new IntervalUtil(5f, 10f);
	}
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getMissileWeaponDamageMult().modifyMult("terran_RegenMissiles", 1 - DAMAGE_REDUCTION * 0.01f);
		stats.getMissileMaxSpeedBonus().modifyMult("terran_RegenMissiles", 1 + MISSILE_MAX_SPEED * 0.01f);
	}
		
	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int) DAMAGE_REDUCTION + "%";
		if (index == 1) return "" + (int) MISSILE_MAX_SPEED + "%";
		return null;
	}
	
	@Override
	public void advanceInCombat(ShipAPI ship, float amount) {
		super.advanceInCombat(ship, amount);

		if (!ship.isAlive()) return;
		
		CombatEngineAPI engine = Global.getCombatEngine();
		
		String key = RM_DATA_KEY + "_" + ship.getId();
		RegenMissileData data = (RegenMissileData) engine.getCustomData().get(key);
		if (data == null) {
			data = new RegenMissileData();
			engine.getCustomData().put(key, data);
		}
		
		data.interval.advance(amount);
		if (data.interval.intervalElapsed()) {
			for (WeaponAPI w : ship.getAllWeapons()) {
				if (w.getType() != WeaponType.MISSILE) continue;
				
				if (w.usesAmmo() && w.getAmmo() < w.getMaxAmmo()) {
					w.setAmmo(w.getMaxAmmo());
				}
			}
		}
		
	}
	
}











