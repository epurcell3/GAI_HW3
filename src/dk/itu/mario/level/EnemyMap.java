package dk.itu.mario.level;

import dk.itu.mario.engine.sprites.Enemy;
import dk.itu.mario.engine.sprites.Sprite;
import dk.itu.mario.engine.sprites.SpriteTemplate;
import dk.itu.mario.scene.LevelScene;

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 3/26/14
 * Time: 11:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class EnemyMap {

    public static SpriteTemplate getEnemyFromInt(int enemyNum){
        if(enemyNum == 0){
            return new SpriteTemplate( 2, false); //goomba
        }
        else if(enemyNum == 1){
            return new SpriteTemplate(1, false); //green koopa
        }
        else if(enemyNum == 2){
            return new SpriteTemplate(0, false); //red koopa
        }
        else if(enemyNum == 3){
            return new SpriteTemplate(1, false); //green koopa
        }
        else if(enemyNum == 4){
            return new SpriteTemplate(3, false); //spiky
        }
        else if(enemyNum == 5){
            return new SpriteTemplate(1, true); //winged green koopa
        }
        else if(enemyNum == 6){
            return new SpriteTemplate(0, true); //winged red koopa
        }
        else if(enemyNum == 7){
            return new SpriteTemplate(2, true); //winged goomba
        }
        else if(enemyNum == 8){
            return new SpriteTemplate(3, true); //winged spikey
        }
        return null;  // 9 is cannon and checked before this is called
    }
}

