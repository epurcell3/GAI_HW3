package dk.itu.mario.level.Biomes;

import dk.itu.mario.level.GenerationValues;

import java.util.ArrayList;

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
        generationValues.setAvgBlocksInRow(5);
        generationValues.setTotalSaturation(1.4);
        setEnemyClusters(generationValues);
    }
    private void setEnemyClusters(GenerationValues generationValues){
        ArrayList<ArrayList<Integer>>  clusters = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> cluster1 = new ArrayList<Integer>();
        cluster1.add(0);
        cluster1.add(1);
        clusters.add(cluster1);
        ArrayList<Integer> cluster2 = new ArrayList<Integer>();
        cluster2.add(1);
        cluster2.add(2);
        clusters.add(cluster2);
        ArrayList<Integer> cluster3 = new ArrayList<Integer>();
        cluster3.add(0);
        cluster3.add(0);
        cluster3.add(1);
        clusters.add(cluster3);
        ArrayList<Integer> cluster4 = new ArrayList<Integer>();
        cluster4.add(0);
        cluster4.add(0);
        cluster4.add(1);
        cluster4.add(2);
        clusters.add(cluster4);
        ArrayList<Integer> cluster5 = new ArrayList<Integer>();
        cluster5.add(0);
        cluster5.add(0);
        clusters.add(cluster5);
        generationValues.setEnemyClusterTypes(clusters);
    }
    public String biomeName(){
        return "Hilly";
    }

    @Override
    public boolean is(GenerationValues values) {
        if(values.getHillCoeff() > ((values.getCoinCoeff() + values.getEnemyCoeff() +values.getBricksCoeff()) /2 )){
            return  true;
        }
        return  false;
    }
}
