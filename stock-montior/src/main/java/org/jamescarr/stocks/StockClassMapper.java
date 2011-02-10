package org.jamescarr.stocks;

import java.util.HashMap;

import org.jamescarr.stocks.complex.StockWatchCriteria;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;

public class StockClassMapper extends DefaultClassMapper {
	public StockClassMapper(){
		HashMap<String, Class<?>> map = new HashMap<String, Class<?>>();
		map.put("stock-criteria", StockWatchCriteria.class);
		setIdClassMapping(map);
	}
	@Override
	public String getClassIdFieldName() {
		return "type";
	}

}
