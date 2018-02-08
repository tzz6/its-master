package com.its.framework.serialize;

public class SerializeException extends RuntimeException {
	private static final long serialVersionUID = 6741908960084368231L;

	public SerializeException() {
	}

	public SerializeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SerializeException(String message) {
		super(message);
	}

	public SerializeException(Throwable cause) {
		super(cause);
	}
}
