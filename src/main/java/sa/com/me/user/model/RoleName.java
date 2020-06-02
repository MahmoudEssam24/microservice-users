package sa.com.me.user.model;

public enum RoleName {

	ROLE_CONSUMER("ROLE_CONSUMER"), ROLE_ADMIN("ROLE_ADMIN");
	private String value;

	private RoleName(String value) {
		this.setValue(value);
	}

	public String getValue() {
		return value;
	}

	void setValue(String value) {
		this.value = value;
	}
}
