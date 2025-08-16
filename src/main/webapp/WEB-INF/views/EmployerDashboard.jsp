<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Employer Dashboard - Job Portal</title>
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

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 600;
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

        .form-row {
            display: flex;
            gap: 20px;
        }

        .form-row .form-group {
            flex: 1;
        }

        textarea.form-control {
            resize: vertical;
            min-height: 100px;
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

        .job-card {
            background: #f8f9ff;
            border-radius: 12px;
            padding: 20px;
            margin-bottom: 20px;
            border-left: 4px solid #667eea;
            transition: all 0.3s ease;
        }

        .job-card:hover {
            transform: translateX(5px);
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
        }

        .job-title {
            font-size: 20px;
            font-weight: 700;
            color: #333;
            margin-bottom: 10px;
        }

        .job-details {
            display: flex;
            gap: 20px;
            margin-bottom: 15px;
            flex-wrap: wrap;
        }

        .job-detail {
            background: white;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 12px;
            color: #666;
            border: 1px solid #e1e1e1;
        }

        .job-actions {
            display: flex;
            gap: 10px;
            margin-top: 15px;
        }

        .btn-small {
            padding: 6px 12px;
            font-size: 12px;
        }

        .btn-success {
            background: #27ae60;
            color: white;
        }

        .btn-danger {
            background: #e74c3c;
            color: white;
        }

        .btn-info {
            background: #3498db;
            color: white;
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

        .applicant-name {
            font-size: 18px;
            font-weight: 700;
            color: #333;
            margin-bottom: 10px;
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

        @media (max-width: 768px) {
            .header-content {
                flex-direction: column;
                gap: 15px;
            }

            .dashboard-nav {
                flex-direction: column;
            }

            .form-row {
                flex-direction: column;
            }

            .job-details {
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
    <header class="header">
        <div class="header-content">
            <div class="logo">🏢 Job Portal - Employer</div>
            <div class="user-info">
                <span>Welcome, <strong id="employerName">John Doe</strong></span>
                <button class="logout-btn" onclick="logout()">Logout</button>
            </div>
        </div>
    </header>

    <div class="dashboard-container">
        <div class="dashboard-nav">
            <button class="nav-tab active" onclick="showTab('overview')">📊 Overview</button>
            <button class="nav-tab" onclick="showTab('post-job')">➕ Post Job</button>
            <button class="nav-tab" onclick="showTab('my-jobs')">📝 My Jobs</button>
            <button class="nav-tab" onclick="showTab('applications')">📋 Applications</button>
        </div>

        <!-- Overview Tab -->
        <div id="overview" class="tab-content active">
            <h2 class="section-title">Dashboard Overview</h2>
            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-number" id="totalJobs">5</div>
                    <div class="stat-label">Active Jobs</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number" id="totalApplications">23</div>
                    <div class="stat-label">Total Applications</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number" id="pendingApplications">12</div>
                    <div class="stat-label">Pending Review</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number" id="hiredCandidates">3</div>
                    <div class="stat-label">Hired Candidates</div>
                </div>
            </div>

            <h3>Recent Applications</h3>
            <div id="recentApplications">
                <!-- Recent applications will be populated here -->
            </div>
        </div>

        <!-- Post Job Tab -->
        <div id="post-job" class="tab-content">
            <h2 class="section-title">Post New Job</h2>
            <form id="jobPostForm" method="post" action="PostJobServlet">
    <div class="form-row">
        <div class="form-group">
            <label for="jobTitle">Job Title *</label>
            <input type="text" id="jobTitle" name="title" class="form-control" required>
        </div>
        <div class="form-group">
            <label for="location">Location *</label>
            <input type="text" id="location" name="location" class="form-control" required>
        </div>
    </div>

    <div class="form-row">
        <div class="form-group">
            <label for="minSalary">Minimum Salary</label>
            <input type="number" id="minSalary" name="min_salary" class="form-control">
        </div>
        <div class="form-group">
            <label for="maxSalary">Maximum Salary</label>
            <input type="number" id="maxSalary" name="max_salary" class="form-control">
        </div>
    </div>

    <div class="form-group">
        <label for="jobDescription">Job Description *</label>
        <textarea id="jobDescription" name="description" class="form-control" required></textarea>
    </div>

    <div class="form-group">
        <label for="applicationDeadline">Application Deadline</label>
        <input type="date" id="applicationDeadline" name="deadline" class="form-control">
    </div>

    <button type="submit" class="btn btn-primary">Post Job</button>
</form>
            
        </div>

        <!-- My Jobs Tab -->
        <div id="my-jobs" class="tab-content">
            <h2 class="section-title">My Posted Jobs</h2>
            <div id="jobsList">
                <!-- Jobs will be populated here -->
            </div>
        </div>

        <!-- Applications Tab -->
        <div id="applications" class="tab-content">
            <h2 class="section-title">Job Applications</h2>
            <div id="applicationsList">
                <!-- Applications will be populated here -->
            </div>
        </div>
    </div>

    <script>
    // Function to switch between tabs
    function showTab(tabId) {
        document.querySelectorAll('.tab-content').forEach(tab => {
            tab.classList.remove('active');
        });
        document.getElementById(tabId).classList.add('active');

        document.querySelectorAll('.nav-tab').forEach(btn => {
            btn.classList.remove('active');
        });
        event.target.classList.add('active');
    }

    // Handle job post form submission
    document.getElementById("jobPostForm").addEventListener("submit", function (e) {
        e.preventDefault(); // stop page reload

        // Collect form data
        const jobData = {
            title: document.getElementById("jobTitle").value,
            type: document.getElementById("jobType").value,
            location: document.getElementById("location").value,
            experience: document.getElementById("experienceLevel").value,
            minSalary: document.getElementById("minSalary").value,
            maxSalary: document.getElementById("maxSalary").value,
            description: document.getElementById("jobDescription").value,
            requirements: document.getElementById("requirements").value,
            benefits: document.getElementById("benefits").value,
            deadline: document.getElementById("applicationDeadline").value
        };

        // Add job to "My Jobs" list dynamically
        const jobsList = document.getElementById("jobsList");

        const jobCard = document.createElement("div");
        jobCard.classList.add("job-card");
        jobCard.innerHTML = `
            <div class="job-title">${jobData.title} (${jobData.type})</div>
            <div class="job-details">
                <div class="job-detail">📍 ${jobData.location}</div>
                <div class="job-detail">💼 ${jobData.experience}</div>
                <div class="job-detail">💰 ${jobData.minSalary || "N/A"} - ${jobData.maxSalary || "N/A"}</div>
                <div class="job-detail">⏰ Deadline: ${jobData.deadline || "Not specified"}</div>
            </div>
            <p>${jobData.description}</p>
            <p><strong>Requirements:</strong> ${jobData.requirements}</p>
            <p><strong>Benefits:</strong> ${jobData.benefits || "Not specified"}</p>
            <div class="job-actions">
                <button class="btn btn-small btn-info">View</button>
                <button class="btn btn-small btn-danger" onclick="this.parentElement.parentElement.remove()">Delete</button>
            </div>
        `;

        jobsList.appendChild(jobCard);

        // Reset form
        this.reset();

        // Success message
        alert("✅ Job posted successfully! Check 'My Jobs' tab.");

        // Switch to My Jobs tab
        showTab("my-jobs");
    });

    // Fake logout
    function logout() {
        alert("You have been logged out!");
        window.location.href = "login.jsp"; // or any login page
    }
</script>

</body>
</html>