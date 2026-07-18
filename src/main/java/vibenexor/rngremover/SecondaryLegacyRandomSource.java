package vibenexor.rngremover;

import net.minecraft.util.ThreadingDetector;

import java.util.concurrent.atomic.AtomicLong;

public class SecondaryLegacyRandomSource {
    // this is literally just so mobs can get a unique uuid, basically a copy of all basic LegacyRandomSource
    // functions, plus generateUniqueSeed is from RandomSupport. it shouldn't affect anything other than the UUIDs

    private final AtomicLong seed = new AtomicLong();

    private static final AtomicLong SEED_UNIQUIFIER = new AtomicLong(8682522807148012L);

    public SecondaryLegacyRandomSource(final long seed) {
        this.setSeed(seed);
    }

    public int next(final int bits) {
        long oldSeed = this.seed.get();
        long newSeed = oldSeed * 25214903917L + 11L & 281474976710655L;
        if (!this.seed.compareAndSet(oldSeed, newSeed)) {
            throw ThreadingDetector.makeThreadingException("SecondaryLegacyRandomSource", null);
        } else {
            return (int)(newSeed >> 48 - bits);
        }
    }

    public void setSeed(final long seed) {
        if (!this.seed.compareAndSet(this.seed.get(), (seed ^ 25214903917L) & 281474976710655L)) {
            throw ThreadingDetector.makeThreadingException("SecondaryLegacyRandomSource", null);
        }
    }

    public long nextLong() {
        int upper = this.next(32);
        int lower = this.next(32);
        long shifted = (long)upper << 32;
        return shifted + lower;
    }

    public static long generateUniqueSeed() {
        return SEED_UNIQUIFIER.updateAndGet(current -> current * 1181783497276652981L) ^ System.nanoTime();
    }
}
