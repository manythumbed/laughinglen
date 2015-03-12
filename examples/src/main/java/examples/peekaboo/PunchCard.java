package examples.peekaboo;

public final class PunchCard {
	public final String content;
	public final boolean folded;
	public final boolean spindled;
	public final boolean mutilated;

	private PunchCard(final String content, final boolean folded, final boolean spindled, final boolean mutilated) {
		this.content = content;
		this.folded = folded;
		this.spindled = spindled;
		this.mutilated = mutilated;
	}

	public static class Builder	{
		private final String content;
		private boolean folded, spindled, mutilated;

		public Builder(final String content) {
			this.content = content;
		}

		public Builder fold()	{
			this.folded = true;
			return this;
		}

		public Builder spindle()	{
			this.spindled = true;
			return this;
		}

		public Builder mutilate()	{
			this.mutilated = true;
			return this;
		}

		public PunchCard build()	{
			return new PunchCard(content, folded, spindled, mutilated);
		}
	}
}
