package cbr.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Feature {

	double min();
	double max();
	int maxlen() default 10000;
	
}
