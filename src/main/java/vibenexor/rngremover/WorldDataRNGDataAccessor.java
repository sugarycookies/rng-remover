package vibenexor.rngremover;

public interface WorldDataRNGDataAccessor {
    RNGData RNGState();

    void setRNGState(RNGData RNG);

    boolean getXoroshiroState();

    boolean getLegacyState();

    boolean getSingleThreadedState();

    boolean getThreadSafeLegacyState();

    boolean getGaussianState();

    long getXoroshiroSeed();

    long getLegacySeed();

    long getSingleThreadedSeed();

    long getThreadSafeLegacySeed();

    double getGaussianValue();

    boolean getUUIDState();

    String getXoroshiroSeedS();

    String getLegacySeedS();

    String getSingleThreadedSeedS();

    String getThreadSafeLegacySeedS();

    String getGaussianValueS();
}
