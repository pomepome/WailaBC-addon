package pome.waila.bcaddon.util;

public class ProviderTypes
{
	private boolean provStack,
					provHead,
					provBody,
					provTail,
					provNBT;

	public ProviderTypes(boolean stack, boolean head, boolean body, boolean tail,boolean nbt)
	{
		provStack = stack;
		provHead = head;
		provBody = body;
		provTail = tail;
		provNBT = nbt;
	}

	public boolean getProvidingStack()
	{
		return provStack;
	}
	public boolean getProvidingHead()
	{
		return provHead;
	}
	public boolean getProvidingBody()
	{
		return provBody;
	}
	public boolean getProvidingTail()
	{
		return provTail;
	}
	public boolean getProvidingNBT()
	{
		return provNBT;
	}
}
