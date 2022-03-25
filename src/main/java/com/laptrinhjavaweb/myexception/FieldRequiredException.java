package com.laptrinhjavaweb.myexception;

public class FieldRequiredException extends RuntimeException{// nếu extends Exception thì ở hàm check ta phải khai báo thêm 1 cái throws 
	public FieldRequiredException(String errMessage) {
		super(errMessage);
	}
}

// thừa kế exception thì sẽ là checked exception
// tu xay exception thi nên nhắm dc là ta build checked hay unchecked exception