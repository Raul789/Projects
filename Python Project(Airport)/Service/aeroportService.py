from Domain.aeroport import Aeroport
from Domain.aeroportValidator import AeroportValidator
from Repository.json_repository import JsonRepository


class AeroportService:
    def __init__(self, aeroportRepository: JsonRepository,
                 aeroportValidator: AeroportValidator):
        self.aeroportRepository = aeroportRepository
        self.aeroportValidator = aeroportValidator

    def adauga(self, idAeroport: str, indicativ: str, lungime: int):
        if idAeroport == "":
            raise ValueError("Id-ul aeroportului nu poate fi nul!")
        aeroport = Aeroport(idAeroport, indicativ, lungime)
        self.aeroportValidator.validare(aeroport)
        self.aeroportRepository.create(aeroport)

    def getAll(self):
        return self.aeroportRepository.read()

    def afisDesc(self):
        rezultat = []
        for aeroport in self.aeroportRepository.read():
            rezultat.append({
                "indicativ ": aeroport.indicativ,
                "lungime": aeroport.lungime
            })

        print(sorted(rezultat, key = lambda x: int(x["lungime"]),reverse = True))

