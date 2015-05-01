package com.moontrader.util;

import java.util.List;

public class ListFilter implements Filter {

	@Override
	public boolean filter(Object obj) {
		return obj instanceof List;
	}

}
