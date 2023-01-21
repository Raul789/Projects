//
// Created by yoonl on 5/31/2022.
//

#ifndef TURC_RAUL_316_UI_H
#define TURC_RAUL_316_UI_H
#include "Service.h"

class UI {
private:
    Service service;
public:
    /**
     * Basic cosntructor
     */
    UI();
    /**
     * Copy constructor
     */
    UI(const Service &service1);
    /**
     * Menu startup function
     */
    void RunMenu();
};


#endif //TURC_RAUL_316_UI_H
