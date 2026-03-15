package org.dm;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;

/**
 * @author Jire
 */
public final class Argon2Util {

    private Argon2Util() {
        // Private constructor to prevent instantiation
    }

    public static final Argon2Types DEFAULT_TYPE = Argon2Types.ARGON2id;

    public static final int DEFAULT_ITERATIONS = 2;
    public static final int DEFAULT_MEMORY = 65536;

    public static final int DEFAULT_PARALLELISM = Runtime.getRuntime().availableProcessors();

    private static final ThreadLocal<Argon2> threadLocal2i = ThreadLocal.withInitial(() ->
            Argon2Factory.create(Argon2Types.ARGON2i)
    );

    private static final ThreadLocal<Argon2> threadLocal2d = ThreadLocal.withInitial(() ->
            Argon2Factory.create(Argon2Types.ARGON2d)
    );

    private static final ThreadLocal<Argon2> threadLocal2id = ThreadLocal.withInitial(() ->
            Argon2Factory.create(Argon2Types.ARGON2id)
    );

    public static Argon2 get2i() {
        return threadLocal2i.get();
    }

    public static Argon2 get2d() {
        return threadLocal2d.get();
    }

    public static Argon2 get2id() {
        return threadLocal2id.get();
    }

    public static Argon2 forType(Argon2Types argon2Types) {
        switch (argon2Types) {
            case ARGON2d: return get2d();
            case ARGON2i: return get2i();
            case ARGON2id: return get2id();
            default: throw new IllegalArgumentException("Unknown Argon2 type: " + argon2Types);
        }
    }

    public static Argon2 getDefault() {
        return forType(DEFAULT_TYPE);
    }

    public static Argon2Types argon2Type(String password) {
        if (password == null) return null;

        if (password.startsWith("$argon2id$")) {
            return Argon2Types.ARGON2id;
        } else if (password.startsWith("$argon2i$")) {
            return Argon2Types.ARGON2i;
        } else if (password.startsWith("$argon2d$")) {
            return Argon2Types.ARGON2d;
        }
        return null;
    }

    public static Argon2 forHash(String hash) {
        Argon2Types type = argon2Type(hash);
        if (type == null) {
            throw new UnsupportedOperationException("Could not determine type for hash \"" + hash + "\"");
        }
        return forType(type);
    }
}