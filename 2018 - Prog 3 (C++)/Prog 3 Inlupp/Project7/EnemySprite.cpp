#include "EnemySprite.h"
#include "System.h"
#include <SDL_image.h>
#include <string>
using namespace std;

namespace cwing {


	EnemySprite::EnemySprite(int x, int y, int w, int h) : Sprite(x, y, w, h)
	{
		SDL_Surface* surf2 = SDL_LoadBMP("c:/images/gubbe.bmp");
		setSurface(surf2);
		SDL_FreeSurface(surf2);
		const char* dd = "c:/images/gubbe.bmp";
		makeTexture(dd);
	}
	void EnemySprite::setSurface(SDL_Surface* s) {
		surf = s;
	}

	int EnemySprite::getType() {
		return 2;
	}

	EnemySprite* EnemySprite::getInstance(int x, int y, int w, int h) {
		return new EnemySprite(x, y, w, h);
	}
	
	void EnemySprite::makeTexture(const char* sd) {
		if (texture != nullptr)
			SDL_DestroyTexture(texture);
		SDL_Surface* surf = SDL_LoadBMP(sd);
		Uint32 white = SDL_MapRGB(surf->format, 255, 255, 255);
		SDL_SetColorKey(surf, true, white);
		texture = SDL_CreateTextureFromSurface(sys.getRen(), surf);

		setWH(surf->w, surf->h);
		SDL_FreeSurface(surf);


	}
	void EnemySprite::draw() {
		SDL_RenderCopy(sys.getRen(), getTexture(), NULL, &getRect());
	}
	void EnemySprite::setSpeedx(int i) {
		speedx =+ i;
	}void EnemySprite::setSpeedy(int i) {
		speedy =+ i;
	}
	int EnemySprite::getSpeedx() {
		return speedx;
	}int EnemySprite::getSpeedy() {
		return speedy;
	}
	EnemySprite::~EnemySprite()
	{
		if (texture != nullptr)
			SDL_DestroyTexture(texture);
		if (surf != nullptr)
			SDL_FreeSurface(surf);
		if (surf != nullptr)
			SDL_FreeSurface(surf2);
	}

}