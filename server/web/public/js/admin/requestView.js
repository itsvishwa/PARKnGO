// Fetch data from the backend API
async function fetchData() {
    try {
        const response = await fetch('http://localhost/PARKnGO/server/web/admins/requestsView'); // Replace this URL with your actual backend endpoint
        if (!response.ok) {
            throw new Error('Network response was not ok.');
        }
        const data = await response.json();
        return data; // Return the fetched data
    } catch (error) {
        console.error('Error fetching data:', error);
        return []; // Return an empty array in case of an error
    }
}

// Function to create cards using fetched data
async function createCards() {
    const pendingApplications = await fetchData();

    const cardContainer = document.getElementById("cardContainer");

    // Loop through fetched data and create cards
    pendingApplications.forEach(application => {
        const card = document.createElement("div");
        card.classList.add("b-card", "bg-white"); 

        

        // Adjust padding, width, and height here
        card.style.padding = "45px"; // Change the padding as needed
        card.style.width = "125%"; // Change the width as needed
        card.style.height = "100%"; // Change the height as needed


        // Set up the card content using application data
        card.innerHTML = `
        
            <div class="b-card1-icon bg-white">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="r-menu-logo">
                <path fillRule="evenodd" d="M3 2.25a.75.75 0 000 1.5v16.5h-.75a.75.75 0 000 1.5H15v-18a.75.75 0 000-1.5H3zM6.75 19.5v-2.25a.75.75 0 01.75-.75h3a.75.75 0 01.75.75v2.25a.75.75 0 01-.75.75h-3a.75.75 0 01-.75-.75zM6 6.75A.75.75 0 016.75 6h.75a.75.75 0 010 1.5h-.75A.75.75 0 016 6.75zM6.75 9a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zM6 12.75a.75.75 0 01.75-.75h.75a.75.75 0 010 1.5h-.75a.75.75 0 01-.75-.75zM10.5 6a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zm-.75 3.75A.75.75 0 0110.5 9h.75a.75.75 0 010 1.5h-.75a.75.75 0 01-.75-.75zM10.5 12a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zM16.5 6.75v15h5.25a.75.75 0 000-1.5H21v-12a.75.75 0 000-1.5h-4.5zm1.5 4.5a.75.75 0 01.75-.75h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75h-.008a.75.75 0 01-.75-.75v-.008zm.75 2.25a.75.75 0 00-.75.75v.008c0 .414.336.75.75.75h.008a.75.75 0 00.75-.75v-.008a.75.75 0 00-.75-.75h-.008zM18 17.25a.75.75 0 01.75-.75h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75h-.008a.75.75 0 01-.75-.75v-.008z" clipRule="evenodd" />
              </svg>
            </div>
            <div class="b-card2-icon bg-primary">
              <div class="b-card2-content text-white">
              <p>${application.companyName}</p>
              </div>
            </div>           
            <div class="buttons">              
              <button class="approve-button" type="button">Approve</button>
              <button class="reject-button" type="button">Reject</button>
            </div>  
            <div>
              <div class="b-card5-icon bg-white">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" class="r-menu-logo">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M15 10.5a3 3 0 11-6 0 3 3 0 016 0z" />
                  <path strokeLinecap="round" strokeLinejoin="round" d="M19.5 10.5c0 7.142-7.5 11.25-7.5 11.25S4.5 17.642 4.5 10.5a7.5 7.5 0 1115 0z" />
                </svg>
              </div>
              <div class="b-card4-icon bg-white b-card4-content text-black">
              <p>${application.address}</p>
              </div>
            </div>
            <div class="b-card6-icon">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" class="r-menu-logo">
                <path strokeLinecap="round" strokeLinejoin="round" d="M6.75 3v2.25M17.25 3v2.25M3 18.75V7.5a2.25 2.25 0 012.25-2.25h13.5A2.25 2.25 0 0121 7.5v11.25m-18 0A2.25 2.25 0 005.25 21h13.5A2.25 2.25 0 0021 18.75m-18 0v-7.5A2.25 2.25 0 015.25 9h13.5A2.25 2.25 0 0121 11.25v7.5m-9-6h.008v.008H12v-.008zM12 15h.008v.008H12V15zm0 2.25h.008v.008H12v-.008zM9.75 15h.008v.008H9.75V15zm0 2.25h.008v.008H9.75v-.008zM7.5 15h.008v.008H7.5V15zm0 2.25h.008v.008H7.5v-.008zm6.75-4.5h.008v.008h-.008v-.008zm0 2.25h.008v.008h-.008V15zm0 2.25h.008v.008h-.008v-.008zm2.25-4.5h.008v.008H16.5v-.008zm0 2.25h.008v.008H16.5V15z" />
              </svg>
            </div>
            <div class="b-card7-icon bg-white b-card7-content text-black">
            <p>${application.dateTime}</p>
            </div>
            <div class="download-statement">
            <p>Download the application (<a href="${application.downloadLink}">Click here to download</a>)</p>             
            </div>
          
        `;

        cardContainer.appendChild(card); // Append the card to the container
    });

  }
// Call the createCards function to generate cards when the page loads
window.onload = createCards;
