<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Candidate Dashboard - Job Portal</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            min-height: 100vh;
        }

        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px 0;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
        }

        .header-content {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            font-size: 24px;
            font-weight: bold;
        }

        .user-info {
            display: flex;
            align-items: center;
            gap: 20px;
        }

        .logout-btn {
            background: rgba(255, 255, 255, 0.2);
            border: 1px solid rgba(255, 255, 255, 0.3);
            color: white;
            padding: 8px 16px;
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .logout-btn:hover {
            background: rgba(255, 255, 255, 0.3);
        }

        .dashboard-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 30px 20px;
        }

        .dashboard-nav {
            display: flex;
            gap: 20px;
            margin-bottom: 30px;
        }

        .nav-tab {
            background: white;
            border: none;
            padding: 12px 24px;
            border-radius: 10px;
            cursor: pointer;
            font-weight: 600;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        }

        .nav-tab.active {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        .nav-tab:hover:not(.active) {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
        }

        .tab-content {
            display: none;
            background: white;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
        }

        .tab-content.active {
            display: block;
        }

        .section-title {
            font-size: 24px;
            color: #333;
            margin-bottom: 20px;
            font-weight: 700;
        }

        .search-filters {
            background: #f8f9ff;
            padding: 20px;
            border-radius: 12px;
            margin-bottom: 30px;
            border: 2px solid #e1e5e9;
        }

        .filter-row {
            display: flex;
            gap: 20px;
            margin-bottom: 20px;
        }

        .filter-group {
            flex: 1;
        }

        .filter-group label {
            display: block;
            margin-bottom: 5px;
            color: #333;
            font-weight: 600;
            font-size: 14px;
        }

        .form-control {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e1e1e1;
            border-radius: 8px;
            font-size: 14px;
            transition: all 0.3s ease;
        }

        .form-control:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }

        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
        }

        .btn-outline {
            background: transparent;
            border: 2px solid #667eea;
            color: #667eea;
        }

        .btn-outline:hover {
            background: #667eea;
            color: white;
        }

        .job-card {
            background: white;
            border: 2px solid #e1e5e9;
            border-radius: 12px;
            padding: 25px;
            margin-bottom: 20px;
            transition: all 0.3s ease;
            position: relative;
        }

        .job-card:hover {
            border-color: #667eea;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
            transform: translateY(-2px);
        }

        .job-card.applied {
            background: #f0f9f4;
            border-color: #27ae60;
        }

        .job-header {
            display: flex;
            justify-content: space-between;
            align-items: start;
            margin-bottom: 15px;
        }

        .job-title {
            font-size: 22px;
            font-weight: 700;
            color: #333;
            margin-bottom: 5px;
        }

        .company-name {
            color: #667eea;
            font-weight: 600;
            font-size: 16px;
        }

        .job-details {
            display: flex;
            gap: 15px;
            margin-bottom: 15px;
            flex-wrap: wrap;
        }

        .job-detail {
            background: #f8f9ff;
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 12px;
            color: #666;
            border: 1px solid #e1e1e1;
            font-weight: 500;
        }

        .job-description {
            color: #666;
            line-height: 1.6;
            margin-bottom: 15px;
        }

        .job-actions {
            display: flex;
            gap: 10px;
            align-items: center;
        }

        .btn-small {
            padding: 8px 16px;
            font-size: 14px;
        }

        .btn-success {
            background: #27ae60;
            color: white;
        }

        .btn-info {
            background: #3498db;
            color: white;
        }

        .btn-disabled {
            background: #95a5a6;
            color: white;
            cursor: not-allowed;
        }

        .applied-badge {
            background: #27ae60;
            color: white;
            padding: 4px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
        }

        .application-card {
            background: white;
            border: 2px solid #e1e1e1;
            border-radius: 12px;
            padding: 20px;
            margin-bottom: 15px;
            transition: all 0.3s ease;
        }

        .application-card:hover {
            border-color: #667eea;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
        }

        .application-status {
            padding: 4px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            text-transform: uppercase;
        }

        .status-pending {
            background: #fff3cd;
            color: #856404;
        }

        .status-accepted {
            background: #d4edda;
            color: #155724;
        }

        .status-rejected {
            background: #f8d7da;
            color: #721c24;
        }

        .status-shortlisted {
            background: #cce7ff;
            color: #0056b3;
        }

        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }

        .stat-card {
            background: white;
            padding: 25px;
            border-radius: 15px;
            text-align: center;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }

        .stat-card:hover {
            transform: translateY(-5px);
        }

        .stat-number {
            font-size: 36px;
            font-weight: 700;
            color: #667eea;
            margin-bottom: 10px;
        }

        .stat-label {
            color: #666;
            font-weight: 600;
        }

        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
        }

        .modal-content {
            background-color: white;
            margin: 50px auto;
            padding: 30px;
            border-radius: 15px;
            width: 90%;
            max-width: 600px;
            max-height: 80vh;
            overflow-y: auto;
        }

        .close {
            float: right;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
            color: #999;
        }

        .close:hover {
            color: #333;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 600;
        }

        textarea.form-control {
            resize: vertical;
            min-height: 120px;
        }

        @media (max-width: 768px) {
            .header-content {
                flex-direction: column;
                gap: 15px;
            }

            .dashboard-nav {
                flex-wrap: wrap;
            }

            .filter-row {
                flex-direction: column;
            }

            .job-header {
                flex-direction: column;
                align-items: start;
            }

            .job-details {
                flex-direction: column;
            }

            .job-actions {
                flex-direction: column;
                align-items: stretch;
            }
        }
    </style>
</head>
<body>
    <header class="header">
        <div class="header-content">
            <div class="logo">👤 Job Portal - Candidate</div>
            <div class="user-info">
                <span>Welcome, <strong id="candidateName">Jane Smith</strong></span>
                <button class="logout-btn" onclick="logout()">Logout</button>
            </div>
        </div>
    </header>

    <div class="dashboard-container">
        <div class="dashboard-nav">
            <button class="nav-tab active" onclick="showTab('overview')">📊 Overview</button>
            <button class="nav-tab" onclick="showTab('search-jobs')">🔍 Find Jobs</button>
            <button class="nav-tab" onclick="showTab('my-applications')">📋 My Applications</button>
            <button class="nav-tab" onclick="showTab('profile')">👤 Profile</button>
        </div>

        <!-- Overview Tab -->
        <div id="overview" class="tab-content active">
            <h2 class="section-title">Dashboard Overview</h2>
            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-number" id="totalApplications">7</div>
                    <div class="stat-label">Total Applications</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number" id="pendingApplications">3</div>
                    <div class="stat-label">Pending Review</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number" id="shortlistedApplications">2</div>
                    <div class="stat-label">Shortlisted</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number" id="jobsViewed">15</div>
                    <div class="stat-label">Jobs Viewed</div>
                </div>
            </div>

            <h3>Recent Job Matches</h3>
            <div id="recommendedJobs">
                <!-- Recommended jobs will be populated here -->
            </div>
        </div>

        <!-- Search Jobs Tab -->
        <div id="search-jobs" class="tab-content">
            <h2 class="section-title">Find Your Next Job</h2>
            
            <div class="search-filters">
                <div class="filter-row">
                    <div class="filter-group">
                        <label for="searchKeyword">Keywords</label>
                        <input type="text" id="searchKeyword" class="form-control" placeholder="Job title, skills, company...">
                    </div>
                    <div class="filter-group">
                        <label for="searchLocation">Location</label>
                        <input type="text" id="searchLocation" class="form-control" placeholder="City, State, Remote...">
                    </div>
                </div>
                
                <div class="filter-row">
                    <div class="filter-group">
                        <label for="filterJobType">Job Type</label>
                        <select id="filterJobType" class="form-control">
                            <option value="">All Types</option>
                            <option value="full-time">Full Time</option>
                            <option value="part-time">Part Time</option>
                            <option value="contract">Contract</option>
                            <option value="internship">Internship</option>
                        </select>
                    </div>
                    <div class="filter-group">
                        <label for="filterExperience">Experience Level</label>
                        <select id="filterExperience" class="form-control">
                            <option value="">All Levels</option>
                            <option value="entry">Entry Level</option>
                            <option value="mid">Mid Level</option>
                            <option value="senior">Senior Level</option>
                        </select>
                    </div>
                    <div class="filter-group">
                        <label for="filterSalary">Min Salary</label>
                        <input type="number" id="filterSalary" class="form-control" placeholder="e.g., 50000">
                    </div>
                    <div class="filter-group" style="display: flex; align-items: end;">
                        <button type="button" class="btn btn-primary" onclick="searchJobs()">Search Jobs</button>
                    </div>
                </div>
            </div>

            <div id="jobsResults">
                <!-- Job search results will be populated here -->
            </div>
        </div>

        <!-- My Applications Tab -->
        <div id="my-applications" class="tab-content">
            <h2 class="section-title">My Job Applications</h2>
            <div id="applicationsList">
                <!-- Applications will be populated here -->
            </div>
        </div>

        <!-- Profile Tab -->
        <div id="profile" class="tab-content">
            <h2 class="section-title">My Profile</h2>
            <form id="profileForm">
                <div class="filter-row">
                    <div class="filter-group">
                        <label for="profileFirstName">First Name</label>
                        <input type="text" id="profileFirstName" name="firstName" class="form-control" value="Jane">
                    </div>
                    <div class="filter-group">
                        <label for="profileLastName">Last Name</label>
                        <input type="text" id="profileLastName" name="lastName" class="form-control" value="Smith">
                    </div>
                </div>
                
                <div class="filter-row">
                    <div class="filter-group">
                        <label for="profileEmail">Email</label>
                        <input type="email" id="profileEmail" name="email" class="form-control" value="jane.smith@email.com">
                    </div>
                    <div class="filter-group">
                        <label for="profilePhone">Phone</label>
                        <input type="tel" id="profilePhone" name="phone" class="form-control" value="555-0123">
                    </div>
                </div>

                <div class="form-group">
                    <label for="profileLocation">Current Location</label>
                    <input type="text" id="profileLocation" name="location" class="form-control" value="San Francisco, CA">
                </div>

                <div class="form-group">
                    <label for="profileSkills">Skills</label>
                    <textarea id="profileSkills" name="skills" class="form-control">JavaScript, React, Node.js, Python, AWS, MongoDB</textarea>
                </div>

                <div class="form-group">
                    <label for="profileExperience">Years of Experience</label>
                    <select id="profileExperience" name="experience" class="form-control">
                        <option value="0-1">0-1 years</option>
                        <option value="1-3">1-3 years</option>
                        <option value="3-5" selected>3-5 years</option>
                        <option value="5-10">5-10 years</option>
                        <option value="10+">10+ years</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Update Profile</button>
            </form>
        </div>
    </div>

    <!-- Application Modal -->
    <div id="applicationModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeApplicationModal()">&times;</span>
            <h2>Apply for Job</h2>
            <form id="applicationForm">
                <input type="hidden" id="applyJobId" name="jobId">
                
                <div class="form-group">
                    <label>Job Title</label>
                    <div id="applyJobTitle" style="font-weight: bold; color: #667eea;"></div>
                </div>
                
                <div class="form-group">
                    <label>Company</label>
                    <div id="applyCompanyName" style="font-weight: bold;"></div>
                </div>

                <div class="form-group">
                    <label for="coverLetter">Cover Letter *</label>
                    <textarea id="coverLetter" name="coverLetter" class="form-control" required 
                              placeholder="Write a compelling cover letter explaining why you're the perfect fit for this role..."></textarea>
                </div>

                <div class="form-group">
                    <label for="additionalNotes">Additional Notes</label>
                    <textarea id="additionalNotes" name="additionalNotes" class="form-control" 
                              placeholder="Any additional information you'd like to share..."></textarea>
                </div>

                <button type="submit" class="btn btn-primary">Submit Application</button>
            </form>
        </div>
    </div>

  </body>
  </html>
  