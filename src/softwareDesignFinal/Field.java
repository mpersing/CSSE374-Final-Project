package softwareDesignFinal;

public class Field {
	private String name, type, accessMod;
	private Boolean statick;
	
	public Field(String name, String type, String accessMod, Boolean statick) {
		super();
		this.name = name;
		this.type = type;
		this.accessMod = accessMod;
		this.statick = statick;
	}

	// TODO: Add static underlining things
	public String toString()
	{
		String builder = "";
		if(accessMod.equals("private")) {
			builder += "- ";
		} else if(accessMod.equals("public")) {
			builder += "+ ";
		} else if(accessMod.equals("protected")) {
			builder += "# ";
		}
		builder += name;
		builder += " : ";
		builder += type;
		return builder;
	}
}
