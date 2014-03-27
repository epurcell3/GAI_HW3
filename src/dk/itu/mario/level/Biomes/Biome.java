package dk.itu.mario.level.Biomes;

import dk.itu.mario.level.GenerationValues;

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 3/26/14
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Biome {
    public abstract void changeToBiome(GenerationValues generationValues);
    public abstract String biomeName();
    public abstract boolean is(GenerationValues values);
}
