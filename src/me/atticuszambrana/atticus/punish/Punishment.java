package me.atticuszambrana.atticus.punish;

import java.awt.Color;

public class Punishment {
	
	private int punishId;
	
	private String moderatorId;
	private String targetId;
	private String reason;
	private long timestamp;
	private PunishmentType type;
	
	public Punishment(int punishId, String moderatorId, String targetId, String reason, long timestamp, PunishmentType type) {
		this.punishId = punishId;
		this.moderatorId = moderatorId;
		this.targetId = targetId;
		this.reason = reason;
		this.timestamp = timestamp;
		this.type = type;
	}
	
	public String getModId() {
		return moderatorId;
	}
	
	public String getTargetId() {
		return targetId;
	}
	
	public String getReason() {
		return reason;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public PunishmentType getType() {
		return type;
	}
	
	public int getID() {
		return punishId;
	}
	
	public enum PunishmentType {
		KICK("Kick", Color.BLUE),
		WARNING("Warning", Color.RED),
		BAN("Ban", Color.PINK),
		MUTE("Mute", Color.ORANGE);
		
		private String name;
		private Color color;
		
		PunishmentType(String name, Color color) {
			this.name = name;
			this.color = color;
		}
		
		public String getName() {
			return name;
		}
		
		public Color getColor() {
			return color;
		}
	}
}
