package br.ufrn.imd.impromptu.actions;

import io.javalin.Context;
import io.javalin.Handler;

public class IndexAction implements Handler {

	@Override
	public void handle(Context ctx) throws Exception {
		ctx.result("Hello world (but actually there is more)");
	}
	
}
