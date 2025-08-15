<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Job Portal - Change Password</title>
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
            font-family: 'Arial', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 0;
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

        /* Mobile Menu */
        .mobile-menu-btn {
            display: none;
            background: none;
            border: none;
            font-size: 24px;
            color: var(--text-color);
            cursor: pointer;
        }

        .forgot-container {
            background: white;
            border-radius: 20px;
            box-shadow: 0 25px 50px rgba(0, 0, 0, 0.15);
            padding: 50px;
            width: 100%;
            max-width: 450px;
            margin: 20px auto;
            position: relative;
            overflow: hidden;
        }

        .forgot-container::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 5px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }

        .header {
            text-align: center;
            margin-bottom: 40px;
        }

        .header h1 {
            color: #333;
            font-size: 32px;
            font-weight: 700;
            margin-bottom: 10px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
        }

        .header p {
            color: #666;
            font-size: 16px;
            font-weight: 400;
            line-height: 1.5;
        }

        .form-group {
            margin-bottom: 25px;
            position: relative;
        }

        .form-group label {
            display: block;
            margin-bottom: 10px;
            color: #333;
            font-weight: 600;
            font-size: 14px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .form-control {
            width: 100%;
            padding: 16px 20px;
            border: 2px solid #e1e5e9;
            border-radius: 12px;
            font-size: 16px;
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
            background-color: #f8f9ff;
            position: relative;
        }

        .form-control:focus {
            outline: none;
            border-color: #667eea;
            background-color: white;
            box-shadow: 0 0 0 4px rgba(102, 126, 234, 0.1);
            transform: translateY(-2px);
        }

        .form-control:hover {
            border-color: #c1c8d3;
            transform: translateY(-1px);
        }

        select.form-control {
            cursor: pointer;
        }

        .forgot-btn {
            width: 100%;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            padding: 18px 30px;
            border-radius: 12px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
            margin-top: 20px;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .forgot-btn:hover {
            transform: translateY(-3px);
            box-shadow: 0 15px 35px rgba(102, 126, 234, 0.4);
        }

        .forgot-btn:active {
            transform: translateY(-1px);
        }

        .forgot-btn:disabled {
            opacity: 0.7;
            cursor: not-allowed;
            transform: none;
        }

        .back-to-login {
            text-align: center;
            margin-top: 25px;
        }

        .back-to-login a {
            color: #667eea;
            text-decoration: none;
            font-size: 14px;
            font-weight: 500;
            transition: color 0.3s ease;
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }

        .back-to-login a:hover {
            color: #764ba2;
            text-decoration: underline;
        }

        .register-link {
            text-align: center;
            margin-top: 30px;
            padding-top: 25px;
            border-top: 1px solid #e1e5e9;
            color: #666;
        }

        .register-link a {
            color: #667eea;
            text-decoration: none;
            font-weight: 600;
            transition: color 0.3s ease;
        }

        .register-link a:hover {
            color: #764ba2;
            text-decoration: underline;
        }

        .error {
            color: #e74c3c;
            font-size: 13px;
            margin-top: 8px;
            display: none;
            padding: 8px;
            background-color: #fdf2f2;
            border-radius: 6px;
            border: 1px solid #fecaca;
        }

        .success {
            color: #27ae60;
            font-size: 13px;
            margin-top: 8px;
            display: none;
            padding: 8px;
            background-color: #f0f9f4;
            border-radius: 6px;
            border: 1px solid #a7f3d0;
        }

        .required {
            color: #e74c3c;
        }

        .loading {
            display: none;
            text-align: center;
            margin-top: 20px;
        }

        .loading::after {
            content: '';
            display: inline-block;
            width: 20px;
            height: 20px;
            border: 2px solid #667eea;
            border-radius: 50%;
            border-top-color: transparent;
            animation: spin 1s linear infinite;
        }

        @keyframes spin {
            to {
                transform: rotate(360deg);
            }
        }

        .form-control.error-input {
            border-color: #e74c3c;
            background-color: #fdf2f2;
        }

        .form-control.success-input {
            border-color: #27ae60;
            background-color: #f0f9f4;
        }

        /* Icon styles */
        .input-icon {
            position: absolute;
            left: 15px;
            top: 50%;
            transform: translateY(-50%);
            color: #666;
            font-size: 16px;
            z-index: 1;
        }

        .form-control.with-icon {
            padding-left: 50px;
        }

        /* Password toggle styles */
        .password-toggle {
            position: absolute;
            right: 15px;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
            color: #666;
            font-size: 18px;
            transition: all 0.3s ease;
            z-index: 2;
            user-select: none;
            padding: 5px;
            border-radius: 4px;
        }

        .password-toggle:hover {
            color: #667eea;
            background-color: rgba(102, 126, 234, 0.1);
        }

        .password-toggle:active {
            transform: translateY(-50%) scale(0.95);
        }

        /* Adjust input padding when both icon and toggle are present */
        .form-control.with-icon.with-password-toggle {
            padding-left: 50px;
            padding-right: 50px;
        }

        /* For inputs that only have password toggle (no left icon) */
        .form-control.with-password-toggle {
            padding-right: 50px;
        }

        /* Animation for smooth toggle */
        @keyframes togglePulse {
            0% { transform: translateY(-50%) scale(1); }
            50% { transform: translateY(-50%) scale(1.1); }
            100% { transform: translateY(-50%) scale(1); }
        }

        .password-toggle.animate {
            animation: togglePulse 0.3s ease;
        }

        .role-icon {
            display: inline-block;
            margin-right: 8px;
            font-size: 14px;
        }

        /* Step indicator */
        .step-indicator {
            display: flex;
            justify-content: center;
            margin-bottom: 30px;
            position: relative;
        }

        .step {
            display: flex;
            flex-direction: column;
            align-items: center;
            position: relative;
            z-index: 1;
        }

        .step-number {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background: #e1e5e9;
            color: #666;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: 600;
            font-size: 14px;
            margin-bottom: 8px;
        }

        .step.active .step-number {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        .step.completed .step-number {
            background: #27ae60;
            color: white;
        }

        .step-text {
            font-size: 12px;
            color: #666;
            text-align: center;
        }

        .step.active .step-text {
            color: #667eea;
            font-weight: 600;
        }

        .step-line {
            position: absolute;
            top: 20px;
            left: 50%;
            width: 100px;
            height: 2px;
            background: #e1e5e9;
            transform: translateX(-50%);
            z-index: 0;
        }

        .step.completed .step-line {
            background: #27ae60;
        }

        /* Info box */
        .info-box {
            background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%);
            border: 1px solid rgba(102, 126, 234, 0.2);
            border-radius: 12px;
            padding: 20px;
            margin-bottom: 25px;
        }

        .info-box .info-icon {
            color: #667eea;
            font-size: 20px;
            margin-bottom: 10px;
        }

        .info-box h3 {
            color: #333;
            font-size: 16px;
            font-weight: 600;
            margin-bottom: 8px;
        }

        .info-box p {
            color: #666;
            font-size: 14px;
            line-height: 1.6;
            margin: 0;
        }

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

            .forgot-container {
                padding: 30px 25px;
                margin: 10px;
                border-radius: 15px;
            }

            .header h1 {
                font-size: 28px;
            }

            .form-control {
                padding: 14px 16px;
                font-size: 14px;
            }

            .form-control.with-icon.with-password-toggle {
                padding-left: 45px;
                padding-right: 45px;
            }

            .password-toggle {
                font-size: 16px;
                padding: 8px;
            }

            .forgot-btn {
                padding: 16px 25px;
                font-size: 14px;
            }

            .step-line {
                width: 60px;
            }
        }
    </style>
</head>
<body>
    <div class="forgot-container">
        <div class="header">
            <h1>Change Password</h1>
            <p>Enter your current password and create a new one</p>
        </div>

        <div class="info-box">
            <div class="info-icon">🔐</div>
            <h3>Secure Password Change</h3>
            <p>For your security, please verify your current password before setting a new one. Your new password must be at least 6 characters long.</p>
        </div>

        <form id="forgotForm" action="processChangePassword.jsp" method="POST">
            <div class="form-group">
                <label for="email">Email Address <span class="required">*</span></label>
                <div style="position: relative;">
                    <span class="input-icon">📧</span>
                    <input type="email" id="email" name="email" class="form-control with-icon" 
                           placeholder="Enter your email address" required autocomplete="email">
                </div>
                <div class="error" id="emailError">Please enter a valid email address</div>
            </div>

            <div class="form-group">
                <label for="userRole">Account Type <span class="required">*</span></label>
                <select id="userRole" name="userRole" class="form-control" required>
                    <option value="">Select your account type...</option>
                    <option value="candidate"><span class="role-icon">👤</span>Job Seeker (Candidate)</option>
                    <option value="employer"><span class="role-icon">🏢</span>Employer</option>
                </select>
                <div class="error" id="roleError">Please select your account type</div>
            </div>

            <div class="form-group">
                <label for="oldPassword">Current Password <span class="required">*</span></label>
                <div style="position: relative;">
                    <span class="input-icon">🔒</span>
                    <input type="password" id="oldPassword" name="oldPassword" 
                           class="form-control with-icon with-password-toggle" 
                           placeholder="Enter your current password" required autocomplete="current-password">
                    <span class="password-toggle" onclick="togglePassword('oldPassword')">👁️</span>
                </div>
                <div class="error" id="oldPasswordError">Current password is required</div>
            </div>

            <div class="form-group">
                <label for="newPassword">New Password <span class="required">*</span></label>
                <div style="position: relative;">
                    <span class="input-icon">🔑</span>
                    <input type="password" id="newPassword" name="newPassword" 
                           class="form-control with-icon with-password-toggle" 
                           placeholder="Enter new password" required autocomplete="new-password">
                    <span class="password-toggle" onclick="togglePassword('newPassword')">👁️</span>
                </div>
                <div class="error" id="newPasswordError">Password must be at least 6 characters</div>
            </div>

            <div class="form-group">
                <label for="confirmPassword">Confirm New Password <span class="required">*</span></label>
                <div style="position: relative;">
                    <span class="input-icon">🔐</span>
                    <input type="password" id="confirmPassword" name="confirmPassword" 
                           class="form-control with-icon with-password-toggle" 
                           placeholder="Confirm new password" required autocomplete="new-password">
                    <span class="password-toggle" onclick="togglePassword('confirmPassword')">👁️</span>
                </div>
                <div class="error" id="confirmPasswordError">Passwords do not match</div>
            </div>

            <div class="loading" id="loading">
                Changing password...
            </div>

            <button type="submit" class="forgot-btn" id="forgotButton">
                Change Password
            </button>

            <div class="success" id="resetSuccess">
                Password changed successfully! Redirecting to login...
            </div>

            <div class="error" id="resetError">
                Failed to change password. Please check your current password and try again.
            </div>
        </form>

        <div class="back-to-login">
            <a href="/Secure-Online-Job-Portal-System/login">
                <i class="fas fa-arrow-left"></i> Back to Login
            </a>
        </div>

        <div class="register-link">
            Don't have an account? <a href="/Secure-Online-Job-Portal-System/register">Create one here</a>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function(){
            // Enhanced toggle password function with animation
            function togglePassword(fieldId) {
                let pw = $('#' + fieldId);
                let toggle = pw.siblings('.password-toggle');
                
                // Add animation class
                toggle.addClass('animate');
                
                if(pw.attr('type') === 'password'){
                    pw.attr('type', 'text');
                    toggle.text('🙈').css('color', '#667eea');
                } else {
                    pw.attr('type', 'password');
                    toggle.text('👁️').css('color', '#666');
                }
                
                // Remove animation class after animation completes
                setTimeout(() => {
                    toggle.removeClass('animate');
                }, 300);
            }

            // Make togglePassword global
            window.togglePassword = togglePassword;

            // Real-time validation clears errors
            $('input, select').on('input change', function(){
                $(this).removeClass('error-input success-input');
                $('#' + this.id + 'Error').hide();
                
                // Real-time password confirmation check
                if(this.id === 'confirmPassword' || this.id === 'newPassword') {
                    checkPasswordMatch();
                }
            });

            // Check password match in real-time
            function checkPasswordMatch() {
                let newPassword = $('#newPassword').val();
                let confirmPassword = $('#confirmPassword').val();
                
                if(confirmPassword && newPassword !== confirmPassword) {
                    $('#confirmPassword').addClass('error-input');
                    $('#confirmPasswordError').text('Passwords do not match').show();
                } else if(confirmPassword && newPassword === confirmPassword) {
                    $('#confirmPassword').removeClass('error-input').addClass('success-input');
                    $('#confirmPasswordError').hide();
                }
            }

            // Main form submission
            $('#forgotForm').on('submit', function(e){
                e.preventDefault();

                let email = $('#email').val().trim();
                let role = $('#userRole').val();
                let oldPassword = $('#oldPassword').val();
                let newPassword = $('#newPassword').val();
                let confirmPassword = $('#confirmPassword').val();

                // Validation
                let valid = true;
                $('.error').hide();
                $('.form-control').removeClass('error-input success-input');

                // Email validation
                if(!email){
                    $('#emailError').text('Email is required').show();
                    $('#email').addClass('error-input');
                    valid = false;
                } else if(!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)){
                    $('#emailError').text('Enter a valid email').show();
                    $('#email').addClass('error-input');
                    valid = false;
                } else {
                    $('#email').addClass('success-input');
                }

                // Role validation
                if(!role){
                    $('#roleError').text('Please select your account type').show();
                    $('#userRole').addClass('error-input');
                    valid = false;
                } else {
                    $('#userRole').addClass('success-input');
                }

                // Current password validation
                if(!oldPassword){
                    $('#oldPasswordError').text('Current password is required').show();
                    $('#oldPassword').addClass('error-input');
                    valid = false;
                } else if(oldPassword.length < 6){
                    $('#oldPasswordError').text('Current password must be at least 6 characters').show();
                    $('#oldPassword').addClass('error-input');
                    valid = false;
                } else {
                    $('#oldPassword').addClass('success-input');
                }

                // New password validation
                if(!newPassword){
                    $('#newPasswordError').text('New password is required').show();
                    $('#newPassword').addClass('error-input');
                    valid = false;
                } else if(newPassword.length < 6){
                    $('#newPasswordError').text('New password must be at least 6 characters').show();
                    $('#newPassword').addClass('error-input');
                    valid = false;
                } else if(newPassword === oldPassword){
                    $('#newPasswordError').text('New password must be different from current password').show();
                    $('#newPassword').addClass('error-input');
                    valid = false;
                } else {
                    $('#newPassword').addClass('success-input');
                }

                // Confirm password validation
                if(!confirmPassword){
                    $('#confirmPasswordError').text('Please confirm your new password').show();
                    $('#confirmPassword').addClass('error-input');
                    valid = false;
                } else if(newPassword !== confirmPassword){
                    $('#confirmPasswordError').text('Passwords do not match').show();
                    $('#confirmPassword').addClass('error-input');
                    valid = false;
                } else {
                    $('#confirmPassword').addClass('success-input');
                }

                if(!valid) return;

                $('#forgotButton').attr('disabled', true).text('Changing Password...');
                $('#loading').show();

                // AJAX call to change password
                $.ajax({
                    url: '/Secure-Online-Job-Portal-System/users/change-password',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        email: email, 
                        role: role,
                        oldPassword: oldPassword, 
                        newPassword: newPassword
                    }),
                    dataType: 'json',
                    success: function(data){
                        $('#loading').hide();
                        if(data.status === 'success'){
                            $('#resetSuccess').show();
                            setTimeout(() => {
                                window.location.href = '/Secure-Online-Job-Portal-System/login';
                            }, 2500);
                        } else {
                            $('#resetError').text(data.message || 'Failed to change password').show();
                            $('#forgotButton').attr('disabled', false).text('Change Password');
                        }
                    },
                    error: function(xhr, status, error) {
                        $('#loading').hide();
                        let errMsg = 'Failed to change password. Please check your current password.';
                        if (xhr.responseJSON && xhr.responseJSON.message) {
                            errMsg = xhr.responseJSON.message;
                        } else if (xhr.status === 401) {
                            errMsg = 'Current password is incorrect.';
                        } else if (xhr.status === 404) {
                            errMsg = 'Account not found with this email and role.';
                        }
                        $('#resetError').text(errMsg).show();
                        $('#forgotButton').attr('disabled', false).text('Change Password');
                    }
                });
            });

            // Form field auto-focus
            $('#email').focus();
        });
    </script>
</body>
</html>
