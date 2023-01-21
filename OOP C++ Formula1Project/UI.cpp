//
// Created by yoonl on 5/31/2022.
//

#include "UI.h"
#include "Repository.h"
#include "Service.h"
#include "istream"
#include "Masina.h"
using namespace std;


UI::UI(const Service &service1) {
    this->service = service1;
}

void PrintMenu(){
    std::cout<<"----------F1 Race Monitor------------"<<std::endl;
    std::cout<<"1)Add a car"<<std::endl;
    std::cout<<"2)Show all the cars"<<std::endl;
    cout<<"3)Change soft tires "<<endl;
    cout<<"4)Visualize after k tours "<<endl;
    std::cout<<"5)Visualize the cars with same damage on the wheels"<<std::endl;
    std::cout<<"6)Exit!"<<endl;
}

void UI::RunMenu() {
    bool ok = true;
    int option;
    while(ok) {
        PrintMenu();
        std::cout<<"Enter the option: "<<std::endl;
        std::cin>>option;
        if(option == 1)
        {
            Masina b;
            cin>>b;
            service.addCar(b);
        }
        else if(option == 2)
        {
            service.showAll();
        }
        else if(option == 3)
        {
            service.showAll();
        }
        else if(option == 4)
        {
            service.TimeRemaining();
        }
        else if(option == 5)
        {
            service.SameTime();
        }
        else if(option == 6)
            ok = false;
        else
            std::cout<<"Wrong option! Try again!"<<std::endl;

    }
}

