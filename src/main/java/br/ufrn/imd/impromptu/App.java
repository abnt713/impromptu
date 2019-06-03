package br.ufrn.imd.impromptu;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;

import br.ufrn.imd.impromptu.controllers.IndexController;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Javalin app = Javalin.create().start(8000);
        app.routes(() -> {
        	path("", () -> {
        		get(IndexController::index);
        	});
        	path("users", () -> {
        		get(IndexController::index);
        	});
        });
    }
}
