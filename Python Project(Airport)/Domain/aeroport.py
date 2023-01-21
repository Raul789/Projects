from dataclasses import dataclass
from Domain.entity import Entity


@dataclass
class Aeroport(Entity):
    indicativ: str
    lungime: int
