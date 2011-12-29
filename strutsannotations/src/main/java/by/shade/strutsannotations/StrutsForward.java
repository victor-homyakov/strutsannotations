package by.shade.strutsannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Describes an ActionForward that is to be made available to an Action as a return value. An
 * ActionForward is referenced by a logical name and encapsulates a URI. Instead of deprecated
 * contextRelative="true" use module="/some_module".
 *
 * Should be used on static final String fields.
 *
 * @author homyakov
 * @version $Id$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface StrutsForward {

    /**
     * Module-relative or context-relative URI to which control should be forwarded, or an absolute
     * or relative URI to which control should be redirected. Example: "/page.jsp".
     *
     * The module-relative or context-relative path to the resources that is encapsulated by the
     * logical name of this ActionForward. If the path is to be considered context-relative when
     * used in a modular application, then module should be set to proper value.
     *
     * This value should begin with a slash ("/") character.
     *
     * When value is left unchanged (empty) then default value is taken from the annotated string.
     */
    String path() default "";

    /**
     * The module prefix to use with this path. This value should begin with a slash ("/") or be an
     * empty string (default for current module and module-relative URI).
     */
    String module() default "";

    /**
     * Set to true if the controller servlet should call HttpServletResponse.sendRedirect() on the
     * associated path; otherwise false. Default: false.
     */
    boolean redirect() default false;

    // boolean contextRelative() default false;

}
