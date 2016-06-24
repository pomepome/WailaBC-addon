package pome.waila.bcaddon.util;

public class TimeHolder
{
	private long lastMillisec;
	private int lastEnergy;

	public TimeHolder(long milli,int energy)
	{
		setValue(milli, energy);
	}

	public long getLastMillisec()
	{
		return lastMillisec;
	}

	public int getLastEnergy()
	{
		return lastEnergy;
	}

	public void setValue(long milli,int energy)
	{
		lastMillisec = milli;
		lastEnergy = energy;
	}
}
