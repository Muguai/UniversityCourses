#ifndef PLAYERSPRITE_H
#define PLAYERSPRITE_H
#include "Sprite.h"
namespace cwing {
	class PlayerSprite : public Sprite
	{
	public:
		static PlayerSprite* getInstance(int x, int y, int w, int h);
		~PlayerSprite();
		void draw();
		void setSurface(SDL_Surface* s);
		SDL_Surface* getSurface() { return surf; }
		virtual void collision(Sprite* s) {}
		virtual void tick() {}
		void makeTexture(const char*);
	protected:
		PlayerSprite(int x, int y, int w, int h);
		SDL_Texture* getTexture() const { return texture; }
	private:
		SDL_Surface* surf = nullptr;
		SDL_Surface* surf2 = nullptr;
		SDL_Texture* texture = nullptr;

	};
}
#endif
