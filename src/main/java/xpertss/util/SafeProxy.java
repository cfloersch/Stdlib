package xpertss.util;

import xpertss.threads.Threads;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * This class will wrap a given class and proxy calls to it catching any runtime
 * exceptions that may be thrown. Those exceptions will be sent to the calling
 * thread's uncaught exception handler and {@code null} will be returned.
 * <p/>
 * This would most generally be used to wrap a Java AWT Event Listener so that
 * exceptions in one listener do not impact the delivery of events to other
 * listeners. There are likely other use cases it may be useful for.
 *
 * @param <T>
 */
public class SafeProxy <T> implements InvocationHandler {

    /**
     * Construct an instance of the SafeProxy compatible with the given proxied class.
     */
    public static <T> T newInstance(Class<T> proxiedClass, T proxied)
    {
        return proxiedClass.cast(Proxy.newProxyInstance(proxiedClass.getClassLoader(),
                new Class[] { proxiedClass },
                new SafeProxy<T>(proxied)));
    }


    private final T proxied;


    private SafeProxy(T proxied)
    {
        this.proxied = proxied;
    }


    @Override
    public Object invoke(Object proxy, final Method method, final Object[] args)
            throws Throwable
    {
        if ("equals".equals(method.getName())) {
            return (args[0] == proxy);
        } else if("hashCode".equals(method.getName())) {
            return hashCode();
        } else if("toString".equals(method.getName())) {
            return toString();
        } else {
            try {
                return method.invoke(proxied, args);
            } catch(InvocationTargetException e) {
                Threads.report(e.getTargetException());
            } catch(Throwable t) {
                Threads.report(t);
            }
            return null;
        }
    }


    @Override
    public String toString()
    {
        return "SafeProxy<" + proxied.toString() + ">";
    }

}
