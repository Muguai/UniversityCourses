#ifndef SESSION_H
#define SESSION_H
#include <vector>
#include "Sprite.h"
#include <iostream>
namespace cwing {

	class Session
	{
	public:
		void add(Sprite* );
		void remove(Sprite* );
		void spawner(Sprite*);
		void oadd(Sprite*);
		void ospawner(Sprite*);
		void GameIsOver(Sprite*);
		void run();
		
		int getKlockan() { return Klockan; }
		void setKlockan(int i) { Klockan = i; }
		void addToKlockan(int i) { Klockan += i; }

		int getCounter() { return counter; }
		void setCounter(int i) { counter = i; }
		void addToCounter(int i) { counter += i; }

		int getTime() { return time; }
		void setTime(int i) { time = i; }
		void addToTime(int i) { time += i; }

		int getlifes() { return lifes; }
		void setlifes(int i) { lifes = i; }

		bool getQuit() { return quit; }
		void setQuit(bool i) { quit = i; }

		void setfps(int i) { fps = i; }
		~Session();
	private:
		bool quit = false;
		int lifes = 0;
		int time = 0;
		int Klockan = 0;
		int h = 0;
		int fps = 0;
		int counter = 50;
		std::vector<Sprite*> comps, added, removed, spawn, gameOver, Oadded, Ospawn ;
	};

} 
#endif