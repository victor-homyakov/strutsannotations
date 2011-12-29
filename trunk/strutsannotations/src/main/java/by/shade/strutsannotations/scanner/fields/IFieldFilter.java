package by.shade.strutsannotations.scanner.fields;

import java.lang.reflect.Field;

/**
 * Interface for field filters.
 *
 * @author homyakov
 * @version $Id$
 */
public interface IFieldFilter {

    /**
     * @param field
     *            field
     * @return true if field matches criteria
     */
    boolean fieldMatches(Field field);

}
