#ifndef SPRITE_H
#define SPRITE_H
#include <SDL.h>

namespace cwing {
	class Sprite
	{
	public:
		virtual void mouseDown(const SDL_Event& event) {}
		virtual void mouseUp(const SDL_Event& event) {}
		virtual void keyDown(const SDL_Event& event) {}
		virtual void keyUp(const SDL_Event& event) {}
		virtual void draw() {};
		virtual void tick() {}
		virtual void collision(Sprite* s) {}
		virtual ~Sprite() {}
		virtual int getType() { return 0; };
		SDL_Rect getRect() { return rect; }

		int getdefaultY() { return defaulty; }
		int getdefaultx() { return defautlx; }
		void setRectx(int i);
		void setRecty(int i);
		void makeRectx(int i);
		void makeRecty(int i);
		void setdefaulty(int i);
		void setdefaultx(int i);

		Sprite(const Sprite&) = delete;
		const Sprite& operator=(const Sprite&) = delete;
	protected:
		Sprite(int x, int y, int w, int h);
		void setWH(int w, int h);
	private:
		int defaulty;
		int defautlx;
		SDL_Rect rect;
	};
}
#endif
