package dk.itu.mario.level;

import dk.itu.mario.MarioInterface.GamePlay;
import dk.itu.mario.level.Biomes.Biome;
import dk.itu.mario.level.Biomes.Hilly;

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
    private double avgeBlocksToRow;
    private double totalSaturation;
    public Biome biome;
    public GenerationValues(GamePlay playerMetrics){
        enemyCoeff = getEnemiesKilled(playerMetrics);
        coinCoeff = playerMetrics.coinsCollected;
        hillCoeff = playerMetrics.jumpsNumber;
        holeCoeff = playerMetrics.jumpsNumber / (playerMetrics.timesOfDeathByFallingIntoGap + 1);
        totalSaturation = 1.05;
        enemyClusterSize = 10;
        avgEnemiesInCuster = 3;
        coinClusterSize = 8;
        avgCoinsInCluster = 5;
        hillClusterSize = 16;
        avgHillsInCluster = 2;
        avgeBlocksToRow = 4;
        fixCoeffs(playerMetrics);
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
        coinCoeff /= (playerMetrics.totalCoins * 4 / 5);
        bricksCoeff /= ((playerMetrics.totalEmptyBlocks + playerMetrics.totalCoinBlocks + playerMetrics.totalpowerBlocks) /2);
    }
    private void identifyBiomesAndAdjust(){
        if(isHilly()){
            biome = new Hilly();
            biome.changeToBiome(this);
            return;
        }

    }
    private boolean isHilly(){
        if(hillCoeff > ((coinCoeff + enemyCoeff +bricksCoeff) /2 )){
            return  true;
        }
        return  false;
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

    public double getAvgeBlocksToRow() {
        return avgeBlocksToRow;
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

    public void setAvgeBlocksToRow(double avgeBlocksToRow) {
        this.avgeBlocksToRow = avgeBlocksToRow;
    }

    public double getTotalSaturation() {
        return totalSaturation;
    }

    public void setTotalSaturation(double totalSaturation) {
        this.totalSaturation = totalSaturation;
    }
}
