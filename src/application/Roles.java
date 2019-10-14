package application;

public enum Roles {
	TOP("top"), JUNGLE("jungle"), MID("mid"), BOT("bot"), SUPPORT("support"), ALL("all");
	
	private String label;

	private Roles(String label) {
		this.label = label;
	}
	
	public String toString() {
		return label;
	}
}
