package by.shade.strutsannotations.scanner.classes;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Scans given class path for classes matching filter.
 *
 * @author homyakov
 * @version $Id$
 */
public class ClassScanner {

    /** Commons logging instance. */
    private static final Log LOG = LogFactory.getLog(ClassScanner.class);

    private final ClassHandler classHandler;

    private final File root;

    /**
     * Create class scanner. Uses context ClassLoader for this Thread to load found classes.
     *
     * @param classPath
     *            class path, should point to existing directory
     * @param rootPackage
     *            package to start scan from
     */
    public ClassScanner(final File classPath, final String rootPackage) {
        this(classPath, Thread.currentThread().getContextClassLoader(), rootPackage);
    }

    /**
     * Create class scanner. Uses ClassLoader passed as argument to load found classes.
     *
     * @param classPath
     *            class path, should point to existing directory
     * @param classLoader
     *            class loader to load found classes
     * @param rootPackage
     *            package to start scan from
     */
    public ClassScanner(final File classPath, final ClassLoader classLoader,
            final String rootPackage) {
        if (!classPath.exists()) {
            throw new IllegalArgumentException(classPath + " doesn't exist");
        }
        if (!classPath.isDirectory()) {
            throw new IllegalArgumentException(classPath + " is not a directory");
        }

        classHandler = new ClassHandler(classPath, classLoader);
        final String path = rootPackage.replace('.', File.separatorChar);
        root = new File(classPath, path);
        if (!root.exists()) {
            throw new IllegalArgumentException(rootPackage
                    + " package doesn't exist under classpath " + classPath);
        }
    }

    /**
     * Build list of matching classes.
     *
     * @param filter
     *            filter to include only matching classes
     * @return collection of filtered classes
     * @throws ClassNotFoundException
     *             if one of classes was not found by class loader
     */
    public Collection<Class<?>> list(final IClassFilter filter) throws ClassNotFoundException {
        final List<Class<?>> result = new ArrayList<Class<?>>();
        if (LOG.isInfoEnabled()) {
            LOG.info("Started scanning of " + root);
        }
        traverse(root, filter, result);
        return result;
    }

    private void traverse(final File dir, final IClassFilter filter, final List<Class<?>> result)
            throws ClassNotFoundException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Directory " + dir);
        }
        final File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                traverse(file, filter, result);
                continue;
            }

            if (!classHandler.hasClassExtension(file)) {
                continue;
            }

            final String name = classHandler.toQualifiedClassName(file);
            if (!filter.nameMatches(name)) {
                continue;
            }

            Class<?> clazz = null;
            try {
                clazz = classHandler.toClassDefinition(file);
            } catch (NoClassDefFoundError e) {
                // Class loader can't load the type hierarchy. The same error
                // will be thrown run-time, so that we can ignore this class
                continue;
            }

            if (filter.classMatches(clazz)) {
                if (LOG.isInfoEnabled()) {
                    LOG.info("Action " + name);
                }
                result.add(clazz);
            }
        }
    }

}
