from dataclasses import dataclass
from Domain.entity import Entity


@dataclass
class Cursa(Entity):
    id_aeroport_pornire: str
    id_aeroport_sosire: str
    distanta: int



