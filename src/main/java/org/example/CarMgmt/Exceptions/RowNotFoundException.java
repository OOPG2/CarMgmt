package org.example.CarMgmt.Exceptions;

public class RowNotFoundException extends Exception {

	private static final long serialVersionUID = -8838486308079386864L;
	
	public RowNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
