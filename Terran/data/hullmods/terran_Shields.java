package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class terran_Shields extends BaseHullMod {

	public static final float SHIELD_BONUS_UNFOLD = 2000f;
	public static final float UPKEEP_PENALTY = 150f;
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getShieldUnfoldRateMult().modifyPercent(id, SHIELD_BONUS_UNFOLD);
		stats.getShieldUpkeepMult().modifyMult(id, 1f + UPKEEP_PENALTY * 0.01f);
	}

	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int) UPKEEP_PENALTY + "%";
		return null;
	}

	public boolean isApplicableToShip(ShipAPI ship) {
		return ship != null && ship.getShield() != null && !ship.getVariant().getHullMods().contains("acceleratedshieldemitter");
	}
	
	public String getUnapplicableReason(ShipAPI ship) {
		if (ship == null || ship.getShield() == null) {
			return "Ship has no shields";
		}

		if (ship.getVariant().getHullMods().contains("acceleratedshieldemitter")) {
			return "Incompatible with Accelerated Shields";
		}

		return null;
	}
}
