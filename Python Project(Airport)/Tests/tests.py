from Domain.aeroport import Aeroport
from Domain.aeroportValidator import AeroportValidator
from Domain.cursaValidator import CursaValidator
from Repository.json_repository import JsonRepository
from Service.cursaService import CursaService
from Service.aeroportService import AeroportService


def runAllTests():
    testAdaugaLocalitate()
    testAdaugaCursa()

def clearFile(filename:str):
    with open(filename,"w") as f:
        pass

def testAdaugaLocalitate():
    clearFile("test-aeroport.json")
    aeroportRepository = JsonRepository("test-aeroport.json")
    aeroportValidator = AeroportValidator()
    aeroportService = AeroportService(aeroportRepository, aeroportValidator)
    clearFile("test-aeroport.json")

    aeroportService.adauga("1","NYCK",int("500"))
    aeroportService.adauga("2", "ALBM", int("1400"))

    aeroporturi = aeroportService.getAll()
    assert len(aeroporturi) == 2
    assert aeroporturi[0].id_entity =="1"
    assert aeroporturi[0].indicativ== "NYCK"
    assert aeroporturi[0].lungime == 500
    assert aeroporturi[1].id_entity == "2"
    assert aeroporturi[1].indicativ == "ALBM"
    assert aeroporturi[1].lungime == 1400

def testAdaugaCursa():
    clearFile("test-rute.json")
    aeroportRepository = JsonRepository("test-rute.json")
    clearFile("test-rute.json")
    cursaRepository = JsonRepository("test-rute.json")
    cursaValidator = CursaValidator()
    cursaService = CursaService(cursaRepository, cursaValidator, aeroportRepository)
    clearFile("test-aeroport.json")
    aeroportRepository.create(Aeroport("1","NYCK",int("500")))
    aeroportRepository.create(Aeroport("2", "ALBM", int("1400")))

    cursaService.adauga("1","1","2",int("1400"))
    curse = cursaService.getAll()
    assert len(curse) == 1
    assert curse[0].idCursa =="1"
