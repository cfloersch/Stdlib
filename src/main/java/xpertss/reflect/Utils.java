package xpertss.reflect;

import xpertss.function.Predicate;

/**
 *
 */
class Utils {


   public static Predicate<AnnotatedWithParams> parameters(final Class<?>[] params)
   {
      return new Predicate<AnnotatedWithParams>() {
         @Override
         public boolean apply(AnnotatedWithParams input) {
            if(input.getParameterCount() != params.length) return false;
            for(int i = 0; i < params.length; i++) {
               AnnotatedParameter param = input.getParameter(i);
               if(param.getRawType() != params[i]) return false;
            }
            return true;
         }
      };
   }


   public static int accessModifier(AnnotatedMember member)
   {
      return (member.isPublic()) ? 0 : (member.isProtected()) ? 1 : (member.isPrivate()) ? 3 : 2;
   }


}
