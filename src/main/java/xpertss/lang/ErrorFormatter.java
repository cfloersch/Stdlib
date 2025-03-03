package xpertss.lang;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * A simple builder style class to modify the way Errors (aka Throwable)s are
 * printed. This is useful for logging frameworks that wish to attempt to minimize
 * the volume of data sent to the logs.
 * <pre>
 *         ErrorFormatter format = ErrorFormatter.create()
 *            .withShortClassNames()
 *            .withCauseFilter(t -> t.getCause() != null)
 *            .withStackFilter(t -> {
 *                String clsName = t.getClassName();
 *                return clsName.startsWith("java")
 *                        || clsName.startsWith("javax");
 *            });
 *         format.print(e, System.err);
 *
 * </pre>
 * The above example would print only the root cause for each error and it would
 * shorten each line by stripping off the package prefix from class names. The stack
 * itself would omit any stack element that occurred within a core java or javax
 * package class.
 */
public final class ErrorFormatter {


    private Predicate<StackTraceElement> stackFilter;
    private Predicate<Throwable> causeFilter;
    private boolean stripPackage;

    private ErrorFormatter() {}

    public static ErrorFormatter create()
    {
        return new ErrorFormatter();
    }



    /**
     * Apply a filter to the Stack Element. The given predicate should return TRUE
     * for each Stack element that should be filtered OUT of the stack trace.
     * <p/>
     * This is usually used to filter out classes that belong to core JDK for example
     * or the framework the application is running in.
     */
    public ErrorFormatter withStackFilter(Predicate<StackTraceElement> filter)
    {
        this.stackFilter = filter;
        return this;
    }

    /**
     * Apply a filter to the Throwables in the causal chain. The given predicate
     * should return TRUE for each throwable that should be filtered OUT of the
     * stack trace.
     * <p/>
     * An example usage may be to filter out all causes other than the root cause.
     */
    public ErrorFormatter withCauseFilter(Predicate<Throwable> filter)
    {
        this.causeFilter = filter;
        return this;
    }

    /**
     * Strip the package name prefix from both the Throwable class and all of
     * the classes in the stack.
     */
    public ErrorFormatter withShortClassNames()
    {
        this.stripPackage = true;
        return this;
    }

    /**
     * Strip the package name prefix from both the Throwable class and all of
     * the classes in the stack.
     */
    public ErrorFormatter withFullClassNames()
    {
        this.stripPackage = false;
        return this;
    }


    /**
     * Create a String representation of the stack trace of the given throwable.
     */
    public String format(Throwable t)
    {
        StringWriter writer = new StringWriter();
        try(PrintWriter pw = new PrintWriter(writer)) {
            print(t, pw);
            return writer.toString();
        }
    }


    /**
     * Print the given throwable's stack trace to the specified print stream.
     */
    public void print(Throwable t, PrintStream ps)
    {
        PrintWriter writer = new PrintWriter(ps);
        print(t, writer);
    }

    /**
     * Print the given throwable's stack trace to the specified print writer.
     */
    public void print(Throwable t, PrintWriter pw)
    {
        List<Throwable> chain = new ArrayList<>();
        Predicate<Throwable> filter = causeFilter();
        do {
            if(!filter.test(t)) chain.add(t);
        } while((t = t.getCause()) != null);

        for(int i = 0; i < chain.size(); i++) {
            Throwable item = chain.get(i);
            if(i > 0) pw.print("Caused by: ");
            pw.print(formatName(item.getClass().getName()));
            if (!Strings.isEmpty(item.getMessage()))
                pw.print(" : " + item.getMessage());
            pw.println();
            printStack(item, pw);
        }
        pw.flush();
    }

    private void printStack(Throwable t, PrintWriter pw)
    {
        StackTraceElement[] stack = t.getStackTrace();
        Predicate<StackTraceElement> filter = stackFilter();
        for(StackTraceElement element : stack) {
            if(!filter.test(element)) {
                pw.print("\tat ");
                pw.print(formatName(element.getClassName()));
                pw.format(".%s", element.getMethodName());
                if(element.isNativeMethod()) {
                    pw.println("(Native Method)");
                } else if(element.getLineNumber() >= 0) {
                    pw.format("(line:%d)%n", element.getLineNumber());
                } else {
                    pw.println("(Unknown Source)");
                }
                pw.flush();
            }
        }
    }





    private String formatName(String name)
    {
        int index = name.lastIndexOf('.');
        return (!stripPackage) ? name :  name.substring(index + 1);
    }

    private Predicate<Throwable> causeFilter()
    {
        return (causeFilter != null) ? causeFilter : throwable -> false;
    }

    private Predicate<StackTraceElement> stackFilter()
    {
        return (stackFilter != null) ? stackFilter : stackTraceElement -> false;
    }


}
