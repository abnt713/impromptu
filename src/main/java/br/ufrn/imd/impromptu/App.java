package br.ufrn.imd.impromptu;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.path;

import java.util.function.Supplier;

import org.joor.Reflect;
import org.joor.ReflectException;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import br.ufrn.imd.impromptu.controllers.IndexController;
import br.ufrn.imd.impromptu.controllers.JUnit4Controller;
import br.ufrn.imd.impromptu.junit4.FirstUnitTest;
import br.ufrn.imd.impromptu.junit5.JUnit5Daemon;
import io.javalin.Javalin;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	runStringfiedClass();
		runJUnit4FromAPI();
		runJUnit5FromAPI();
    	runJavalin();
    }
    
	private static void runStringfiedClass() {
		try {
			Supplier<String> supplier = Reflect.compile(
	    		    "com.example.HelloWorld",
	    		    "package com.example;\n" +
	    		    "class HelloWorld implements java.util.function.Supplier<String> {\n" +
	    		    "    public String get() {\n" +
	    		    "        return \"Hello World!\";\n" +
	    		    "    }\n" +
	    		    "}\n").create().get();

			// Prints "Hello World!"
			System.out.println(supplier.get());
		}  catch (ReflectException rex) {
			System.out.println(rex.getMessage());
		}
    }
    
    private static void runJUnit4FromAPI() {
    	JUnitCore junit = new JUnitCore();
//		junit.addListener(new TextListener(System.out));
		Result result = junit.run(FirstUnitTest.class);
		System.out.println(result.getFailureCount());
    }
    
    private static void runJUnit5FromAPI() {
		JUnit5Daemon junit = new JUnit5Daemon();
		TestExecutionSummary summary = junit.runFromClass();
		System.out.println(summary.getTestsFailedCount());
	}
    
    private static void runJavalin() {
    	Javalin app = Javalin.create().start(8000);
        app.routes(() -> {
        	path("", () -> {
        		get(IndexController::index);
        	});
        	
        	path("junit4", () -> {
        		post(JUnit4Controller::post);
        	});
        	
        	path("users", () -> {
        		get(IndexController::index);
        	});
        });
    }
}
