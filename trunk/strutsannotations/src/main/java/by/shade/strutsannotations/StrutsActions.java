package by.shade.strutsannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author serafimovich
 * @version $Id$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface StrutsActions {
    /**
     * All inner actions in action-class must have same form-bean, this means that class of
     * form-bean must be same for all form-beans objects.
     *
     * @see StrutsAction
     */
    StrutsAction[] value() default { @StrutsAction };
}
