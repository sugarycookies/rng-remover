package vibenexor.rngremover;

public class RNGData {

  public static boolean disableXoroshiro = true;
  public static boolean disableLegacy = true;
  public static boolean disableSingleThreaded = true;

  public static long xoroshiroSeedHi = Long.MAX_VALUE;
  public static long xoroshiroSeedLo = Long.MAX_VALUE;
  public static long legacySeed = Long.MAX_VALUE;
}
