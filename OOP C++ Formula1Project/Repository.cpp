//
// Created by yoonl on 5/31/2022.
//

#include "Repository.h"
#include "Masina.h"

Repository::Repository() {
this->masini = vector<Masina>();
}

Repository::~Repository() {
    this->masini.clear();
}

vector<Masina> Repository::getAll() {
    return masini;
}

void Repository::showAll() {
    for(int i = 0;i<masini.size();i++)
    {
        cout<<masini[i]<<endl;
    }
}

void Repository::ChangeTire() {

}

void Repository::SameTime() {
    int laps;
    cout<<"Laps: ";
    cin>>laps;
    int dim = masini.size();
    int v[dim];
    for(int i=0;i<masini.size();i++)
    {
        float usage = 0;
        if(masini[i].getCauciucuri() == "hard")
        {
            usage = 59+(0.2*laps);
        }
        if(masini[i].getCauciucuri() == "soft")
        {
            usage = 50+(0.5*laps);
        }
        v[i] = usage;
    }
    for(int i=0;i<dim;i++)
    {
        for(int j=i+1;j<=dim;j++)
                if (v[i] == v[j]) {
                    cout<<masini[i]<<endl;
                    cout<<masini[j]<<endl;
                }
    }
}

void Repository::TimeRemaining() {
    int laps;
    cout<<"Laps: ";
    cin>>laps;
    for(int i=0;i<masini.size();i++)
    {
        float time;
        if(masini[i].getCauciucuri() == "hard")
        {

            time = 59+(0.2*laps);

        }
        if(masini[i].getCauciucuri() == "soft")
        {
            time = 50+(0.5*laps);
        }
        cout<<masini[i]<<" time remaining: "<<time<<endl;
    }
}

void Repository::addCar(Masina &m) {
    this->masini.push_back(m);
}
