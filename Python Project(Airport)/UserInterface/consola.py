from Service.cursaService import CursaService
from Service.aeroportService import AeroportService


class Consola:
    def __init__(self, aeroportService: AeroportService,
                 cursaService: CursaService):
        self.aeroportService = aeroportService
        self.cursaService = cursaService

    def runMenu(self):
        while True:
            print("1.Adauga aeroport")
            print("2.Adauga cursa")
            print("3.Afisare curse desc")
            print("4.Afisare aeroporturi grup dupa lung max")
            print("5.Export json ")
            print("a1) Afisare aeroporturi")
            print("a2) Afisare curse")
            print("x.Iesire")

            optiune = input("Dati optiunea: ")

            if optiune == "1":
                self.adaugaAeroport()
                for aeroport in self.aeroportService.getAll():
                    print(aeroport)
            elif optiune == "2":
                self.adaugaCursa()
                for cursa in self.cursaService.getAll():
                    print(cursa)
            elif optiune == "3":
                self.cursaService.curseDesc()
            elif optiune =="4":
                self.aeroportService.afisDesc()
            elif optiune == "5":
                self.exportJson()
            elif optiune =="a1":
                print(self.aeroportService.getAll())
            elif optiune =="a2":
                print(self.cursaService.getAll())
            elif optiune == "x":
                break
            else:
                print("Optiune gresita! Reincercati")

    def adaugaAeroport(self):
        try:
            idAeroport = input("Dati id-ul aeroportului: ")
            indicativ = input("Dati indicativul(maxim 4 caractere): ")
            lungime = int(input("Dati lungimea aeroportului(minim 500 m): "))
            self.aeroportService.adauga(idAeroport, indicativ, lungime)
        except Exception as e:
            print(e)

    def adaugaCursa(self):
        try:
            idCursa = input("Dati id-ul cursei: ")
            id_aeroport_pornire = input("Dati id-ul aeroportului de pornire: ")
            id_aeroport_sosire = input("Dati id-ul aeroportului de sosire:")
            distanta = int(input("Dati distanta (minim 500 km): "))
            self.cursaService.adauga(idCursa, id_aeroport_pornire, id_aeroport_sosire, distanta)
        except Exception as e:
            print(e)

    def exportJson(self):
        filename = input("Dati numele fisierului in ")
        self.cursaService.exportJson(filename + ".json")