package utils;

public enum Code {
	OK(200), NOT_FOUND(404), INTERNAL_SERVER_ERROR(500);

	private final int code;

	Code(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
