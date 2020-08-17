package com.task.common.exception;

public class InvalidFIlter extends RuntimeException {
	private static final long serialVersionUID = -3669856272291407468L;

	public InvalidFIlter(String exception) {
		super(exception);
	}
}