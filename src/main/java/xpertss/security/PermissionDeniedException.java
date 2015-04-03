package xpertss.security;

/**
 * This Exception is thrown by any method wishing to report to the 
 * caller that it does not have permission to perform this operation.
 */
public class PermissionDeniedException extends SecurityException {
	
	public PermissionDeniedException()
	{
		super();
	}
	
	public PermissionDeniedException(String msg)
	{
		super(msg);
	}
	
}