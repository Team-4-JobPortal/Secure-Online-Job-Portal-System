<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>JobHub - Find Your Dream Job</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            line-height: 1.6;
            color: #333;
        }

        :root {
            --primary-color: #3b82f6;
            --secondary-color: #1e40af;
            --success-color: #10b981;
            --warning-color: #f59e0b;
            --text-color: #374151;
            --light-bg: #f8fafc;
            --white: #ffffff;
            --gradient: linear-gradient(135deg, #3b82f6 0%, #1e40af 100%);
            --shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
            --shadow-hover: 0 15px 35px rgba(0, 0, 0, 0.15);
        }

        /* Navigation */
        .navbar {
            background: var(--white);
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 1000;
            transition: all 0.3s ease;
        }

        .nav-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            height: 70px;
        }

        .logo {
            font-size: 28px;
            font-weight: 800;
            background: var(--gradient);
            background-clip: text;
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            text-decoration: none;
        }

        .nav-menu {
            display: flex;
            list-style: none;
            align-items: center;
            gap: 30px;
        }

        .nav-menu a {
            text-decoration: none;
            color: var(--text-color);
            font-weight: 500;
            transition: color 0.3s ease;
            position: relative;
        }

        .nav-menu a:hover {
            color: var(--primary-color);
        }

        .nav-menu a::after {
            content: '';
            position: absolute;
            bottom: -5px;
            left: 0;
            width: 0;
            height: 2px;
            background: var(--primary-color);
            transition: width 0.3s ease;
        }

        .nav-menu a:hover::after {
            width: 100%;
        }

        .auth-buttons {
            display: flex;
            gap: 15px;
            align-items: center;
        }

        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 50px;
            font-weight: 600;
            text-decoration: none;
            cursor: pointer;
            transition: all 0.3s ease;
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }

        .btn-outline {
            background: transparent;
            border: 2px solid var(--primary-color);
            color: var(--primary-color);
        }

        .btn-outline:hover {
            background: var(--primary-color);
            color: white;
            transform: translateY(-2px);
            box-shadow: var(--shadow);
        }

        .btn-primary {
            background: var(--gradient);
            color: white;
            border: 2px solid transparent;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: var(--shadow-hover);
        }

        .btn-danger {
            background: #ef4444;
            color: white;
            border: 2px solid transparent;
        }

        .btn-danger:hover {
            background: #dc2626;
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(239, 68, 68, 0.3);
        }

        /* Mobile Menu */
        .mobile-menu-btn {
            display: none;
            background: none;
            border: none;
            font-size: 24px;
            color: var(--text-color);
            cursor: pointer;
        }

        /* Hero Section */
        .hero {
            background: var(--gradient);
            padding: 120px 0 80px;
            color: white;
            position: relative;
            overflow: hidden;
        }

        .hero::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1000 300"><path d="M0,300V80c50-20,100-40,200-30s200,50,400,20s300-80,400-50v280z" fill="rgba(255,255,255,0.1)"/></svg>') no-repeat;
            background-size: cover;
            opacity: 0.7;
        }

        .hero-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
            position: relative;
            z-index: 2;
        }

        .hero-content {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 60px;
            align-items: center;
        }

        .hero-text h1 {
            font-size: 3.5rem;
            font-weight: 800;
            margin-bottom: 20px;
            line-height: 1.2;
        }

        .hero-text p {
            font-size: 1.2rem;
            margin-bottom: 30px;
            opacity: 0.9;
            line-height: 1.8;
        }

        .hero-buttons {
            display: flex;
            gap: 20px;
            flex-wrap: wrap;
        }

        .hero-image {
            text-align: center;
        }

        .hero-illustration {
            width: 100%;
            max-width: 500px;
            height: auto;
        }

        /* Search Section */
        .search-section {
            background: white;
            padding: 40px 0;
            margin-top: -40px;
            position: relative;
            z-index: 3;
        }

        .search-container {
            max-width: 800px;
            margin: 0 auto;
            padding: 0 20px;
        }

        .search-form {
            background: white;
            padding: 30px;
            border-radius: 20px;
            box-shadow: var(--shadow-hover);
            display: grid;
            grid-template-columns: 1fr 1fr auto;
            gap: 15px;
            align-items: end;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        .form-group label {
            font-weight: 600;
            margin-bottom: 8px;
            color: var(--text-color);
        }

        .form-group input, .form-group select {
            padding: 15px;
            border: 2px solid #e5e7eb;
            border-radius: 10px;
            font-size: 16px;
            transition: border-color 0.3s ease;
        }

        .form-group input:focus, .form-group select:focus {
            outline: none;
            border-color: var(--primary-color);
            box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
        }

        .search-btn {
            padding: 15px 30px;
            background: var(--gradient);
            color: white;
            border: none;
            border-radius: 10px;
            font-weight: 600;
            cursor: pointer;
            transition: transform 0.3s ease;
            height: fit-content;
        }

        .search-btn:hover {
            transform: translateY(-2px);
            box-shadow: var(--shadow-hover);
        }

        /* Features Section */
        .features {
            padding: 80px 0;
            background: var(--light-bg);
        }

        .features-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
        }

        .section-header {
            text-align: center;
            margin-bottom: 60px;
        }

        .section-header h2 {
            font-size: 2.5rem;
            font-weight: 800;
            color: var(--text-color);
            margin-bottom: 15px;
        }

        .section-header p {
            font-size: 1.1rem;
            color: #6b7280;
            max-width: 600px;
            margin: 0 auto;
        }

        .features-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 40px;
        }

        .feature-card {
            background: white;
            padding: 40px 30px;
            border-radius: 20px;
            text-align: center;
            box-shadow: var(--shadow);
            transition: transform 0.3s ease;
        }

        .feature-card:hover {
            transform: translateY(-10px);
        }

        .feature-icon {
            width: 80px;
            height: 80px;
            margin: 0 auto 20px;
            background: var(--gradient);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 32px;
            color: white;
        }

        .feature-card h3 {
            font-size: 1.5rem;
            font-weight: 700;
            margin-bottom: 15px;
            color: var(--text-color);
        }

        .feature-card p {
            color: #6b7280;
            line-height: 1.7;
        }

        /* Stats Section */
        .stats {
            padding: 80px 0;
            background: white;
        }

        .stats-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
        }

        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 40px;
            text-align: center;
        }

        .stat-item h3 {
            font-size: 3rem;
            font-weight: 800;
            color: var(--primary-color);
            margin-bottom: 10px;
        }

        .stat-item p {
            font-size: 1.1rem;
            color: var(--text-color);
            font-weight: 600;
        }

        /* Footer */
        .footer {
            background: #1f2937;
            color: white;
            padding: 60px 0 30px;
        }

        .footer-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
        }

        .footer-content {
            display: grid;
            grid-template-columns: 2fr 1fr 1fr 1fr;
            gap: 40px;
            margin-bottom: 40px;
        }

        .footer-section h3 {
            font-size: 1.3rem;
            font-weight: 700;
            margin-bottom: 20px;
            color: white;
        }

        .footer-section p, .footer-section a {
            color: #9ca3af;
            text-decoration: none;
            line-height: 1.8;
            transition: color 0.3s ease;
        }

        .footer-section a:hover {
            color: var(--primary-color);
        }

        .footer-links {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        .footer-bottom {
            border-top: 1px solid #374151;
            padding-top: 30px;
            text-align: center;
            color: #9ca3af;
        }

        /* User Profile Dropdown */
        .user-profile {
            position: relative;
            display: none;
        }

        .user-profile.show {
            display: block;
        }

        .profile-btn {
            background: var(--light-bg);
            border: 2px solid var(--primary-color);
            border-radius: 50px;
            padding: 8px 20px;
            display: flex;
            align-items: center;
            gap: 10px;
            cursor: pointer;
            color: var(--primary-color);
            font-weight: 600;
        }

        .profile-dropdown {
            position: absolute;
            top: 100%;
            right: 0;
            background: white;
            border-radius: 10px;
            box-shadow: var(--shadow-hover);
            padding: 10px 0;
            min-width: 200px;
            display: none;
        }

        .profile-dropdown.show {
            display: block;
        }

        .dropdown-item {
            padding: 12px 20px;
            color: var(--text-color);
            text-decoration: none;
            display: flex;
            align-items: center;
            gap: 10px;
            transition: background 0.3s ease;
        }

        .dropdown-item:hover {
            background: var(--light-bg);
            color: var(--primary-color);
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .nav-menu {
                display: none;
            }

            .mobile-menu-btn {
                display: block;
            }

            .auth-buttons {
                gap: 10px;
            }

            .btn {
                padding: 10px 20px;
                font-size: 14px;
            }

            .hero-content {
                grid-template-columns: 1fr;
                gap: 40px;
                text-align: center;
            }

            .hero-text h1 {
                font-size: 2.5rem;
            }

            .search-form {
                grid-template-columns: 1fr;
                gap: 20px;
            }

            .section-header h2 {
                font-size: 2rem;
            }

            .footer-content {
                grid-template-columns: 1fr;
                gap: 30px;
            }

            .stats-grid {
                grid-template-columns: repeat(2, 1fr);
            }

            .stat-item h3 {
                font-size: 2rem;
            }
        }

        @media (max-width: 480px) {
            .hero-text h1 {
                font-size: 2rem;
            }

            .hero-buttons {
                flex-direction: column;
                align-items: stretch;
            }

            .stats-grid {
                grid-template-columns: 1fr;
            }
        }

        /* Animation */
        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .animate-fade-in {
            animation: fadeInUp 0.6s ease forwards;
        }
    </style>
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar">
        <div class="nav-container">
            <a href="#" class="logo">
                <i class="fas fa-briefcase"></i> JobHub
            </a>
            
            <ul class="nav-menu">
                <li><a href="#home">Home</a></li>
                <li><a href="#jobs">Find Jobs</a></li>
                <li><a href="#companies">Companies</a></li>
                <li><a href="#about">About</a></li>
                <li><a href="#contact">Contact</a></li>
            </ul>

            <div class="auth-buttons">
                <!-- Login/Register Buttons (shown when not logged in) -->
                <div id="authButtons">
                    <a href="#" class="btn btn-outline" onclick="showLogin()">
                        <i class="fas fa-sign-in-alt"></i> Login
                    </a>
                    <a href="#" class="btn btn-primary" onclick="showRegister()">
                        <i class="fas fa-user-plus"></i> Register
                    </a>
                </div>

                <!-- User Profile (shown when logged in) -->
                <div class="user-profile" id="userProfile">
                    <button class="profile-btn" onclick="toggleDropdown()">
                        <i class="fas fa-user-circle"></i>
                        <span id="userName">John Doe</span>
                        <i class="fas fa-chevron-down"></i>
                    </button>
                    <div class="profile-dropdown" id="profileDropdown">
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-user"></i> Profile
                        </a>
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-briefcase"></i> My Jobs
                        </a>
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-cog"></i> Settings
                        </a>
                        <a href="#" class="dropdown-item" onclick="logout()">
                            <i class="fas fa-sign-out-alt"></i> Logout
                        </a>
                    </div>
                </div>
            </div>

            <button class="mobile-menu-btn">
                <i class="fas fa-bars"></i>
            </button>
        </div>
    </nav>

    <!-- Hero Section -->
    <section class="hero" id="home">
        <div class="hero-container">
            <div class="hero-content">
                <div class="hero-text animate-fade-in">
                    <h1>Find Your Dream Job Today</h1>
                    <p>Connect with top employers and discover exciting career opportunities. Your perfect job is just a click away!</p>
                    <div class="hero-buttons">
                        <a href="#jobs" class="btn btn-primary btn-lg">
                            <i class="fas fa-search"></i> Find Jobs
                        </a>
                        <a href="#" class="btn btn-outline btn-lg" onclick="showRegister()">
                            <i class="fas fa-upload"></i> Post Resume
                        </a>
                    </div>
                </div>
                <div class="hero-image">
                    <svg class="hero-illustration" viewBox="0 0 500 400" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <rect x="50" y="100" width="400" height="250" rx="20" fill="white" fill-opacity="0.1"/>
                        <rect x="80" y="130" width="150" height="20" rx="10" fill="white" fill-opacity="0.3"/>
                        <rect x="80" y="170" width="200" height="15" rx="7" fill="white" fill-opacity="0.2"/>
                        <rect x="80" y="200" width="180" height="15" rx="7" fill="white" fill-opacity="0.2"/>
                        <rect x="80" y="230" width="160" height="15" rx="7" fill="white" fill-opacity="0.2"/>
                        <circle cx="350" cy="180" r="30" fill="white" fill-opacity="0.3"/>
                        <rect x="320" y="230" width="60" height="30" rx="15" fill="#10b981"/>
                        <path d="M100 50 L200 20 L300 60 L400 30" stroke="white" stroke-width="3" fill="none" stroke-opacity="0.4"/>
                        <circle cx="150" cy="40" r="8" fill="white" fill-opacity="0.6"/>
                        <circle cx="250" cy="50" r="6" fill="white" fill-opacity="0.5"/>
                        <circle cx="350" cy="25" r="10" fill="white" fill-opacity="0.7"/>
                    </svg>
                </div>
            </div>
        </div>
    </section>

    <!-- Search Section -->
    <section class="search-section">
        <div class="search-container">
            <form class="search-form">
                <div class="form-group">
                    <label for="job-title">Job Title</label>
                    <input type="text" id="job-title" placeholder="e.g. Software Developer">
                </div>
                <div class="form-group">
                    <label for="location">Location</label>
                    <select id="location">
                        <option value="">All Locations</option>
                        <option value="mumbai">Mumbai</option>
                        <option value="delhi">Delhi</option>
                        <option value="bangalore">Bangalore</option>
                        <option value="pune">Pune</option>
                        <option value="hyderabad">Hyderabad</option>
                    </select>
                </div>
                <button type="submit" class="search-btn">
                    <i class="fas fa-search"></i> Search
                </button>
            </form>
        </div>
    </section>

    <!-- Features Section -->
    <section class="features" id="about">
        <div class="features-container">
            <div class="section-header">
                <h2>Why Choose JobHub?</h2>
                <p>We connect talented professionals with amazing companies. Discover what makes us the preferred choice for job seekers.</p>
            </div>
            <div class="features-grid">
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-search"></i>
                    </div>
                    <h3>Smart Job Matching</h3>
                    <p>Our AI-powered algorithm matches you with jobs that perfectly fit your skills, experience, and career goals.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-building"></i>
                    </div>
                    <h3>Top Companies</h3>
                    <p>Access opportunities from leading companies across various industries, from startups to Fortune 500 corporations.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-rocket"></i>
                    </div>
                    <h3>Fast Applications</h3>
                    <p>Apply to multiple jobs with just one click. Our streamlined process saves you time and gets you noticed faster.</p>
                </div>
            </div>
        </div>
    </section>

    <!-- Stats Section -->
    <section class="stats">
        <div class="stats-container">
            <div class="stats-grid">
                <div class="stat-item">
                    <h3>50K+</h3>
                    <p>Active Jobs</p>
                </div>
                <div class="stat-item">
                    <h3>10K+</h3>
                    <p>Companies</p>
                </div>
                <div class="stat-item">
                    <h3>100K+</h3>
                    <p>Job Seekers</p>
                </div>
                <div class="stat-item">
                    <h3>95%</h3>
                    <p>Success Rate</p>
                </div>
            </div>
        </div>
    </section>

    <!-- Footer -->
    <footer class="footer" id="contact">
        <div class="footer-container">
            <div class="footer-content">
                <div class="footer-section">
                    <h3><i class="fas fa-briefcase"></i> JobHub</h3>
                    <p>Your gateway to amazing career opportunities. Connect with top employers and find your dream job today.</p>
                    <div style="margin-top: 20px;">
                        <i class="fab fa-facebook" style="margin-right: 15px; font-size: 20px; color: var(--primary-color);"></i>
                        <i class="fab fa-twitter" style="margin-right: 15px; font-size: 20px; color: var(--primary-color);"></i>
                        <i class="fab fa-linkedin" style="margin-right: 15px; font-size: 20px; color: var(--primary-color);"></i>
                        <i class="fab fa-instagram" style="margin-right: 15px; font-size: 20px; color: var(--primary-color);"></i>
                    </div>
                </div>
                <div class="footer-section">
                    <h3>For Job Seekers</h3>
                    <div class="footer-links">
                        <a href="#">Browse Jobs</a>
                        <a href="#">Career Advice</a>
                        <a href="#">Resume Builder</a>
                        <a href="#">Salary Guide</a>
                    </div>
                </div>
                <div class="footer-section">
                    <h3>For Employers</h3>
                    <div class="footer-links">
                        <a href="#">Post Jobs</a>
                        <a href="#">Search Resumes</a>
                        <a href="#">Pricing</a>
                        <a href="#">Recruitment Solutions</a>
                    </div>
                </div>
                <div class="footer-section">
                    <h3>Support</h3>
                    <div class="footer-links">
                        <a href="#">Help Center</a>
                        <a href="#">Contact Us</a>
                        <a href="#">Privacy Policy</a>
                        <a href="#">Terms of Service</a>
                    </div>
                </div>
            </div>
            <div class="footer-bottom">
                <p>&copy; 2025 JobHub. All rights reserved. Made with ❤️ for job seekers everywhere.</p>
            </div>
        </div>
    </footer>

    <script>
        // Authentication state
        let isLoggedIn = false;
        let currentUser = {
            name: 'John Doe',
            email: 'john.doe@email.com'
        };

        // Initialize page
        document.addEventListener('DOMContentLoaded', function() {
            updateAuthUI();
            addScrollEffect();
        });

        // Authentication functions
        function showLogin() {
            const email = prompt('Enter your email:');
            const password = prompt('Enter your password:');
            
            if (email && password) {
                // Simulate login
                if (email && password.length >= 6) {
                    isLoggedIn = true;
                    currentUser.name = email.split('@')[0];
                    currentUser.email = email;
                    updateAuthUI();
                    showNotification('Login successful! Welcome back.', 'success');
                } else {
                    showNotification('Invalid credentials. Please try again.', 'error');
                }
            }
        }

        function showRegister() {
            const name = prompt('Enter your full name:');
            const email = prompt('Enter your email:');
            const password = prompt('Create a password (min 6 characters):');
            
            if (name && email && password) {
                if (password.length >= 6) {
                    isLoggedIn = true;
                    currentUser.name = name;
                    currentUser.email = email;
                    updateAuthUI();
                    showNotification('Registration successful! Welcome to JobHub.', 'success');
                } else {
                    showNotification('Password must be at least 6 characters long.', 'error');
                }
            }
        }

        function logout() {
            if (confirm('Are you sure you want to logout?')) {
                isLoggedIn = false;
                currentUser = { name: '', email: '' };
                updateAuthUI();
                closeDropdown();
                showNotification('You have been logged out successfully.', 'info');
            }
        }

        function updateAuthUI() {
            const authButtons = document.getElementById('authButtons');
            const userProfile = document.getElementById('userProfile');
            const userName = document.getElementById('userName');
            
            if (isLoggedIn) {
                authButtons.style.display = 'none';
                userProfile.classList.add('show');
                userName.textContent = currentUser.name;
            } else {
                authButtons.style.display = 'flex';
                userProfile.classList.remove('show');
                closeDropdown();
            }
        }

        function toggleDropdown() {
            const dropdown = document.getElementById('profileDropdown');
            dropdown.classList.toggle('show');
        }

        function closeDropdown() {
            const dropdown = document.getElementById('profileDropdown');
            dropdown.classList.remove('show');
        }

        // Close dropdown when clicking outside
        document.addEventListener('click', function(event) {
            const userProfile = document.getElementById('userProfile');
            if (!userProfile.contains(event.target)) {
                closeDropdown();
            }
        });

        // Notification system
        function showNotification(message, type = 'info') {
            const notification = document.createElement('div');
            notification.style.cssText = `
                position: fixed;
                top: 90px;
                right: 20px;
                padding: 15px 20px;
                border-radius: 10px;
                color: white;
                font-weight: 600;
                z-index: 2000;
                animation: slideIn 0.3s ease;
                max-width: 300px;
                box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
            `;
            
            switch(type) {
                case 'success':
                    notification.style.background = '#10b981';
                    notification.innerHTML = `<i class="fas fa-check-circle"></i> ${message}`;
                    break;
                case 'error':
                    notification.style.background =