package com.task.constants;

import lombok.Getter;

@Getter
public enum Status {

	//OPEN("open"), WIP("wip"), DONE("done"), CANCELLED("cancelled");
	open, wip, done, cancelled;

	/*
	 * String value;
	 * 
	 * Status(String value) { this.value = value; }
	 * 
	 * public String getValue() { return this.value; }
	 */
}
