package br.ufrn.imd.impromptu.actions;

import org.joor.ReflectException;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import br.ufrn.imd.impromptu.JUnit5TestResult;
import br.ufrn.imd.impromptu.JavaPatternsNotMatchingException;
import br.ufrn.imd.impromptu.TestResult;
import br.ufrn.imd.impromptu.TextClassCode;
import br.ufrn.imd.impromptu.junit5.JUnit5Daemon;
import io.javalin.Context;
import io.javalin.Handler;

public class JUnit5CompileAction implements Handler {

	@Override
	public void handle(Context ctx) throws Exception {
		String content = ctx.body();
		TextClassCode textClass = new TextClassCode(content);
		
		try {
			TestExecutionSummary summary = this.runJUnit5(textClass);
			
			TestResult testResult = new JUnit5TestResult(summary, true);
			
			ctx.result(testResult.serialize());
		} catch (ReflectException e) {
			ctx.res.setStatus(400);
			ctx.result("{\"type\": \"compile-error\", \"msg\": " + e.getMessage() + "}");
		} catch (JavaPatternsNotMatchingException e) {
			ctx.res.setStatus(400);
			ctx.result("{\"type\": \"class-def-error\", \"msg\": " + e.getMessage() + "}");
		}
	}
	
	private TestExecutionSummary runJUnit5(TextClassCode textClass) throws ReflectException, JavaPatternsNotMatchingException {
		JUnit5Daemon daemon = new JUnit5Daemon();
		return daemon.runFromClass(textClass.getCodeClass());
	}

}
