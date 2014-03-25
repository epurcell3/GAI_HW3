package dk.itu.mario.level.generator;

import java.util.Random;

import dk.itu.mario.MarioInterface.GamePlay;
import dk.itu.mario.MarioInterface.LevelGenerator;
import dk.itu.mario.MarioInterface.LevelInterface;
import dk.itu.mario.level.MyLevel;


public class MyLevelGenerator extends CustomizedLevelGenerator implements LevelGenerator{

    private double enemyCoeff;
    private double coinCoeff;
    private double hillCoeff;
    private double holeCoeff;
    private double bricksCoeff;

	public LevelInterface generateLevel(GamePlay playerMetrics) {
        enemyCoeff = getEnemiesKilled(playerMetrics);
        coinCoeff = playerMetrics.coinsCollected;
        hillCoeff = playerMetrics.jumpsNumber;
        holeCoeff = playerMetrics.jumpsNumber / (playerMetrics.timesOfDeathByFallingIntoGap + 1);
        fixCoeffs(playerMetrics);
        identifyBiomesAndAdjust();
        normalizeCoeffs(1.1);
		LevelInterface level = new MyLevel(320,15,new Random().nextLong(),1,LevelInterface.TYPE_OVERGROUND,playerMetrics);
		return level;
	}

	@Override
	public LevelInterface generateLevel(String detailedInfo) {
		// TODO Auto-generated method stub
		return null;
	}
    private double coeffTotal(){
        return enemyCoeff + coinCoeff + hillCoeff + holeCoeff;
    }
    public void normalizeCoeffs(double toValue){
        double total = coeffTotal();
        enemyCoeff = (enemyCoeff / total) * toValue;
        coinCoeff = (coinCoeff / total) * toValue;
        hillCoeff = (hillCoeff / total) * toValue;
        holeCoeff = (holeCoeff / total) * toValue;
    }
    private int getEnemiesKilled(GamePlay playerMetrics){
        return playerMetrics.ArmoredTurtlesKilled + playerMetrics.CannonBallKilled + playerMetrics.ChompFlowersKilled + playerMetrics.GoombasKilled + playerMetrics.RedTurtlesKilled + playerMetrics.GreenTurtlesKilled + playerMetrics.JumpFlowersKilled;
    }
    private void fixCoeffs(GamePlay playerMetrics){
        bricksCoeff = (playerMetrics.percentageBlocksDestroyed * Integer.valueOf(playerMetrics.totalEmptyBlocks).doubleValue()) + playerMetrics.powerBlocksDestroyed +  playerMetrics.coinBlocksDestroyed;
        hillCoeff += playerMetrics.aimlessJumps;
        hillCoeff /= (playerMetrics.totalTime/2);
        holeCoeff -=  (playerMetrics.aimlessJumps / 2);
        holeCoeff  /= 5;
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
            adjustForHilly();
            return;
        }

    }
    private boolean isHilly(){
        if(hillCoeff > ((coinCoeff + enemyCoeff +bricksCoeff) /2 )){
            return  true;
        }
        return  false;
    }
    private void adjustForHilly(){
        hillCoeff *= 3;
        holeCoeff  = holeCoeff * 3 / 2;
        coinCoeff /= 2;
        enemyCoeff /= 2;
        bricksCoeff /= 2;
    }


}
