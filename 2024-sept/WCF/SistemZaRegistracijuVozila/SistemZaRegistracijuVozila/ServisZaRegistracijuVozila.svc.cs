using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace SistemZaRegistracijuVozila
{
    [ServiceBehavior(InstanceContextMode =InstanceContextMode.Single)]
    public class ServisZaRegistracijuVozila : IServisZaRegistracijuVozila
    {

        Dictionary<Vozilo, Registracija> registracije;
        int brojRegistracije;
        public ServisZaRegistracijuVozila()
        {
            this.registracije = new Dictionary<Vozilo, Registracija>();
            this.brojRegistracije = 0;
        }
        public void RegistrujVozilo(Vlasnik vlasnik, Vozilo vozilo, DateTime datumKRajaRegistracije)
        {
            Registracija reg = new Registracija();
            reg.RDatumKrajaRegistracije = datumKRajaRegistracije;
            reg.RDatumPocetkaRegistracije = DateTime.Now;
            reg.RBrojRegistracije = brojRegistracije;
            reg.RVlasnik = vlasnik;
            brojRegistracije++;
            this.registracije.Add(vozilo, reg);
        }

        public List<Registracija> VratiListuSvihRegistracija(Vozilo vozilo)
        {
            return this.registracije.Values.ToList();
        }

        public List<Vozilo> VratiListuSvihVozila(Vlasnik vlasnik)
        {
            var filtered = this.registracije.Where(item => item.Value.RVlasnik.Jmbg.Equals(vlasnik.Jmbg));
            var vozila = filtered.Select(p => p.Key); 
            return vozila.ToList();
        }

        public Dictionary<Vozilo, Registracija> VratiSvaVozilaSaRegistracijama()
        {
            return this.registracije;
        }
    }
}
