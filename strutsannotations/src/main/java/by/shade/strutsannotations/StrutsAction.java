package by.shade.strutsannotations;

import java.beans.Introspector;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;

/**
 * Describes an ActionMapping object that is to be used to process a request for a specific
 * module-relative URI. Generates <em>parameter</em> for EventDispatchAction automatically.
 *
 * Default values:
 * <ul>
 * <li>path: "/" + uncapitalized name of the action class without the last "Action"
 * ("/getCardsCount" for GetCardsCountAction.class)</li>
 * <li>name: uncapitalized canonical name of the form bean class ("actionForm" for ActionForm.class)
 * </li>
 * <li>parameter: list of dispatch methods for EventDispatchAction, empty otherwise</li>
 * <li>scope: "session"</li>
 * </ul>
 *
 * To use annotations in entire project, replace Struts servlet in web.xml (changes in
 * struts-config.xml are not required):
 *
 * <pre>
 *   &lt;servlet-class>by.shade.strutsannotations.ActionServlet&lt;/servlet-class>
 *   &lt;!-- &lt;servlet-class>org.apache.struts.action.ActionServlet&lt;/servlet-class> -->
 * </pre>
 *
 * To use annotations in one module, insert in struts-config.xml for this module:
 *
 * <pre>
 * &lt;struts-config>
 *   &lt;plug-in className="by.shade.strutsannotations.plugin.StrutsAnnotationsPlugin" />
 * &lt;/struts-config>
 * </pre>
 *
 * TODO add forwards: StrutsForward[] forwards() default {};
 *
 * @author homyakov
 * @version $Id$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface StrutsAction {

    /**
     * The module-relative path of the submitted request, starting with a "/" character, and without
     * the filename extension if extension mapping is used. Default: "/" + uncapitalized name of the
     * action class without the last "Action" ("/getCardsCount" for GetCardsCountAction.class).
     */
    String path() default StringUtils.EMPTY;

    /**
     * Class of the form bean, if any, that is associated with this action mapping.
     */
    Class<? extends ActionForm> form() default ActionForm.class;

    /**
     * Name of the form bean, if any, that is associated with this action mapping.
     *
     * Default: empty if no form bean class specified; uncapitalized canonical name of the form bean
     * class (first character lower-cased, see {@link Introspector#decapitalize(String)}) if form
     * bean class specified ("actionForm" for ActionForm.class).
     */
    String name() default StringUtils.EMPTY;

    /**
     * Module-relative path of the action or other resource to which control should be returned if a
     * validation error is encountered. Valid only when "name" is specified. Required if "name" is
     * specified and the input bean returns validation errors. Optional if "name" is specified and
     * the input bean does not return validation errors. Default: empty.
     */
    String input() default StringUtils.EMPTY;

    /**
     * General purpose configuration parameter that can be used to pass extra information to the
     * Action instance selected by this Action. Default: list of dispatch methods for
     * EventDispatchAction (filled by plugin), empty otherwise.
     */
    String parameter() default StringUtils.EMPTY;

    /**
     * Only for EventDispatchAction. The <em>defaultMethod</em> is purely optional, and specifies
     * default method name when no request parameters match the list of methods. If this is not
     * specified, <code>unspecified</code> method will be invoked by default.
     */
    String defaultMethod() default StringUtils.EMPTY;

    /**
     * The context ("request" or "session") that is used to access our ActionForm bean, if any.
     * Optional if "name" is specified, else not valid. Default: "session".
     */
    String scope() default "session";

    /**
     * Set to "true" if the validate method of the ActionForm bean should be called prior to calling
     * the Action object for this action mapping, or set to "false" if you do not want the validate
     * method called. Default: true.
     */
    boolean validate() default true;

    /**
     * Set to "true" if the Action can be cancelled. By default, when an Action is cancelled,
     * validation is bypassed and the Action should not execute the business operation. If a request
     * tries to cancel an Action when cancellable is not set, a "InvalidCancelException" is thrown.
     * Default: false.
     */
    boolean cancellable() default false;

    /**
     * Struts module, starting with a "/" character (empty for default module). Default: empty.
     */
    String module() default StringUtils.EMPTY;

}
