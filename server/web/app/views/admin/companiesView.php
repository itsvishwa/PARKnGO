<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link href="<?php echo URLROOT; ?>/css/style.css" rel="stylesheet" />
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link rel="stylesheet" href="<?php echo URLROOT; ?>/css/admin/companiesView.css" />
  <title>Companies</title>
</head>

<body>
  <div class="container">
    <div class="left-container">
      <div class="bg-div">
        <img src="<?php echo URLROOT; ?>/css/assets/logo-black.png" alt="logo" width="85%" />
        <div>
          <ul class="menu">
            <li>
              <a href="./dashboardView">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M15.59 14.37a6 6 0 01-5.84 7.38v-4.8m5.84-2.58a14.98 14.98 0 006.16-12.12A14.98 14.98 0 009.631 8.41m5.96 5.96a14.926 14.926 0 01-5.841 2.58m-.119-8.54a6 6 0 00-7.381 5.84h4.8m2.581-5.84a14.927 14.927 0 00-2.58 5.84m2.699 2.7c-.103.021-.207.041-.311.06a15.09 15.09 0 01-2.448-2.448 14.9 14.9 0 01.06-.312m-2.24 2.39a4.493 4.493 0 00-1.757 4.306 4.493 4.493 0 004.306-1.758M16.5 9a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0z" />
                </svg>
                Dashboard
              </a>
            </li>
            <li>
              <a href="./requestsView">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" class="menu-logo">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M7.5 8.25h9m-9 3H12m-9.75 1.51c0 1.6 1.123 2.994 2.707 3.227 1.129.166 2.27.293 3.423.379.35.026.67.21.865.501L12 21l2.755-4.133a1.14 1.14 0 01.865-.501 48.172 48.172 0 003.423-.379c1.584-.233 2.707-1.626 2.707-3.228V6.741c0-1.602-1.123-2.995-2.707-3.228A48.394 48.394 0 0012 3c-2.392 0-4.744.175-7.043.513C3.373 3.746 2.25 5.14 2.25 6.741v6.018z" />
                </svg>
                Requests
              </a>
            </li>
            <li>
              <a href="./requestsHistoryView">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" class="menu-logo">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M12 6v6h4.5m4.5 0a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
                Request History
              </a>
            </li>
            <li class="active">
              <a href="./companiesView">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" class="menu-logo">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M2.25 21h19.5m-18-18v18m10.5-18v18m6-13.5V21M6.75 6.75h.75m-.75 3h.75m-.75 3h.75m3-6h.75m-.75 3h.75m-.75 3h.75M6.75 21v-3.375c0-.621.504-1.125 1.125-1.125h2.25c.621 0 1.125.504 1.125 1.125V21M3 3h12m-.75 4.5H21m-3.75 3.75h.008v.008h-.008v-.008zm0 3h.008v.008h-.008v-.008zm0 3h.008v.008h-.008v-.008z" />
                </svg>
                Companies
              </a>
            </li>
            <li>
              <a href="./deletionView">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" class="menu-logo">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M18 18.72a9.094 9.094 0 003.741-.479 3 3 0 00-4.682-2.72m.94 3.198l.001.031c0 .225-.012.447-.037.666A11.944 11.944 0 0112 21c-2.17 0-4.207-.576-5.963-1.584A6.062 6.062 0 016 18.719m12 0a5.971 5.971 0 00-.941-3.197m0 0A5.995 5.995 0 0012 12.75a5.995 5.995 0 00-5.058 2.772m0 0a3 3 0 00-4.681 2.72 8.986 8.986 0 003.74.477m.94-3.197a5.971 5.971 0 00-.94 3.197M15 6.75a3 3 0 11-6 0 3 3 0 016 0zm6 3a2.25 2.25 0 11-4.5 0 2.25 2.25 0 014.5 0zm-13.5 0a2.25 2.25 0 11-4.5 0 2.25 2.25 0 014.5 0z" />
                </svg>
                Deletion
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="right-container">
      <div class="header">
        <div class="pageName">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="menu-logo">
            <path fillRule="evenodd" d="M3 6.75A.75.75 0 013.75 6h16.5a.75.75 0 010 1.5H3.75A.75.75 0 013 6.75zM3 12a.75.75 0 01.75-.75h16.5a.75.75 0 010 1.5H3.75A.75.75 0 013 12zm0 5.25a.75.75 0 01.75-.75h16.5a.75.75 0 010 1.5H3.75a.75.75 0 01-.75-.75z" clipRule="evenodd" />
          </svg>
          <h3>Companies</h3>
        </div>
        <div class="profile">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo mr">
            <path stroke-linecap="round" stroke-linejoin="round" d="M14.857 17.082a23.848 23.848 0 005.454-1.31A8.967 8.967 0 0118 9.75v-.7V9A6 6 0 006 9v.75a8.967 8.967 0 01-2.312 6.022c1.733.64 3.56 1.085 5.455 1.31m5.714 0a24.255 24.255 0 01-5.714 0m5.714 0a3 3 0 11-5.714 0" />
          </svg>

          <a href="./dashboardView" class="company-name"><?php echo $_SESSION['user_name']; ?></a>
          
          
        </div>
      </div>
      <div class="business">
        <div class="heading">
        </div>
      </div>
      <div class="b-card bg-white">
        <div class="b-card-content p text-grey">
          <p>Search a company</p>
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" class="search-logo">
            <path strokeLinecap="round" strokeLinejoin="round" d="M21 21l-5.197-5.197m0 0A7.5 7.5 0 105.196 5.196a7.5 7.5 0 0010.607 10.607z" />
          </svg>
        </div>
      </div>
      <div class="card-section1">
        <div class="b-card1 bg-white">
          <div class="company-icon bg-grey">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="r-menu-logo">
              <path fillRule="evenodd" d="M3 2.25a.75.75 0 000 1.5v16.5h-.75a.75.75 0 000 1.5H15v-18a.75.75 0 000-1.5H3zM6.75 19.5v-2.25a.75.75 0 01.75-.75h3a.75.75 0 01.75.75v2.25a.75.75 0 01-.75.75h-3a.75.75 0 01-.75-.75zM6 6.75A.75.75 0 016.75 6h.75a.75.75 0 010 1.5h-.75A.75.75 0 016 6.75zM6.75 9a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zM6 12.75a.75.75 0 01.75-.75h.75a.75.75 0 010 1.5h-.75a.75.75 0 01-.75-.75zM10.5 6a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zm-.75 3.75A.75.75 0 0110.5 9h.75a.75.75 0 010 1.5h-.75a.75.75 0 01-.75-.75zM10.5 12a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zM16.5 6.75v15h5.25a.75.75 0 000-1.5H21v-12a.75.75 0 000-1.5h-4.5zm1.5 4.5a.75.75 0 01.75-.75h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75h-.008a.75.75 0 01-.75-.75v-.008zm.75 2.25a.75.75 0 00-.75.75v.008c0 .414.336.75.75.75h.008a.75.75 0 00.75-.75v-.008a.75.75 0 00-.75-.75h-.008zM18 17.25a.75.75 0 01.75-.75h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75h-.008a.75.75 0 01-.75-.75v-.008z" clipRule="evenodd" />
            </svg>
          </div>
          <div class="company-card2-icon bg-white">
            <div class="company-card2-content text-black">
              <p>ABC PVT LTD </p>
            </div>
            <div class="company-card3-content text-grey">
              <p>ID COMP001A1</p>
            </div>
            <div class="location-icon">
              <span class="material-symbols-outlined">
                location_on
              </span>
            </div>
            <div class="location-card2-icon bg-white">
              <div class="location-card2-content text-black">
                <p>No: 13, Temple Rd, Colombo</p>
              </div>
              <div class="mail-icon">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="ur-menu-logo">
                  <path d="M1.5 8.67v8.58a3 3 0 003 3h15a3 3 0 003-3V8.67l-8.928 5.493a3 3 0 01-3.144 0L1.5 8.67z" />
                  <path d="M22.5 6.908V6.75a3 3 0 00-3-3h-15a3 3 0 00-3 3v.158l9.714 5.978a1.5 1.5 0 001.572 0L22.5 6.908z" />
                </svg>
              </div>
              <div class="mail-card2-icon bg-white">
                <div class="mail-card2-content text-black">
                  <p>abc@abc.lk</p>
                </div>
                <div class="contact-icon">
                  <span class="material-symbols-outlined">
                    call
                  </span>
                </div>
                <div class="contact-card2-icon bg-white">
                  <div class="contact-card2-content text-black">
                    <p>011-1561567</p>
                  </div>
                  <div class="b-view-more bg-grey">
                    <div class="b-view-more-card2-content text-black">
                      <p>View More</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div>
            </div>
          </div>
        </div>
        <div class="b-card2 bg-white">
          <div class="company-icon bg-grey">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="r-menu-logo">
              <path fillRule="evenodd" d="M3 2.25a.75.75 0 000 1.5v16.5h-.75a.75.75 0 000 1.5H15v-18a.75.75 0 000-1.5H3zM6.75 19.5v-2.25a.75.75 0 01.75-.75h3a.75.75 0 01.75.75v2.25a.75.75 0 01-.75.75h-3a.75.75 0 01-.75-.75zM6 6.75A.75.75 0 016.75 6h.75a.75.75 0 010 1.5h-.75A.75.75 0 016 6.75zM6.75 9a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zM6 12.75a.75.75 0 01.75-.75h.75a.75.75 0 010 1.5h-.75a.75.75 0 01-.75-.75zM10.5 6a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zm-.75 3.75A.75.75 0 0110.5 9h.75a.75.75 0 010 1.5h-.75a.75.75 0 01-.75-.75zM10.5 12a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zM16.5 6.75v15h5.25a.75.75 0 000-1.5H21v-12a.75.75 0 000-1.5h-4.5zm1.5 4.5a.75.75 0 01.75-.75h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75h-.008a.75.75 0 01-.75-.75v-.008zm.75 2.25a.75.75 0 00-.75.75v.008c0 .414.336.75.75.75h.008a.75.75 0 00.75-.75v-.008a.75.75 0 00-.75-.75h-.008zM18 17.25a.75.75 0 01.75-.75h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75h-.008a.75.75 0 01-.75-.75v-.008z" clipRule="evenodd" />
            </svg>
          </div>
          <div class="company-card2-icon bg-white">
            <div class="company-card2-content text-black">
              <p>ABC PVT LTD </p>
            </div>
            <div class="company-card3-content text-grey">
              <p>ID COMP001A1</p>
            </div>
            <div class="location-icon">
              <span class="material-symbols-outlined">
                location_on
              </span>
            </div>
            <div class="location-card2-icon bg-white">
              <div class="location-card2-content text-black">
                <p>No: 13, Temple Rd, Colombo</p>
              </div>
              <div class="mail-icon">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="ur-menu-logo">
                  <path d="M1.5 8.67v8.58a3 3 0 003 3h15a3 3 0 003-3V8.67l-8.928 5.493a3 3 0 01-3.144 0L1.5 8.67z" />
                  <path d="M22.5 6.908V6.75a3 3 0 00-3-3h-15a3 3 0 00-3 3v.158l9.714 5.978a1.5 1.5 0 001.572 0L22.5 6.908z" />
                </svg>
              </div>
              <div class="mail-card2-icon bg-white">
                <div class="mail-card2-content text-black">
                  <p>abc@abc.lk</p>
                </div>
                <div class="contact-icon">
                  <span class="material-symbols-outlined">
                    call
                  </span>
                </div>
                <div class="contact-card2-icon bg-white">
                  <div class="contact-card2-content text-black">
                    <p>011-1561567</p>
                  </div>
                  <div class="b-view-more bg-grey">
                    <div class="b-view-more-card2-content text-black">
                      <p>View More</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div>
            </div>
          </div>
        </div>
      </div>
      <div class="card-section2">
        <div class="b-card1 bg-white">
          <div class="company-icon bg-grey">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="r-menu-logo">
              <path fillRule="evenodd" d="M3 2.25a.75.75 0 000 1.5v16.5h-.75a.75.75 0 000 1.5H15v-18a.75.75 0 000-1.5H3zM6.75 19.5v-2.25a.75.75 0 01.75-.75h3a.75.75 0 01.75.75v2.25a.75.75 0 01-.75.75h-3a.75.75 0 01-.75-.75zM6 6.75A.75.75 0 016.75 6h.75a.75.75 0 010 1.5h-.75A.75.75 0 016 6.75zM6.75 9a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zM6 12.75a.75.75 0 01.75-.75h.75a.75.75 0 010 1.5h-.75a.75.75 0 01-.75-.75zM10.5 6a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zm-.75 3.75A.75.75 0 0110.5 9h.75a.75.75 0 010 1.5h-.75a.75.75 0 01-.75-.75zM10.5 12a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zM16.5 6.75v15h5.25a.75.75 0 000-1.5H21v-12a.75.75 0 000-1.5h-4.5zm1.5 4.5a.75.75 0 01.75-.75h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75h-.008a.75.75 0 01-.75-.75v-.008zm.75 2.25a.75.75 0 00-.75.75v.008c0 .414.336.75.75.75h.008a.75.75 0 00.75-.75v-.008a.75.75 0 00-.75-.75h-.008zM18 17.25a.75.75 0 01.75-.75h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75h-.008a.75.75 0 01-.75-.75v-.008z" clipRule="evenodd" />
            </svg>
          </div>
          <div class="company-card2-icon bg-white">
            <div class="company-card2-content text-black">
              <p>ABC PVT LTD </p>
            </div>
            <div class="company-card3-content text-grey">
              <p>ID COMP001A1</p>
            </div>
            <div class="location-icon">
              <span class="material-symbols-outlined">
                location_on
              </span>
            </div>
            <div class="location-card2-icon bg-white">
              <div class="location-card2-content text-black">
                <p>No: 13, Temple Rd, Colombo</p>
              </div>
              <div class="mail-icon">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="ur-menu-logo">
                  <path d="M1.5 8.67v8.58a3 3 0 003 3h15a3 3 0 003-3V8.67l-8.928 5.493a3 3 0 01-3.144 0L1.5 8.67z" />
                  <path d="M22.5 6.908V6.75a3 3 0 00-3-3h-15a3 3 0 00-3 3v.158l9.714 5.978a1.5 1.5 0 001.572 0L22.5 6.908z" />
                </svg>
              </div>
              <div class="mail-card2-icon bg-white">
                <div class="mail-card2-content text-black">
                  <p>abc@abc.lk</p>
                </div>
                <div class="contact-icon">
                  <span class="material-symbols-outlined">
                    call
                  </span>
                </div>
                <div class="contact-card2-icon bg-white">
                  <div class="contact-card2-content text-black">
                    <p>011-1561567</p>
                  </div>
                  <div class="b-view-more bg-grey">
                    <div class="b-view-more-card2-content text-black">
                      <p>View More</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div>
            </div>
          </div>
        </div>
        <div class="b-card2 bg-white">
          <div class="company-icon bg-grey">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="r-menu-logo">
              <path fillRule="evenodd" d="M3 2.25a.75.75 0 000 1.5v16.5h-.75a.75.75 0 000 1.5H15v-18a.75.75 0 000-1.5H3zM6.75 19.5v-2.25a.75.75 0 01.75-.75h3a.75.75 0 01.75.75v2.25a.75.75 0 01-.75.75h-3a.75.75 0 01-.75-.75zM6 6.75A.75.75 0 016.75 6h.75a.75.75 0 010 1.5h-.75A.75.75 0 016 6.75zM6.75 9a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zM6 12.75a.75.75 0 01.75-.75h.75a.75.75 0 010 1.5h-.75a.75.75 0 01-.75-.75zM10.5 6a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zm-.75 3.75A.75.75 0 0110.5 9h.75a.75.75 0 010 1.5h-.75a.75.75 0 01-.75-.75zM10.5 12a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zM16.5 6.75v15h5.25a.75.75 0 000-1.5H21v-12a.75.75 0 000-1.5h-4.5zm1.5 4.5a.75.75 0 01.75-.75h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75h-.008a.75.75 0 01-.75-.75v-.008zm.75 2.25a.75.75 0 00-.75.75v.008c0 .414.336.75.75.75h.008a.75.75 0 00.75-.75v-.008a.75.75 0 00-.75-.75h-.008zM18 17.25a.75.75 0 01.75-.75h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75h-.008a.75.75 0 01-.75-.75v-.008z" clipRule="evenodd" />
            </svg>
          </div>
          <div class="company-card2-icon bg-white">
            <div class="company-card2-content text-black">
              <p>ABC PVT LTD </p>
            </div>
            <div class="company-card3-content text-grey">
              <p>ID COMP001A1</p>
            </div>
            <div class="location-icon">
              <span class="material-symbols-outlined">
                location_on
              </span>
            </div>
            <div class="location-card2-icon bg-white">
              <div class="location-card2-content text-black">
                <p>No: 13, Temple Rd, Colombo</p>
              </div>
              <div class="mail-icon">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="ur-menu-logo">
                  <path d="M1.5 8.67v8.58a3 3 0 003 3h15a3 3 0 003-3V8.67l-8.928 5.493a3 3 0 01-3.144 0L1.5 8.67z" />
                  <path d="M22.5 6.908V6.75a3 3 0 00-3-3h-15a3 3 0 00-3 3v.158l9.714 5.978a1.5 1.5 0 001.572 0L22.5 6.908z" />
                </svg>
              </div>
              <div class="mail-card2-icon bg-white">
                <div class="mail-card2-content text-black">
                  <p>abc@abc.lk</p>
                </div>
                <div class="contact-icon">
                  <span class="material-symbols-outlined">
                    call
                  </span>
                </div>
                <div class="contact-card2-icon bg-white">
                  <div class="contact-card2-content text-black">
                    <p>011-1561567</p>
                  </div>
                  <div class="b-view-more bg-grey">
                    <div class="b-view-more-card2-content text-black">
                      <p>View More</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div>
            </div>
          </div>
        </div>
      </div>
      <div class="card-section3">
        <div class="b-card1 bg-white">
          <div class="company-icon bg-grey">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="r-menu-logo">
              <path fillRule="evenodd" d="M3 2.25a.75.75 0 000 1.5v16.5h-.75a.75.75 0 000 1.5H15v-18a.75.75 0 000-1.5H3zM6.75 19.5v-2.25a.75.75 0 01.75-.75h3a.75.75 0 01.75.75v2.25a.75.75 0 01-.75.75h-3a.75.75 0 01-.75-.75zM6 6.75A.75.75 0 016.75 6h.75a.75.75 0 010 1.5h-.75A.75.75 0 016 6.75zM6.75 9a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zM6 12.75a.75.75 0 01.75-.75h.75a.75.75 0 010 1.5h-.75a.75.75 0 01-.75-.75zM10.5 6a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zm-.75 3.75A.75.75 0 0110.5 9h.75a.75.75 0 010 1.5h-.75a.75.75 0 01-.75-.75zM10.5 12a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zM16.5 6.75v15h5.25a.75.75 0 000-1.5H21v-12a.75.75 0 000-1.5h-4.5zm1.5 4.5a.75.75 0 01.75-.75h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75h-.008a.75.75 0 01-.75-.75v-.008zm.75 2.25a.75.75 0 00-.75.75v.008c0 .414.336.75.75.75h.008a.75.75 0 00.75-.75v-.008a.75.75 0 00-.75-.75h-.008zM18 17.25a.75.75 0 01.75-.75h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75h-.008a.75.75 0 01-.75-.75v-.008z" clipRule="evenodd" />
            </svg>
          </div>
          <div class="company-card2-icon bg-white">
            <div class="company-card2-content text-black">
              <p>ABC PVT LTD </p>
            </div>
            <div class="company-card3-content text-grey">
              <p>ID COMP001A1</p>
            </div>
            <div class="location-icon">
              <span class="material-symbols-outlined">
                location_on
              </span>
            </div>
            <div class="location-card2-icon bg-white">
              <div class="location-card2-content text-black">
                <p>No: 13, Temple Rd, Colombo</p>
              </div>
              <div class="mail-icon">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="ur-menu-logo">
                  <path d="M1.5 8.67v8.58a3 3 0 003 3h15a3 3 0 003-3V8.67l-8.928 5.493a3 3 0 01-3.144 0L1.5 8.67z" />
                  <path d="M22.5 6.908V6.75a3 3 0 00-3-3h-15a3 3 0 00-3 3v.158l9.714 5.978a1.5 1.5 0 001.572 0L22.5 6.908z" />
                </svg>
              </div>
              <div class="mail-card2-icon bg-white">
                <div class="mail-card2-content text-black">
                  <p>abc@abc.lk</p>
                </div>

                <div class="contact-icon">
                  <span class="material-symbols-outlined">
                    call
                  </span>
                </div>
                <div class="contact-card2-icon bg-white">
                  <div class="contact-card2-content text-black">
                    <p>011-1561567</p>
                  </div>
                  <div class="b-view-more bg-grey">
                    <div class="b-view-more-card2-content text-black">
                      <p>View More</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div>
            </div>
          </div>
        </div>
        <div class="b-card2 bg-white">
          <div class="company-icon bg-grey">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="r-menu-logo">
              <path fillRule="evenodd" d="M3 2.25a.75.75 0 000 1.5v16.5h-.75a.75.75 0 000 1.5H15v-18a.75.75 0 000-1.5H3zM6.75 19.5v-2.25a.75.75 0 01.75-.75h3a.75.75 0 01.75.75v2.25a.75.75 0 01-.75.75h-3a.75.75 0 01-.75-.75zM6 6.75A.75.75 0 016.75 6h.75a.75.75 0 010 1.5h-.75A.75.75 0 016 6.75zM6.75 9a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zM6 12.75a.75.75 0 01.75-.75h.75a.75.75 0 010 1.5h-.75a.75.75 0 01-.75-.75zM10.5 6a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zm-.75 3.75A.75.75 0 0110.5 9h.75a.75.75 0 010 1.5h-.75a.75.75 0 01-.75-.75zM10.5 12a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zM16.5 6.75v15h5.25a.75.75 0 000-1.5H21v-12a.75.75 0 000-1.5h-4.5zm1.5 4.5a.75.75 0 01.75-.75h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75h-.008a.75.75 0 01-.75-.75v-.008zm.75 2.25a.75.75 0 00-.75.75v.008c0 .414.336.75.75.75h.008a.75.75 0 00.75-.75v-.008a.75.75 0 00-.75-.75h-.008zM18 17.25a.75.75 0 01.75-.75h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75h-.008a.75.75 0 01-.75-.75v-.008z" clipRule="evenodd" />
            </svg>
          </div>
          <div class="company-card2-icon bg-white">
            <div class="company-card2-content text-black">
              <p>ABC PVT LTD </p>
            </div>
            <div class="company-card3-content text-grey">
              <p>ID COMP001A1</p>
            </div>
            <div class="location-icon">
              <span class="material-symbols-outlined">
                location_on
              </span>
            </div>
            <div class="location-card2-icon bg-white">
              <div class="location-card2-content text-black">
                <p>No: 13, Temple Rd, Colombo</p>
              </div>
              <div class="mail-icon">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="ur-menu-logo">
                  <path d="M1.5 8.67v8.58a3 3 0 003 3h15a3 3 0 003-3V8.67l-8.928 5.493a3 3 0 01-3.144 0L1.5 8.67z" />
                  <path d="M22.5 6.908V6.75a3 3 0 00-3-3h-15a3 3 0 00-3 3v.158l9.714 5.978a1.5 1.5 0 001.572 0L22.5 6.908z" />
                </svg>
              </div>
              <div class="mail-card2-icon bg-white">
                <div class="mail-card2-content text-black">
                  <p>abc@abc.lk</p>
                </div>
                <div class="contact-icon">
                  <span class="material-symbols-outlined">
                    call
                  </span>
                </div>
                <div class="contact-card2-icon bg-white">
                  <div class="contact-card2-content text-black">
                    <p>011-1561567</p>
                  </div>
                  <div class="b-view-more bg-grey">
                    <div class="b-view-more-card2-content text-black">
                      <p>View More</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="dashboard-main-section">
      <div class="update-section">
        <div class="table-heading">
          <div class="heading">
          </div>
        </div>
      </div>
</body>

</html>