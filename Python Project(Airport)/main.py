from Domain.cursaValidator import CursaValidator
from Domain.aeroportValidator import AeroportValidator
from Repository.json_repository import JsonRepository
from Service.cursaService import CursaService
from Service.aeroportService import AeroportService
from UserInterface.consola import Consola
from Tests.tests import runAllTests


def main():
    aeroportRepository = JsonRepository("aeroport.json")
    aeroportValidator = AeroportValidator()
    cursaRepository = JsonRepository("cursa.json")
    cursaValidator = CursaValidator()

    aeroportService = AeroportService(aeroportRepository, aeroportValidator)
    cursaService = CursaService(cursaRepository, cursaValidator, aeroportRepository)
    consola = Consola(aeroportService,cursaService)
    consola.runMenu()


if __name__ == '__main__':
    runAllTests()
    main()