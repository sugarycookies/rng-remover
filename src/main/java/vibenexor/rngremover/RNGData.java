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
          Codec.BOOL.fieldOf("forceUUIDsRandom").forGetter(RNGData::forceUUIDsRandom)
          ).apply(i, RNGData::new)
  );
  //so presumably the RNGData per world will have to be stored somewhere eventually so that when you load a world again
  //it knows what RNGData to use. but also the codec is used in PrimaryLevelData already in rngremover$settagdata,
  //the normal setTagData function seems to be putting all of the PrimaryLevelData onto one tag. this tag is what ends
  //up saved as level.dat, so this should mean that the RNGData gets saved already and we don't have to worry more about
  //that hopefully

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

  public static boolean forceUUIDsRandom = true; //if legacy is enabled this shouldn't matter

  public RNGData(boolean dX, boolean dL, boolean dST, boolean dTSL, boolean dG, long xS, long lS, long sTS, long tSLS, double gV, boolean fUR) {
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
  }

  public RNGData() {
    disableXoroshiro = false;
    disableLegacy = false;
    disableSingleThreaded = false;
    disableThreadSafeLegacy = false;
    disableGaussian = false;

    xoroshiroSeed = Long.MAX_VALUE;
    legacySeed = Long.MAX_VALUE;
    singleThreadedSeed = Long.MAX_VALUE;
    threadSafeLegacySeed = Long.MAX_VALUE;
    gaussianValue = 0.0;

    forceUUIDsRandom = false;
  }

  public static RNGData of(boolean dX, boolean dL, boolean dST, boolean dTSL, boolean dG, long xS, long lS, long sTS, long tSLS, double gV, boolean fUR) {
    RNGData rng = new RNGData(dX, dL, dST, dTSL, dG, xS, lS, sTS, tSLS, gV, fUR);
    return rng;
  }

  public static RNGData of(RNGData data) {
    RNGData rng = RNGData.of(data.disableXoroshiro(), data.disableLegacy(), data.disableSingleThreaded(), data.disableThreadSafeLegacy(), data.disableGaussian(), data.xoroshiroSeed(), data.legacySeed(), data.singleThreadedSeed(), data.threadSafeLegacySeed(), data.gaussianValue(), data.forceUUIDsRandom());
    return rng;
  }

  public void setDisableXoroshiro(boolean x) {
    disableXoroshiro = x;
  }

  public void setDisableLegacy(boolean l) {
    disableXoroshiro = l;
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
    xoroshiroSeed = RNGData.toLongSeed(l);
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
}
