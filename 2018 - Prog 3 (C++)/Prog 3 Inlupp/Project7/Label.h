#ifndef LABEL_H
#define LABEL_H
#include <string>
#include "Labeled.h"

namespace cwing {

	class Label : public Labeled
	{
	public:
		static Label* getInstance(int x, int y, const std::string& txt);
		void draw();
		virtual void tick() {}
		~Label();
	protected:
		Label(int x, int y, const std::string& txt);
	};

} 

#endif