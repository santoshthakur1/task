package com.task.common;

public class SearchNotSupported extends RuntimeException {
	private static final long serialVersionUID = -3669856572291407468L;

	public SearchNotSupported(String exception) {
		super(exception);
	}
}
