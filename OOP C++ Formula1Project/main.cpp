#include <iostream>

#include "Repository.h"
#include "Service.h"
#include "UI.h"
#include "Tests.h"
#include "Masina.h"

int main() {
    testAll();
    Repository repository;
    Service service(repository);
    UI ui(service);
    ui.RunMenu();
    return 0;
}
