#ifndef ENEMYSPRITE_H
#define ENEMYSPRITE_H
#include "Sprite.h"
namespace cwing {
	class EnemySprite : public Sprite
	{
	public:
		static EnemySprite* getInstance(int x, int y, int w, int h);
		~EnemySprite();
		void draw();
		virtual void collison(Sprite* s) {}
		virtual void tick() {}
		virtual void perform() {}
		int getType();
		void setSpeedx(int i);
		void setSpeedy(int i);
		int getSpeedx();
		int getSpeedy();
		void makeTexture(const char*);
		void setSurface(SDL_Surface* s);
		SDL_Surface* getSurface() { return surf; }
	protected:
		EnemySprite(int x, int y, int w, int h);

		SDL_Texture* getTexture() const { return texture; }

	private:
		int i = 0;
		int speedx = 0;
		int speedy = 0;
		SDL_Surface* surf = nullptr;
		SDL_Surface* surf2 = nullptr;
		SDL_Texture* texture = nullptr;
		SDL_Texture* upIcon;
	};
}
#endif