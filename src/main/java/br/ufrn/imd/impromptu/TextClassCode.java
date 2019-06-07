package br.ufrn.imd.impromptu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joor.Reflect;
import org.joor.ReflectException;

public class TextClassCode {
	
	private final String textCode;
	
	private final Pattern packagePatt;
	private final Pattern classPatt;
	
	public TextClassCode(String textCode) {
		// TODO - validate preconditions
		this.textCode = textCode;
		
		packagePatt = Pattern.compile("package ([a-zA-Z0-9\\.]*);");
		classPatt = Pattern.compile("(?:public\\s)?class ([A-Za-z0-9]+)");
	}
	
	public Class<?> getCodeClass() throws ReflectException, JavaPatternsNotMatchingException {
		return Reflect.compile(this.className(), this.textCode).create().get().getClass();
	}
	
	private String className() throws JavaPatternsNotMatchingException {
		Matcher pkgMatcher = this.packagePatt.matcher(this.textCode);
		Matcher classMatcher = this.classPatt.matcher(this.textCode);
		
		boolean foundPkg = false;
		boolean foundClass = false;
		
		String pkg = "";
		String codeClass = "";
		
		while (pkgMatcher.find()) {
			foundPkg = true;
			pkg = pkgMatcher.group(1);
		}
		
		while (classMatcher.find()) {
			foundClass = true;
			codeClass = classMatcher.group(1);
		}
		
		if (!foundPkg || !foundClass) {
			throw new JavaPatternsNotMatchingException();
		}
		
		return pkg + "." + codeClass;
	}
}
