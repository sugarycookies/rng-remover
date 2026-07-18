package vibenexor.rngremover;

public class RNGData {

  public static boolean disableXoroshiro = true;
  public static boolean disableLegacy = true;
  public static boolean disableSingleThreaded = true;
  public static boolean disableThreadSafeLegacy = true;

  public static long xoroshiroSeedHi = Long.MAX_VALUE;
  public static long xoroshiroSeedLo = Long.MAX_VALUE;
  public static long legacySeed = Long.MAX_VALUE;

  // fixed nextDouble() output collapses MarsagliaPolarGaussian's rejection loop to a single
  // point; must short-circuit nextGaussian() directly rather than let the loop spin forever.
  public static double gaussianValue = 0.0d;
}
