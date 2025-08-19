<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Job Portal - User Registration</title>
  <style>
    * { margin:0; padding:0; box-sizing:border-box; }
    body {
      font-family: 'Arial', sans-serif;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      min-height: 100vh;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .registration-container {
      background: #fff; border-radius: 15px; box-shadow: 0 20px 40px rgba(0,0,0,.1);
      padding: 40px; width: 100%; max-width: 600px; margin: 20px;
    }
    .header { text-align:center; margin-bottom:30px; }
    .header h1 { color:#333; font-size:28px; font-weight:600; margin-bottom:10px; }
    .header p { color:#666; font-size:16px; }
    .form-group { margin-bottom:25px; }
    .form-group label { display:block; margin-bottom:8px; color:#333; font-weight:500; font-size:14px; }
    .form-control {
      width:100%; padding:12px 15px; border:2px solid #e1e1e1; border-radius:8px; font-size:14px;
      transition: all .3s ease; background-color:#f9f9f9;
    }
    .form-control:focus { outline:none; border-color:#667eea; background:#fff; box-shadow:0 0 0 3px rgba(102,126,234,.1); }
    .form-control:hover { border-color:#c1c1c1; }
    select.form-control { cursor:pointer; }
    textarea.form-control { resize:vertical; min-height:100px; }
    .form-row { display:flex; gap:20px; }
    .form-row .form-group { flex:1; }
    .role-specific-section {
      background:#f8f9ff; border-radius:12px; padding:25px; margin-top:20px; border-left:4px solid #667eea;
      display:none; animation: slideDown .4s ease-out;
    }
    .role-specific-section.show { display:block; }
    .section-title {
      color:#667eea; font-size:18px; font-weight:600; margin-bottom:20px; display:flex; align-items:center;
    }
    .section-title:before { content:''; display:inline-block; width:4px; height:20px; background:#667eea; margin-right:10px; border-radius:2px; }
    .submit-btn {
      width:100%; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color:#fff; border:none; padding:15px 30px;
      border-radius:10px; font-size:16px; font-weight:600; cursor:pointer; transition: all .3s ease; margin-top:20px;
      position:relative; display:flex; align-items:center; justify-content:center;
    }
    .submit-btn:hover:not(:disabled) { transform: translateY(-2px); box-shadow:0 10px 25px rgba(102,126,234,.3); }
    .submit-btn:active { transform: translateY(0); }
    .submit-btn:disabled { opacity:.7; cursor:not-allowed; transform:none; }
    .loading-spinner {
      display:none; width:20px; height:20px; border:2px solid #fff; border-top:2px solid transparent; border-radius:50%;
      animation: spin 1s linear infinite; margin-right:10px;
    }
    @keyframes spin { 0%{transform:rotate(0)} 100%{transform:rotate(360deg)} }
    .login-link { text-align:center; margin-top:25px; color:#666; }
    .login-link a { color:#667eea; text-decoration:none; font-weight:500; }
    .login-link a:hover { text-decoration:underline; }
    @keyframes slideDown { from{opacity:0; transform: translateY(-20px)} to{opacity:1; transform: translateY(0)} }
    .error { color:#e74c3c; font-size:12px; margin-top:5px; display:none; }
    .required { color:#e74c3c; }
    .alert {
      padding:15px; margin-bottom:20px; border-radius:8px; display:none; animation: slideDown .3s ease-out; position:relative;
    }
    .alert.success { background:#d4edda; border:1px solid #c3e6cb; color:#155724; }
    .alert.error { background:#f8d7da; border:1px solid #f5c6cb; color:#721c24; }
    .alert.warning { background:#fff3cd; border:1px solid #ffeaa7; color:#856404; }
    .alert-close { position:absolute; right:10px; top:10px; cursor:pointer; font-size:18px; line-height:1; color:inherit; opacity:.7; }
    .alert-close:hover { opacity:1; }
    @media (max-width:768px){
      .registration-container { padding:20px; margin:10px; }
      .form-row { flex-direction:column; gap:0; }
      .header h1 { font-size:24px; }
    }
  </style>
</head>
<body>
  <div class="registration-container">
    <div class="header">
      <h1>Create Your Account</h1>
      <p>Join our job portal and start your career journey</p>
    </div>

    <div id="alertMessage" class="alert">
      <span class="alert-close" onclick="closeAlert()">&times;</span>
      <div id="alertText"></div>
    </div>

    <form id="registrationForm" novalidate>
      <div class="form-row">
        <div class="form-group">
          <label for="firstName">First Name <span class="required">*</span></label>
          <input type="text" id="firstName" name="firstName" class="form-control" required />
          <div class="error" id="firstNameError">First name is required</div>
        </div>
        <div class="form-group">
          <label for="lastName">Last Name <span class="required">*</span></label>
          <input type="text" id="lastName" name="lastName" class="form-control" required />
          <div class="error" id="lastNameError">Last name is required</div>
        </div>
      </div>

      <div class="form-row">
        <div class="form-group">
          <label for="email">Email Address <span class="required">*</span></label>
          <input type="email" id="email" name="email" class="form-control" required />
          <div class="error" id="emailError">Valid email is required</div>
        </div>
        <div class="form-group">
          <label for="phone">Phone Number <span class="required">*</span></label>
          <input type="tel" id="phone" name="phone" class="form-control" required />
          <div class="error" id="phoneError">Phone number is required</div>
        </div>
      </div>

      <div class="form-row">
        <div class="form-group">
          <label for="password">Password <span class="required">*</span></label>
          <input type="password" id="password" name="password" class="form-control" required minlength="8" />
          <div class="error" id="passwordError">Password must be at least 8 characters</div>
        </div>
        <div class="form-group">
          <label for="confirmPassword">Confirm Password <span class="required">*</span></label>
          <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required />
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

      <div id="candidateSection" class="role-specific-section">
        <div class="section-title">Personal Information & Skills</div>

        <div class="form-group">
          <label for="currentLocation">Current Location <span class="required">*</span></label>
          <input type="text" id="currentLocation" name="currentLocation" class="form-control" placeholder="City, State, Country" />
          <div class="error" id="locationError">Current location is required</div>
        </div>

        <div class="form-group">
          <label for="skills">Skills <span class="required">*</span></label>
          <textarea id="skills" name="skills" class="form-control" placeholder="List your key skills (e.g., Java, Python, etc.)" rows="4"></textarea>
          <div class="error" id="skillsError">Please list your skills</div>
        </div>

        <div class="form-group">
          <label for="experience">Years of Experience</label>
          <select id="experience" name="experience" class="form-control">
            <option value="">Select experience level</option>
            <option value="JUNIOR">Junior (0-5 years)</option>
            <option value="SENIOR">Senior (5-10 years)</option>
            <option value="LEAD">Lead (10+ years)</option>
          </select>
        </div>
      </div>

      <div id="employerSection" class="role-specific-section">
        <div class="section-title">Company Details</div>

        <div class="form-group">
          <label for="companyName">Company Name <span class="required">*</span></label>
          <input type="text" id="companyName" name="companyName" class="form-control" placeholder="Enter your company name" />
          <div class="error" id="companyNameError">Company name is required</div>
        </div>

        <div class="form-group">
          <label for="companyDescription">Company Description <span class="required">*</span></label>
          <textarea id="companyDescription" name="companyDescription" class="form-control" placeholder="Describe your company, industry, and what you do..." rows="4"></textarea>
          <div class="error" id="companyDescError">Company description is required</div>
        </div>

        <div class="form-group">
          <label for="currentProfile">Current Profile <span class="required">*</span></label>
          <textarea id="currentProfile" name="currentProfile" class="form-control" placeholder="Describe your current professional profile and role..." rows="4"></textarea>
          <div class="error" id="currentProfileError">Current profile is required</div>
        </div>
      </div>

      <button type="submit" class="submit-btn" id="submitBtn">
        <div class="loading-spinner" id="loadingSpinner"></div>
        <span id="submitText">Create Account</span>
      </button>
    </form>

    <div class="login-link">
      Already have an account? <a href="/Secure-Online-Job-Portal-System/login">Sign in here</a>
    </div>
  </div>

  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <script>
    // Server configuration
    const API_BASE_URL = 'http://localhost:8080/Secure-Online-Job-Portal-System';
    const REGISTRATION_ENDPOINT = API_BASE_URL + '/auth/register'; // Your Spring MVC POST endpoint

    function showRoleSpecificSection() {
      const role = document.getElementById('userRole').value;
      const candidateSection = document.getElementById('candidateSection');
      const employerSection = document.getElementById('employerSection');

      candidateSection.classList.remove('show');
      employerSection.classList.remove('show');

      setRequiredFields(null); // clear all role-required first

      if (role === 'candidate') {
        candidateSection.classList.add('show');
        setRequiredFields('candidate');
      } else if (role === 'employer') {
        employerSection.classList.add('show');
        setRequiredFields('employer');
      }
    }

    function setRequiredFields(role) {
      const candidateFields = ['currentLocation', 'skills'];
      const employerFields = ['companyName', 'companyDescription', 'currentProfile'];

      // clear
      candidateFields.forEach(id => { const el = document.getElementById(id); if (el) el.removeAttribute('required'); });
      employerFields.forEach(id => { const el = document.getElementById(id); if (el) el.removeAttribute('required'); });

      if (role === 'candidate') {
        candidateFields.forEach(id => { const el = document.getElementById(id); if (el) el.setAttribute('required',''); });
      } else if (role === 'employer') {
        employerFields.forEach(id => { const el = document.getElementById(id); if (el) el.setAttribute('required',''); });
      }
    }

    function showAlert(message, type, autoClose = true) {
      const alertDiv = document.getElementById('alertMessage');
      const alertText = document.getElementById('alertText');
      alertText.textContent = message;
      alertDiv.className = 'alert ' + type;
      alertDiv.style.display = 'block';
      alertDiv.scrollIntoView({ behavior: 'smooth', block: 'center' });
      if (autoClose && type !== 'error') {
        setTimeout(() => { alertDiv.style.display = 'none'; }, 5000);
      }
    }
    function closeAlert() { document.getElementById('alertMessage').style.display = 'none'; }

    function setLoadingState(loading) {
      const btn = document.getElementById('submitBtn');
      const spinner = document.getElementById('loadingSpinner');
      const text = document.getElementById('submitText');
      if (loading) {
        btn.disabled = true; spinner.style.display = 'inline-block'; text.textContent = 'Creating Account...';
      } else {
        btn.disabled = false; spinner.style.display = 'none'; text.textContent = 'Create Account';
      }
    }

    function validateForm() {
      let isValid = true;

      // Hide old errors
      document.querySelectorAll('.error').forEach(e => e.style.display = 'none');

      // Check required fields
      document.querySelectorAll('input[required], select[required], textarea[required]').forEach(field => {
        if (!field.value || !field.value.trim()) {
          const err = document.getElementById(field.id + 'Error');
          if (err) err.style.display = 'block';
          field.style.borderColor = '#e74c3c';
          isValid = false;
        } else {
          field.style.borderColor = '#e1e1e1';
        }
      });

      // Password rules
      const password = document.getElementById('password').value;
      const confirmPassword = document.getElementById('confirmPassword').value;
      if (password.length < 8) {
        document.getElementById('passwordError').style.display = 'block';
        document.getElementById('password').style.borderColor = '#e74c3c';
        isValid = false;
      }
      if (password !== confirmPassword) {
        document.getElementById('confirmPasswordError').style.display = 'block';
        document.getElementById('confirmPassword').style.borderColor = '#e74c3c';
        isValid = false;
      }

      // Email format
      const email = document.getElementById('email').value.trim();
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (email && !emailPattern.test(email)) {
        const err = document.getElementById('emailError');
        err.textContent = 'Please enter a valid email address';
        err.style.display = 'block';
        document.getElementById('email').style.borderColor = '#e74c3c';
        isValid = false;
      }

      return isValid;
    }

    function collectFormData() {
      // Do not include confirmPassword; trim values; skip empty optional strings
      const form = document.getElementById('registrationForm');
      const data = {};
      Array.from(form.elements).forEach(el => {
        if (!el.name || el.type === 'submit' || el.name === 'confirmPassword') return;
        const v = (el.value || '').trim();
        if (v !== '') data[el.name] = v;
      });
      return data;
    }

    function buildPayload(formData) {
      

      const role = formData.userRole;
      const payload = {
    		  role: role,
    		  firstName: formData.firstName,
    		  lastName: formData.lastName,
    		  phoneNumber: formData.phone, 
    		  email: formData.email,
    		  password: formData.password
    		};
    		if (role === 'candidate') {
    		  payload.candidateProfile = {      
    		    currentLocation: formData.currentLocation,
    		    skills: formData.skills,
    		    experienceLevel: formData.experience || null 
    		  };
    		} else if (role === 'employer') {
    		  payload.employerProfile = {       
    		    companyName: formData.companyName,
    		    companyDescription: formData.companyDescription,
    		    currentProfile: formData.currentProfile
    		  };
    		}

      return payload;
    }

    function parseSpringErrors(xhr) {
      // Try typical Spring error shapes
      // 1) { message: "...", errors: [ { field, defaultMessage }, ... ] }
      // 2) { errors: [ "msg1", "msg2" ] }
      // 3) { error: "..."} or plain text
      try {
        const json = xhr.responseJSON || JSON.parse(xhr.responseText);
        if (json) {
          if (json.message && Array.isArray(json.errors)) {
            const details = json.errors.map(e => e.defaultMessage || (e.field ? (e.field + ': ' + e.message) : e)).join('; ');
            return json.message + (details ? ' - ' + details : '');
          }
          if (Array.isArray(json.errors)) {
            return json.errors.join('; ');
          }
          if (json.message) return json.message;
          if (json.error) return json.error;
        }
      } catch (e) {
        // fall through to raw text
      }
      return xhr.responseText || 'Registration failed. Please try again.';
    }

    function submitRegistration(payload) {
      $.ajax({
        url: REGISTRATION_ENDPOINT,
        type: 'POST',
        contentType: 'application/json',
        //dataType: 'json',
        data: JSON.stringify(payload),
        success: function (data, textStatus, xhr) {
          showAlert('Registration successful! Redirecting to login page...', 'success');
          // Reset form and UI
          $('#registrationForm')[0].reset();
          $('#candidateSection').removeClass('show');
          $('#employerSection').removeClass('show');

          setTimeout(function () {
            window.location.href = '/Secure-Online-Job-Portal-System/login';
          }, 1500);
        },
        error: function (xhr, status, error) {
        	  // DEBUG: See exactly what's wrong
        	  console.group('AJAX Registration Error Debug');
        	  console.log('HTTP Status Code:', xhr.status);           // Most important - should be 200/201
        	  console.log('jQuery Status Text:', status);             // "parsererror" = JSON parsing failed
        	  console.log('Error Thrown:', error);                    // Details about the error
        	  console.log('Response Content-Type:', xhr.getResponseHeader('Content-Type'));
        	  console.log('Raw Response Body:', xhr.responseText);    // What server actually sent
        	  console.log('All Headers:', xhr.getAllResponseHeaders());
        	  try {
        	    const json = xhr.responseJSON || JSON.parse(xhr.responseText);
        	    console.log('Parsed JSON:', json);
        	  } catch (e) {
        	    console.log('JSON Parse Error:', e.message);          // "Unexpected token" = not JSON
        	  }
        	  console.groupEnd();

        	  // Your existing error handling code
        	  let msg = 'Registration failed. Please try again.';
        	  if (xhr.status === 0) {
        	    msg = 'Unable to connect to server. Please ensure it is running at ' + API_BASE_URL;
        	  } else if (xhr.status === 400) {
        	    msg = parseSpringErrors(xhr) || 'Invalid data provided. Please check your input.';
        	  } else if (xhr.status === 409) {
        	    msg = 'Email or phone number already exists.';
        	  } else if (xhr.status >= 500) {
        	    msg = 'Server error. Please try again later.';
        	  } else {
        	    msg = parseSpringErrors(xhr);
        	  }
        	  showAlert(msg, 'error', false);
        	}
,
        complete: function () { setLoadingState(false); }
      });
    }

    // Event handlers
    $('#registrationForm').on('submit', function (e) {
      e.preventDefault();
      closeAlert();

       if (!validateForm()) {
        showAlert('Please fix all validation errors before submitting', 'error');
        return;
      } 

      setLoadingState(true);

      try {
        const formData = collectFormData();
        const payload = buildPayload(formData);
        submitRegistration(payload);
      } catch (err) {
        console.error(err);
        showAlert('An error occurred while submitting the form', 'error', false);
        setLoadingState(false);
      }
    });

    // Live validations
    $('#confirmPassword').on('input', function () {
      const password = $('#password').val();
      const confirmPassword = $(this).val();
      const errorDiv = $('#confirmPasswordError');
      if (password !== confirmPassword) {
        errorDiv.show(); $(this).css('border-color', '#e74c3c');
      } else {
        errorDiv.hide(); $(this).css('border-color', '#27ae60');
      }
    });

    $('#email').on('blur', function () {
      const email = $(this).val().trim();
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      const errorDiv = $('#emailError');
      if (email && !emailPattern.test(email)) {
        errorDiv.text('Please enter a valid email address').show();
        $(this).css('border-color', '#e74c3c');
      } else if (email) {
        errorDiv.hide(); $(this).css('border-color', '#27ae60');
      } else {
        $(this).css('border-color', '#e1e1e1');
      }
    });

    $('#password').on('input', function () {
      const password = $(this).val();
      const errorDiv = $('#passwordError');
      if (password.length > 0 && password.length < 8) {
        errorDiv.show(); $(this).css('border-color', '#e74c3c');
      } else if (password.length >= 8) {
        errorDiv.hide(); $(this).css('border-color', '#27ae60');
      } else {
        errorDiv.hide(); $(this).css('border-color', '#e1e1e1');
      }
    });

    $('.form-control').on('focus', function () {
      // Reset to focus color if previously in error
      $(this).css('border-color', '#667eea');
    });

    $(document).ready(function () {
      setLoadingState(false);
    });
    
  </script>
</body>
</html>
