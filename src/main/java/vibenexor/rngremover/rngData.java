package vibenexor.rngremover;

public class rngData {
    public boolean rngActive_Xoroshiro;
    public boolean rngActive_Legacy;
    public long xoroshiroSeedHi;
    public long xoroshiroSeedLo;
    public long legacySeed;

    public rngData() {
        rngActive_Xoroshiro = true;
        rngActive_Legacy = true;
        xoroshiroSeedHi = 7640891576956012809L;
        xoroshiroSeedLo = -7046029254386353131L;
        legacySeed = 1394759132691302L;
    }

    public void updateData(boolean rx, boolean rl, long xh, long xl, long ls) {
        rngActive_Xoroshiro = rx;
        rngActive_Legacy = rl;
        xoroshiroSeedHi = xh;
        xoroshiroSeedLo = xl;
        legacySeed = ls;
    }
}
