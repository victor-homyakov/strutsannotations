package by.shade.strutsannotations.scanner.classes;

/**
 * Interface for class filters.
 *
 * @author homyakov
 * @version $Id$
 */
public interface IClassFilter {

    /**
     * @param clazz
     *            class to test
     * @return true if class matches criteria
     */
    boolean classMatches(Class<?> clazz);

    /**
     * @param name
     *            qualified class name to test
     * @return true if name matches criteria
     */
    boolean nameMatches(String name);

}
