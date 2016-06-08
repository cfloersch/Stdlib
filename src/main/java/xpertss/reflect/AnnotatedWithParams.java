package xpertss.reflect;

/**
 * TODO
 */
public interface AnnotatedWithParams extends AnnotatedMember {

   boolean isVarArgs();

   AnnotatedParameter getParameter(int index);

   int getParameterCount();

}
