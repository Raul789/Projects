//
// Created by yoonl on 5/31/2022.
//

#ifndef TURC_RAUL_316_SERVICE_H
#define TURC_RAUL_316_SERVICE_H
#include "iostream"
#include "Repository.h"
#include "vector"
#include "Masina.h"

class Service {
private:
    Repository repository;
public:
    /**
     * Basic constructor
     */
    Service();
    /**
     * Copy constructor
     */
    Service(const Repository &repository1);
    /**
     * Add entity function
     */
     void addCar(Masina m);
    /**
   * Get all entities function
   */
    void getAll();
    /**
     * Shows all entities function
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
     * Time remaining
     */
     void TimeRemaining();
};


#endif //TURC_RAUL_316_SERVICE_H
