package by.shade.strutsannotations.scanner.classes;

import org.apache.struts.action.Action;

import by.shade.strutsannotations.StrutsAction;
import by.shade.strutsannotations.StrutsActions;

/**
 * Filter for annotated Struts actions.
 *
 * @author homyakov
 * @version $Id$
 */
public class ActionClassFilter implements IClassFilter {

    /* (non-Javadoc)
     * @see by.shade.strutsannotations.scanner.IClassFilter#classMatches(java.lang.Class)
     */
    public boolean classMatches(final Class<?> clazz) {
        return Action.class.isAssignableFrom(clazz)
                && (clazz.isAnnotationPresent(StrutsAction.class) || clazz
                        .isAnnotationPresent(StrutsActions.class));
    }

    /* (non-Javadoc)
     * @see by.shade.strutsannotations.scanner.IClassFilter#nameMatches(java.lang.String)
     */
    public boolean nameMatches(final String name) {
        return true;
    }

}
