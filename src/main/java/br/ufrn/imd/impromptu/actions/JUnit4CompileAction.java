package br.ufrn.imd.impromptu.actions;

import org.joor.ReflectException;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import br.ufrn.imd.impromptu.JavaPatternsNotMatchingException;
import br.ufrn.imd.impromptu.TestResult;
import br.ufrn.imd.impromptu.TextClassCode;
import br.ufrn.imd.impromptu.JUnit4TestResult;
import io.javalin.Context;
import io.javalin.Handler;

public class JUnit4CompileAction implements Handler {

	@Override
	public void handle(Context ctx) throws Exception {
		String content = ctx.body();
		
		// The best approach here would be an injection of a factory
		// and using it to instantiate the code... But this is
		// good enough for now
		TextClassCode textClass = new TextClassCode(content);
		
		try {
			Result result = this.runJUnit4(textClass);
			System.out.println(result.getFailureCount());
			
			TestResult testResult = new JUnit4TestResult(result);
			
			ctx.result(testResult.serialize());
		} catch (ReflectException e) {
			ctx.res.setStatus(400);
			ctx.result("{\"type\": \"compile-error\", \"msg\": " + e.getMessage() + "}");
		} catch (JavaPatternsNotMatchingException e) {
			ctx.res.setStatus(400);
			ctx.result("{\"type\": \"class-def-error\", \"msg\": " + e.getMessage() + "}");
		}
	}
	
	private Result runJUnit4(TextClassCode textClass) throws ReflectException, JavaPatternsNotMatchingException {
		// As for JUnitCode... I don't know. Maybe it's
		// a good idea let the reference to this live only
		// in this scope
		JUnitCore junit = new JUnitCore();
//		junit.addListener(new TextListener(System.out));
		return junit.run(textClass.getCodeClass());		
	}
	
}
