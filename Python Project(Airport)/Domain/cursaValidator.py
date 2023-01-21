from dataclasses import dataclass
from Domain.cursa import Cursa


@dataclass
class CursaValidator:
    def validare(self, cursa: Cursa):
        self.cursa = Cursa
        erori = []
        if cursa.id_aeroport_pornire == "":
            erori.append("Id-ul ap  de pornire nu trebuie sa fie nul!")
        if cursa.id_aeroport_sosire == "":
            erori.append("Id-ul ap  de sosire nu trebuie sa fie nul!")
        if str(cursa.id_aeroport_pornire) == str(cursa.id_aeroport_sosire):
            erori.append("Id-ul aeroportului de pornire trebuie sa fie diferit de cel de sosire!")
        if int(cursa.distanta) < 500:
            erori.append("Distanta cursei trebuie sa fie de minim 500 km!")
        if erori:
            raise ValueError(erori)
