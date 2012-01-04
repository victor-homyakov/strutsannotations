package by.shade.strutsannotations.scanner.classes;

import java.io.File;

/**
 * Incapsulates methods working with classPath and ClassLoader.
 *
 * @author homyakov
 * @version $Id$
 */
public class ClassHandler {

    private static final String DOT_CLASS = ".class";

    private final String classPath;
    private final ClassLoader classLoader;

    public ClassHandler(String classPath, ClassLoader classLoader) {
        this.classPath = classPath;
        this.classLoader = classLoader;
    }

    public Class<?> toClassDefinition(final File file) throws ClassNotFoundException {
        final String name = toQualifiedClassName(file);
        return classLoader.loadClass(name);
    }

    public String toQualifiedClassName(final File file) {
        String name = file.getPath();
        if (classPath.length() > 0) {
            name = name.substring(classPath.length() + 1/*File.separator.length()*/);
        }
        name = name.substring(0, name.length() - DOT_CLASS.length());
        return name.replace(File.separatorChar, '.');
        // if (LOG.isDebugEnabled()) {LOG.debug("File " + name);}
    }

    public boolean hasClassExtension(final File file) {
        return file.getName().endsWith(DOT_CLASS);
    }

}
