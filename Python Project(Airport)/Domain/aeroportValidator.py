from dataclasses import dataclass
from Domain.aeroport import Aeroport


@dataclass
class AeroportValidator:
    def validare(self,aeroport: Aeroport):
        self.aeroport = Aeroport
        erori = []
        if len(str(aeroport.indicativ)) >4:
            erori.append("Indicativul trebuie sa aiba maxim 4 caractere!")
        if int(aeroport.lungime) <500:
            erori.append("Distanta aeroportului trebuie sa fie minim 500!")
        if erori:
            raise ValueError(erori)