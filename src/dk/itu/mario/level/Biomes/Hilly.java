package dk.itu.mario.level.Biomes;

import dk.itu.mario.level.GenerationValues;

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 3/26/14
 * Time: 2:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class Hilly implements Biome {
    public Hilly(){

    }

    public void changeToBiome(GenerationValues generationValues) {
        generationValues.setHillCoeff(generationValues.getHillCoeff() * 3);
        generationValues.setHoleCoeff(generationValues.getHoleCoeff() * 3 / 2);
        generationValues.setCoinCoeff(generationValues.getCoinCoeff()  / 2);
        generationValues.setEnemyCoeff(generationValues.getEnemyCoeff() / 2);
        generationValues.setBricksCoeff(generationValues.getBricksCoeff() / 2);
        generationValues.setAvgEnemiesInCuster(1);
        generationValues.setCoinClusterSize(6);
        generationValues.setAvgCoinsInCluster(3);
        generationValues.setHillClusterSize(8);
        generationValues.setAvgHillsInCluster(6);
        generationValues.setAvgeBlocksToRow(5);
        generationValues.setTotalSaturation(1.4);
    }

    public String biomeName(){
        return "Hilly";
    }
}
