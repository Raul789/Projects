from Domain.cursa import Cursa
from Domain.cursaValidator import CursaValidator
from Repository.json_repository import JsonRepository
import jsonpickle


class CursaService:
    def __init__(self, cursaRepository: JsonRepository,
                 cursaValidator: CursaValidator,
                 aeroportRepository: JsonRepository):
        self.cursaRepository = cursaRepository
        self.cursaValidator = cursaValidator
        self.aeroportRepository = aeroportRepository

    def adauga(self, idCursa: str, id_aeroport_pornire: str, id_aeroport_sosire: str, distanta: int):
        if idCursa == "":
            raise ValueError("ID-ul cursei nu poate fi nul!")
        cursa = Cursa(idCursa, id_aeroport_pornire, id_aeroport_sosire, distanta)
        self.cursaValidator.validare(cursa)
        self.cursaRepository.create(cursa)

    def getAll(self):
        return self.cursaRepository.read()

    def curseDesc(self):
        rezultat = []
        for cursa in self.cursaRepository.read():
            rezultat.append({
                "aeroport pornire": int(cursa.id_aeroport_pornire),
                "aeroport oprire": int(cursa.id_aeroport_sosire),
                "dist": int(cursa.distanta)
            })

        print(sorted(rezultat, key=lambda x: x["dist"], reverse=True))

    def exportJson(self, filename: str):
        rezultat = {}
        for aeroport in self.aeroportRepository.read():
            rezultat[aeroport.indicativ] = []

        for cursa in self.cursaRepository.read():
            aerp = self.aeroportRepository.read(cursa.id_aeroport_pornire)
            indp = aerp.indicativ
            aers =self.aeroportRepository.read(cursa.id_aeroport_sosire)
            indiso = aers.indicativ
            rezultat[indp].append(indiso)



        with open(filename, 'w') as f:
            f.write(jsonpickle.dumps(rezultat))
