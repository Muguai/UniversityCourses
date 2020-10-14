#include "Session.h"
#include <SDL.h>
#include "System.h"
#include <iostream>
#include <string>
#include "Sprite.h"
#include "PlayerSprite.h"
#include "EnemySprite.h"
using namespace std;
namespace cwing {
	void Session::oadd(Sprite* c) {
		added.push_back(c);
		Oadded.push_back(c);
	}
	void Session::ospawner(Sprite* c) {
		spawn.push_back(c);
		Ospawn.push_back(c);
	}
	void Session::spawner(Sprite* c) {
		spawn.push_back(c);
	}
	void Session::add(Sprite* c) {
		added.push_back(c);
	}

	void Session::GameIsOver(Sprite* c) {
		gameOver.push_back(c);
	}
	void Session::remove(Sprite *c) {
		removed.push_back(c);
	}
 	void Session::run() {
		const int tickInterval = 1000 / fps;
		Uint32 nextTick;
		int delay;
		int h = 0;
		SDL_SetRenderDrawColor(sys.getRen(), 255, 255, 255, 255);
		while (!quit) {
			for (Sprite* c : added)
				comps.push_back(c);
			added.clear();

			addToTime(1);
			if (getTime() == 60) {
				addToKlockan(1);
				setTime(0);
			}

			nextTick = SDL_GetTicks() + tickInterval;

			for (Sprite* c : comps) {
				bool HIT = false;
				for (Sprite* d : comps) {
                    if (c == d) {
						HIT = false;
					}else {
						HIT = SDL_HasIntersection(&c->getRect(), &d->getRect());
					}
					if (HIT) {
						c->collision(d);
					}
					HIT = false;
				}
			}
			
			
			SDL_Event eevent;
			for (Sprite* c : comps) {

				c->tick();
			}
			SDL_Event event;
			while (SDL_PollEvent(&event)) {

				switch (event.type) {
				case SDL_QUIT: quit = true; break;
				}
			}

			for (Sprite* c : removed) {
				for (vector<Sprite*>::iterator i = comps.begin(); i != comps.end(); )
					if (*i == c) {
						i = comps.erase(i);
						int g = c->getType();
						if (g == 2) {

						}
						else {
							delete c;
						}
						
					}
					else
						i++;
			}
			removed.clear();

			SDL_RenderClear(sys.getRen());
			for (Sprite* c : comps)
				c->draw();
			SDL_RenderPresent(sys.getRen());

			h += 1;
			if (h > 50) {
				bool found = false;
				for (Sprite* c : spawn) {
					for (Sprite* d : comps) {
						if (d == c) {
							found = true;
						}

					}
					if (!found) {
						c->makeRecty(c->getdefaultY());
						c->makeRectx(c->getdefaultx());
						added.push_back(c);
					}
					found = false;
						
				}
				h = 0;
			}
			
			if (getCounter() < 1) {
				for (Sprite* g : gameOver) {
					g->tick();
				}
				spawn.clear();
				comps.clear();

				bool stop = true;   
				while (stop) {
					SDL_Event devent;
					while (SDL_PollEvent(&devent)) {
						switch (devent.type) {
						case SDL_QUIT: { quit = true; break; }
						case SDL_KEYDOWN: {setCounter(getlifes()); stop = false; break; }
						}
					}
					SDL_RenderClear(sys.getRen());
					for (Sprite* g : gameOver) {
						g->draw();
					}
					SDL_RenderPresent(sys.getRen());
				}
				for (Sprite* c : Oadded) {
					c->setdefaultx(c->getdefaultx());
					c->setdefaulty(c->getdefaultY());
					add(c);
				}
				for (Sprite* c : Ospawn) {
					spawner(c);
				}
				setTime(0);
				setKlockan(0);
			}
			
            int delay = nextTick - SDL_GetTicks();
		    if (delay > 0)
			SDL_Delay(delay); 
		} // yttre while

	}

	Session::~Session(){
		bool found = false;
		for (Sprite* c : spawn) {
			for (Sprite* d : comps) {
				if (d == c) {
				   found = true;
				}

			}
			if (!found) {
				comps.push_back(c);
			}
			found = false;

		}
		spawn.clear();
		for (Sprite* c : comps)
			delete c;	
	}
} 