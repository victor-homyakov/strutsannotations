package by.shade.strutsannotations.scanner.fields;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Scans given class for fields matching filter.
 *
 * @author homyakov
 * @version $Id$
 */
public class FieldScanner {

    private final Class<?> clazz;

    // private final boolean inherited;

    /**
     * @param clazz
     *            class to scan
     */
    public FieldScanner(final Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * @param filter
     *            filter
     * @return list of matching fields
     */
    public Collection<Field> list(final IFieldFilter filter) {
        // XXX maybe include inherited fields?
        final Field[] fields = clazz.getDeclaredFields();
        final List<Field> result = new ArrayList<Field>(fields.length);
        for (Field field : fields) {
            if (filter.fieldMatches(field)) {
                result.add(field);
            }
        }
        return result;
    }

}
