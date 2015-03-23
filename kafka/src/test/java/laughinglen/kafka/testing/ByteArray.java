package laughinglen.kafka.testing;

import com.google.common.collect.ImmutableList;
import org.assertj.core.util.Lists;

import java.util.List;

public final class ByteArray {
	private final List<Byte> bytes = Lists.newArrayList();

	public void add(final byte b)	{
		bytes.add(b);
	}

	public final List<Byte> bytes()	{
		return ImmutableList.copyOf(bytes);
	}
}
