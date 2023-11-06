const updates = [
  {
    NumberPlate: 'ABC-123',
    ArrivedAt: '09:30 AM',
    ParkedHours: 3,
    TotalPrice: '$15.00',
    PaidBy: 'Cash',
  },
  {
    NumberPlate: 'XYZ-789',
    ArrivedAt: '10:15 AM',
    ParkedHours: 2,
    TotalPrice: '$10.00',
    PaidBy: 'Card',
  },
  {
    NumberPlate: 'LMN-456',
    ArrivedAt: '11:00 AM',
    ParkedHours: 4,
    TotalPrice: '$20.00',
    PaidBy: 'Card',
  },
  {
    NumberPlate: 'PQR-321',
    ArrivedAt: '12:30 PM',
    ParkedHours: 1,
    TotalPrice: '$5.00',
    PaidBy: 'Cash',
  },
  {
    NumberPlate: 'EFG-654',
    ArrivedAt: '02:00 PM',
    ParkedHours: 5,
    TotalPrice: '$25.00',
    PaidBy: 'Card',
  },
  // Add more JSON data here
];

const officers = [
  {
    Name: 'Janindu',
    Parking: 'Parking 1',
  },
  {
    Name: 'Vishwa',
    Parking: 'Parking 2',
  },
  {
    Name: 'Uththtara',
    Parking: 'Parking 3',
  },
  {
    Name: 'Dhanushika',
    Parking: 'Parking 4',
  },
  {
    Name: 'Mindula',
    Parking: 'Parking 5',
  },
  // Add more JSON data here
];

const parkingData = [
  {
    ParkingName: 'Parking 1',
    Address: '123 Main St, City',
    CurrentFreeSlots: 15,
    PricePerHour: 50,
    TotalSlots: {
      Cars: 20,
      Vans: 10,
      Buses: 5,
      Bicycles: 30,
    },
    IsPublic: true,
    ParkingOfficerName: 'John Doe',
    TodayEarnings: 2500,
  },
  {
    ParkingName: 'Parking 2',
    Address: '456 Elm St, Town',
    CurrentFreeSlots: 10,
    PricePerHour: 60,
    TotalSlots: {
      Cars: 15,
      Vans: 8,
      Buses: 3,
      Bicycles: 25,
    },
    IsPublic: false,
    ParkingOfficerName: 'Jane Smith',
    TodayEarnings: 1800,
  },
  {
    ParkingName: 'Parking 3',
    Address: '789 Oak St, Village',
    CurrentFreeSlots: 5,
    PricePerHour: 40,
    TotalSlots: {
      Cars: 10,
      Vans: 5,
      Buses: 2,
      Bicycles: 20,
    },
    IsPublic: true,
    ParkingOfficerName: 'Robert Johnson',
    TodayEarnings: 1200,
  },
  // Add more parking data objects here
];

function populateOfficerTable() {
  const tableBody = document.querySelector('#officersTable tbody');

  officers.forEach((row) => {
    const newRow = document.createElement('tr');
    newRow.innerHTML = `
                    <td>${row.Name}</td>
                    <td>${row.Parking}</td>
                `;
    tableBody.appendChild(newRow);
  });
}

function populateParkingCards() {
  const parkingCardsContainer = document.getElementById('parkingCards');

  parkingData.forEach((parking) => {
    const card = document.createElement('div');
    const total =
      parking.TotalSlots.Cars +
      parking.TotalSlots.Vans +
      parking.TotalSlots.Buses +
      parking.TotalSlots.Bicycles;
    card.className = 'parking-card';

    card.innerHTML = `
              <div class="parking-space-card">                    
                <div class="parking-card-header">
                  <h3 class="parking-card-bold">${parking.ParkingName}</h3>
                  <p class="parking-card-bold">${parking.Address}</p>
                </div>
                <div class="parking-card-body">
                  <div class="parking-card-info">
                    <p>Free Slots: <span class="parking-card-bold">${
                      parking.CurrentFreeSlots
                    }</span></p>
                    <p class="parking-card-bold">Rs. ${
                      parking.PricePerHour
                    }/ 1H</p>
                  </div>
                  <div class="parking-card-info">
                    <p>Total Slots: <span class="parking-card-bold">${total}</span> (Cars: <span class="parking-card-bold">${
      parking.TotalSlots.Cars
    }</span> | Vans: <span class="parking-card-bold">${
      parking.TotalSlots.Vans
    }</span> | Buses: <span class="parking-card-bold">${
      parking.TotalSlots.Buses
    }</span> | Bicycles: <span class="parking-card-bold">${
      parking.TotalSlots.Bicycles
    }</span>)</p>
                  <p class="parking-type bg-green text-white">${
                    parking.IsPublic ? 'Public' : 'Private'
                  }</p>
                  </div>
                  
                  <p class="parking-officer">Parking Officer: <span class="parking-card-bold">${
                    parking.ParkingOfficerName
                  }</span></p>
                  <p class="today-earning">Today's Earnings: <span class="parking-card-bold">Rs. ${
                    parking.TodayEarnings
                  }.00</span></p>
                </div>
              </div>
              `;

    parkingCardsContainer.appendChild(card);
  });
}

// Call the function to populate the table
populateUpdateTable();
populateOfficerTable();
populateParkingCards();
