#include "System.h"
#include <SDL.h>
#include <stdexcept>
using namespace std;

namespace cwing {

	System::System()
	{
		SDL_Init(SDL_INIT_EVERYTHING);
		win = SDL_CreateWindow("Cwing", 100, 100, 1000, 800, 0);
		ren = SDL_CreateRenderer(win, -1, 0);
		TTF_Init();
		font = TTF_OpenFont("c:/Windows/Fonts/arial.ttf", 36);
		if (font == nullptr)
			throw std::runtime_error("Font ej hittad!");
	}

	SDL_Renderer* System::getRen() const {
		return ren;
	}

	TTF_Font* System::getFont() const {
		return font;
	}

	System::~System()
	{
		TTF_CloseFont(font);
		TTF_Quit();
		SDL_DestroyWindow(win);
		SDL_DestroyRenderer(ren);
		SDL_Quit();
	}

	System sys;

} 