package com.eastlaw.semicolon.error;

public class InvalidRequestException extends RuntimeException{
	public InvalidRequestException(String message){
		super(message);
	}
}
