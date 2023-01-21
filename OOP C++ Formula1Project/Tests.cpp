//
// Created by yoonl on 5/31/2022.
//

#include "Tests.h"
#include "cassert"
#include "Repository.h"
#include "Service.h"
#include "Masina.h"

void testAll(){
    testEntity();
    testRepository();
}

void testEntity(){
    Masina m(1,"ferari","hard");
    assert(m.getCauciucuri() == "hard");
    assert(m.getEchipa() == "ferari");
    assert(m.getNr() == 1);
}

void testRepository(){
    Repository repository;
    Masina m1(1,"ferari","hard");
    Masina m2(2,"lambo","soft");
    repository.addCar(m1);
    repository.addCar(m2);
    assert(repository.getAll().size());
}
