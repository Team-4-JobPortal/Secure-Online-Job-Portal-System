<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="true" %>


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

        .btn:disabled {
            opacity: 0.6;
            cursor: not-allowed;
            transform: none;
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
		    padding: 4px 8px;
		    border-radius: 4px;
		    font-weight: bold;
		    text-transform: uppercase;
		    font-size: 12px;
		}
		
		.status-pending {
		    background-color: #fff3cd;
		    color: #856404;
		    border: 1px solid #ffeaa7;
		}
		
		.status-accepted {
		    background-color: #d4edda;
		    color: #155724;
		    border: 1px solid #c3e6cb;
		}
		
		.status-rejected {
		    background-color: #f8d7da;
		    color: #721c24;
		    border: 1px solid #f5c6cb;
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

        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 8px;
            font-weight: 600;
        }

        .alert-success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }

        .loading {
            display: inline-block;
            width: 20px;
            height: 20px;
            border: 3px solid #f3f3f3;
            border-top: 3px solid #667eea;
            border-radius: 50%;
            animation: spin 1s linear infinite;
            margin-right: 10px;
        }

        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
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
                <span>Welcome, <strong id="employerName"></strong></span>
                <button class="logout-btn" onclick="logout()">Logout</button>
            </div>
        </div>
    </header>

    <div class="dashboard-container">
        <div class="dashboard-nav">
            <button class="nav-tab active" onclick="showTab('overview', event)">📊 Overview</button>
			<button class="nav-tab" onclick="showTab('post-job', event)">➕ Post Job</button>
			<button class="nav-tab" onclick="showTab('my-jobs', event)">📝 My Jobs</button>
			<button class="nav-tab" onclick="showTab('applications', event)">📋 Applications</button>
			<button class="nav-tab" onclick="showTab('history', event)">📋 History</button>
        </div>

        <!-- Overview Tab -->
        <div id="overview" class="tab-content active">
            <h2 class="section-title">Dashboard Overview</h2>
            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-number" id="totalJobs">0</div>
                    <div class="stat-label">Active Jobs</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number" id="totalApplications">0</div>
                    <div class="stat-label">Total Applications</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number" id="pendingApplications">0</div>
                    <div class="stat-label">Pending Review</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number" id="hiredCandidates">0</div>
                    <div class="stat-label">Hired Candidates</div>
                </div>
            </div>

            
        </div>

        <!-- Post Job Tab -->
        <div id="post-job" class="tab-content">
            <h2 class="section-title">Post New Job</h2>
            
            <!-- Alert container -->
            <div id="alertContainer"></div>
            
            <form id="jobPostForm">
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
                        <input type="number" id="minSalary" name="min_salary" class="form-control" min="0">
                    </div>
                    <div class="form-group">
                        <label for="maxSalary">Maximum Salary</label>
                        <input type="number" id="maxSalary" name="max_salary" class="form-control" min="0">
                    </div>
                </div>

                <div class="form-group">
                    <label for="jobDescription">Job Description *</label>
                    <textarea id="jobDescription" name="description" class="form-control" required placeholder="Enter detailed job description..."></textarea>
                </div>

                <div class="form-group">
                    <label for="applicationDeadline">Application Deadline</label>
                    <input type="date" id="applicationDeadline" name="deadline" class="form-control">
                </div>

                <button type="submit" class="btn btn-primary" id="submitBtn">
                    <span id="submitText">Post Job</span>
                </button>
            </form>
        </div>

        <!-- My Jobs Tab -->
        <div id="my-jobs" class="tab-content">
            <h2 class="section-title">My Posted Jobs</h2>
            <div id="jobsList">
                <p>Loading jobs...</p>
            </div>
        </div>

        <!-- Applications Tab -->
        <div id="applications" class="tab-content">
            <h2 class="section-title">Job Applications</h2>
            <div id="applicationsList">
                <p>Loading applications...</p>
            </div>
        </div>
        
        <!-- History Tab  -->
     	<div id="history" class="tab-content">
            <h2 class="section-title">History</h2>
            <div id="historylist">
                <p>Loading Deleted jobs...</p>
            </div>
        </div>
    
    </div>
    
    
    


   <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
let authToken = null;

$(document).ready(function() {
    authToken = getCookie('token') || localStorage.getItem('authToken');
    if (!authToken) {
        alert('Please login first!');
        window.location.href = '/Secure-Online-Job-Portal-System/login';
        return;
    }
    loadUserInfo(); 
    loadDashboardData();
    setMinDate();

    $("#jobPostForm").submit(function(e) {
        e.preventDefault();
        postJob();
    });
});

function showTab(tabId, event) {
    $(".tab-content").removeClass("active");
    $(".nav-tab").removeClass("active");

    $("#" + tabId).addClass("active");
    if (event) $(event.target).addClass("active");

    if (tabId === 'my-jobs') {
        loadMyJobs();
    } else if (tabId === 'applications') {
        loadApplications();
    } else if (tabId === 'overview') {
        loadDashboardData();
    } else if(tabId == 'history'){
    		loadHistory();
    }
}

function loadUserInfo() {
    $.ajax({
        url: "/Secure-Online-Job-Portal-System/auth/user/me",
        method: "GET",
        headers: { "Authorization": "Bearer " + authToken },
        success: function(data) {
            let displayName = data.firstName && data.lastName 
                ? data.firstName + " " + data.lastName 
                : data.email; // fallback if name missing
            $("#employerName").text(displayName);
        },
        error: function() {
            $("#employerName").text("Employer");
        }
    });
}


// Set minimum date to today
function setMinDate() {
    const today = new Date().toISOString().split('T')[0];
    $("#applicationDeadline").attr("min", today);
}

// Job posting
function postJob() {
    const submitBtn = $("#submitBtn");
    const submitText = $("#submitText");

    submitBtn.prop("disabled", true);
    submitText.html('<span class="loading"></span>Posting Job...');
    showAlert('', '');

    const minSalary = parseInt($("#minSalary").val()) || 0;
    const maxSalary = parseInt($("#maxSalary").val()) || 0;

    if (maxSalary > 0 && minSalary > maxSalary) {
        showAlert('Maximum salary cannot be less than minimum salary!', 'error');
        resetSubmitButton();
        return;
    }

    const jobData = {
        title: $("#jobTitle").val().trim(),
        location: $("#location").val().trim(),
        min_salary: minSalary,
        max_salary: maxSalary,
        description: $("#jobDescription").val().trim(),
        
        deadline: formatDeadline($("#applicationDeadline").val())
        
    };

    $.ajax({
        url: "/Secure-Online-Job-Portal-System/jobs/postJob",
        method: "POST",
        contentType: "application/json",
        headers: { "Authorization": "Bearer " + authToken },
        data: JSON.stringify(jobData),
        success: function() {
            showAlert('✅ Job posted successfully!', 'success');
            $("#jobPostForm")[0].reset();
            setMinDate();
            loadMyJobs();
            loadDashboardData();
            setTimeout(() => showTab('my-jobs'), 2000);
        },
        error: function(xhr) {
            showAlert('❌ ' + (xhr.responseText || 'Failed to post job'), 'error');
        },
        complete: resetSubmitButton
    });
}

function resetSubmitButton() {
    $("#submitBtn").prop("disabled", false);
    $("#submitText").html('Post Job');
}

function formatDeadline(dateString) {
	if (!dateString) return null;
	
	const date = new Date(dateString);  
	date.setHours(23, 59, 59, 999);    // Set the time to 23:59:59.999
	//console.log(date.toISOString);
	return date.toISOString();
}

function showAlert(message, type) {
    const alertContainer = $("#alertContainer");
    if (!message) {
        alertContainer.html('');
        return;
    }
    const alertClass = type === 'error' ? 'alert-error' : 'alert-success';
    alertContainer.html(`<div class="alert ${alertClass}">${message}</div>`);
    if (type === 'success') {
        setTimeout(() => alertContainer.html(''), 5000);
    }
}

function getCookie(name) {
    const match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
    return match ? match[2] : null;
}

// Dashboard data
function loadDashboardData() {
    $.ajax({
        url: "/Secure-Online-Job-Portal-System/applications/employer/stats",
        method: "GET",
        headers: { "Authorization": "Bearer " + authToken },
        success: function(data) {
            $("#totalJobs").text(data.totalJobs || 0);
            $("#totalApplications").text(data.totalApplications || 0);
            $("#pendingApplications").text(data.pendingApplications || 0);
            $("#hiredCandidates").text(data.hiredCandidates || 0);
        }
    });
}
//My Jobs
function loadMyJobs() {
    const jobsList = $("#jobsList");
    jobsList.html("<p>Loading jobs...</p>");

    $.ajax({
        url: "/Secure-Online-Job-Portal-System/jobs/currentLoginEmployerJobPosted/list",
        method: "GET",
        headers: { "Authorization": "Bearer " + authToken },
        success: function(jobs) {
            jobsList.html("");
            if (!jobs || jobs.length === 0) {
                jobsList.html("<p>No jobs posted yet. <a href='#' onclick=\"showTab('post-job')\">Post your first job</a>!</p>");
                return;
            }
            jobs.forEach(job => {
                const salaryRange = job.min_salary && job.max_salary
                    ? `₹${job.min_salary} - ₹${job.max_salary}`
                    : job.min_salary ? `From ₹${job.min_salary}`
                    : job.max_salary ? `Up to ₹${job.max_salary}`
                    : 'Salary negotiable';
                        
                const deadline = job.deadline ? new Date(job.deadline).toLocaleDateString() : "No deadline";
                
                const jobCard = `
                    <div class="job-card">
                        <div class="job-title">${escapeHtml(job.title)}</div>
                        <div class="job-details">
                            <div class="job-detail">📍 ${escapeHtml(job.location)}</div>
                            <div class="job-detail">💰 ${salaryRange}</div>
                            <div class="job-detail">⏰ Deadline: ${deadline}</div>
                        </div>
                        <p>${escapeHtml(job.description)}</p>
                        <div class="job-actions">
                            <button class="btn btn-danger btn-small" onclick="deleteJob(${job.job_id})">Delete</button>
                        </div>
                    </div>`;
                jobsList.append(jobCard);
            });
        },
        error: function() {
            jobsList.html("<p>Error loading jobs. Please try again.</p>");
        }
    });
}


// Applications
function loadApplications() {
    const applicationsList = $("#applicationsList");
    applicationsList.html("<p>Loading applications...</p>");

    $.ajax({
        url: "/Secure-Online-Job-Portal-System/applications/my-job-applications",
        method: "GET",
        headers: { "Authorization": "Bearer " + authToken },
        success: function(applications) {
            applicationsList.html("");
            if (!applications || applications.length === 0) {
                applicationsList.html("<p>No applications received yet.</p>");
                return;
            }
            applications.forEach(app => {
                // Extract the correct values from the nested objects
                const applicantName = `${app.user.firstName} ${app.user.lastName}`;
                const jobTitle = app.job.title;
                const applicationId = app.application_id;
                const applicationDate = app.applicationDate || 'N/A';
                
                const appCard = `
                    <div class="application-card">
                        <div class="applicant-name">${escapeHtml(applicantName)}</div>
                        <p><strong>Job:</strong> ${escapeHtml(jobTitle)}</p>
                        <p><strong>Applied:</strong> ${formatApplicationDate(applicationDate)}</p>
                        <p><strong>Status:</strong> <span class="application-status status-${app.status.toLowerCase()}">${app.status}</span></p>
                        <div class="job-actions">
                        ${app.status.toLowerCase() === 'pending' ? `
                        	    <button class="btn btn-success btn-small" onclick="updateApplicationStatus(${applicationId}, 'accepted')">Accept</button>
                        	    <button class="btn btn-danger btn-small" onclick="updateApplicationStatus(${applicationId}, 'rejected')">Reject</button>
                        	` : ''}
                        </div>
                    </div>`;
                applicationsList.append(appCard);
            });
        },
        error: function() {
            applicationsList.html("<p>Error loading applications. Please try again.</p>");
        }
    });
}

//History 
function loadHistory() {
    const jobsList = $("#historylist");
    jobsList.html("<p>Loading jobs...</p>");

    $.ajax({
        url: "/Secure-Online-Job-Portal-System/jobs/history",
        method: "GET",
        headers: { "Authorization": "Bearer " + authToken },
        success: function(jobs) {
            jobsList.html("");
            if (!jobs || jobs.length === 0) {
                jobsList.html("<p>No jobs posted yet. </p>");
                return;
            }
            jobs.forEach(job => {
                const salaryRange = job.min_salary && job.max_salary
                    ? `₹${job.min_salary} - ₹${job.max_salary}`
                    : job.min_salary ? `From ₹${job.min_salary}`
                    : job.max_salary ? `Up to ₹${job.max_salary}`
                    : 'Salary negotiable';
                        
                const deadline = job.deadline ? new Date(job.deadline).toLocaleDateString() : "No deadline";
                
                const jobCard = `
                    <div class="job-card">
                        <div class="job-title">${escapeHtml(job.title)}</div>
                        <div class="job-details">
                            <div class="job-detail">📍 ${escapeHtml(job.location)}</div>
                            <div class="job-detail">💰 ${salaryRange}</div>
                            <div class="job-detail">⏰ Deadline: ${deadline}</div>
                        </div>
                        <p>${escapeHtml(job.description)}</p>
                    </div>`;
                jobsList.append(jobCard);
            });
        },
        error: function() {
            jobsList.html("<p>Error loading jobs. Please try again.</p>");
        }
    });
}


// hisyory end


function escapeHtml(text) {
    const map = { '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#039;' };
    return text.replace(/[&<>"']/g, m => map[m]);
}

function formatApplicationDate(dateString) {
    if (!dateString) return 'N/A';
    try { return new Date(dateString).toLocaleDateString(); }
    catch { return 'Invalid date'; }
}

function viewApplications(jobId) { alert(`Viewing applications for job ID: ${jobId}`); }
function editJob(jobId) { alert(`Editing job ID: ${jobId}`); }

function deleteJob(jobId) {
    if (confirm('Are you sure you want to delete this job?')) {
        $.ajax({
            url: `/Secure-Online-Job-Portal-System/jobs/${jobId}`,
            method: "DELETE",
            headers: { "Authorization": "Bearer " + authToken },
            success: function() {
                alert('Job deleted successfully!');
                loadMyJobs();
                loadDashboardData();
            },
            error: function() {
                alert('Error: Failed to delete job');
            }
        });
    }
}

function viewApplication(applicationId) { alert(`Viewing application ID: ${applicationId}`); }

function updateApplicationStatus(applicationId, status) {
    if (!confirm(`Are you sure you want to ${status} this application?`)) {
        return;
    }
    
    $.ajax({
        url: `/Secure-Online-Job-Portal-System/applications/${applicationId}/status?status=${status}`,
        method: "PUT",
        headers: { 
            "Authorization": "Bearer " + authToken,
            "Content-Type": "application/json"
        },
        success: function(response) {
            alert(response.message || "Application status updated successfully!");
            
            // Refresh the applications list to show updated status
            loadApplications();
        },
        error: function(xhr) {
            let errorMessage = "Error updating application status.";
            if (xhr.responseText) {
                try {
                    const errorResponse = JSON.parse(xhr.responseText);
                    errorMessage = errorResponse.message || errorResponse;
                } catch (e) {
                    errorMessage = xhr.responseText;
                }
            }
            alert(errorMessage);
        }
    });
}

function logout() {
    if (confirm('Are you sure you want to logout?')) {
        document.cookie = 'token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
        window.location.href = '/Secure-Online-Job-Portal-System';
    }
}
</script>


</body>
</html>