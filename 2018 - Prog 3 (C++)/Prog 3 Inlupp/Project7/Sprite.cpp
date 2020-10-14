#include "Sprite.h"
#include <SDL.h>
#include "System.h"

namespace cwing {

	Sprite::Sprite(int x, int y, int w, int h) :rect{ x,y, w, h }
	{
		defautlx = x;
		defaulty = y;
	}

	void Sprite::setWH(int w, int h) {
		rect.w = w;
		rect.h = h;
	}
	void Sprite::setRectx(int i) {
		rect.x += i;
	}
	void Sprite::setRecty(int i) {
		rect.y += i;
	}
	void Sprite::makeRecty(int i) {
		rect.y = i;
	}
	void Sprite::makeRectx(int i) {
		rect.x = i;
	}
	void Sprite::setdefaultx(int i) {
		defaulty += i;
	}
	void Sprite::setdefaulty(int i) {
		defautlx += i;
	}
}
