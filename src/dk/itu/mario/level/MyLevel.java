package dk.itu.mario.level;

import java.util.ArrayList;
import java.util.Random;

import dk.itu.mario.MarioInterface.GamePlay;
import dk.itu.mario.MarioInterface.LevelInterface;
import dk.itu.mario.engine.sprites.Enemy;
import dk.itu.mario.engine.sprites.SpriteTemplate;


public class MyLevel extends Level{
	//Store information about the level
	 public   int ENEMIES = 0; //the number of enemies the level contains
	 public   int BLOCKS_EMPTY = 0; // the number of empty blocks
	 public   int BLOCKS_COINS = 0; // the number of coin blocks
	 public   int BLOCKS_POWER = 0; // the number of power blocks
	 public   int COINS = 0; //These are the coins in boxes that Mario collect


	 	private static Random levelSeedRandom = new Random();
	    public static long lastSeed;

	    Random random;

        private GenerationValues values;
	    private int difficulty;
	    private int type;
		private int gaps;
		
		private int[][] heightmap;
		private int[] highestlevel;
		
		public MyLevel(int width, int height)
	    {
			super(width, height);
			heightmap = new int[width][2];
			highestlevel = new int[width];
	    }


		public MyLevel(int width, int height, long seed, int difficulty, int type, GamePlay playerMetrics, GenerationValues values)
	    {
	        this(width, height);
            this.values = values;
	        creat(seed, difficulty, type);
	    }

	    public void creat(long seed, int difficulty, int type)
	    {
	        this.type = type;
	        this.difficulty = difficulty;

	        lastSeed = seed;
	        random = new Random(seed);

	        //create the start location
	        int length = 0;
	        length += setFloor(0, width, true);

	        //create all of the medium sections
	        while (length < width - 64)
	        {	
	        	length += setFloor(length, width - length, false);
	        }
        	
	        //set the end piece
	        int floor = height - 1 - random.nextInt(4);

	        xExit = length + 8;
	        yExit = floor;

	        // fills the end piece
	        for (int x = length; x < width; x++)
	        {
	            heightmap[x][0] = floor;
	            heightmap[x][1] = -1;
	            highestlevel[x] = floor;
	        }
	        
	        //Set hills
    		int nclusters = (int)(width / values.getHillClusterSize() * values.getHillCoeff());
	        for (int i = 0; i < nclusters; i++)
	        {
        		int xo = random.nextInt(xExit - 64) + 4;
        		int nhills = (int)(random.nextGaussian() * values.getAvgHillsInCluster()/2) + values.getAvgHillsInCluster();
        		for (int j = 0; j < nhills; j++)
        		{
        			//Pick an unused x as a starting point.
        			int x;
                    int t = 0;
                    boolean toContinue = false;
        			do {
                        do{
        				    x = (int)(random.nextGaussian() * values.getHillClusterSize()) + xo;
                        }while (x < 0);
                        t++;
                        if(t > 50){
                            toContinue = true;
                            break;
                        }

        			} while (heightmap[x][1] != -1 && !toContinue);
        			if(toContinue)
        				continue;
        			if (random.nextInt(20) == 0) //5% chance to spawn a tube
        			{
        				heightmap[x][1] = -2;
        				continue;
        			}
        			//Pick an unused length l where x+l isn't used.
        			int l;
                    t = 0;
        			do {
        				l = random.nextInt(5) + 3;
                        t++;
                        if(t > 50){
                            toContinue = true;
                            break;
                        }
        			} while (heightmap[x+l][1] != -1 && ! toContinue);
        			//Pick a height
                    if(toContinue){
                        continue;
                    }
        			int lev = highestlevel[x];
        			for (int k = 0; k < l; k++)
        			{
        				if (highestlevel[x+k] < lev) //silly counting downwards...
        					lev = highestlevel[x+k];
        			}
        			int h = lev - random.nextInt(3) - 2;
        			//Set the heights
        			heightmap[x][1] = h;
        			heightmap[x+l][1] = h;
        			for (int k = 0; k < l; k++)
        			{
        				highestlevel[x+k] = heightmap[x][1];
        			}
        		}
	        }
	        
	        //Set holes
	        for (int x = 10; x < xExit - 8; x++)
	        {
	        	boolean hole = random.nextDouble() * 2 < values.getHoleCoeff();
	        	if (hole) {
	        		gaps++;
	    	    	//jl: jump length
	    	        int jl = random.nextInt(2) + 2;
	    	        if (heightmap[x-1][0] < heightmap[x+jl+1][0])
	    	        	continue;
	    	        for (int k = 0; k < jl; k++) {
	    	        	heightmap[x+k][0] = height + 1;
	    	        }
	    	        
	    	        x += jl + 3;
	        	}
	        }
	        
	        fillMap();

	        if (type == LevelInterface.TYPE_CASTLE || type == LevelInterface.TYPE_UNDERGROUND)
	        {
	            int ceiling = 0;
	            int run = 0;
	            for (int x = 0; x < width; x++)
	            {
	                if (run-- <= 0 && x > 4)
	                {
	                    ceiling = random.nextInt(4);
	                    run = random.nextInt(4) + 4;
	                }
	                for (int y = 0; y < height; y++)
	                {
	                    if ((x > 4 && y <= ceiling) || x < 1)
	                    {
	                        setBlock(x, y, GROUND);
	                    }
	                }
	            }
	        }

	        fixWalls();

	    }
	    
	    private int setFloor(int xo, int maxLength, boolean safe)
	    {
	    	
	    	int adjuster = Math.abs((int)(random.nextGaussian() * values.getHillClusterSize()/2));
	    	if (adjuster < -1 * values.getHillClusterSize() + 4)
	    		adjuster = -1 * values.getHillClusterSize() + 4;
	        int length = adjuster + values.getHillClusterSize() * 2;
	        
	        if (safe)
	        	length = 10 + random.nextInt(5);

	        if (length > maxLength)
	        	length = maxLength;

	        int floor = height - 1 - random.nextInt(4);

	        //runs from the specified x position to the length of the segment
	        for (int x = xo; x < xo + length; x++)
	        {
	            heightmap[x][0] = floor;
	            heightmap[x][1] = -1;
	            highestlevel[x] = floor;
	        }
	        return length;
	    }
	    
	    private void fillMap()
	    {
	    	//fill floor
	    	int ncoins = 0;
	    	int nblocks = 0;
	    	for (int x = 0; x < width; x++) {
	    		for (int y = 0; y < height; y++) {
	    			
	    			if (y >= heightmap[x][0]) {
	                    setBlock(x, y, GROUND);
	                    
	    			}
	    		}
	    		if (ncoins == 0)
	    		{
		    		boolean coins = random.nextDouble() * 2 < values.getCoinCoeff();
	                if (coins) {
	                	ncoins = Math.abs((int)(random.nextGaussian() * values.getAvgCoinsInCluster()));
	                }
	    		}
	    		else
	    		{
	    			if (heightmap[x][0] < height && highestlevel[x] != heightmap[x][0] - 2 && x < xExit - 20 && x > 10)
	    			{
	    				ncoins--;
	    				if (highestlevel[x] == -1)
	    					setBlock(x, heightmap[x][0] - 2, COIN);
	    				else
	    					setBlock(x, highestlevel[x] -2, COIN);
	    				COINS++;
	    			}
	    		}
	    		if (nblocks == 0)
	    		{
	    			boolean blocks = random.nextDouble() * 2 < values.getBricksCoeff();
	    			if (blocks) {
	    				nblocks = Math.abs((int)(random.nextGaussian() * values.getAvgBlocksInRow()));
	    				if (nblocks < 2)
	    					nblocks = 0;
	    			}
	    		}
	    		else
	    		{
	    			if (x < xExit -20 && x > 10 && heightmap[x][0] < height && heightmap[x-1][0] < height && heightmap[x+1][0] < height &&
	    					highestlevel[x] == heightmap[x][0])
	    			{
	    				nblocks--;
	    				if (random.nextInt(3) == 0)
                        {
                            if (random.nextInt(4) == 0)
                            {
                                setBlock(x, heightmap[x][0] - 4, BLOCK_POWERUP);
                                BLOCKS_POWER++;
                            }
                            else
                            {	//the fills a block with a hidden coin
                                setBlock(x, heightmap[x][0] - 4, BLOCK_COIN);
                                BLOCKS_COINS++;
                            }
                        }
                        else if (random.nextInt(4) == 0)
                        {
                            if (random.nextInt(4) == 0)
                            {
                                setBlock(x, heightmap[x][0] - 4, (byte) (2 + 1 * 16));
                            }
                            else
                            {
                                setBlock(x, heightmap[x][0] - 4, (byte) (1 + 1 * 16));
                            }
                        }
                        else
                        {
                            setBlock(x, heightmap[x][0] - 4, BLOCK_EMPTY);
                            BLOCKS_EMPTY++;
                        }
	    			}
	    		}
	    	}
	    	
	    	//fill in hills
	    	boolean[] used = new boolean[width];
	    	for (int i = 0; i < width; i++)
	    		used[i] = false;
	    	for (int xo = 0; xo < width; xo++) {
	    		if (heightmap[xo][1] > 0 && !used[xo]) {
	    			int x = xo;
	    			do {
	    				for (int y = heightmap[xo][1]; y < heightmap[x][0]; y++) {
	    					int xx = 5;
                            if (x == xo) xx = 4;
                            else if (heightmap[x+1][1] == heightmap[xo][1]) xx = 6;
                            int yy = 9;
                            if (y == heightmap[xo][1]) yy = 8;

                            if (getBlock(x, y) == 0)
                            {
                                setBlock(x, y, (byte) (xx + yy * 16));
                            }
                            else
                            {
                                if (getBlock(x, y) == HILL_TOP_LEFT) setBlock(x, y, HILL_TOP_LEFT_IN);
                                if (getBlock(x, y) == HILL_TOP_RIGHT) setBlock(x, y, HILL_TOP_RIGHT_IN);
                            }
	    				}
	    				x++;
	    			} while (heightmap[x][1] != heightmap[xo][1]);
	    			used[x] = true;
	    		}
	    		else if (heightmap[xo][1] == -2) { //spawn tube
	    			int tubeHeight = heightmap[xo][0] - random.nextInt(2) - 2;
	    	        int xTube = xo + 1 + random.nextInt(4);
	    	        for (int x = xo; x <= xo + 3; x++)
	    	        {
	    	            if (x > xTube + 1)
	    	            {
	    	                xTube += 3 + random.nextInt(4);
	    	                tubeHeight = heightmap[xo][0] - random.nextInt(2) - 2;
	    	            }
	    	            if (xTube >= xo + heightmap[xo][0] - 2) xTube += 10;

	    	            if (x == xTube && random.nextInt(11) < difficulty + 1 && values.getBiome().biomeName() != "Hilly")
	    	            {
	    	                setSpriteTemplate(x, tubeHeight, new SpriteTemplate(Enemy.ENEMY_FLOWER, false));
	    	                ENEMIES++;
	    	            }

	    	            for (int y = 0; y < heightmap[xo][0]; y++)
	    	            {
    	                    if ((x == xTube || x == xTube + 1) && y >= tubeHeight)
    	                    {
    	                        int xPic = 10 + x - xTube;

    	                        if (y == tubeHeight)
    	                        {
    	                        	//tube top
    	                            setBlock(x, y, (byte) (xPic + 0 * 16));
    	                        }
    	                        else
    	                        {
    	                        	//tube side
    	                            setBlock(x, y, (byte) (xPic + 1 * 16));
    	                        }
    	                    }
	    	            }
	    	        }
	    		}
	    	}
	    	
	    	int nEnemyGroups = (int)(width / values.getEnemyClusterSize() * values.getEnemyCoeff());
	    	for (int i = 0; i < nEnemyGroups; i++) {
	    		int x0 = random.nextInt(xExit - 64) + 15;
	    		int n = values.getEnemyClusterTypes().size();
	    		if (n > 0)
	    		{
		    		ArrayList<Integer> enemies = values.getEnemyClusterTypes().get(random.nextInt(n));
		    		for (int j = 0; j < enemies.size(); j++) {
		    			int x = (int)(random.nextGaussian() * values.getEnemyClusterSize()) + x0;
		    			if (x < 15) x = 15;
		    			if (x > xExit - 32) x = xExit - 32;
		    			int y;
		    			for (y = 0; y < heightmap[x][0]; y++) {
		    				if (getBlock(x,y) != 0) {
		    					if (random.nextInt(3) == 0)
		    						break;
		    				}
		    			}
		    			if (enemies.get(j) != 9) {
			    			setSpriteTemplate(x, y - 1, EnemyMap.getEnemyFromInt(enemies.get(j)));
		                	ENEMIES++;
		    			}
		    			else {
		    				int cannonHeight = y - random.nextInt(5);
		    				for (int y0 = cannonHeight; y0 < y ; y0++) {
		    					if (y0 == cannonHeight)
		                        {
		                            setBlock(x, y0, (byte) (14 + 0 * 16));
		                        }
		                        else if (y0 == cannonHeight + 1)
		                        {
		                            setBlock(x, y0, (byte) (14 + 1 * 16));
		                        }
		                        else
		                        {
		                            setBlock(x, y0, (byte) (14 + 2 * 16));
		                        }
		    				}
		    			}
		    		}
	    		}
	    		else {
	    			int nEnemies = (int)(random.nextGaussian() * values.getAvgEnemiesInCuster()/2) + values.getAvgEnemiesInCuster();
	    			for (int j = 0; j < nEnemies; j++) {
	    				int x = (int)(random.nextGaussian() * values.getEnemyClusterSize()) + x0;
		    			if (x < 15) x = 15;
		    			if (x > xExit - 32) x = xExit - 32;
		    			for (int y = 0; y < heightmap[x][0]; y++) {
		    				if (getBlock(x,y) != 0) {
		    					if (random.nextInt(3) == 0)
		    					{
		    						int etype = random.nextInt(4);

			    	                if (difficulty < 1)
			    	                {
			    	                    etype = Enemy.ENEMY_GOOMBA;
			    	                }
			    	                else if (difficulty < 3)
			    	                {
			    	                    etype = random.nextInt(3);
			    	                }
		    		    			setSpriteTemplate(x, y - 1, new SpriteTemplate(etype, random.nextInt(35) < difficulty));
		    	                	ENEMIES++;
		    						break;
		    					}
		    				}
		    			}
	    			}
	    		}
	    	}
	    }



	    private void fixWalls()
	    {
	        boolean[][] blockMap = new boolean[width + 1][height + 1];

	        for (int x = 0; x < width + 1; x++)
	        {
	            for (int y = 0; y < height + 1; y++)
	            {
	                int blocks = 0;
	                for (int xx = x - 1; xx < x + 1; xx++)
	                {
	                    for (int yy = y - 1; yy < y + 1; yy++)
	                    {
	                        if (getBlockCapped(xx, yy) == GROUND){
	                        	blocks++;
	                        }
	                    }
	                }
	                blockMap[x][y] = blocks == 4;
	            }
	        }
	        blockify(this, blockMap, width + 1, height + 1);
	    }

	    private void blockify(Level level, boolean[][] blocks, int width, int height){
	        int to = 0;
	        if (type == LevelInterface.TYPE_CASTLE)
	        {
	            to = 4 * 2;
	        }
	        else if (type == LevelInterface.TYPE_UNDERGROUND)
	        {
	            to = 4 * 3;
	        }

	        boolean[][] b = new boolean[2][2];

	        for (int x = 0; x < width; x++)
	        {
	            for (int y = 0; y < height; y++)
	            {
	                for (int xx = x; xx <= x + 1; xx++)
	                {
	                    for (int yy = y; yy <= y + 1; yy++)
	                    {
	                        int _xx = xx;
	                        int _yy = yy;
	                        if (_xx < 0) _xx = 0;
	                        if (_yy < 0) _yy = 0;
	                        if (_xx > width - 1) _xx = width - 1;
	                        if (_yy > height - 1) _yy = height - 1;
	                        b[xx - x][yy - y] = blocks[_xx][_yy];
	                    }
	                }

	                if (b[0][0] == b[1][0] && b[0][1] == b[1][1])
	                {
	                    if (b[0][0] == b[0][1])
	                    {
	                        if (b[0][0])
	                        {
	                            level.setBlock(x, y, (byte) (1 + 9 * 16 + to));
	                        }
	                        else
	                        {
	                            // KEEP OLD BLOCK!
	                        }
	                    }
	                    else
	                    {
	                        if (b[0][0])
	                        {
	                        	//down grass top?
	                            level.setBlock(x, y, (byte) (1 + 10 * 16 + to));
	                        }
	                        else
	                        {
	                        	//up grass top
	                            level.setBlock(x, y, (byte) (1 + 8 * 16 + to));
	                        }
	                    }
	                }
	                else if (b[0][0] == b[0][1] && b[1][0] == b[1][1])
	                {
	                    if (b[0][0])
	                    {
	                    	//right grass top
	                        level.setBlock(x, y, (byte) (2 + 9 * 16 + to));
	                    }
	                    else
	                    {
	                    	//left grass top
	                        level.setBlock(x, y, (byte) (0 + 9 * 16 + to));
	                    }
	                }
	                else if (b[0][0] == b[1][1] && b[0][1] == b[1][0])
	                {
	                    level.setBlock(x, y, (byte) (1 + 9 * 16 + to));
	                }
	                else if (b[0][0] == b[1][0])
	                {
	                    if (b[0][0])
	                    {
	                        if (b[0][1])
	                        {
	                            level.setBlock(x, y, (byte) (3 + 10 * 16 + to));
	                        }
	                        else
	                        {
	                            level.setBlock(x, y, (byte) (3 + 11 * 16 + to));
	                        }
	                    }
	                    else
	                    {
	                        if (b[0][1])
	                        {
	                        	//right up grass top
	                            level.setBlock(x, y, (byte) (2 + 8 * 16 + to));
	                        }
	                        else
	                        {
	                        	//left up grass top
	                            level.setBlock(x, y, (byte) (0 + 8 * 16 + to));
	                        }
	                    }
	                }
	                else if (b[0][1] == b[1][1])
	                {
	                    if (b[0][1])
	                    {
	                        if (b[0][0])
	                        {
	                        	//left pocket grass
	                            level.setBlock(x, y, (byte) (3 + 9 * 16 + to));
	                        }
	                        else
	                        {
	                        	//right pocket grass
	                            level.setBlock(x, y, (byte) (3 + 8 * 16 + to));
	                        }
	                    }
	                    else
	                    {
	                        if (b[0][0])
	                        {
	                            level.setBlock(x, y, (byte) (2 + 10 * 16 + to));
	                        }
	                        else
	                        {
	                            level.setBlock(x, y, (byte) (0 + 10 * 16 + to));
	                        }
	                    }
	                }
	                else
	                {
	                    level.setBlock(x, y, (byte) (0 + 1 * 16 + to));
	                }
	            }
	        }
	    }
	    
	    public RandomLevel clone() throws CloneNotSupportedException {

	    	RandomLevel clone=new RandomLevel(width, height);

	    	clone.xExit = xExit;
	    	clone.yExit = yExit;
	    	byte[][] map = getMap();
	    	SpriteTemplate[][] st = getSpriteTemplate();
	    	
	    	for (int i = 0; i < map.length; i++)
	    		for (int j = 0; j < map[i].length; j++) {
	    			clone.setBlock(i, j, map[i][j]);
	    			clone.setSpriteTemplate(i, j, st[i][j]);
	    	}
	    	clone.BLOCKS_COINS = BLOCKS_COINS;
	    	clone.BLOCKS_EMPTY = BLOCKS_EMPTY;
	    	clone.BLOCKS_POWER = BLOCKS_POWER;
	    	clone.ENEMIES = ENEMIES;
	    	clone.COINS = COINS;
	    	
	        return clone;

	      }


}
