package br.ufrn.imd.impromptu.controllers;

import org.joor.Reflect;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import io.javalin.Context;

public class JUnit4Controller {
	
	public static void post(Context ctx) {
		System.out.println(ctx.body());
		String content = ctx.body();
		// TODO - Extract classNames in runtime with regular expressions
		String cName = "me.example.ThirdUnitTest";
		Class<?> classN = Reflect.compile(cName, content).create().get().getClass();
		
		
		JUnitCore junit = new JUnitCore();
		junit.addListener(new TextListener(System.out));
		Result result;
		result = junit.run(classN);
		System.out.println(result.getFailureCount());
		ctx.result("" + result.getFailureCount());
	}
	
}
