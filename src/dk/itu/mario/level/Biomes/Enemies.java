package dk.itu.mario.level.Biomes;

import dk.itu.mario.level.GenerationValues;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 3/27/14
 * Time: 12:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class Enemies implements Biome {
    @Override
    public void changeToBiome(GenerationValues generationValues) {
        generationValues.setHillCoeff(generationValues.getHillCoeff() /2 );
        generationValues.setHoleCoeff(generationValues.getHoleCoeff() / 5);
        generationValues.setCoinCoeff(generationValues.getCoinCoeff()  / 5);
        generationValues.setEnemyCoeff(generationValues.getEnemyCoeff() * 4);
        generationValues.setBricksCoeff(generationValues.getBricksCoeff() / 2);
        generationValues.setAvgEnemiesInCuster(6);
        generationValues.setCoinClusterSize(6);
        generationValues.setAvgCoinsInCluster(3);
        generationValues.setHillClusterSize(12);
        generationValues.setAvgHillsInCluster(2);
        generationValues.setAvgBlocksInRow(4);
        generationValues.setTotalSaturation(1.6);
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
        cluster3.add(4);
        clusters.add(cluster3);
        ArrayList<Integer> cluster4 = new ArrayList<Integer>();
        cluster4.add(6);
        clusters.add(cluster4);
        ArrayList<Integer> cluster5 = new ArrayList<Integer>();
        cluster5.add(7);
        clusters.add(cluster5);
        ArrayList<Integer> cluster6 = new ArrayList<Integer>();
        cluster6.add(9);
        clusters.add(cluster6);
        ArrayList<Integer> cluster7 = new ArrayList<Integer>();
        cluster7.add(5);
        cluster7.add(6);
        clusters.add(cluster7);
        ArrayList<Integer> cluster8 = new ArrayList<Integer>();
        cluster8.add(5);
        cluster8.add(6);
        cluster8.add(7);
        clusters.add(cluster8);
        generationValues.setEnemyClusterTypes(clusters);

    }

    @Override
    public String biomeName() {
        return "Enemies";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean is(GenerationValues values) {
        if(values.getEnemyCoeff() > (values.getHillCoeff() + values.getCoinCoeff()) * 1.2){
              return  true;
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
