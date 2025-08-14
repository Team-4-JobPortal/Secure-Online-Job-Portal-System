<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>JobHub - Candidate Dashboard</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
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

        body {
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            line-height: 1.6;
            color: var(--text-color);
            background: var(--light-bg);
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

        /* User Profile Dropdown */
        .user-profile {
            position: relative;
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
            margin-top: 70px;
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

        /* Welcome Section */
        .welcome-section {
            background: white;
            padding: 60px 0;
            text-align: center;
        }

        .welcome-container {
            max-width: 800px;
            margin: 0 auto;
            padding: 0 20px;
        }

        .welcome-section h2 {
            font-size: 2.5rem;
            color: var(--text-color);
            margin-bottom: 20px;
        }

        .welcome-section p {
            font-size: 1.1rem;
            color: #666;
            margin-bottom: 40px;
        }

        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 30px;
            margin-top: 40px;
        }

        .stat-card {
            background: var(--light-bg);
            padding: 30px;
            border-radius: 15px;
            text-align: center;
            transition: transform 0.3s ease;
        }

        .stat-card:hover {
            transform: translateY(-5px);
        }

        .stat-number {
            font-size: 2.5rem;
            font-weight: 800;
            color: var(--primary-color);
            margin-bottom: 10px;
        }

        .stat-label {
            color: #666;
            font-weight: 500;
        }

        /* Quick Actions */
        .quick-actions {
            background: var(--light-bg);
            padding: 60px 0;
        }

        .actions-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
        }

        .actions-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 30px;
            margin-top: 40px;
        }

        .action-card {
            background: white;
            padding: 40px;
            border-radius: 20px;
            box-shadow: var(--shadow);
            transition: all 0.3s ease;
            text-align: center;
        }

        .action-card:hover {
            transform: translateY(-5px);
            box-shadow: var(--shadow-hover);
        }

        .action-icon {
            font-size: 3rem;
            color: var(--primary-color);
            margin-bottom: 20px;
        }

        .action-card h3 {
            font-size: 1.5rem;
            margin-bottom: 15px;
            color: var(--text-color);
        }

        .action-card p {
            color: #666;
            margin-bottom: 25px;
            line-height: 1.6;
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

            .stats-grid {
                grid-template-columns: 1fr;
            }

            .actions-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar">
        <div class="nav-container">
            <a href="/Secure-Online-Job-Portal-System/" class="logo">
                <i class="fas fa-briefcase"></i> JobHub
            </a>
          
            <ul class="nav-menu">
                <li><a href="/Secure-Online-Job-Portal-System/">Home</a></li>
                <li><a href="#jobs">Find Jobs</a></li>
                <li><a href="#companies">Companies</a></li>
                <li><a href="#about">About</a></li>
                <li><a href="#contact">Contact</a></li>
            </ul>

            <div class="auth-buttons">
                <!-- Show User Profile + Logout since user is logged in -->
                <div class="user-profile" id="userProfile">
                    <button class="profile-btn" onclick="toggleDropdown()">
                        <i class="fas fa-user-circle"></i>
                        <span id="userName">${sessionScope.loggedInUser}</span>
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
                        <a href="/Secure-Online-Job-Portal-System/logout" class="dropdown-item">
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
                    <h1>Welcome Back, Candidate!</h1>
                    <p>Ready to find your next opportunity? Explore thousands of jobs from top companies and take your career to the next level.</p>
                    <div class="hero-buttons">
                        <a href="#jobs" class="btn btn-primary btn-lg">
                            <i class="fas fa-search"></i> Browse Jobs
                        </a>
                        <a href="#" class="btn btn-outline btn-lg">
                            <i class="fas fa-upload"></i> Update Resume
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

    <!-- Welcome Section -->
    <section class="welcome-section">
        <div class="welcome-container">
            <h2>Your Job Search Dashboard</h2>
            <p>Track your applications, discover new opportunities, and manage your career journey all in one place.</p>
            
            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-number">12</div>
                    <div class="stat-label">Jobs Applied</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number">5</div>
                    <div class="stat-label">Interviews</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number">3</div>
                    <div class="stat-label">Saved Jobs</div>
                </div>
            </div>
        </div>
    </section>

    <!-- Quick Actions -->
    <section class="quick-actions">
        <div class="actions-container">
            <h2 style="text-align: center; margin-bottom: 40px; font-size: 2.5rem; color: var(--text-color);">Quick Actions</h2>
            
            <div class="actions-grid">
                <div class="action-card">
                    <div class="action-icon">
                        <i class="fas fa-search"></i>
                    </div>
                    <h3>Search Jobs</h3>
                    <p>Find the perfect job that matches your skills, experience, and career goals.</p>
                    <a href="#jobs" class="btn btn-primary">Start Searching</a>
                </div>
                
                <div class="action-card">
                    <div class="action-icon">
                        <i class="fas fa-file-alt"></i>
                    </div>
                    <h3>Manage Resume</h3>
                    <p>Update your resume, cover letter, and professional information to stand out to employers.</p>
                    <a href="#" class="btn btn-outline">Edit Resume</a>
                </div>
                
                <div class="action-card">
                    <div class="action-icon">
                        <i class="fas fa-bell"></i>
                    </div>
                    <h3>Job Alerts</h3>
                    <p>Set up personalized job alerts to never miss opportunities that match your criteria.</p>
                    <a href="#" class="btn btn-outline">Set Alerts</a>
                </div>
            </div>
        </div>
    </section>

    <script>
        // Toggle profile dropdown
        function toggleDropdown() {
            const dropdown = document.getElementById('profileDropdown');
            dropdown.classList.toggle('show');
        }

        // Close dropdown when clicking outside
        document.addEventListener('click', function(event) {
            const userProfile = document.getElementById('userProfile');
            if (userProfile && !userProfile.contains(event.target)) {
                const dropdown = document.getElementById('profileDropdown');
                if (dropdown) {
                    dropdown.classList.remove('show');
                }
            }
        });

        // Check if user is logged in, if not redirect to login
        window.addEventListener('load', function() {
            // Debug: Log session information
            console.log('Candidate dashboard loaded successfully');
            console.log('Logged in user:', '${loggedInUser}');
            console.log('User role:', '${role}');
            
            // Check if user is logged in
            if (!'${loggedInUser}') {
                console.log('No user logged in, redirecting to login');
                window.location.href = '/Secure-Online-Job-Portal-System/login';
            } else {
                console.log('User is logged in:', '${loggedInUser}');
                // Update the user name display
                const userNameElement = document.getElementById('userName');
                if (userNameElement) {
                    userNameElement.textContent = '${loggedInUser}';
                }
            }
        });
    </script>
</body>
</html>
