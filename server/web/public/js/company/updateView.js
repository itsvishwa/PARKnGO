const advancedUpdates = [
  {
    NumberPlate: 'ABC-123',
    ParkingSpace: 'Parking 1',
    VehicleType: 'Car',
    ArrivedAt: '09:30 AM',
    LeftAt: '12:30 PM',
    ParkedHours: 3,
    AssignBy: 'John Doe',
    ReleasedBy: 'Alice Smith',
    TotalPrice: '$15.00',
    PaidBy: 'Cash',
  },
  {
    NumberPlate: 'XYZ-789',
    ParkingSpace: 'Parking 2',
    VehicleType: 'Van',
    ArrivedAt: '10:15 AM',
    LeftAt: '12:15 PM',
    ParkedHours: 2,
    AssignBy: 'Bob Johnson',
    ReleasedBy: 'Eve Brown',
    TotalPrice: '$10.00',
    PaidBy: 'Card',
  },
  {
    NumberPlate: 'DEF-456',
    ParkingSpace: 'Parking 3',
    VehicleType: 'Car',
    ArrivedAt: '08:45 AM',
    LeftAt: '11:30 AM',
    ParkedHours: 2.75,
    AssignBy: 'Jane Smith',
    ReleasedBy: 'David Wilson',
    TotalPrice: '$12.50',
    PaidBy: 'Cash',
  },
  {
    NumberPlate: 'GHI-789',
    ParkingSpace: 'Parking 4',
    VehicleType: 'Bike',
    ArrivedAt: '09:00 AM',
    LeftAt: '10:30 AM',
    ParkedHours: 1.5,
    AssignBy: 'Sarah Lee',
    ReleasedBy: 'Michael Brown',
    TotalPrice: '$5.00',
    PaidBy: 'Card',
  },
  {
    NumberPlate: 'JKL-123',
    ParkingSpace: 'Parking 5',
    VehicleType: 'Car',
    ArrivedAt: '10:00 AM',
    LeftAt: '01:45 PM',
    ParkedHours: 3.75,
    AssignBy: 'Chris Davis',
    ReleasedBy: 'Olivia Taylor',
    TotalPrice: '$18.75',
    PaidBy: 'Cash',
  },
  {
    NumberPlate: 'MNO-456',
    ParkingSpace: 'Parking 6',
    VehicleType: 'Van',
    ArrivedAt: '08:30 AM',
    LeftAt: '10:00 AM',
    ParkedHours: 1.5,
    AssignBy: 'Emily Johnson',
    ReleasedBy: 'William Clark',
    TotalPrice: '$9.00',
    PaidBy: 'Card',
  },
  {
    NumberPlate: 'PQR-789',
    ParkingSpace: 'Parking 7',
    VehicleType: 'Car',
    ArrivedAt: '11:15 AM',
    LeftAt: '01:30 PM',
    ParkedHours: 2.25,
    AssignBy: 'Daniel Miller',
    ReleasedBy: 'Sophia White',
    TotalPrice: '$13.50',
    PaidBy: 'Cash',
  },
  {
    NumberPlate: 'STU-123',
    ParkingSpace: 'Parking 8',
    VehicleType: 'Bike',
    ArrivedAt: '10:45 AM',
    LeftAt: '11:30 AM',
    ParkedHours: 0.75,
    AssignBy: 'Liam Johnson',
    ReleasedBy: 'Ava Wilson',
    TotalPrice: '$3.75',
    PaidBy: 'Card',
  },
  {
    NumberPlate: 'VWX-456',
    ParkingSpace: 'Parking 9',
    VehicleType: 'Car',
    ArrivedAt: '09:30 AM',
    LeftAt: '12:45 PM',
    ParkedHours: 3.25,
    AssignBy: 'Ethan Harris',
    ReleasedBy: 'Mia Adams',
    TotalPrice: '$16.25',
    PaidBy: 'Cash',
  },
  {
    NumberPlate: 'YZA-789',
    ParkingSpace: 'Parking 10',
    VehicleType: 'Car',
    ArrivedAt: '10:30 AM',
    LeftAt: '01:15 PM',
    ParkedHours: 2.75,
    AssignBy: 'Aiden Turner',
    ReleasedBy: 'Chloe Parker',
    TotalPrice: '$13.75',
    PaidBy: 'Card',
  },
  {
    NumberPlate: 'LMN-456',
    ParkingSpace: 'Parking 3',
    VehicleType: 'Car',
    ArrivedAt: '11:00 AM',
    LeftAt: '03:30 PM',
    ParkedHours: 4.5,
    AssignBy: 'Sarah Johnson',
    ReleasedBy: 'Michael Clark',
    TotalPrice: '$20.00',
    PaidBy: 'Cash',
  },
  {
    NumberPlate: 'PQR-321',
    ParkingSpace: 'Parking 4',
    VehicleType: 'Car',
    ArrivedAt: '12:30 PM',
    LeftAt: '01:30 PM',
    ParkedHours: 1,
    AssignBy: 'David Wilson',
    ReleasedBy: 'Olivia White',
    TotalPrice: '$5.00',
    PaidBy: 'Card',
  },
  {
    NumberPlate: 'EFG-654',
    ParkingSpace: 'Parking 5',
    VehicleType: 'Van',
    ArrivedAt: '02:00 PM',
    LeftAt: '07:15 PM',
    ParkedHours: 5.25,
    AssignBy: 'Linda Davis',
    ReleasedBy: 'Robert Lee',
    TotalPrice: '$25.00',
    PaidBy: 'Card',
  },
  {
    NumberPlate: 'RST-987',
    ParkingSpace: 'Parking 6',
    VehicleType: 'Car',
    ArrivedAt: '08:45 AM',
    LeftAt: '11:30 AM',
    ParkedHours: 2.75,
    AssignBy: 'Sophia Martin',
    ReleasedBy: 'William Harris',
    TotalPrice: '$13.50',
    PaidBy: 'Cash',
  },
  {
    NumberPlate: 'JKL-789',
    ParkingSpace: 'Parking 7',
    VehicleType: 'Car',
    ArrivedAt: '01:15 PM',
    LeftAt: '04:30 PM',
    ParkedHours: 3.25,
    AssignBy: 'Emily Anderson',
    ReleasedBy: 'Daniel Young',
    TotalPrice: '$16.25',
    PaidBy: 'Card',
  },
  {
    NumberPlate: 'TUV-567',
    ParkingSpace: 'Parking 8',
    VehicleType: 'Car',
    ArrivedAt: '09:30 AM',
    LeftAt: '01:45 PM',
    ParkedHours: 4.25,
    AssignBy: 'Chloe Martinez',
    ReleasedBy: 'Joseph Turner',
    TotalPrice: '$21.50',
    PaidBy: 'Cash',
  },
  {
    NumberPlate: 'MNO-890',
    ParkingSpace: 'Parking 9',
    VehicleType: 'Van',
    ArrivedAt: '03:30 PM',
    LeftAt: '06:45 PM',
    ParkedHours: 3.25,
    AssignBy: 'Grace Lewis',
    ReleasedBy: 'Andrew King',
    TotalPrice: '$15.00',
    PaidBy: 'Card',
  },
  {
    NumberPlate: 'HIJ-234',
    ParkingSpace: 'Parking 10',
    VehicleType: 'Car',
    ArrivedAt: '10:45 AM',
    LeftAt: '12:15 PM',
    ParkedHours: 1.5,
    AssignBy: 'Lucas Moore',
    ReleasedBy: 'Ella Green',
    TotalPrice: '$7.50',
    PaidBy: 'Cash',
  },
  {
    NumberPlate: 'CDE-678',
    ParkingSpace: 'Parking 11',
    VehicleType: 'Van',
    ArrivedAt: '04:00 PM',
    LeftAt: '05:15 PM',
    ParkedHours: 1.25,
    AssignBy: 'Aiden Perez',
    ReleasedBy: 'Sofia Scott',
    TotalPrice: '$6.25',
    PaidBy: 'Card',
  },
  {
    NumberPlate: 'FGH-123',
    ParkingSpace: 'Parking 12',
    VehicleType: 'Car',
    ArrivedAt: '11:30 AM',
    LeftAt: '03:00 PM',
    ParkedHours: 3.5,
    AssignBy: 'Jackson Turner',
    ReleasedBy: 'Mia Hall',
    TotalPrice: '$17.50',
    PaidBy: 'Cash',
  },
];

function populateAdvancedUpdatesTable() {
  const tableBody = document.querySelector('#advancedUpdatesTable tbody');

  advancedUpdates.forEach((row) => {
    const newRow = document.createElement('tr');
    newRow.innerHTML = `
                          <td>${row.NumberPlate}</td>
                          <td>${row.ParkingSpace}</td>
                          <td>${row.VehicleType}</td>
                          <td>${row.ArrivedAt}</td>
                          <td>${row.LeftAt}</td>
                          <td>${row.ParkedHours}</td>
                          <td>${row.AssignBy}</td>
                          <td>${row.ReleasedBy}</td>
                          <td>${row.TotalPrice}</td>
                          <td>${row.PaidBy}</td>
                        `;
    tableBody.appendChild(newRow);
  });
}

populateAdvancedUpdatesTable();
