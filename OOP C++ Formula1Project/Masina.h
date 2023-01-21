//
// Created by yoonl on 5/31/2022.
//

#ifndef TURC_RAUL_316_MASINA_H
#define TURC_RAUL_316_MASINA_H
#include "iostream"
#include "cstring"
using namespace std;

class Masina {
private:
    int nr;
    string echipa;
    string cauciucuri;
public:
    /**
     * Basic constructor
     */
     Masina();
    /**
    * Copy constructor
    */
    Masina(const Masina &m);
    /**
     *  constructor with parameters
     */
     Masina(int nr,string echipa,string cauciucuri);
    /**
    * Destructor
    */
    ~Masina();
    /**
     * getter for number
     */
     int getNr();
    /**
    * getter for team
    */
    string getEchipa();
    /**
     * getter for tires
     */
     string getCauciucuri();
    /**
    *  Override op copare "=="
    */
    bool operator==(const Masina &entity) const;
    /**
     * Overrite la operatorul de non egalitate
     */
    bool operator!=(const Masina &entity) const;
    /**
     * Overrite la operatorul de citire">>"
     */
    friend istream &operator>>(istream &is, Masina &entity);
    /**
     * Overrite la operatorul de afisare"<<"
     */
    friend ostream &operator<<(ostream &os, const Masina &entity);
};


#endif //TURC_RAUL_316_MASINA_H
