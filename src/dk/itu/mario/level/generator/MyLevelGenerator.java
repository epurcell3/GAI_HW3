package dk.itu.mario.level.generator;

import java.util.Random;

import dk.itu.mario.MarioInterface.GamePlay;
import dk.itu.mario.MarioInterface.LevelGenerator;
import dk.itu.mario.MarioInterface.LevelInterface;
import dk.itu.mario.level.GenerationValues;
import dk.itu.mario.level.MyLevel;


public class MyLevelGenerator extends CustomizedLevelGenerator implements LevelGenerator{


    private GenerationValues values;

	public LevelInterface generateLevel(GamePlay playerMetrics) {
          values = new GenerationValues(playerMetrics);

		LevelInterface level = new MyLevel(320,15,new Random().nextLong(),1,LevelInterface.TYPE_OVERGROUND,playerMetrics, values);
		return level;
	}

	@Override
	public LevelInterface generateLevel(String detailedInfo) {
		// TODO Auto-generated method stub
		return null;
	}


}
