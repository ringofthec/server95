package gameserver.utils;

import java.util.UUID;

/**
 * @author a
 *
 */
public class UuidGenerator
{
	public static String newUuid()
	{
		String uuid = UUID.randomUUID().toString();
		return uuid.replace("-", "");
	}
}
