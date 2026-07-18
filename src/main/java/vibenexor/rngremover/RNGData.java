package vibenexor.rngremover;

public class RNGData {
  public static boolean disableXoroshiro = true;
  public static boolean disableLegacy = true;
  public static boolean disableSingleThreaded = true;
  public static boolean disableThreadSafeLegacy = true;
  public static boolean disableGaussian = true; //if legacy and xoroshiro and singlethreaded are disabled this
                                                //should lock to true to prevent crash

  public static long xoroshiroSeedHi = Long.MAX_VALUE;
  public static long xoroshiroSeedLo = Long.MAX_VALUE;
  public static long legacySeed = Long.MAX_VALUE;
  public static long singleThreadedSeed = Long.MAX_VALUE;
  public static long threadSafeLegacySeed = Long.MAX_VALUE;
  public static double gaussianValue = 0.0d;

  public static boolean forceUUIDsRandom = true; //if legacy is enabled this shouldn't matter
}
