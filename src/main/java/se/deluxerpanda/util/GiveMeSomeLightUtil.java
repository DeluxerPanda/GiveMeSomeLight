package se.deluxerpanda.util;

public class GiveMeSomeLightUtil {
    public static int getBlock(int lightmap) {
        return (lightmap >> 4) & 0xF;
    }

    public static int getSky(int lightmap) {
        return (lightmap >> 20) & 0xF;
    }

    public static int pack(int Block, int sky) {
        return (sky << 20) | (Block << 4);
    }
}
