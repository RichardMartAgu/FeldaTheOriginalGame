package com.svalero.game.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.svalero.game.managers.SpriteManager;

import static com.svalero.game.utils.Constants.ENEMY_HEIGHT;
import static com.svalero.game.utils.Constants.ENEMY_WIDTH;

public class Enemy extends Character {

    public enum EnemyType {
        green, gray, yellow
    }

    protected EnemyType type;
    public boolean isDying = false;

    Animation<TextureRegion> rightAnimation, leftAnimation,idleAnimation,dieAnimation;

    public Enemy(Vector2 position, int hearts) {
        super(position, hearts);

    }

    @Override
    public void update(float dt, SpriteManager spriteManager) {
    }

    @Override
    public void die(SpriteManager spriteManager) {
        dead = true;
        spriteManager.removeEnemy(this);
    }
    public void reduceLife(int amount) {
        if (isDying) return;
        hearts -= amount;
        if (hearts <= 0) {
            isDying = true;
        }
    }

}
