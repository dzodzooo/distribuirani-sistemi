using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SistemZaRegistracijuVozilaClient.SistemZaRegistracijuVozila;
namespace SistemZaRegistracijuVozilaClient
{
    class Program
    {
        static void Main(string[] args)
        {
            ServisZaRegistracijuVozilaClient client = new ServisZaRegistracijuVozilaClient();
            Vlasnik vlasnik = new Vlasnik();
            vlasnik.Ime = "Jovana";
            vlasnik.Prezime = "Stojanovic";
            vlasnik.Jmbg = "123";

            Vozilo vozilo = new Vozilo();
            vozilo.Boja = "crvena";
            vozilo.Marka = "Citroen";
            vozilo.Brojsasije = 33;
            client.RegistrujVozilo(vlasnik, vozilo, new DateTime(2035, 12, 12));

            Vozilo vozilo2 = new Vozilo();
            vozilo2.Boja = "bela";
            vozilo2.Marka = "Reno";
            vozilo2.Brojsasije = 34;
            client.RegistrujVozilo(vlasnik, vozilo2, new DateTime(2035, 12, 12));

            Vlasnik vlasnik2 = new Vlasnik();
            vlasnik2.Ime = "Pera";
            vlasnik2.Prezime = "Peric";
            vlasnik2.Jmbg = "126k3";

            Vozilo vozilo3 = new Vozilo();
            vozilo3.Boja = "crna";
            vozilo3.Marka = "BMW";
            vozilo3.Brojsasije = 92;
            client.RegistrujVozilo(vlasnik2, vozilo3, new DateTime(2035, 12, 12));

            var svavozilasareg=client.VratiSvaVozilaSaRegistracijama();
            foreach (var item in svavozilasareg)
            {
                Console.WriteLine(item.Key.Brojsasije);
            }
            Console.WriteLine();

            var svavozila1 = client.VratiListuSvihVozila(vlasnik);
            foreach (var item in svavozila1)
            {
                Console.WriteLine(item.Brojsasije);
            }
        }
    }
}
