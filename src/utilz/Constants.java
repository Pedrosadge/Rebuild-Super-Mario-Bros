package utilz;

import game.Game;

public class Constants {

	public static final float GRAVITY = 0.04f * Game.SCALE;
	public static final int ANI_SPEED = 20;

    public static class PlayerConstants {
		public static final int NORMAL = 0;
		public static final int BIG = 1;
		public static final int FIRE = 2;
		public static final int BIGFIRE = 3;


        public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int JUMPING = 2;
		public static final int FALLING = 3;
		public static final int HIT = 4;
		public static final int DEAD = 5;
		


        public static int GetSpriteAmount(int player_action) {
			switch (player_action) {
			case DEAD:
				return 1;
			case RUNNING:
				return 3;  
			case IDLE:
				return 1; 
			case HIT:
			case JUMPING:
			case FALLING:
			default:
				return 1;
			}
		}
    }

	public static class EnemyConstants {
		public static final int GOOMBA = 0;
		public static final int KOOPA = 1;

		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int HIT = 2;
		public static final int DEAD = 3;

		public static final int GOOMBA_WIDTH_DEFAULT = 32;
		public static final int GOOMBA_HEIGHT_DEFAULT = 32;
		public static final int GOOMBA_WIDTH = (int) (GOOMBA_WIDTH_DEFAULT * Game.SCALE);
		public static final int GOOMBA_HEIGHT = (int) (GOOMBA_HEIGHT_DEFAULT * Game.SCALE);
		public static final int GOOMBA_DRAWOFFSET_X = (int) (1 * Game.SCALE);
		public static final int GOOMBA_DRAWOFFSET_Y = (int) (1 * Game.SCALE);

		public static final int KOOPA_WIDTH_DEFAULT = 32;
		public static final int KOOPA_HEIGHT_DEFAULT = 32;
		public static final int KOOPA_WIDTH = (int) (KOOPA_WIDTH_DEFAULT * Game.SCALE);
		public static final int KOOPA_HEIGHT = (int) (KOOPA_HEIGHT_DEFAULT * Game.SCALE);
		public static final int KOOPA_DRAWOFFSET_X = (int) (1 * Game.SCALE);
		public static final int KOOPA_DRAWOFFSET_Y = (int) (1 * Game.SCALE);

		public static int GetSpriteAmount(int enemy_type, int enemy_state) {
			switch (enemy_state) {
			case IDLE: 
				return 1;
			case RUNNING:
				return 2;
			case HIT:
				return 1;
			case DEAD:
				return 1;
			}
			return 0;
		}

	}

	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
}
