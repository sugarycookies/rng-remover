package vibenexor.rngremover;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.apache.commons.lang3.StringUtils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RNGData {
  public static Codec<RNGData> CODEC = RecordCodecBuilder.create((i) -> i.group(
          Codec.BOOL.fieldOf("disableXoroshiro").forGetter(RNGData::disableXoroshiro),
          Codec.BOOL.fieldOf("disableLegacy").forGetter(RNGData::disableLegacy),
          Codec.BOOL.fieldOf("disableSingleThreaded").forGetter(RNGData::disableSingleThreaded),
          Codec.BOOL.fieldOf("disableThreadSafeLegacy").forGetter(RNGData::disableThreadSafeLegacy),
          Codec.BOOL.fieldOf("disableGaussian").forGetter(RNGData::disableGaussian),
          Codec.LONG.fieldOf("xoroshiroSeed").forGetter(RNGData::xoroshiroSeed),
          Codec.LONG.fieldOf("legacySeed").forGetter(RNGData::legacySeed),
          Codec.LONG.fieldOf("singleThreadedSeed").forGetter(RNGData::singleThreadedSeed),
          Codec.LONG.fieldOf("threadSafeLegacySeed").forGetter(RNGData::threadSafeLegacySeed),
          Codec.DOUBLE.fieldOf("gaussianValue").forGetter(RNGData::gaussianValue),
          Codec.BOOL.fieldOf("forceUUIDsRandom").forGetter(RNGData::forceUUIDsRandom),
          Codec.BOOL.fieldOf("unfixFarlands").forGetter(RNGData::unfixFarlands),
          Codec.BOOL.fieldOf("forceStructuresSpawn").forGetter(RNGData::forceStructuresSpawn),
          Codec.BOOL.fieldOf("forceNoiseRepeat").forGetter(RNGData::forceNoiseRepeat)
          ).apply(i, RNGData::new)
  );

  public static boolean disableXoroshiro = false;
  public static boolean disableLegacy = false;
  public static boolean disableSingleThreaded = false;
  public static boolean disableThreadSafeLegacy = false;
  public static boolean disableGaussian = false; //if legacy and xoroshiro and singlethreaded are disabled this
                                                //should lock to true to prevent crash

  public static long xoroshiroSeed = Long.MAX_VALUE; //deleted low/hi seed distinction since it only returns one anyway
  public static long legacySeed = Long.MAX_VALUE;
  public static long singleThreadedSeed = Long.MAX_VALUE;
  public static long threadSafeLegacySeed = Long.MAX_VALUE;
  public static double gaussianValue = 0.0d;

  public static boolean forceUUIDsRandom = false; //if legacy is enabled this shouldn't matter
  public static boolean unfixFarlands = false;
  public static boolean forceStructureSpawn = false;
  public static boolean forceNoiseRepeat = false;

  public RNGData(boolean dX, boolean dL, boolean dST, boolean dTSL, boolean dG, long xS, long lS, long sTS, long tSLS, double gV, boolean fUR, boolean uFA, boolean fSS, boolean fNR) {
    disableXoroshiro = dX;
    disableLegacy = dL;
    disableSingleThreaded = dST;
    disableThreadSafeLegacy = dTSL;
    disableGaussian = dG;

    xoroshiroSeed = xS;
    legacySeed = lS;
    singleThreadedSeed = sTS;
    threadSafeLegacySeed = tSLS;
    gaussianValue = gV;

    forceUUIDsRandom = fUR;
    unfixFarlands = uFA;
    forceStructureSpawn = fSS;
    forceNoiseRepeat = fNR;
  }

  public RNGData() {

  }

  public static void resetData() {
    new RNGData(false, false, false, false, false, Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE, 0.0d, false, false, false, false);
  }

  public static RNGData of(boolean dX, boolean dL, boolean dST, boolean dTSL, boolean dG, long xS, long lS, long sTS, long tSLS, double gV, boolean fUR, boolean uFA, boolean fSS, boolean fNR) {
    RNGData rng = new RNGData(dX, dL, dST, dTSL, dG, xS, lS, sTS, tSLS, gV, fUR, uFA, fSS, fNR);
    return rng;
  }

  public static RNGData of(RNGData data) {
    RNGData rng = RNGData.of(data.disableXoroshiro(), data.disableLegacy(), data.disableSingleThreaded(), data.disableThreadSafeLegacy(), data.disableGaussian(), data.xoroshiroSeed(), data.legacySeed(), data.singleThreadedSeed(), data.threadSafeLegacySeed(), data.gaussianValue(), data.forceUUIDsRandom(), data.unfixFarlands(), data.forceStructuresSpawn(), data.forceNoiseRepeat());
    return rng;
  }

  public void setDisableXoroshiro(boolean x) {
    disableXoroshiro = x;
  }

  public void setDisableLegacy(boolean l) {
    disableLegacy = l;
  }

  public void setDisableSingleThreaded(boolean s) {
    disableSingleThreaded = s;
  }

  public void setDisableThreadSafeLegacy(boolean t) {
    disableThreadSafeLegacy = t;
  }

  public void setDisableGaussian(boolean g) {
    disableGaussian = g;
  }

  public void setXoroshiroSeed(String x) {
    xoroshiroSeed = RNGData.toLongSeed(x);
  }

  public void setLegacySeed(String l) {
    legacySeed = RNGData.toLongSeed(l);
  }

  public void setSingleThreadedSeed(String s) {
    singleThreadedSeed = RNGData.toLongSeed(s);
  }

  public void setThreadSafeLegacySeed(String t) {
    threadSafeLegacySeed = RNGData.toLongSeed(t);
  }

  public void setGaussianValue(String g) throws NoSuchAlgorithmException {
    g =  g.trim();
    if (StringUtils.isEmpty(g)) {
      gaussianValue = 0.0d;
    } else {
      try {
        gaussianValue = Double.parseDouble(g);
      } catch (NumberFormatException exception) {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(g.getBytes(StandardCharsets.UTF_8));
        ByteBuffer buffer = ByteBuffer.wrap(hash);
        long step = buffer.getLong();
        gaussianValue = Double.longBitsToDouble(step);
      }
    }
  }

  public static long toLongSeed(String seedString) {
    seedString = seedString.trim();
    if (StringUtils.isEmpty(seedString)) {
      return Long.MAX_VALUE;
    } else {
      try {
        return Long.parseLong(seedString);
      } catch (NumberFormatException exception) {
        return (long)seedString.hashCode();
      }
    }
  }

  public void setUUIDs(boolean u) {
    forceUUIDsRandom = u;
  }

  public void setUnfixFarlands(boolean u) {
    unfixFarlands = u;
  }

  public void setForceStructureSpawn(boolean f) {
    forceStructureSpawn = f;
  }

  public void setForceNoiseRepeat(boolean f) {
    forceNoiseRepeat = f;
  }

  //just to make the codec work
  public boolean disableXoroshiro() {
    return disableXoroshiro;
  }

  public boolean disableLegacy() {
    return disableLegacy;
  }

  public boolean disableSingleThreaded() {
    return disableSingleThreaded;
  }

  public boolean disableThreadSafeLegacy() {
    return disableThreadSafeLegacy;
  }

  public boolean disableGaussian() {
    return disableGaussian;
  }

  public long xoroshiroSeed() {
    return xoroshiroSeed;
  }

  public long legacySeed() {
    return legacySeed;
  }

  public long singleThreadedSeed() {
    return singleThreadedSeed;
  }

  public long threadSafeLegacySeed() {
    return threadSafeLegacySeed;
  }

  public double gaussianValue() {
    return gaussianValue;
  }

  public boolean forceUUIDsRandom() {
    return forceUUIDsRandom;
  }

  public boolean unfixFarlands() {
    return unfixFarlands;
  }

  public boolean forceStructuresSpawn() {
    return forceStructureSpawn;
  }

  public boolean forceNoiseRepeat() {
    return forceNoiseRepeat;
  }
}
