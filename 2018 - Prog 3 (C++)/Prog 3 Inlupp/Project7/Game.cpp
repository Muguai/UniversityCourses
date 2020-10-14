#include <SDL.h>
#include "Session.h"
#include "Label.h"
#include "PlayerSprite.h"
#include "EnemySprite.h"
#include <iostream>
#include <string>
using namespace std;
using namespace cwing;
Session ses;
Label* lbl;
Label* lbl2;
Label* lbl3;
bool moving = false;
class Player : public PlayerSprite {
public:
	Player() :PlayerSprite(400, 270, 150, 150) { }
	
	void collision(Sprite* s) {
		if (dynamic_cast<EnemySprite*>(s)) {
			ses.addToCounter(-1);
			lbl->setText(ses.getCounter());
			ses.remove(s);
		}
		}
		void tick() {
			SDL_Event event;
			while (SDL_PollEvent(&event)) {
				switch (event.type) {
				case SDL_MOUSEBUTTONDOWN: {
					SDL_Point point = { event.button.x, event.button.y };
					if (SDL_PointInRect(&point, &getRect()))
						moving = true;
				}
		        break;
				case SDL_MOUSEBUTTONUP:
					moving = false;
					break;

				case SDL_QUIT: ses.setQuit(true); break;
				case SDL_MOUSEMOTION:
					if (moving) {
						setRectx(event.motion.xrel);
						setRecty(event.motion.yrel);
					}
					break;
		        }
		    }

	}
		
};
class label1 : public Label {
public:
	label1() :Label(0, 425, "Chimichonga") {}
	void tick() {
		std::string over = "Game OVER you survived: ";
		over += to_string(ses.getKlockan());
		over += " Seconds.";
		setTextString(over);
	}
};
class Klockan : public Label {
public:
	Klockan() :Label(425, 0, "0") {}
	void tick() {
	std:string timer = "time: ";
	    timer += to_string(ses.getKlockan());
		setTextString(timer);
	}
};
class Enemy : public EnemySprite {
public:
	Enemy() :EnemySprite(0, 0, 150, 150) {}
	void tick() {
		setRecty(getSpeedy());
		setRectx(getSpeedx());

		// out of bounds remove
     	if (getRect().x == -1500 || getRect().x == 1500) {
			ses.remove(this);
		}
		if (getRect().y == -1500 || getRect().y == 1500) {
			ses.remove(this);
		}
	}

};

int main(int argc, char** argv) {

	    ses.setfps(60);
		ses.setlifes(30);
		ses.setCounter(ses.getlifes());

		const char* sd = "c:/images/Pingu.bmp";
		const char* st = "c:/images/gubbe.bmp";

		std::string over = "Game OVER ";
        Label* l = new label1();
	    l->setTextString(over);
		ses.GameIsOver(l);

		lbl2 = Label::getInstance(0, 500, "Press Any Key to reset game");
		ses.GameIsOver(lbl2);

		lbl = Label::getInstance(100, 100, "Hej");
		lbl->setText(ses.getCounter());
		ses.oadd(lbl);

		lbl3 = Label::getInstance(10, 100, "Lifes:");
		ses.oadd(lbl3);


        Label* klocka = new Klockan();
        ses.oadd(klocka);

		PlayerSprite* p = new Player();
		p->makeTexture(st);
		ses.oadd(p);

		EnemySprite* e = new Enemy();
		e->setSpeedx(3);
		e->setSpeedy(5||6);
        e->makeTexture(sd);

		EnemySprite* e2 = new Enemy();
		e2->setdefaultx(0);
		e2->setdefaulty(850);
		e2->setSpeedx(-1);
		e2->setSpeedy(3||4);
        e2->makeTexture(sd);

		EnemySprite* e3 = new Enemy();
		e3->setdefaultx(0);
		e3->setdefaulty(425);
		e3->setSpeedx(0);
		e3->setSpeedy(3||4);
        e3->makeTexture(sd);

		EnemySprite* e4 = new Enemy();
		e4->setdefaultx(700);
		e4->setSpeedx(3);
		e4->setSpeedy(-2);
        e4->makeTexture(sd);

		EnemySprite* e5 = new Enemy();
		e5->setdefaultx(700);
		e5->setdefaulty(425);
		e5->setSpeedx(0);
		e5->setSpeedy(-1);
        e5->makeTexture(sd);

		EnemySprite* e6 = new Enemy();
		e6->setdefaultx(700);
		e6->setdefaulty(850);
 		e6->setSpeedx(-1);
	    e6->setSpeedy(-4);
        e6->makeTexture(sd);

		EnemySprite* e7 = new Enemy();
		e7->setdefaultx(300);
		e7->setdefaulty(850);
		e7->setSpeedx(-3);
		e7->setSpeedy(0);
		e7->makeTexture(sd);

		EnemySprite* e8 = new Enemy();
		e8->setdefaultx(300);
		e8->setdefaulty(0);
		e8->setSpeedx(3);
		e8->setSpeedy(0);
		e8->makeTexture(sd);


		ses.ospawner(e);
		ses.ospawner(e2);
		ses.ospawner(e3);
		ses.ospawner(e4);
		ses.ospawner(e5);
		ses.ospawner(e6);
		ses.ospawner(e7);
		ses.ospawner(e8);

	    ses.run();
	    return 0;
}