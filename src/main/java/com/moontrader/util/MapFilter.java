package com.moontrader.util;

import java.util.Map;

public class MapFilter implements Filter {

	@Override
	public boolean filter(Object obj) {
		return obj instanceof Map;
	}

}
