//
// Created by yoonl on 5/31/2022.
//

#include "Masina.h"

///default constructor
Masina::Masina(){
    this->nr =0;
    this->cauciucuri = "";
    this->echipa ="";
}

Masina::Masina(const Masina &m) {
    this->nr =m.nr;
    this->cauciucuri = m.cauciucuri;
    this->echipa = m.echipa;
}

Masina::Masina(int nr, string echipa, string cauciucuri) {
    this->nr = nr;
    this->echipa = echipa;
    this->cauciucuri = cauciucuri;
}

Masina::~Masina() {
    this->nr =0;
    this->cauciucuri = "";
    this->echipa ="";
}

int Masina::getNr() {
    return this->nr;
}

string Masina::getEchipa() {
    return this->echipa;
}

string Masina::getCauciucuri() {
    return this->cauciucuri;
}

bool Masina::operator==(const Masina &entity) const {
    return(nr == entity.nr && echipa == entity.echipa && cauciucuri == entity.cauciucuri);
}

bool Masina::operator!=(const Masina &entity) const {
    return !(entity == *this);
}

istream &operator>>(istream &is, Masina &entity) {
    int nr;
    string echipa;
    string cauciucuri;
    cout<<"Nr: ";
    cin>>nr;
    cout<<"Echipa: ";
    cin>>echipa;
    cout<<"Cauciucuri: ";
    cin>>cauciucuri;
    entity.nr = nr;
    entity.echipa = echipa;
    entity.cauciucuri = cauciucuri;
    return is;
}

ostream &operator<<(ostream &os, const Masina &entity) {
    int laptime = 0;
    if(entity.cauciucuri =="soft")
        laptime = 50;
    if(entity.cauciucuri == "hard")
        laptime = 69;
    os<<"Nr: "<<entity.nr<<" Team: "<<entity.echipa<<" Tires: "<<entity.cauciucuri<<" Laptime: "<<laptime<<endl;
    return os;
}
