package map.exceptions;

public class BadStateException extends Exception
{
	private static final long serialVersionUID = 1L;

	public BadStateException(String text)
	{
		super(text);
	}
}
