<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Job Portal - Login</title>
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

        .login-container {
            background: white;
            border-radius: 20px;
            box-shadow: 0 25px 50px rgba(0, 0, 0, 0.15);
            padding: 50px;
            width: 100%;
            max-width: 450px;
            margin: 20px;
            position: relative;
            overflow: hidden;
        }

        .login-container::before {
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

        .password-container {
            position: relative;
        }

        .password-toggle {
            position: absolute;
            right: 15px;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
            color: #666;
            font-size: 18px;
            user-select: none;
            padding: 5px;
        }

        .password-toggle:hover {
            color: #667eea;
        }

        .login-btn {
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

        .login-btn:hover {
            transform: translateY(-3px);
            box-shadow: 0 15px 35px rgba(102, 126, 234, 0.4);
        }

        .login-btn:active {
            transform: translateY(-1px);
        }

        .login-btn:disabled {
            opacity: 0.7;
            cursor: not-allowed;
            transform: none;
        }

        .forgot-password {
            text-align: center;
            margin-top: 25px;
        }

        .forgot-password a {
            color: #667eea;
            text-decoration: none;
            font-size: 14px;
            font-weight: 500;
            transition: color 0.3s ease;
        }

        .forgot-password a:hover {
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

        @media (max-width: 768px) {
            .login-container {
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

            .login-btn {
                padding: 16px 25px;
                font-size: 14px;
            }
        }

        /* Icon styles */
        .input-icon {
            position: absolute;
            left: 15px;
            top: 50%;
            transform: translateY(-50%);
            color: #666;
            font-size: 16px;
        }

        .form-control.with-icon {
            padding-left: 50px;
        }

        .role-icon {
            display: inline-block;
            margin-right: 8px;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="header">
            <h1>Welcome Back</h1>
            <p>Sign in to your account to continue</p>
        </div>

        <form id="loginForm" action="authenticateUser.jsp" method="POST">
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
                <label for="password">Password <span class="required">*</span></label>
                <div class="password-container">
                    <span class="input-icon">🔒</span>
                    <input type="password" id="password" name="password" class="form-control with-icon" 
                           placeholder="Enter your password" required autocomplete="current-password">
                    <span class="password-toggle" onclick="togglePassword()">👁️</span>
                </div>
                <div class="error" id="passwordError">Password is required</div>
            </div>

            <div class="form-group">
                <label for="userRole">Login As <span class="required">*</span></label>
                <select id="userRole" name="userRole" class="form-control" required>
                    <option value="">Select your role...</option>
                    <option value="candidate"><span class="role-icon">👤</span>Job Seeker (Candidate)</option>
                    <option value="employer"><span class="role-icon">🏢</span>Employer</option>
                </select>
                <div class="error" id="roleError">Please select your role</div>
            </div>

            <div class="loading" id="loading">
                Signing you in...
            </div>

            <button type="submit" class="login-btn" id="loginButton">
                Sign In
            </button>

            <div class="success" id="loginSuccess">
                Login successful! Redirecting...
            </div>

            <div class="error" id="loginError">
                Invalid credentials or role mismatch. Please try again.
            </div>
        </form>

        <div class="forgot-password">
            <a href="forgotPassword.jsp">Forgot your password?</a>
        </div>

        <div class="register-link">
            Don't have an account? <a href="registration.jsp">Create one here</a>
        </div>
    </div>

    <script>
        // Toggle password visibility
        function togglePassword() {
            const passwordField = document.getElementById('password');
            const toggleIcon = document.querySelector('.password-toggle');
            
            if (passwordField.type === 'password') {
                passwordField.type = 'text';
                toggleIcon.textContent = '🙈';
            } else {
                passwordField.type = 'password';
                toggleIcon.textContent = '👁️';
            }
        }

        // Form validation
        function validateForm() {
            let isValid = true;
            const email = document.getElementById('email');
            const password = document.getElementById('password');
            const userRole = document.getElementById('userRole');

            // Reset error states
            document.querySelectorAll('.error').forEach(error => error.style.display = 'none');
            document.querySelectorAll('.form-control').forEach(input => {
                input.classList.remove('error-input', 'success-input');
            });

            // Email validation
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!email.value.trim()) {
                showError('emailError', 'Email is required');
                email.classList.add('error-input');
                isValid = false;
            } else if (!emailRegex.test(email.value)) {
                showError('emailError', 'Please enter a valid email address');
                email.classList.add('error-input');
                isValid = false;
            } else {
                email.classList.add('success-input');
            }

            // Password validation
            if (!password.value.trim()) {
                showError('passwordError', 'Password is required');
                password.classList.add('error-input');
                isValid = false;
            } else if (password.value.length < 6) {
                showError('passwordError', 'Password must be at least 6 characters');
                password.classList.add('error-input');
                isValid = false;
            } else {
                password.classList.add('success-input');
            }

            // Role validation
            if (!userRole.value) {
                showError('roleError', 'Please select your role');
                userRole.classList.add('error-input');
                isValid = false;
            } else {
                userRole.classList.add('success-input');
            }

            return isValid;
        }

        function showError(elementId, message) {
            const errorElement = document.getElementById(elementId);
            errorElement.textContent = message;
            errorElement.style.display = 'block';
        }

        // Form submission
        document.getElementById('loginForm').addEventListener('submit', function(e) {
            e.preventDefault();
            
            if (!validateForm()) {
                return;
            }

            const loginButton = document.getElementById('loginButton');
            const loading = document.getElementById('loading');
            const loginError = document.getElementById('loginError');
            const loginSuccess = document.getElementById('loginSuccess');

            // Show loading state
            loginButton.disabled = true;
            loginButton.textContent = 'Signing In...';
            loading.style.display = 'block';
            loginError.style.display = 'none';
            loginSuccess.style.display = 'none';

            // Simulate API call (replace with actual form submission)
            setTimeout(() => {
                // Reset loading state
                loginButton.disabled = false;
                loginButton.textContent = 'Sign In';
                loading.style.display = 'none';

                // For demo purposes - replace with actual authentication
                const email = document.getElementById('email').value;
                const password = document.getElementById('password').value;
                const role = document.getElementById('userRole').value;

                // Demo authentication (replace with actual server-side authentication)
                if (email && password.length >= 6 && role) {
                    loginSuccess.style.display = 'block';
                    
                    // Redirect based on role after 2 seconds
                    setTimeout(() => {
                        if (role === 'employer') {
                            window.location.href = 'employerDashboard.jsp';
                        } else {
                            window.location.href = 'candidateDashboard.jsp';
                        }
                    }, 2000);
                } else {
                    loginError.style.display = 'block';
                }
            }, 1500);
        });

        // Real-time validation
        document.getElementById('email').addEventListener('input', function() {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (this.value && emailRegex.test(this.value)) {
                this.classList.remove('error-input');
                this.classList.add('success-input');
                document.getElementById('emailError').style.display = 'none';
            }
        });

        document.getElementById('password').addEventListener('input', function() {
            if (this.value.length >= 6) {
                this.classList.remove('error-input');
                this.classList.add('success-input');
                document.getElementById('passwordError').style.display = 'none';
            }
        });

        document.getElementById('userRole').addEventListener('change', function() {
            if (this.value) {
                this.classList.remove('error-input');
                this.classList.add('success-input');
                document.getElementById('roleError').style.display = 'none';
            }
        });

        // Auto-focus on email field when page loads
        window.addEventListener('load', function() {
            document.getElementById('email').focus();
        });
    </script>
</body>
</html>