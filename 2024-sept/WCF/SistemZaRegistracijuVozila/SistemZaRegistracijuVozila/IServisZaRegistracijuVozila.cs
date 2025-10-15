using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace SistemZaRegistracijuVozila
{
	[ServiceContract]
	public interface IServisZaRegistracijuVozila
	{
		[OperationContract(IsOneWay = true)]
		void RegistrujVozilo(Vlasnik vlasnik, Vozilo vozilo, DateTime datumKRajaRegistracije);
		[OperationContract(IsOneWay =false)]
		List<Vozilo> VratiListuSvihVozila(Vlasnik vlasnik);
		[OperationContract(IsOneWay =false)]
		List<Registracija> VratiListuSvihRegistracija(Vozilo vozilo);
		[OperationContract(IsOneWay =false)]
		Dictionary<Vozilo, Registracija> VratiSvaVozilaSaRegistracijama();
	}


	[DataContract]
	public class Vlasnik
	{
		string ime, prezime, jmbg;
		
		[DataMember]
		public string Ime { get; set; }
		
		[DataMember]
		public string Prezime { get; set; }

		[DataMember]
		public string Jmbg { get; set; }
	}

	[DataContract]
	public class Vozilo
	{
		string marka, model, boja;
		int brojsasije;

		[DataMember]
		public string Marka { get; set; }

		[DataMember]
		public string Model { get; set; }
		
		[DataMember]
		public string Boja { get; set; }

		[DataMember]
		public int Brojsasije { get; set; }
	}
	[DataContract]
	public class Registracija
	{
		Vlasnik rVlasnik;
		Vozilo rVozilo;
		DateTime rDatumPocetkaRegistracije, rDatumKrajaRegistracije;
		int rBrojRegistracije;

		[DataMember]
		public Vlasnik RVlasnik { get; set; }

		[DataMember]
		public DateTime RDatumKrajaRegistracije { get; set; }

		[DataMember]
		public DateTime RDatumPocetkaRegistracije { get; set; }
		[DataMember]
		public int RBrojRegistracije { get; set; }
	}
}
