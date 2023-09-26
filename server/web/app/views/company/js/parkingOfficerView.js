const parkingOfficers = [
  {
    profileImage:
      'https://funkylife.in/wp-content/uploads/2023/08/whatsapp-dp-717.jpg',
    firstName: 'John',
    lastName: 'Doe',
    NIC: '123456789V',
    officerID: 'P12345',
    mobileNumber: '123-456-7890',
    assignedParkingSpace: 'Parking Lot 1',
  },
  {
    profileImage:
      'https://funkylife.in/wp-content/uploads/2023/08/whatsapp-dp-717.jpg',
    firstName: 'Jane',
    lastName: 'Smith',
    NIC: '987654321V',
    officerID: 'P54321',
    mobileNumber: '987-654-3210',
    assignedParkingSpace: 'Parking Lot 2',
  },
  {
    profileImage:
      'https://funkylife.in/wp-content/uploads/2023/08/whatsapp-dp-717.jpg',
    firstName: 'Mike',
    lastName: 'Johnson',
    NIC: '456789012V',
    officerID: 'P67890',
    mobileNumber: '456-789-0123',
    assignedParkingSpace: 'Parking Lot 3',
  },
  {
    profileImage:
      'https://funkylife.in/wp-content/uploads/2023/08/whatsapp-dp-717.jpg',
    firstName: 'Emily',
    lastName: 'Brown',
    NIC: '789012345V',
    officerID: 'P23456',
    mobileNumber: '789-012-3456',
    assignedParkingSpace: 'Parking Lot 4',
  },
  {
    profileImage:
      'https://funkylife.in/wp-content/uploads/2023/08/whatsapp-dp-717.jpg',
    firstName: 'Daniel',
    lastName: 'Wilson',
    NIC: '234567890V',
    officerID: 'P34567',
    mobileNumber: '234-567-8901',
    assignedParkingSpace: 'Parking Lot 5',
  },
  {
    profileImage:
      'https://funkylife.in/wp-content/uploads/2023/08/whatsapp-dp-717.jpg',
    firstName: 'Sarah',
    lastName: 'Clark',
    NIC: '890123456V',
    officerID: 'P45678',
    mobileNumber: '890-123-4567',
    assignedParkingSpace: 'Parking Lot 6',
  },
  {
    profileImage:
      'https://funkylife.in/wp-content/uploads/2023/08/whatsapp-dp-717.jpg',
    firstName: 'Michael',
    lastName: 'White',
    NIC: '345678901V',
    officerID: 'P56789',
    mobileNumber: '345-678-9012',
    assignedParkingSpace: 'Parking Lot 7',
  },
  {
    profileImage:
      'https://funkylife.in/wp-content/uploads/2023/08/whatsapp-dp-717.jpg',
    firstName: 'Olivia',
    lastName: 'Taylor',
    NIC: '901234567V',
    officerID: 'P67890',
    mobileNumber: '901-234-5678',
    assignedParkingSpace: 'Parking Lot 8',
  },
  {
    profileImage:
      'https://funkylife.in/wp-content/uploads/2023/08/whatsapp-dp-717.jpg',
    firstName: 'William',
    lastName: 'Anderson',
    NIC: '567890123V',
    officerID: 'P78901',
    mobileNumber: '567-890-1234',
    assignedParkingSpace: 'Parking Lot 9',
  },
];

function populateParkingCards() {
  const parkingOfficerContainer = document.getElementById('officerCards');

  parkingOfficers.forEach((officer) => {
    const card = document.createElement('div');
    card.className = 'officer-card';

    card.innerHTML = `
                          <div class="officer-section-one">
                            <img src="${officer.profileImage}" alt="profile image" class="dp-image"/>
                            <h3 class="officer-name">${officer.firstName} ${officer.lastName}</h3>
                            <h3 class="officer-id">${officer.officerID}</h3>
                            <h3 class="allocated-parking">${officer.assignedParkingSpace}</h3>
                          </div>
                          <div class="officer-section-second">
                            <p>NIC ${officer.NIC}</p>
                            <p class="officer-number">
                              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="button-icon mr-5">
                                <path fill-rule="evenodd" d="M1.5 4.5a3 3 0 013-3h1.372c.86 0 1.61.586 1.819 1.42l1.105 4.423a1.875 1.875 0 01-.694 1.955l-1.293.97c-.135.101-.164.249-.126.352a11.285 11.285 0 006.697 6.697c.103.038.25.009.352-.126l.97-1.293a1.875 1.875 0 011.955-.694l4.423 1.105c.834.209 1.42.959 1.42 1.82V19.5a3 3 0 01-3 3h-2.25C8.552 22.5 1.5 15.448 1.5 6.75V4.5z" clip-rule="evenodd" />
                              </svg>
                              ${officer.mobileNumber}
                            </p>
                          </div>
                        `;

    parkingOfficerContainer.appendChild(card);
  });

  const screenWidth = window.innerWidth;
  const cardsPerRow =
    screenWidth >= 1400
      ? 4
      : screenWidth >= 1000
      ? 3
      : screenWidth >= 600
      ? 2
      : 1;

  if (index >= cardsPerRow) {
    card.style.display = 'none';
  }
}

populateParkingCards();
window.addEventListener('resize', populateParkingCards);
