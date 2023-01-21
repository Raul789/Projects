//
// Created by yoonl on 5/31/2022.
//

#ifndef TURC_RAUL_316_REPOSITORY_H
#define TURC_RAUL_316_REPOSITORY_H
#include "vector"
#include "ostream"
#include "iostream"
#include "Masina.h"

class Repository {
private:
    vector<Masina> masini;
public:
    /**
     * Basic constructor
     */
    Repository();
    /**
     * Destructor
     */
    ~Repository();
    /**
    * Getall function for getting all the cars
    */
    vector<Masina> getAll();
    /**
     * Shows all entities cars
     */
    void showAll();
    /**
     * Change of tires
     */
     void ChangeTire();
     /**
      * The cars that have the same time left
      */
      void SameTime();
      /**
       * Add a car
       */
       void addCar(Masina &m);
       /**
        * Time remaining
        */
        void TimeRemaining();
};


#endif //TURC_RAUL_316_REPOSITORY_H
