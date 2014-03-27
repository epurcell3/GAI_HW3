package dk.itu.mario.level.Biomes;

import dk.itu.mario.level.GenerationValues;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 3/27/14
 * Time: 5:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class Greedy implements Biome {
    @Override
    public void changeToBiome(GenerationValues generationValues) {
        generationValues.setHillCoeff(generationValues.getHillCoeff() / 2);
        generationValues.setHoleCoeff(generationValues.getHoleCoeff() /5);
        generationValues.setCoinCoeff(generationValues.getCoinCoeff()  * 5);
        generationValues.setEnemyCoeff(generationValues.getEnemyCoeff() / 2);
        generationValues.setBricksCoeff(generationValues.getBricksCoeff());
        generationValues.setAvgEnemiesInCuster(1);
        generationValues.setCoinClusterSize(15);
        generationValues.setAvgCoinsInCluster(12);
        generationValues.setHillClusterSize(10);
        generationValues.setAvgHillsInCluster(1);
        generationValues.setAvgBlocksInRow(8);
        generationValues.setTotalSaturation(1.4);
        setEnemyClusters(generationValues);
    }

    @Override
    public String biomeName() {
        return "Greedy";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean is(GenerationValues values) {
        if(values.getCoinCoeff() > (values.getEnemyCoeff() + values.getHillCoeff())){
            return true;
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
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
}
