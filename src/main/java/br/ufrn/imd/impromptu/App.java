package br.ufrn.imd.impromptu;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import br.ufrn.imd.impromptu.actions.IndexAction;
import br.ufrn.imd.impromptu.actions.JUnit4CompileAction;
import br.ufrn.imd.impromptu.actions.JUnit5CompileAction;
import io.javalin.Javalin;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	runJavalin();
    }
    
    private static void runJavalin() {
    	int port = 8000;
    	
    	IndexAction index = new IndexAction();
    	JUnit4CompileAction jUnit4C = new JUnit4CompileAction();
    	JUnit5CompileAction jUnit5C = new JUnit5CompileAction();
    	
    	Javalin app = Javalin.create().start(port);
    	
        app.routes(() -> {
        	path("", () -> {
        		get(index);
        	});
        	
        	path("junit4", () -> {
        		post(jUnit4C);
        	});
        	
        	path("junit5", () -> {
        		post(jUnit5C);
        	});
        	
        	
        });
    }
}
