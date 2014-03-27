package dk.itu.mario.level;

import dk.itu.mario.MarioInterface.GamePlay;
import dk.itu.mario.level.Biomes.Biome;
import dk.itu.mario.level.Biomes.Enemies;
import dk.itu.mario.level.Biomes.Hilly;
import dk.itu.mario.level.Biomes.Normal;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 3/26/14
 * Time: 2:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class GenerationValues {
    private double enemyCoeff;
    private double coinCoeff;
    private double hillCoeff;
    private double holeCoeff;
    private double bricksCoeff;
    private int enemyClusterSize;
    private int avgEnemiesInCuster;
    private int coinClusterSize;
    private int avgCoinsInCluster;
    private int hillClusterSize;
    private int avgHillsInCluster;
    private int avgBlocksInRow;
    private double totalSaturation;
    ArrayList<ArrayList<Integer>> enemyClusterTypes;

    public Biome biome;
    public GenerationValues(GamePlay playerMetrics){
        enemyClusterTypes = new ArrayList<ArrayList<Integer>>();

        if(playerMetrics.totalTime == 0 ||  playerMetrics.totalEmptyBlocks == 0 || playerMetrics.totalEnemies == 0 || playerMetrics.totalCoins == 0){
            enemyCoeff = 0.25;
            coinCoeff = 0.2;
            hillCoeff = 0.2;
            bricksCoeff = 0.25;
            holeCoeff = 0.1;
            totalSaturation = 1.0;
            enemyClusterSize = 3;
            avgEnemiesInCuster = 3;
            coinClusterSize = 8;
            avgCoinsInCluster = 5;
            hillClusterSize = 7;
            avgHillsInCluster = 2;
            avgBlocksInRow = 4;
        }
        else{
            enemyCoeff = getEnemiesKilled(playerMetrics);
            coinCoeff = playerMetrics.coinsCollected;
            hillCoeff = playerMetrics.jumpsNumber;
            holeCoeff = playerMetrics.jumpsNumber / (playerMetrics.timesOfDeathByFallingIntoGap + 1);
            totalSaturation = 1.05;
            enemyClusterSize = 3;
            avgEnemiesInCuster = 3;
            coinClusterSize = 8;
            avgCoinsInCluster = 5;
            hillClusterSize = 7;
            avgHillsInCluster = 2;
            avgBlocksInRow = 4;

            fixCoeffs(playerMetrics);
        }
        identifyBiomesAndAdjust();
        normalizeCoeffs(totalSaturation);
    }
    private double coeffTotal(){
        return enemyCoeff + coinCoeff + hillCoeff + holeCoeff + bricksCoeff;
    }
    public void normalizeCoeffs(double toValue){
        double total = coeffTotal();
        enemyCoeff = (enemyCoeff / total) * toValue;
        coinCoeff = (coinCoeff / total) * toValue;
        hillCoeff = (hillCoeff / total) * toValue;
        holeCoeff = (holeCoeff / total) * toValue;
        bricksCoeff = (bricksCoeff / total) * toValue;
    }
    private int getEnemiesKilled(GamePlay playerMetrics){
        return playerMetrics.ArmoredTurtlesKilled + playerMetrics.CannonBallKilled + playerMetrics.ChompFlowersKilled + playerMetrics.GoombasKilled + playerMetrics.RedTurtlesKilled + playerMetrics.GreenTurtlesKilled + playerMetrics.JumpFlowersKilled;
    }
    private void fixCoeffs(GamePlay playerMetrics){
        bricksCoeff = (playerMetrics.percentageBlocksDestroyed * Integer.valueOf(playerMetrics.totalEmptyBlocks).doubleValue()) + playerMetrics.powerBlocksDestroyed +  playerMetrics.coinBlocksDestroyed;
        hillCoeff += playerMetrics.aimlessJumps * 2;
        hillCoeff /= (playerMetrics.totalTime/2);
        holeCoeff -=  (playerMetrics.aimlessJumps );
        holeCoeff  /= 10;
        if (holeCoeff < 0)
        {
            holeCoeff = 0;
        }
        enemyCoeff /= (playerMetrics.totalEnemies /2);
        coinCoeff -= (playerMetrics.coinBlocksDestroyed / 2);
        coinCoeff /= (playerMetrics.totalCoins * 3 / 2);
        bricksCoeff /= ((playerMetrics.totalEmptyBlocks + playerMetrics.totalCoinBlocks + playerMetrics.totalpowerBlocks) /2);
    }
    private void identifyBiomesAndAdjust(){
        Hilly hilly = new Hilly();
        if(hilly.is(this)){
            biome = hilly;
            biome.changeToBiome(this);
            return;
        }
        Enemies enemies = new Enemies();
        if(enemies.is(this)){
            biome = enemies;
            biome.changeToBiome(this);
            return;
        }
        biome = new Normal();
    }
    public double getEnemyCoeff() {
        return enemyCoeff;
    }

    public double getCoinCoeff() {
        return coinCoeff;
    }

    public double getHillCoeff() {
        return hillCoeff;
    }

    public double getHoleCoeff() {
        return holeCoeff;
    }

    public double getBricksCoeff() {
        return bricksCoeff;
    }

    public int getEnemyClusterSize() {
        return enemyClusterSize;
    }

    public int getAvgEnemiesInCuster() {
        return avgEnemiesInCuster;
    }

    public int getCoinClusterSize() {
        return coinClusterSize;
    }

    public int getAvgCoinsInCluster() {
        return avgCoinsInCluster;
    }

    public int getHillClusterSize() {
        return hillClusterSize;
    }

    public int getAvgHillsInCluster() {
        return avgHillsInCluster;
    }

    public int getAvgBlocksInRow() {
        return avgBlocksInRow;
    }

    public Biome getBiome() {
        return biome;
    }

    public void setBiome(Biome biome) {
        this.biome = biome;
    }

    public void setEnemyCoeff(double enemyCoeff) {
        this.enemyCoeff = enemyCoeff;
    }

    public void setCoinCoeff(double coinCoeff) {
        this.coinCoeff = coinCoeff;
    }

    public void setHillCoeff(double hillCoeff) {
        this.hillCoeff = hillCoeff;
    }

    public void setHoleCoeff(double holeCoeff) {
        this.holeCoeff = holeCoeff;
    }

    public void setBricksCoeff(double bricksCoeff) {
        this.bricksCoeff = bricksCoeff;
    }

    public void setEnemyClusterSize(int enemyClusterSize) {
        this.enemyClusterSize = enemyClusterSize;
    }

    public void setAvgEnemiesInCuster(int avgEnemiesInCuster) {
        this.avgEnemiesInCuster = avgEnemiesInCuster;
    }

    public void setCoinClusterSize(int coinClusterSize) {
        this.coinClusterSize = coinClusterSize;
    }

    public void setAvgCoinsInCluster(int avgCoinsInCluster) {
        this.avgCoinsInCluster = avgCoinsInCluster;
    }

    public void setHillClusterSize(int hillClusterSize) {
        this.hillClusterSize = hillClusterSize;
    }

    public void setAvgHillsInCluster(int avgHillsInCluster) {
        this.avgHillsInCluster = avgHillsInCluster;
    }

    public void setAvgBlocksInRow(int avgBlocksInRow) {
        this.avgBlocksInRow = avgBlocksInRow;
    }

    public double getTotalSaturation() {
        return totalSaturation;
    }

    public void setTotalSaturation(double totalSaturation) {
        this.totalSaturation = totalSaturation;
    }

    public ArrayList<ArrayList<Integer>> getEnemyClusterTypes() {
        return enemyClusterTypes;
    }

    public void setEnemyClusterTypes(ArrayList<ArrayList<Integer>> enemyClusterTypes) {
        this.enemyClusterTypes = enemyClusterTypes;
    }
}
