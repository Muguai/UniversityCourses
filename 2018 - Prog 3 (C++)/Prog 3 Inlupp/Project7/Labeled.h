#ifndef LABELED_H
#define LABELED_H
#include <string>
#include <SDL.h>
#include "Sprite.h"
namespace cwing {

	class Labeled : public Sprite
	{
	public:
		void setText(const int i);
		void setTextString(std::string i);
		std::string getText() const;
		virtual void tick() {}
		~Labeled();
	protected:
		Labeled(int x, int y, const std::string& txt);
		SDL_Texture* getTexture() const { return texture; }
	private:
		std::string text;
		SDL_Texture* texture = nullptr;
		void makeTexture();
		
	};

}
#endif