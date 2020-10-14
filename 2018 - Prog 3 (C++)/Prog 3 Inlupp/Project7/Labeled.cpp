#include "Labeled.h"
#include "SDL_ttf.h"
#include "System.h"
using namespace std;

namespace cwing {

	Labeled::Labeled(int x, int y, const string& txt) :Sprite(x, y, 0, 0), text(txt)
	{
		makeTexture();
	}

	string Labeled::getText() const {
		return text;
	}

	void Labeled::makeTexture() {
		if (texture != nullptr)
			SDL_DestroyTexture(texture);
		SDL_Surface* surf = TTF_RenderText_Solid(sys.getFont(), text.c_str(), { 0,0,0 });
		texture = SDL_CreateTextureFromSurface(sys.getRen(), surf);
		setWH(surf->w, surf->h);
		SDL_FreeSurface(surf);

	}

	void Labeled::setText(int i) {
		string str = to_string(i);
		text = str;
		makeTexture();
	}
	void Labeled::setTextString(string i) {
		string str = i;
		text = str;
		makeTexture();
	}

	Labeled::~Labeled()
	{
		SDL_DestroyTexture(texture);
	}

}