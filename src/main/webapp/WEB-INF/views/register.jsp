<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Job Portal - User Registration</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .registration-container {
            background: white;
            border-radius: 15px;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
            padding: 40px;
            width: 100%;
            max-width: 600px;
            margin: 20px;
        }

        .header {
            text-align: center;
            margin-bottom: 30px;
        }

        .header h1 {
            color: #333;
            font-size: 28px;
            font-weight: 600;
            margin-bottom: 10px;
        }

        .header p {
            color: #666;
            font-size: 16px;
        }

        .form-group {
            margin-bottom: 25px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
            font-size: 14px;
        }

        .form-control {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e1e1e1;
            border-radius: 8px;
            font-size: 14px;
            transition: all 0.3s ease;
            background-color: #f9f9f9;
        }

        .form-control:focus {
            outline: none;
            border-color: #667eea;
            background-color: white;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }

        .form-control:hover {
            border-color: #c1c1c1;
        }

        select.form-control {
            cursor: pointer;
        }

        textarea.form-control {
            resize: vertical;
            min-height: 100px;
        }

        .role-specific-section {
            background-color: #f8f9ff;
            border-radius: 12px;
            padding: 25px;
            margin-top: 20px;
            border-left: 4px solid #667eea;
            display: none;
            animation: slideDown 0.4s ease-out;
        }

        .role-specific-section.show {
            display: block;
        }

        .section-title {
            color: #667eea;
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
        }

        .section-title:before {
            content: '';
            display: inline-block;
            width: 4px;
            height: 20px;
            background-color: #667eea;
            margin-right: 10px;
            border-radius: 2px;
        }

        .form-row {
            display: flex;
            gap: 20px;
        }

        .form-row .form-group {
            flex: 1;
        }

        .submit-btn {
            width: 100%;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            padding: 15px 30px;
            border-radius: 10px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            margin-top: 20px;
        }

        .submit-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(102, 126, 234, 0.3);
        }

        .submit-btn:active {
            transform: translateY(0);
        }

        .login-link {
            text-align: center;
            margin-top: 25px;
            color: #666;
        }

        .login-link a {
            color: #667eea;
            text-decoration: none;
            font-weight: 500;
        }

        .login-link a:hover {
            text-decoration: underline;
        }

        @keyframes slideDown {
            from {
                opacity: 0;
                transform: translateY(-20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .error {
            color: #e74c3c;
            font-size: 12px;
            margin-top: 5px;
            display: none;
        }

        .required {
            color: #e74c3c;
        }

        @media (max-width: 768px) {
            .registration-container {
                padding: 20px;
                margin: 10px;
            }

            .form-row {
                flex-direction: column;
                gap: 0;
            }

            .header h1 {
                font-size: 24px;
            }
        }
    </style>
</head>
<body>
    <div class="registration-container">
        <div class="header">
            <h1>Create Your Account</h1>
            <p>Join our job portal and start your career journey</p>
        </div>

        <form id="registrationForm" action="registerUser.jsp" method="POST" enctype="multipart/form-data">
            <!-- Common Fields -->
            <div class="form-row">
                <div class="form-group">
                    <label for="firstName">First Name <span class="required">*</span></label>
                    <input type="text" id="firstName" name="firstName" class="form-control" required>
                    <div class="error" id="firstNameError">First name is required</div>
                </div>
                <div class="form-group">
                    <label for="lastName">Last Name <span class="required">*</span></label>
                    <input type="text" id="lastName" name="lastName" class="form-control" required>
                    <div class="error" id="lastNameError">Last name is required</div>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="email">Email Address <span class="required">*</span></label>
                    <input type="email" id="email" name="email" class="form-control" required>
                    <div class="error" id="emailError">Valid email is required</div>
                </div>
                <div class="form-group">
                    <label for="phone">Phone Number <span class="required">*</span></label>
                    <input type="tel" id="phone" name="phone" class="form-control" required>
                    <div class="error" id="phoneError">Phone number is required</div>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="password">Password <span class="required">*</span></label>
                    <input type="password" id="password" name="password" class="form-control" required minlength="8">
                    <div class="error" id="passwordError">Password must be at least 8 characters</div>
                </div>
                <div class="form-group">
                    <label for="confirmPassword">Confirm Password <span class="required">*</span></label>
                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>
                    <div class="error" id="confirmPasswordError">Passwords must match</div>
                </div>
            </div>

            <div class="form-group">
                <label for="userRole">Select Your Role <span class="required">*</span></label>
                <select id="userRole" name="userRole" class="form-control" required onchange="showRoleSpecificSection()">
                    <option value="">Choose your role...</option>
                    <option value="candidate">Job Seeker (Candidate)</option>
                    <option value="employer">Employer</option>
                </select>
                <div class="error" id="userRoleError">Please select a role</div>
            </div>

            <!-- Candidate Specific Section -->
            <div id="candidateSection" class="role-specific-section">
                <div class="section-title">Personal Information & Skills</div>
                
                <div class="form-group">
                    <label for="currentLocation">Current Location <span class="required">*</span></label>
                    <input type="text" id="currentLocation" name="currentLocation" class="form-control" 
                           placeholder="City, State, Country">
                    <div class="error" id="locationError">Current location is required</div>
                </div>

                <div class="form-group">
                    <label for="skills">Skills <span class="required">*</span></label>
                    <textarea id="skills" name="skills" class="form-control" 
                              placeholder="List your key skills (e.g., Java, Python, Project Management, etc.)" rows="4"></textarea>
                    <div class="error" id="skillsError">Please list your skills</div>
                </div>



                <div class="form-group">
                    <label for="experience">Years of Experience</label>
                    <select id="experience" name="experience" class="form-control">
                        <option value="">Select experience level</option>
                        <option value="0-1">0-1 years (Entry Level)</option>
                        <option value="1-3">1-3 years</option>
                        <option value="3-5">3-5 years</option>
                        <option value="5-10">5-10 years</option>
                        <option value="10+">10+ years (Senior Level)</option>
                    </select>
                </div>
            </div>

            <!-- Employer Specific Section -->
            <div id="employerSection" class="role-specific-section">
                <div class="section-title">Company Details</div>
                
                <div class="form-group">
                    <label for="companyName">Company Name <span class="required">*</span></label>
                    <input type="text" id="companyName" name="companyName" class="form-control" 
                           placeholder="Enter your company name">
                    <div class="error" id="companyNameError">Company name is required</div>
                </div>

                <div class="form-group">
                    <label for="companyDescription">Company Description <span class="required">*</span></label>
                    <textarea id="companyDescription" name="companyDescription" class="form-control" 
                              placeholder="Describe your company, industry, and what you do..." rows="4"></textarea>
                    <div class="error" id="companyDescError">Company description is required</div>
                </div>

                <div class="form-group">
                    <label for="currentProfile">Current Profile <span class="required">*</span></label>
                    <textarea id="currentProfile" name="currentProfile" class="form-control" 
                              placeholder="Describe your current professional profile and role..." rows="4"></textarea>
                    <div class="error" id="currentProfileError">Current profile is required</div>
                </div>
            </div>

            <button type="submit" class="submit-btn">Create Account</button>
        </form>

        <div class="login-link">
            Already have an account? <a href="login.jsp">Sign in here</a>
        </div>
    </div>

    <script>
        function showRoleSpecificSection() {
            const userRole = document.getElementById('userRole').value;
            const candidateSection = document.getElementById('candidateSection');
            const employerSection = document.getElementById('employerSection');
            
            // Hide both sections first
            candidateSection.classList.remove('show');
            employerSection.classList.remove('show');
            
            // Show appropriate section based on role
            if (userRole === 'candidate') {
                candidateSection.classList.add('show');
                setRequiredFields('candidate');
            } else if (userRole === 'employer') {
                employerSection.classList.add('show');
                setRequiredFields('employer');
            }
        }

        function setRequiredFields(role) {
            // Remove all role-specific required attributes first
            const candidateFields = ['currentLocation', 'skills'];
            const employerFields = ['companyName', 'companyDescription', 'currentProfile'];
            
            candidateFields.forEach(field => {
                const element = document.getElementById(field);
                if (element) element.removeAttribute('required');
            });
            
            employerFields.forEach(field => {
                const element = document.getElementById(field);
                if (element) element.removeAttribute('required');
            });
            
            // Add required attributes based on selected role
            if (role === 'candidate') {
                candidateFields.forEach(field => {
                    const element = document.getElementById(field);
                    if (element) element.setAttribute('required', '');
                });
            } else if (role === 'employer') {
                employerFields.forEach(field => {
                    const element = document.getElementById(field);
                    if (element) element.setAttribute('required', '');
                });
            }
        }

        // Form validation
        document.getElementById('registrationForm').addEventListener('submit', function(e) {
            let isValid = true;
            
            // Password confirmation check
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            
            if (password !== confirmPassword) {
                document.getElementById('confirmPasswordError').style.display = 'block';
                isValid = false;
            } else {
                document.getElementById('confirmPasswordError').style.display = 'none';
            }
            
            // Role-specific validation
            const userRole = document.getElementById('userRole').value;
            
            if (!isValid) {
                e.preventDefault();
            }
        });

        // Real-time password confirmation
        document.getElementById('confirmPassword').addEventListener('input', function() {
            const password = document.getElementById('password').value;
            const confirmPassword = this.value;
            const errorDiv = document.getElementById('confirmPasswordError');
            
            if (password !== confirmPassword) {
                errorDiv.style.display = 'block';
                this.style.borderColor = '#e74c3c';
            } else {
                errorDiv.style.display = 'none';
                this.style.borderColor = '#27ae60';
            }
        });
    </script>
</body>
</html>