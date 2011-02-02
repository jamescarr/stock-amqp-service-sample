package org.jamescarr.stocks;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


public interface StockService {
	@GET
	@Path("/quote/{ticker}")
	public String getQuote(@PathParam("ticker") String ticker);
}
