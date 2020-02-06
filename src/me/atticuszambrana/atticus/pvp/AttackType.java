package me.atticuszambrana.atticus.pvp;

public enum AttackType {
	
	HIT("hit", 2),
	SLAP("slapped", 2),
	BITE("bit", 4);
	
	String trigger;
	String verb;
	int attackPoints;
	
	AttackType(String trigger, String verb, int attackPoints) {
		this.trigger = trigger;
		this.verb = verb;
		this.attackPoints = attackPoints;
	}
	
	// Use this one
	AttackType(String verb, int attackPoints) {
		this.verb = verb;
		this.attackPoints = attackPoints;
	}
	
	public String getTrigger() {
		return trigger;
	}
	
	public String getVerb() {
		return verb;
	}
	
	public int getAttackPoints() {
		return attackPoints;
	}
}
