package br.ufrn.imd.impromptu.actions;

import io.javalin.Context;

public class IndexController {

	public static void index(Context ctx) {
		ctx.result("Here I am");
	}
	
}
