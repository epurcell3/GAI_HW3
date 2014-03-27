package dk.itu.mario.level.Biomes;

import dk.itu.mario.level.GenerationValues;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 3/26/14
 * Time: 10:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class Normal implements Biome {
    @Override
    public void changeToBiome(GenerationValues generationValues) {
        generationValues.setTotalSaturation(1.1);
        setEnemyClusters(generationValues);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String biomeName() {
        return "Normal";  //To change body of implemented methods use File | Settings | File Templates.
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
        ArrayList<Integer> cluster6 = new ArrayList<Integer>();
        cluster6.add(9);
        clusters.add(cluster6);
        ArrayList<Integer> cluster7 = new ArrayList<Integer>();
        cluster7.add(5);
        cluster7.add(6);
        clusters.add(cluster7);
        generationValues.setEnemyClusterTypes(clusters);



    }
}
