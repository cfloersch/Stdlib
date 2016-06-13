package xpertss.lang;

import xpertss.function.Consumer;
import xpertss.util.Sets;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.util.Set;

import static xpertss.lang.Objects.isOneOf;

/**
 * General utility methods for working with annotations, handling meta-annotations,
 * bridge methods (which the compiler generates for generic declarations) as well
 * as super methods (for optional <em>annotation inheritance</em>).
 */
public final class Annotations {

   private Annotations() { }

   /**
    * Returns a set of meta annotations associated with the given annotation
    * bundle. This will suppress {@link java.lang.annotation.Retention} and
    * {@link java.lang.annotation.Target}. The supplied annotation will be
    * the first annotation included in the returned set.
    */
   public static Set<Annotation> getMetaAnnotations(Annotation bundle)
   {
      Set<Annotation> result = Sets.newLinkedHashSet();
      result.add(Objects.notNull(bundle));
      for (Annotation a : bundle.annotationType().getDeclaredAnnotations()) {
         // minor optimization: by-pass 2 common JDK meta-annotations
         if ((a instanceof Target) || (a instanceof Retention)) {
            continue;
         }
         result.add(a);
      }
      return result;
   }

   /**
    * Returns a set of annotations and their associated meta annotations that
    * are associated with the given annotated element. This includes all
    * annotations associated through inheritance as well as meta-annotations.
    */
   public static Set<Annotation> getAnnotations(AnnotatedElement element)
   {
      Set<Annotation> result = Sets.newLinkedHashSet();
      for(Annotation ann : element.getAnnotations()) {
         result.addAll(getMetaAnnotations(ann));
      }
      return result;
   }


   // TODO All of these determine annotation equality using both type and property values??? Fix that???

   public static Set<Annotation> getAnnotations(Class<?> clazz)
   {
      final Set<Annotation> result = Sets.newLinkedHashSet();
      Classes.walk(clazz, new Consumer<Class>() {
         @Override
         public void apply(Class aClass)
         {
            for(Annotation ann : aClass.getDeclaredAnnotations()) {
               result.add(ann);
            }
         }
      }, null, null);
      return result;
   }

}
