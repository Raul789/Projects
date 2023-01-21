//
// Created by yoonl on 5/31/2022.
//

#include "Service.h"

Service::Service()  = default;

Service::Service(const Repository &repository1) {
    this->repository = repository1;
}

void Service::addCar(Masina m) {
    repository.addCar(m);
}

void Service::getAll() {
    repository.getAll();
}

void Service::showAll() {
    repository.showAll();
}

void Service::ChangeTire() {
    repository.ChangeTire();
}

void Service::SameTime() {
    repository.SameTime();
}

void Service::TimeRemaining() {
    repository.TimeRemaining();
}
