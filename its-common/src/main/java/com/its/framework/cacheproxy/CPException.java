package com.its.framework.cacheproxy;

public class CPException extends RuntimeException {
	private static final long serialVersionUID = 1276770795767504278L;

	public CPException() {
	}

	public CPException(String message) {
		super(message);
	}

	public CPException(String message, Throwable cause) {
		super(message, cause);
	}

	public CPException(Throwable cause) {
		super(cause);
	}
}
