package by.shade.strutsannotations.scanner.fields;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import by.shade.strutsannotations.StrutsForward;

/**
 * Filters fields annotated with StrutsForward.
 *
 * @author homyakov
 * @version $Id$
 */
public class ForwardFieldFilter implements IFieldFilter {

    /* (non-Javadoc)
     * @see by.shade.strutsannotations.scanner.IFieldFilter#fieldMatches(java.lang.reflect.Field)
     */
    public boolean fieldMatches(final Field field) {
        // Only string constants can be used as local forwards
        return field.isAnnotationPresent(StrutsForward.class)
                && String.class.equals(field.getType()) && Modifier.isFinal(field.getModifiers())
                && Modifier.isStatic(field.getModifiers());
    }

}
